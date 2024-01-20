package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.world.gen.decorator.CoconutDecorator;
import com.camcamcamcamcam.biome_backlog.world.gen.decorator.DateDecorator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTreeDecoratorTypes {
	public static final DeferredRegister<TreeDecoratorType<?>> DECORATOR_TYPE = DeferredRegister.create(BuiltInRegistries.TREE_DECORATOR_TYPE, BiomeBacklog.MODID);

	public static final Supplier<TreeDecoratorType<DateDecorator>> DATE = DECORATOR_TYPE.register("date", () -> new TreeDecoratorType<>(DateDecorator.CODEC));
	public static final Supplier<TreeDecoratorType<CoconutDecorator>> COCONUT = DECORATOR_TYPE.register("coconut", () -> new TreeDecoratorType<>(CoconutDecorator.CODEC));
}
