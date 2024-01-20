package com.camcamcamcamcam.biome_backlog.data;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.data.recipe.ColorLoseBuilder;
import com.camcamcamcamcam.biome_backlog.recipe.BlockStateIngredient;
import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModItems;
import com.camcamcamcamcam.biome_backlog.register.ModRecipeSerializers;
import com.camcamcamcamcam.biome_backlog.register.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider implements IConditionBuilder {
    public RecipeGenerator(PackOutput generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput finishedRecipeConsumer) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PALM_SHIELD.get())
                .define('#', ModBlocks.PALM_PLANKS.get())
                .define('I', Items.SHIELD)
                .pattern(" # ")
                .pattern("#I#")
                .pattern(" # ")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Blocks.CALCITE)
                .define('#', ModItems.CALCITE_POWDER_BOTTLE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_calcite_powder", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CALCITE_POWDER_BOTTLE.get()).build()))
                .save(finishedRecipeConsumer, BiomeBacklog.prefix("calcite"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LEATHER)
                .define('#', ModItems.CACTUS_PAD.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_cactus_pad", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CACTUS_PAD.get()).build()))
                .save(finishedRecipeConsumer, BiomeBacklog.prefix("leather"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CACTUS_SALAD.get())
                .requires(ModItems.CACTUS_PAD.get(), 4)
                .requires(Items.BOWL)
                .requires(ModItems.VINEGAR_BOTTLE.get())
                .unlockedBy("has_cactus_pad", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CACTUS_PAD.get()).build()))
                .save(finishedRecipeConsumer);

        SimpleCookingRecipeBuilder.generic(Ingredient.of(ModItems.PRICKLY_PEAR.get()), RecipeCategory.FOOD, ModItems.PRICKLESS_PEAR.get(),
                        0.45F, 300, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .unlockedBy("has_prickly_pear", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.PRICKLY_PEAR.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "prickless_pear"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.PRICKLY_PEAR.get()), RecipeCategory.FOOD, ModItems.PRICKLESS_PEAR.get(),
                        0.45F, 300)
                .unlockedBy("has_prickly_pear", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.PRICKLY_PEAR.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "prickless_pear_from_campfire"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.PRICKLY_PEAR.get()), RecipeCategory.FOOD, ModItems.PRICKLESS_PEAR.get(),
                        0.45F, 150)
                .unlockedBy("has_prickly_pear", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.PRICKLY_PEAR.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "prickless_pear_from_smoker"));


        SimpleCookingRecipeBuilder.generic(Ingredient.of(ModItems.RAW_OSTRICH.get()), RecipeCategory.FOOD, ModItems.COOKED_OSTRICH.get(),
                        0.45F, 300, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .unlockedBy("has_raw_ostrich", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_OSTRICH.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "cooked_ostrich"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_OSTRICH.get()), RecipeCategory.FOOD, ModItems.COOKED_OSTRICH.get(),
                        0.45F, 300)
                .unlockedBy("has_raw_ostrich", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_OSTRICH.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "cooked_ostrich_from_campfire"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.RAW_OSTRICH.get()), RecipeCategory.FOOD, ModItems.COOKED_OSTRICH.get(),
                        0.45F, 150)
                .unlockedBy("has_raw_ostrich", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_OSTRICH.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "cooked_ostrich_from_smoker"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_BLOCK.get())
                .define('#', ModBlocks.SALT_TRAIL.get().asItem())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_salt", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.SALT_TRAIL.get().asItem()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_BRICKS.get())
                .define('#', ModBlocks.SALT_BLOCK.get().asItem())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_salt_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.SALT_BLOCK.get().asItem()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_TILES.get())
                .define('#', ModBlocks.SALT_BRICKS.get().asItem())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_salt_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.SALT_BLOCK.get().asItem()).build()))
                .save(finishedRecipeConsumer);

        stairBuilder(ModBlocks.SALT_STAIRS.get(), Ingredient.of(ModBlocks.SALT_BLOCK.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        stairBuilder(ModBlocks.SALT_BRICK_STAIRS.get(), Ingredient.of(ModBlocks.SALT_BRICKS.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        stairBuilder(ModBlocks.SALT_TILE_STAIRS.get(), Ingredient.of(ModBlocks.SALT_TILES.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        chiseledBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_SALT_BLOCK.get(), Ingredient.of(ModBlocks.SALT_SLAB.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_SLAB.get(), Ingredient.of(ModBlocks.SALT_BLOCK.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_BRICK_SLAB.get(), Ingredient.of(ModBlocks.SALT_BRICKS.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_TILE_SLAB.get(), Ingredient.of(ModBlocks.SALT_TILES.get()))
                .unlockedBy("has_salt_block", has(ModBlocks.SALT_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_LAMP.get())
                .define('#', ModBlocks.SALT_TRAIL.get())
                .define('X', Items.TORCH)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_salt", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.SALT_TRAIL.get()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.THATCH_BLOCK.get())
                .define('#', ModBlocks.PALM_LEAVES.get().asItem())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_palm_leaves", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_LEAVES.get().asItem()).build()))
                .save(finishedRecipeConsumer);

        stairBuilder(ModBlocks.THATCH_STAIRS.get(), Ingredient.of(ModBlocks.THATCH_BLOCK.get()))
                .unlockedBy("has_thatch_block", has(ModBlocks.THATCH_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_PLANKS.get(), 4)
                .requires(ModBlocks.PALM_LOG.get())
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_LOG.get()).build()))
                .save(finishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_PLANKS.get(), 4)
                .requires(ModBlocks.PALM_WOOD.get())
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_WOOD.get()).build()))
                .save(finishedRecipeConsumer, BiomeBacklog.prefix("palm_planks_from_wood"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_PLANKS.get(), 4)
                .requires(ModBlocks.STRIPPED_PALM_LOG.get())
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.STRIPPED_PALM_LOG.get()).build()))
                .save(finishedRecipeConsumer, BiomeBacklog.prefix("palm_planks_from_stripped_log"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_PLANKS.get(), 4)
                .requires(ModBlocks.STRIPPED_PALM_WOOD.get())
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.STRIPPED_PALM_WOOD.get()).build()))
                .save(finishedRecipeConsumer, BiomeBacklog.prefix("palm_planks_from_stripped_wood"));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_FENCE.get(), 3)
                .define('#', ModBlocks.PALM_PLANKS.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("#S#")
                .pattern("#S#")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_FENCE_GATE.get(), 1)
                .define('#', ModBlocks.PALM_PLANKS.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("S#S")
                .pattern("S#S")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_DOOR.get(), 3)
                .define('#', ModBlocks.PALM_PLANKS.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_TRAPDOOR.get(), 2)
                .define('#', ModBlocks.PALM_PLANKS.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_SIGN.get(), 3)
                .define('#', ModBlocks.PALM_PLANKS.get())
                .define('S', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" S ")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_PRESSURE_PLATE.get())
                .define('#', ModBlocks.PALM_PLANKS.get())
                .pattern("##")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_BUTTON.get())
                .requires(ModBlocks.PALM_PLANKS.get())
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PALM_BOAT.get())
                .define('#', ModBlocks.PALM_PLANKS.get())
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_PLANKS.get()).build()))
                .save(finishedRecipeConsumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PALM_BOAT_CHEST.get())
                .requires(ModItems.PALM_BOAT.get())
                .requires(Items.CHEST)
                .unlockedBy("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.PALM_BOAT.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VINEGAR_BOTTLE.get())
                .requires(ModItems.DATE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_date", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.DATE.get()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DATE_BUNCH.get())
                .define('#', ModItems.DATE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_date", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.DATE.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COCONUT_CHUNK.get(), 4)
                .requires(ModItems.COCONUT_HALF.get())
                .unlockedBy("has_coconut_half", inventoryTrigger(ItemPredicate.Builder.item()
                .of(ModItems.COCONUT_HALF.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.DESICCATED_COCONUT.get())
                .requires(ModItems.COCONUT_CHUNK.get())
                .requires(Items.SUGAR)
                .unlockedBy("has_coconut_chunk", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.COCONUT_CHUNK.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BOWL)
                .requires(ModItems.COCONUT_SHELL.get())
                .unlockedBy("has_coconut_shell", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.COCONUT_SHELL.get()).build()))
                .save(finishedRecipeConsumer, BiomeBacklog.prefix("bowl"));

        SimpleCookingRecipeBuilder.generic(Ingredient.of(ModItems.COCONUT_SHELL.get()), RecipeCategory.FOOD, Items.CHARCOAL,
                        0.45F, 300, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new)
                .unlockedBy("has_coconut_shell", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.COCONUT_SHELL.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeBacklog.MODID, "charcoal_from_coconut_shell"));


        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PALM_SLAB.get(), Ingredient.of(ModBlocks.PALM_PLANKS.get()))
                .unlockedBy("has_block", has(ModBlocks.PALM_PLANKS.get().asItem()))
                .save(finishedRecipeConsumer);
        stairBuilder(ModBlocks.PALM_STAIRS.get(), Ingredient.of(ModBlocks.PALM_PLANKS.get()))
                .unlockedBy("has_block", has(ModBlocks.PALM_PLANKS.get().asItem()))
                .save(finishedRecipeConsumer);


        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.WOOL), Blocks.WHITE_WOOL, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("wools"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.WOOL_CARPETS), Blocks.WHITE_CARPET, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("carpets"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.CANDLES), Blocks.CANDLE, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("candles"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.BANNERS), Blocks.WHITE_BANNER, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("banners"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_BEDS), Blocks.WHITE_BED, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("beds"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_CONCRETE), Blocks.WHITE_CONCRETE, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("concretes"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_CONCRETE_POWDER), Blocks.WHITE_CONCRETE_POWDER, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("concrete_powders"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.TERRACOTTA), Blocks.TERRACOTTA, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("terracottas"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_SHULKER_BOXS), Blocks.SHULKER_BOX, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("shulker_boxes"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(Tags.Blocks.STAINED_GLASS), Blocks.GLASS, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("glass"));
        ColorLoseBuilder.recipe(BlockStateIngredient.of(Tags.Blocks.STAINED_GLASS_PANES), Blocks.GLASS_PANE, ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer, BiomeBacklog.prefix("glass_pane"));
    }
}
