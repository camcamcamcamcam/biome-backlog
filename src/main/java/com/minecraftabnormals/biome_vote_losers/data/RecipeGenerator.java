package com.minecraftabnormals.biome_vote_losers.data;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import com.minecraftabnormals.biome_vote_losers.data.recipe.ColorLoseBuilder;
import com.minecraftabnormals.biome_vote_losers.recipe.BlockPropertyPair;
import com.minecraftabnormals.biome_vote_losers.recipe.BlockStateIngredient;
import com.minecraftabnormals.biome_vote_losers.register.ModBlocks;
import com.minecraftabnormals.biome_vote_losers.register.ModItems;
import com.minecraftabnormals.biome_vote_losers.register.ModRecipeSerializers;
import com.minecraftabnormals.biome_vote_losers.register.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Map;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider implements IConditionBuilder {
    public RecipeGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> finishedRecipeConsumer) {
        ShapedRecipeBuilder.shaped(Blocks.CALCITE)
                .define('#', ModItems.CALCITE_POWDER_BOTTLE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_calcite_powder", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CALCITE_POWDER_BOTTLE.get()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(Items.LEATHER)
                .define('#', ModItems.CACTUS_PAD.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_cactus_pad", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CACTUS_PAD.get()).build()))
                .save(finishedRecipeConsumer);

        SimpleCookingRecipeBuilder.cooking(Ingredient.of(ModItems.RAW_OSTRICH.get()), ModItems.COOKED_OSTRICH.get(),
                0.45F, 300, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_raw_ostrich", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_OSTRICH.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeVoteLosers.MODID, "cooked_ostrich"));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ModItems.RAW_OSTRICH.get()), ModItems.COOKED_OSTRICH.get(),
                0.45F, 300)
                .unlockedBy("has_raw_ostrich", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_OSTRICH.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeVoteLosers.MODID, "cooked_ostrich_from_campfire"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.RAW_OSTRICH.get()), ModItems.COOKED_OSTRICH.get(),
                0.45F, 150)
                .unlockedBy("has_raw_ostrich", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RAW_OSTRICH.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeVoteLosers.MODID, "cooked_ostrich_from_smoker"));


        ShapedRecipeBuilder.shaped(ModBlocks.THATCH_BLOCK.get())
                .define('#', ModBlocks.PALM_LEAVES.get().asItem())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_palm_leaves", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModBlocks.PALM_LEAVES.get().asItem()).build()))
                .save(finishedRecipeConsumer);

        stairBuilder(ModBlocks.THATCH_STAIRS.get(), Ingredient.of(ModBlocks.THATCH_BLOCK.get()))
                .unlockedBy("has_thatch_block", has(ModBlocks.THATCH_BLOCK.get().asItem()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.VINEGAR_BOTTLE.get())
                .requires(ModItems.DATE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_date", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.DATE.get()).build()))
                .save(finishedRecipeConsumer);

        ShapedRecipeBuilder.shaped(ModBlocks.DATE_BUNCH.get())
                .define('#', ModItems.DATE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_date", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.DATE.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.COCONUT_CHUNK.get(), 4)
                .requires(ModItems.COCONUT_HALF.get())
                .unlockedBy("has_coconut_half", inventoryTrigger(ItemPredicate.Builder.item()
                .of(ModItems.COCONUT_HALF.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(ModItems.DESICCATED_COCONUT.get())
                .requires(ModItems.COCONUT_CHUNK.get())
                .requires(Items.SUGAR)
                .unlockedBy("has_coconut_chunk", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.COCONUT_CHUNK.get()).build()))
                .save(finishedRecipeConsumer);

        ShapelessRecipeBuilder.shapeless(Items.BOWL)
                .requires(ModItems.COCONUT_SHELL.get())
                .unlockedBy("has_coconut_shell", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.COCONUT_SHELL.get()).build()))
                .save(finishedRecipeConsumer);

        SimpleCookingRecipeBuilder.cooking(Ingredient.of(ModItems.COCONUT_SHELL.get()), Items.CHARCOAL,
                        0.45F, 300, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_coconut_shell", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.COCONUT_SHELL.get()).build()))
                .save(finishedRecipeConsumer, new ResourceLocation(BiomeVoteLosers.MODID, "charcoal_from_coconut_shell"));

        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.WOOL), BlockPropertyPair.of(Blocks.WHITE_WOOL, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.WOOL_CARPETS), BlockPropertyPair.of(Blocks.WHITE_CARPET, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.CANDLES), BlockPropertyPair.of(Blocks.CANDLE, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.BANNERS), BlockPropertyPair.of(Blocks.WHITE_BANNER, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_BEDS), BlockPropertyPair.of(Blocks.WHITE_BED, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_CONCRETE), BlockPropertyPair.of(Blocks.WHITE_CONCRETE, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_CONCRETE_POWDER), BlockPropertyPair.of(Blocks.WHITE_CONCRETE_POWDER, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(BlockTags.TERRACOTTA), BlockPropertyPair.of(Blocks.TERRACOTTA, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(ModTags.COLORED_SHULKER_BOXS), BlockPropertyPair.of(Blocks.SHULKER_BOX, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(Tags.Blocks.STAINED_GLASS), BlockPropertyPair.of(Blocks.GLASS, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
        ColorLoseBuilder.recipe(BlockStateIngredient.of(Tags.Blocks.STAINED_GLASS_PANES), BlockPropertyPair.of(Blocks.GLASS_PANE, Map.of()), ModRecipeSerializers.COLOR_LOSE.get()).save(finishedRecipeConsumer);
    }
}
