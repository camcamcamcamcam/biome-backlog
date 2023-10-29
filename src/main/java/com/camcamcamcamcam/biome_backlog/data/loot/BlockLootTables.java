package com.camcamcamcamcam.biome_backlog.data.loot;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModItems;
import com.camcamcamcamcam.biome_backlog.world.level.block.PearCactusBlock;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BlockLootTables extends BlockLootSubProvider {
	private final Set<Block> knownBlocks = new HashSet<>();
	// [VanillaCopy] super
	protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
	protected static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
	protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
	private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet());

	public BlockLootTables() {
		super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void add(Block block, LootTable.Builder builder) {
		super.add(block, builder);
		knownBlocks.add(block);
	}

	@Override
	protected void generate() {
		this.dropSelf(ModBlocks.PEAR_CACTUS.get());
		LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.STRIPPED_PEAR_CACTUS.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PearCactusBlock.AGE, 2));
		this.add(ModBlocks.STRIPPED_PEAR_CACTUS.get(), createCropDrops(ModBlocks.STRIPPED_PEAR_CACTUS.get(), ModItems.PRICKLY_PEAR.get(), ModItems.CACTUS_PAD.get(), lootitemcondition$builder1));
		this.dropOther(ModBlocks.TUMBLEWEED.get(), ModItems.TUMBLEWEED_SEED.get());

		this.dropSelf(ModBlocks.MOUND.get());
		this.dropSelf(ModBlocks.TERMITE_MOUND.get());
		this.dropWhenSilkTouch(ModBlocks.OSTRICH_EGG.get());
		this.dropWhenSilkTouch(ModBlocks.BAOBAB_LEAVES.get());
		this.dropSelf(ModBlocks.BAOBAB_TRUNK.get());
		this.dropSelf(ModBlocks.BAOBAB_BARK.get());
		this.dropSelf(ModBlocks.BAOBAB_FLOWER.get());
		this.dropOther(ModBlocks.BAOBAB_FRUIT.get(), ModItems.BAOBAB_FRUIT.get());

		this.dropSelf(ModBlocks.BURROW.get());

		this.add(ModBlocks.DATE_BUNCH.get(), (p_176069_) ->
				createSilkTouchDispatchTable(p_176069_, applyExplosionDecay(p_176069_,
						LootItem.lootTableItem(ModItems.DATE.get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))));

		this.add(ModBlocks.COCONUT.get(), (p_176069_) ->
				createSilkTouchDispatchTable(p_176069_, applyExplosionDecay(p_176069_,
				LootItem.lootTableItem(ModItems.COCONUT_HALF.get())
						.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))));

		this.dropSelf(ModBlocks.PALM_LOG.get());
		this.add(ModBlocks.PALM_LEAVES.get(), BlockLootTables::createSilkTouchOrShearDropOrNotTable);
		this.dropSelf(ModBlocks.PALM_LEAVES_HANGING.get());
		this.dropSelf(ModBlocks.THATCH_BLOCK.get());
		this.dropSelf(ModBlocks.THATCH_STAIRS.get());
		this.dropSelf(ModBlocks.STRIPPED_PALM_LOG.get());
		this.dropSelf(ModBlocks.PALM_WOOD.get());
		this.dropSelf(ModBlocks.STRIPPED_PALM_WOOD.get());
		this.dropSelf(ModBlocks.PALM_PLANKS.get());
		this.dropSelf(ModBlocks.COCONUT_SAPLING.get());
		this.dropSelf(ModBlocks.DATE_SAPLING.get());
		this.add(ModBlocks.PALM_DOOR.get(), this::createDoorTable);
		this.dropSelf(ModBlocks.PALM_TRAPDOOR.get());
		this.dropSelf(ModBlocks.PALM_FENCE.get());
		this.dropSelf(ModBlocks.PALM_FENCE_GATE.get());
		this.dropSelf(ModBlocks.PALM_STAIRS.get());
		this.add(ModBlocks.PALM_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(ModBlocks.PALM_BUTTON.get());
		this.dropSelf(ModBlocks.PALM_PRESSURE_PLATE.get());

		this.add(ModBlocks.SALT_BLOCK.get(), (p_176069_) ->
				createSilkTouchDispatchTable(p_176069_, applyExplosionDecay(p_176069_,
						LootItem.lootTableItem(ModBlocks.SALT_TRAIL.get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))));
		this.dropSelf(ModBlocks.SALT_BRICKS.get());
		this.dropSelf(ModBlocks.SALT_TILES.get());
		this.dropSelf(ModBlocks.CHISELED_SALT_BLOCK.get());
		this.add(ModBlocks.SALT_SLAB.get(), this::createSlabItemTable);
		this.add(ModBlocks.SALT_BRICK_SLAB.get(), this::createSlabItemTable);
		this.add(ModBlocks.SALT_TILE_SLAB.get(), this::createSlabItemTable);
		this.dropSelf(ModBlocks.SALT_STAIRS.get());
		this.dropSelf(ModBlocks.SALT_BRICK_STAIRS.get());
		this.dropSelf(ModBlocks.SALT_TILE_STAIRS.get());
		this.dropSelf(ModBlocks.SALT_LAMP.get());
		this.dropSelf(ModBlocks.SALT_TRAIL.get());

		this.dropSelf(ModBlocks.SUCCULENT.get());
		this.add(ModBlocks.POTTED_SUCCULENT.get(), this::createPotFlowerItemTable);

	}

	protected LootTable.Builder createPotFlowerItemTable(ItemLike p_249395_) {
		return LootTable.lootTable().withPool(this.applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(this.applyExplosionCondition(p_249395_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_249395_))));
	}

	protected LootTable.Builder createDoorTable(Block p_252166_) {
		return this.createSinglePropConditionTable(p_252166_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
	}

	protected LootTable.Builder createSlabItemTable(Block p_251313_) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(p_251313_, LootItem.lootTableItem(p_251313_).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p_251313_).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
	}

	// [VanillaCopy] super.droppingWithChancesAndSticks, but non-silk touch parameter can be an item instead of a block
	private LootTable.Builder silkAndStick(Block block, ItemLike nonSilk, float... nonSilkFortune) {
		LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = ObfuscationReflectionHelper.getPrivateValue(BlockLootSubProvider.class, null, "HAS_NO_SHEARS_OR_SILK_TOUCH");
		return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(nonSilk.asItem())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, nonSilkFortune))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}

	protected static LootTable.Builder createSilkTouchOrShearDropOrNotTable(Block p_252253_) {
		LootItemCondition.Builder SILK_TOUCH_OR_SHEARS = ObfuscationReflectionHelper.getPrivateValue(BlockLootSubProvider.class, null, "HAS_SHEARS_OR_SILK_TOUCH");

		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_252253_).when(SILK_TOUCH_OR_SHEARS)));
	}


	private void registerEmpty(Block b) {
		add(b, LootTable.lootTable());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return knownBlocks;
	}
}