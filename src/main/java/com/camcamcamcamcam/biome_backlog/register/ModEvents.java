package com.camcamcamcamcam.biome_backlog.register;

import com.camcamcamcamcam.biome_backlog.BiomeBacklog;
import com.camcamcamcamcam.biome_backlog.capability.DeathTrackCapability;
import com.camcamcamcamcam.biome_backlog.world.level.entity.ModBoat;
import com.camcamcamcamcam.biome_backlog.world.level.entity.ModBoatType;
import com.camcamcamcamcam.biome_backlog.world.level.entity.Vulture;
import com.camcamcamcamcam.biome_backlog.world.server.DeathTrackRequest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof BlockHitResult blockHitResult) {
            Projectile projectile = event.getProjectile();
            Level level = projectile.level();
            Vec3 projectileMovement = projectile.getDeltaMovement();
            if (projectileMovement.length() > 0.1 && level.getBlockState(blockHitResult.getBlockPos()).is(ModTags.PALM_WOOD)) {
                event.setCanceled(true);
                Vec3i direction = blockHitResult.getDirection().getNormal();
                switch (blockHitResult.getDirection()) {
                    case UP, SOUTH, EAST:
                        direction = direction.multiply(-1);
                    default:
                }
                direction = new Vec3i(direction.getX() == 0 ? 1 : direction.getX(), direction.getY() == 0 ? 1 : direction.getY(), direction.getZ() == 0 ? 1 : direction.getZ());
                projectile.setDeltaMovement(projectileMovement.multiply(new Vec3(direction.getX(), direction.getY(), direction.getZ())).multiply(0.5, 0.5, 0.5));
                level.playSound(null, blockHitResult.getBlockPos(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }

        if (event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
            Projectile projectile = event.getProjectile();
            Level level = projectile.level();
            Vec3 projectileMovement = projectile.getDeltaMovement();
            if (projectileMovement.length() > 0.1) {
                if (entityHitResult.getEntity() instanceof ModBoatType boatType) {
                    if (boatType.getModBoatType() == ModBoat.BoatType.PALM) {
                        projectile.setDeltaMovement(new Vec3(-projectileMovement.x, -projectileMovement.y, -projectileMovement.z));
                        level.playSound(null, entityHitResult.getEntity().blockPosition(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);

                        event.setCanceled(true);
                    }
                }
            }
            if (entityHitResult.getEntity() instanceof LivingEntity living) {
                if (living.isBlocking() && living.getUseItem().is(ModItems.PALM_SHIELD.get())) {
                    projectile.setDeltaMovement(new Vec3(-projectileMovement.x, -projectileMovement.y, -projectileMovement.z));
                    living.level().playSound(null, living.blockPosition(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                    living.getUseItem().hurtAndBreak(1, living, (p_35997_) -> {
                        p_35997_.broadcastBreakEvent(living.getUsedItemHand());
                    });
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void livingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity instanceof Player) {
            if (((Player) livingEntity).getLastDeathLocation().isPresent()) {
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    BlockPos pos = ((Player) livingEntity).getLastDeathLocation().get().pos();
                    DeathTrackCapability capability = livingEntity.level().getData(ModCapabilitys.DEATH_TRACK);
                    for (DeathTrackRequest request : capability.getDeathTrackRequestsFor()) {
                            loadChunksAround(serverLevel, request.getVultureUUID(), request.getChunkPosition(), true);
                            Entity entityFromChunk = serverLevel.getEntity(request.getVultureUUID());
                            if (entityFromChunk != null) {
                                entityFromChunk.teleportTo(pos.getX(), pos.getY() + 30, pos.getZ());
                                if (entityFromChunk instanceof Vulture) {
                                    ((Vulture) entityFromChunk).setCircle(false);
                                }
                            }
                            loadChunksAround(serverLevel, request.getVultureUUID(), request.getChunkPosition(), false);
                        capability.removeDeathTrackRequest(request);
                            return;
                        }
                }
            }
        }
    }

    private static void loadChunksAround(ServerLevel serverLevel, UUID ticket, BlockPos center, boolean load) {
        ChunkPos chunkPos = new ChunkPos(center);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                BiomeBacklog.deathRequestController.forceChunk(serverLevel, ticket, chunkPos.x + i, chunkPos.z + j, load, true);
            }
        }

    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity living) {
            if (!event.getLevel().isClientSide && living.isAlive() && living instanceof Vulture && ((Vulture) living).isTame() && ((Vulture) living).hasCircle()) {
                UUID ownerUUID = ((Vulture) living).getOwnerUUID();
                DeathTrackCapability cap = event.getLevel().getData(ModCapabilitys.DEATH_TRACK);
                DeathTrackRequest request = new DeathTrackRequest(living.getUUID(), BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType()).toString(), ownerUUID, living.blockPosition());
                    if (cap.getDeathTrackRequestsFor().isEmpty() || cap.getDeathTrackRequestsFor().stream().noneMatch(predicate -> predicate.getVultureUUID() == event.getEntity().getUUID())) {
                        cap.addDeathTrackRequest(request);
                    }
            }
        }
    }

    @SubscribeEvent
    public static void burnTimeEvent(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(ModBlocks.COCONUT_SAPLING.get().asItem()) || event.getItemStack().is(ModBlocks.DATE_SAPLING.get().asItem())) {
            event.setBurnTime(200);
        }

        if (event.getItemStack().is(ModBlocks.PALM_PLANKS.get().asItem()) || event.getItemStack().is(ModBlocks.PALM_FENCE.get().asItem()) || event.getItemStack().is(ModBlocks.PALM_FENCE_GATE.get().asItem())) {
            event.setBurnTime(300);
        }
        if (event.getItemStack().is(ModBlocks.PALM_STAIRS.get().asItem()) || event.getItemStack().is(ModBlocks.PALM_SLAB.get().asItem())) {
            event.setBurnTime(300);
        }

        if (event.getItemStack().is(ModBlocks.PALM_SLAB.get().asItem())) {
            event.setBurnTime(150);
        }
        if (event.getItemStack().is(ModBlocks.PALM_WOOD.get().asItem()) || event.getItemStack().is(ModBlocks.PALM_LOG.get().asItem()) || event.getItemStack().is(ModBlocks.STRIPPED_PALM_LOG.get().asItem()) || event.getItemStack().is(ModBlocks.STRIPPED_PALM_WOOD.get().asItem())) {
            event.setBurnTime(300);
        }
        if (event.getItemStack().is(ModBlocks.BAOBAB_TRUNK.get().asItem()) || event.getItemStack().is(ModBlocks.BAOBAB_BARK.get().asItem())) {
            event.setBurnTime(300);
        }
    }


}
