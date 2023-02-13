package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.decorator.CoconutDecorator;
import com.camcamcamcamcam.biome_backlog.world.gen.decorator.DateDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTreeDecoratorTypes {
	public static final DeferredRegister<TreeDecoratorType<?>> DECORATOR_TYPE = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, BiomeBacklog.MODID);

	public static final RegistryObject<TreeDecoratorType<DateDecorator>> DATE = DECORATOR_TYPE.register("date", () -> new TreeDecoratorType<>(DateDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<CoconutDecorator>> COCONUT = DECORATOR_TYPE.register("coconut", () -> new TreeDecoratorType<>(CoconutDecorator.CODEC));
}
