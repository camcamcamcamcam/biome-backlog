package com.camcamcamcamcam.biome_backlog.client;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.client.model.MeerkatModel;
import com.camcamcamcamcam.biome_backlog.client.model.OstrichModel;
import com.camcamcamcamcam.biome_backlog.client.model.TumbleweedModel;
import com.camcamcamcamcam.biome_backlog.client.model.VultureModel;
import com.camcamcamcamcam.biome_backlog.client.particle.CalciteBubbleParticle;
import com.camcamcamcamcam.biome_backlog.client.render.*;
import com.camcamcamcamcam.biome_backlog.register.*;
import com.camcamcamcamcam.biome_backlog.world.level.block.SucculentBlock;
import com.camcamcamcamcam.biome_backlog.world.level.entity.ModBoat;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.FoliageColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static void setup(FMLCommonSetupEvent event) {

	}

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent evt) {
		evt.enqueueWork(() -> {
			renderTileEntity();
			Sheets.addWoodType(ModBlocks.PALM_TYPE);
		});
	}

	public static void renderTileEntity() {
		BlockEntityRenderers.register(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(ModEntities.MEERKAT.get(), MeerkatRender::new);
		event.registerEntityRenderer(ModEntities.OSTRICH.get(), OstrichRender::new);
		event.registerEntityRenderer(ModEntities.VULTURE.get(), VultureRender::new);
		event.registerEntityRenderer(ModEntities.TUMBLEWEED.get(), TumbleweedRender::new);
		event.registerEntityRenderer(ModEntities.CALCITE_POWDER.get(), NoopRenderer::new);
		event.registerEntityRenderer(ModEntities.COCONUT.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.MOD_BOAT.get(), (r) -> {
			return new ModBoatRenderer(r, false);
		});
		event.registerEntityRenderer(ModEntities.MOD_CHEST_BOAT.get(), (r) -> {
			return new ModBoatRenderer(r, true);
		});
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.MEERKAT, MeerkatModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.OSTRICH, OstrichModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.VULTURE, VultureModel::createBodyLayer);
		event.registerLayerDefinition(ModModelLayers.TUMBLEWEED, TumbleweedModel::createBodyLayer);
		LayerDefinition layerdefinition18 = BoatModel.createBodyModel();
		LayerDefinition layerdefinition19 = ChestBoatModel.createBodyModel();


		for (ModBoat.BoatType boat$type : ModBoat.BoatType.values()) {
			event.registerLayerDefinition(ModBoatRenderer.createBoatModelName(boat$type), () -> layerdefinition18);
			event.registerLayerDefinition(ModBoatRenderer.createChestBoatModelName(boat$type), () -> layerdefinition19);
		}
	}

	@SubscribeEvent
	public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticles.CALCITE_POWDER_BUBBLE.get(), CalciteBubbleParticle.Provider::new);
		event.registerSpriteSet(ModParticles.CALCITE_POWDER_BUBBLE_POP.get(), BubblePopParticle.Provider::new);
	}

	@SubscribeEvent
	public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
		event.register((state, reader, pos, color) -> {
			return reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.getDefaultColor();
		}, ModBlocks.BAOBAB_LEAVES.get());
		event.register((state, reader, pos, color) -> {
			return reader != null & pos != null ? SucculentBlock.getColor(state.getValue(SucculentBlock.COLOR)) : FoliageColor.getDefaultColor();
		}, ModBlocks.SUCCULENT.get());
	}

	@SubscribeEvent
	public static void modelBake(ModelEvent.ModifyBakingResult event) {

		ItemProperties.register(ModItems.PALM_SHIELD.get(), new ResourceLocation("blocking"), (p_174590_, p_174591_, p_174592_, p_174593_) -> {
			return p_174592_ != null && p_174592_.isUsingItem() && p_174592_.getUseItem() == p_174590_ ? 1.0F : 0.0F;
		});
	}

	@SubscribeEvent
	public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
		event.register((stack, i) -> FoliageColor.getDefaultColor(), ModBlocks.BAOBAB_LEAVES.get().asItem());
	}
}
