package com.minecraftabnormals.biome_backlog.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.minecraftabnormals.biome_backlog.recipe.BlockPropertyPair;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
 * Thanks Aether!
 * https://github.com/Gilded-Games/The-Aether/blob/1.19/src/main/java/com/gildedgames/aether/util/BlockStateRecipeUtil.java
 */
public class BlockStateRecipeUtil {
	public static void writePair(FriendlyByteBuf buf, BlockPropertyPair pair) {
		if (pair.block().defaultBlockState().isAir() && pair.properties().isEmpty()) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeVarInt(Registry.BLOCK.getId(pair.block()));
			CompoundTag tag = new CompoundTag();
			for (Map.Entry<Property<?>, Comparable<?>> entry : pair.properties().entrySet()) {
				Property<?> property = entry.getKey();
				tag.putString(property.getName(), getName(property, entry.getValue()));
			}
			buf.writeNbt(tag);
		}
	}

	public static void writeBiomeKey(FriendlyByteBuf buf, ResourceKey<Biome> biomeKey) {
		if (biomeKey == null) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeResourceLocation(biomeKey.location());
		}
	}

	public static void writeBiomeTag(FriendlyByteBuf buf, TagKey<Biome> biomeTag) {
		if (biomeTag == null) {
			buf.writeBoolean(false);
		} else {
			buf.writeBoolean(true);
			buf.writeResourceLocation(biomeTag.location());
		}
	}

	public static BlockPropertyPair readPair(FriendlyByteBuf buf) {
		if (!buf.readBoolean()) {
			return BlockPropertyPair.of(Blocks.AIR, new HashMap<>());
		} else {
			int id = buf.readVarInt();
			Block block = Registry.BLOCK.byId(id);

			Map<Property<?>, Comparable<?>> properties = new HashMap<>();
			CompoundTag tag = buf.readNbt();

			if (tag != null) {
				for (String propertyName : tag.getAllKeys()) {
					Property<?> property = block.getStateDefinition().getProperty(propertyName);
					if (property != null) {
						Optional<Comparable<?>> comparable = (Optional<Comparable<?>>) property.getValue(propertyName);
						comparable.ifPresent(value -> properties.put(property, value));
					}
				}
			}

			return BlockPropertyPair.of(block, properties);
		}
	}

	public static BlockPropertyPair pairFromJson(JsonObject json) {
		Block block;
		Map<Property<?>, Comparable<?>> properties = Map.of();
		if (json.has("block")) {
			block = BlockStateRecipeUtil.blockFromJson(json);
			if (json.has("properties")) {
				if (json.get("properties").isJsonObject()) {
					properties = BlockStateRecipeUtil.propertiesFromJson(json, block);
				} else {
					throw new JsonSyntaxException("Expected properties to be object");
				}
			}
		} else {
			throw new JsonSyntaxException("Missing block in result");
		}
		return BlockPropertyPair.of(block, properties);
	}

	public static Block blockFromJson(JsonObject json) {
		String blockName = GsonHelper.getAsString(json, "block");
		Block block = Registry.BLOCK.getOptional(new ResourceLocation(blockName)).orElseThrow(() -> new JsonSyntaxException("Unknown block '" + blockName + "'"));
		if (block.defaultBlockState().isAir()) {
			throw new JsonSyntaxException("Invalid block: " + blockName);
		} else {
			return block;
		}
	}

	public static Map<Property<?>, Comparable<?>> propertiesFromJson(JsonObject json, Block block) {
		Map<Property<?>, Comparable<?>> properties = new HashMap<>();
		StateDefinition<Block, BlockState> stateDefinition = block.getStateDefinition();
		JsonObject propertyObject = GsonHelper.getAsJsonObject(json, "properties");
		for (String propertyName : propertyObject.keySet()) {
			Property<?> property = stateDefinition.getProperty(propertyName);
			String valueName = GsonHelper.getAsString(propertyObject, propertyName);
			if (property != null) {
				Optional<Comparable<?>> comparable = (Optional<Comparable<?>>) property.getValue(valueName);
				comparable.ifPresent(value -> properties.put(property, value));
			}
		}
		return properties;
	}

	public static <T extends Comparable<T>, V extends T> BlockState setHelper(Map.Entry<Property<?>, Comparable<?>> properties, BlockState state) {
		return state.setValue((Property<T>) properties.getKey(), (V) properties.getValue());
	}

	public static <T extends Comparable<T>> String getName(Property<T> pProperty, Comparable<?> pValue) {
		return pProperty.getName((T) pValue);
	}
}