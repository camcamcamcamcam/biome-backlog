package com.minecraftabnormals.biome_vote_losers.data.loot;

import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import com.minecraftabnormals.biome_vote_losers.register.ModItems;
import com.minecraftabnormals.biome_vote_losers.world.level.block.PearCactusBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLoot {
	private final Set<Block> knownBlocks = new HashSet<>();
	// [VanillaCopy] super
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
	private static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

	@Override
	protected void add(Block block, LootTable.Builder builder) {
		super.add(block, builder);
		knownBlocks.add(block);
	}

	@Override
	protected void addTables() {
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
		this.dropSelf(ModBlocks.DATE_BUNCH.get());
		this.dropSelf(ModBlocks.COCONUT_BLOCK.get());
		this.dropSelf(ModBlocks.PALM_LOG.get());
		this.dropWhenSilkTouch(ModBlocks.PALM_LEAVES.get());
		this.dropSelf(ModBlocks.PALM_LEAVES_HANGING.get());
		this.dropSelf(ModBlocks.THATCH_BLOCK.get());
		this.dropSelf(ModBlocks.THATCH_STAIRS.get());
		this.dropSelf(ModBlocks.STRIPPED_PALM_LOG.get());
		this.dropSelf(ModBlocks.PALM_WOOD.get());
		this.dropSelf(ModBlocks.STRIPPED_PALM_WOOD.get());
		this.dropSelf(ModBlocks.PALM_PLANKS.get());
		this.dropSelf(ModBlocks.PALM_SAPLING.get());
		this.add(ModBlocks.PALM_DOOR.get(), BlockLoot::createDoorTable);
		this.dropSelf(ModBlocks.PALM_TRAPDOOR.get());
		this.dropSelf(ModBlocks.PALM_FENCE.get());
		this.dropSelf(ModBlocks.PALM_FENCE_GATE.get());
		this.dropSelf(ModBlocks.PALM_STAIRS.get());
		this.add(ModBlocks.PALM_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(ModBlocks.PALM_BUTTON.get());
		this.dropSelf(ModBlocks.PALM_PRESSURE_PLATE.get());

		this.dropSelf(ModBlocks.SALT_BLOCK.get());
		this.dropSelf(ModBlocks.SALT_BRICKS.get());
		this.dropSelf(ModBlocks.SALT_TILES.get());
		this.dropSelf(ModBlocks.CHISELED_SALT_BLOCK.get());
		this.add(ModBlocks.SALT_SLAB.get(), BlockLoot::createSlabItemTable);
		this.add(ModBlocks.SALT_BRICK_SLAB.get(), BlockLoot::createSlabItemTable);
		this.add(ModBlocks.SALT_TILE_SLAB.get(), BlockLoot::createSlabItemTable);
		this.dropSelf(ModBlocks.SALT_STAIRS.get());
		this.dropSelf(ModBlocks.SALT_BRICK_STAIRS.get());
		this.dropSelf(ModBlocks.SALT_TILE_STAIRS.get());
		this.dropSelf(ModBlocks.SALT_LAMP.get());
		this.dropSelf(ModBlocks.SALT.get());
		this.dropSelf(ModBlocks.SUCCULENT.get());
		this.add(ModBlocks.POTTED_SUCCULENT.get(), BlockLoot::createPotFlowerItemTable);

	}


	// [VanillaCopy] super.droppingWithChancesAndSticks, but non-silk touch parameter can be an item instead of a block
	private static LootTable.Builder silkAndStick(Block block, ItemLike nonSilk, float... nonSilkFortune) {
		LootItemCondition.Builder NOT_SILK_TOUCH_OR_SHEARS = ObfuscationReflectionHelper.getPrivateValue(net.minecraft.data.loot.BlockLoot.class, null, "HAS_NO_SHEARS_OR_SILK_TOUCH");
		return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(nonSilk.asItem())).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, nonSilkFortune))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(NOT_SILK_TOUCH_OR_SHEARS).add(applyExplosionDecay(block, LootItem.lootTableItem(Items.STICK).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}


	private void registerEmpty(Block b) {
		add(b, LootTable.lootTable());
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return knownBlocks;
	}
}