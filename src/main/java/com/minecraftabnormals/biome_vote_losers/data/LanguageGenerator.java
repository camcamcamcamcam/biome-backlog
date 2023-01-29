package com.minecraftabnormals.biome_vote_losers.data;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import com.minecraftabnormals.biome_vote_losers.register.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(DataGenerator gen) {
        super(gen, BiomeVoteLosers.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(ModBlocks.PEAR_CACTUS.get(), "Pear Cactus");
        this.add(ModBlocks.STRIPPED_PEAR_CACTUS.get(), "Stripped Pear Cactus");
        this.add(ModItems.CACTUS_PAD.get(), "Cactus Pad");
        this.add(ModItems.PRICKLY_PEAR.get(), "Prickly Pear");
        this.add(ModItems.TUMBLEWEED_SEED.get(), "Tumbleweed Seed");

        this.add(ModBlocks.MOUND.get(), "Mound");
        this.add(ModBlocks.TERMITE_MOUND.get(), "Termite Mound");
        this.add(ModBlocks.OSTRICH_EGG.get(), "Ostrich Egg");
        this.add(ModItems.RAW_OSTRICH.get(), "Raw Ostrich");
        this.add(ModItems.COOKED_OSTRICH.get(), "Cooked Ostrich");
        this.add(ModBlocks.BAOBAB_LEAVES.get(), "Baobab Leaves");
        this.add(ModBlocks.BAOBAB_TRUNK.get(), "Baobab Trunk");
        this.add(ModBlocks.BAOBAB_BARK.get(), "Baobab Bark");
        this.add(ModBlocks.BAOBAB_FLOWER.get(), "Baobab Flower");
        this.add(ModItems.BAOBAB_FRUIT.get(), "Baobab Fruit");

        this.add(ModBlocks.BURROW.get(), "Burrow");
        this.add(ModBlocks.DATE_BUNCH.get(), "Date Bunch");
        this.add(ModBlocks.COCONUT.get(), "Coconut");
        this.add(ModBlocks.PALM_LOG.get(), "Palm Log");
        this.add(ModBlocks.PALM_LEAVES.get(), "Palm Leaves");
        this.add(ModBlocks.PALM_LEAVES_HANGING.get(), "Hanging Palm Leaves");
        this.add(ModBlocks.THATCH_BLOCK.get(), "Thatch Block");
        this.add(ModBlocks.THATCH_STAIRS.get(), "Thatch Stairs");
        this.add(ModBlocks.STRIPPED_PALM_LOG.get(), "Stripped Palm Log");
        this.add(ModBlocks.PALM_WOOD.get(), "Palm Wood");
        this.add(ModBlocks.STRIPPED_PALM_WOOD.get(), "Stripped Palm Wood");
        this.add(ModBlocks.PALM_PLANKS.get(), "Palm Planks");
        this.add(ModBlocks.PALM_SIGN.get(), "Palm Sign");
        this.add(ModBlocks.COCONUT_SAPLING.get(), "Coconut Sapling");
        this.add(ModBlocks.DATE_SAPLING.get(), "Date Sapling");
        this.add(ModBlocks.PALM_DOOR.get(), "Palm Door");
        this.add(ModBlocks.PALM_TRAPDOOR.get(), "Palm Trapdoor");
        this.add(ModBlocks.PALM_FENCE.get(), "Palm Fence");
        this.add(ModBlocks.PALM_FENCE_GATE.get(), "Palm Fence Gate");
        this.add(ModBlocks.PALM_STAIRS.get(), "Palm Stairs");
        this.add(ModBlocks.PALM_SLAB.get(), "Palm Slab");
        this.add(ModBlocks.PALM_BUTTON.get(), "Palm Button");
        this.add(ModBlocks.PALM_PRESSURE_PLATE.get(), "Palm Pressure Plate");
        this.add(ModBlocks.SALT_BLOCK.get(), "Salt Block");
        this.add(ModBlocks.SALT_BRICKS.get(), "Salt Bricks");
        this.add(ModBlocks.SALT_TILES.get(), "Salt Tiles");
        this.add(ModBlocks.CHISELED_SALT_BLOCK.get(), "Chiseled Salt Block");
        this.add(ModBlocks.SALT_SLAB.get(), "Salt Slab");
        this.add(ModBlocks.SALT_BRICK_SLAB.get(), "Salt Brick Slab");
        this.add(ModBlocks.SALT_TILE_SLAB.get(), "Salt Tile Slab");
        this.add(ModBlocks.SALT_STAIRS.get(), "Salt Stairs");
        this.add(ModBlocks.SALT_BRICK_STAIRS.get(), "Salt Brick Stairs");
        this.add(ModBlocks.SALT_TILE_STAIRS.get(), "Salt Tile Stairs");
        this.add(ModBlocks.SALT_LAMP.get(), "Salt Lamp");
        this.add(ModBlocks.SALT_TRAIL.get(), "Salt");
        this.add(ModBlocks.SUCCULENT.get(), "Succulent");

        this.add(ModItems.DATE.get(), "Date");
        // this.add(ModItems.COCONUT.get(), "Coconut");
        this.add(ModItems.COCONUT_HALF.get(), "Coconut Half");
        this.add(ModItems.COCONUT_CHUNK.get(), "Coconut Chunk");
        this.add(ModItems.DESICCATED_COCONUT.get(), "Desiccated Coconut");
        this.add(ModItems.COCONUT_SHELL.get(), "Coconut Shell");
        this.add(ModItems.VINEGAR_BOTTLE.get(), "Vinegar Bottle");
        this.add(ModItems.CALCITE_POWDER_BOTTLE.get(), "Calcite Powder Bottle");
        this.add(ModItems.SUCCULENT_CUTTING.get(), "Succulent Cutting");
        this.add(ModItems.MEERKAT_SPAWN_EGG.get(), "Meerkat Spawn Egg");
        this.add(ModItems.OSTRICH_SPAWN_EGG.get(), "Ostrich Spawn Egg");
        this.add(ModItems.VULTURE_SPAWN_EGG.get(), "Vulture Spawn Egg");

        // this.add(ModBlocks.WORMHOLE.get(), "Wormhole");
    }
}
