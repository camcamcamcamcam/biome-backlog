package com.camcamcamcamcam.biome_backlog.recipe;

import com.camcamcamcamcam.biome_backlog.utils.BlockStateRecipeUtil;
import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class BlockStateIngredient implements Predicate<BlockState> {
	public static final BlockStateIngredient EMPTY = new BlockStateIngredient(Stream.empty());
	private final Value[] values;
	@Nullable
	private BlockPropertyPair[] pairs;

	public static final Codec<BlockStateIngredient> CODEC = codec(true);
	public static final Codec<BlockStateIngredient> CODEC_NONEMPTY = codec(false);

	protected BlockStateIngredient(Stream<? extends Value> values) {
		this.values = values.toArray(Value[]::new);
	}


	private BlockStateIngredient(Value[] p_301101_) {
		this.values = p_301101_;
	}


	@Override
	public boolean test(BlockState state) {
		if (this.pairs.length != 0) {
			for (BlockPropertyPair pair : this.pairs) {
				if (pair.matches(state)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return this.values.length == 0 && (this.pairs == null || this.pairs.length == 0);
	}

	@Nullable
	public BlockPropertyPair[] getPairs() {
		return this.pairs;
	}

	public JsonElement toJson(boolean p_299391_) {
		Codec<BlockStateIngredient> codec = p_299391_ ? CODEC : CODEC_NONEMPTY;
		return Util.getOrThrow(codec.encodeStart(JsonOps.INSTANCE, this), IllegalStateException::new);
	}

	private static Codec<BlockStateIngredient> codec(boolean p_298496_) {
		Codec<Value[]> codec = Codec.list(Value.CODEC).comapFlatMap((p_296902_) -> {
			return !p_298496_ && p_296902_.size() < 1 ? DataResult.error(() -> {
				return "Item array cannot be empty, at least one item must be defined";
			}) : DataResult.success(p_296902_.toArray(new Value[0]));
		}, List::of);
		return ExtraCodecs.either(codec, Value.CODEC).flatComapMap((p_296900_) -> {
			return p_296900_.map(BlockStateIngredient::new, (p_296903_) -> {
				return new BlockStateIngredient(new Value[]{p_296903_});
			});
		}, (p_296899_) -> {
			if (p_296899_.values.length == 1) {
				return DataResult.success(Either.right(p_296899_.values[0]));
			} else {
				return p_296899_.values.length == 0 && !p_298496_ ? DataResult.error(() -> {
					return "Item array cannot be empty, at least one item must be defined";
				}) : DataResult.success(Either.left(p_296899_.values));
			}
		});
	}

	public static BlockStateIngredient of() {
		return EMPTY;
	}

	public static BlockStateIngredient of(BlockPropertyPair... blockPropertyPairs) {
		return ofBlockPropertyPair(Arrays.stream(blockPropertyPairs));
	}

	public static BlockStateIngredient ofBlockPropertyPair(Stream<BlockPropertyPair> blockPropertyPairs) {
		return fromValues(blockPropertyPairs.filter((pair) -> !pair.block().defaultBlockState().isAir()).map(StateValue::new));
	}

	public static BlockStateIngredient of(Block... blocks) {
		return ofBlock(Arrays.stream(blocks));
	}

	public static BlockStateIngredient ofBlock(Stream<Block> blocks) {
		return fromValues(blocks.filter((block) -> !block.defaultBlockState().isAir()).map(BlockValue::new));
	}

	public static BlockStateIngredient of(TagKey<Block> tag) {
		return fromValues(Stream.of(new TagValue(tag)));
	}

	public final void toNetwork(FriendlyByteBuf buf) {
		buf.writeCollection(Arrays.asList(this.pairs), BlockStateRecipeUtil::writePair);
	}

	public static BlockStateIngredient fromNetwork(FriendlyByteBuf buf) {
		var size = buf.readVarInt();
		return fromValues(Stream.generate(() -> {
			BlockPropertyPair pair = BlockStateRecipeUtil.readPair(buf);
			return new StateValue(pair.block(), pair.properties());
		}).limit(size));
	}

	public JsonElement toJson() {
		if (this.values.length == 1) {
			return this.values[0].serialize();
		} else {
			JsonArray jsonArray = new JsonArray();
			for (Value value : this.values) {
				jsonArray.add(value.serialize());
			}
			return jsonArray;
		}
	}

	public static BlockStateIngredient fromJson(@Nullable JsonElement json) {
		if (json != null && !json.isJsonNull()) {
			if (json.isJsonObject()) {
				return fromValues(Stream.of(valueFromJson(json.getAsJsonObject())));
			} else if (json.isJsonArray()) {
				JsonArray jsonArray = json.getAsJsonArray();
				if (jsonArray.size() == 0) {
					throw new JsonSyntaxException("Block array cannot be empty, at least one item must be defined");
				} else {
					return fromValues(StreamSupport.stream(jsonArray.spliterator(), false).map((p_151264_) -> valueFromJson(GsonHelper.convertToJsonObject(p_151264_, "block"))));
				}
			} else {
				throw new JsonSyntaxException("Expected block to be object or array of objects");
			}
		} else {
			throw new JsonSyntaxException("Block cannot be null");
		}
	}

	public static Value valueFromJson(JsonObject json) {
		if (json.has("block") && json.has("tag")) {
			throw new JsonParseException("An ingredient entry is either a tag or a block, not both");
		} else if (json.has("block")) {
			Block block = BlockStateRecipeUtil.blockFromJson(json);
			if (json.has("properties")) {
				Map<Property<?>, Comparable<?>> properties = BlockStateRecipeUtil.propertiesFromJson(json, block);
				return new StateValue(block, properties);
			} else {
				return new BlockValue(block);
			}
		} else if (json.has("tag")) {
			ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
			TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, resourcelocation);
			return new TagValue(tagKey);
		} else {
			throw new JsonParseException("An ingredient entry needs either a tag or a block");
		}
	}

	public static BlockStateIngredient fromValues(Stream<? extends Value> stream) {
		BlockStateIngredient ingredient = new BlockStateIngredient(stream);
		return ingredient.values.length == 0 ? EMPTY : ingredient;
	}

	public static class StateValue implements Value {
		private final Block block;
		private final Map<Property<?>, Comparable<?>> properties;

		public StateValue(Block block, Map<Property<?>, Comparable<?>> properties) {
			this.block = block;
			this.properties = properties;
		}

		public StateValue(BlockPropertyPair blockPropertyPair) {
			this.block = blockPropertyPair.block();
			this.properties = blockPropertyPair.properties();
		}

		@Override
		public Collection<BlockPropertyPair> getPairs() {
			return Collections.singleton(BlockPropertyPair.of(this.block, this.properties));
		}

		@Override
		public JsonObject serialize() {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(this.block).toString());
			JsonObject jsonObject1 = new JsonObject();
			if (!this.properties.isEmpty()) {
				for (Map.Entry<Property<?>, Comparable<?>> entry : this.properties.entrySet()) {
					Property<?> property = entry.getKey();
					jsonObject1.addProperty(property.getName(), BlockStateRecipeUtil.getName(property, entry.getValue()));
				}
			}
			jsonObject.add("properties", jsonObject1);
			return jsonObject;
		}
	}

	public static class BlockValue implements Value {
		static final Codec<Block> BLOCK_CODEC = ExtraCodecs.validate(BuiltInRegistries.BLOCK.byNameCodec(), (p_300046_) -> {
			return DataResult.success(p_300046_);
		});
		static final Codec<BlockValue> CODEC = RecordCodecBuilder.create((p_300421_) -> {
			return p_300421_.group(BLOCK_CODEC.fieldOf("fluid").forGetter((p_299657_) -> {
				return p_299657_.block;
			})).apply(p_300421_, BlockValue::new);
		});
		private final Block block;

		public BlockValue(Block block) {
			this.block = block;
		}

		@Override
		public Collection<BlockPropertyPair> getPairs() {
			return Collections.singleton(BlockPropertyPair.of(this.block, Map.of()));
		}

		@Override
		public JsonObject serialize() {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("block", BuiltInRegistries.BLOCK.getKey(this.block).toString());
			return jsonObject;
		}
	}

	public static class TagValue implements Value {

		static final Codec<TagValue> CODEC = RecordCodecBuilder.create((p_300241_) -> {
			return p_300241_.group(TagKey.codec(Registries.BLOCK).fieldOf("tag").forGetter((p_301340_) -> {
				return p_301340_.tag;
			})).apply(p_300241_, TagValue::new);
		});
		private final TagKey<Block> tag;

		public TagValue(TagKey<Block> tag) {
			this.tag = tag;
		}

		@Override
		public Collection<BlockPropertyPair> getPairs() {
			List<BlockPropertyPair> list = new ArrayList<>();
			BuiltInRegistries.BLOCK.getTagOrEmpty(this.tag).forEach(holder -> list.add(BlockPropertyPair.of(holder.value(), Map.of())));
			return list;
		}

		@Override
		public JsonObject serialize() {
			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("tag", this.tag.location().toString());
			return jsonobject;
		}
	}

	public interface Value {
		Codec<Value> CODEC = ExtraCodecs.xor(BlockValue.CODEC, TagValue.CODEC).xmap((p_300070_) -> {
			return p_300070_.map((p_301348_) -> {
				return p_301348_;
			}, (p_298354_) -> {
				return p_298354_;
			});
		}, (p_299608_) -> {
			if (p_299608_ instanceof TagValue ingredient$tagvalue) {
				return Either.right(ingredient$tagvalue);
			} else if (p_299608_ instanceof BlockValue ingredient$itemvalue) {
				return Either.left(ingredient$itemvalue);
			} else {
				throw new UnsupportedOperationException("This is neither an item value nor a tag value.");
			}
		});
		Collection<BlockPropertyPair> getPairs();

		JsonObject serialize();
	}
}