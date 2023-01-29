package com.minecraftabnormals.biome_backlog.register;

import com.minecraftabnormals.biome_backlog.BiomeBacklog;
import com.minecraftabnormals.biome_backlog.capability.DeathTrackCapability;
import com.minecraftabnormals.biome_backlog.world.level.entity.ModBoat;
import com.minecraftabnormals.biome_backlog.world.level.entity.ModBoatType;
import com.minecraftabnormals.biome_backlog.world.level.entity.Vulture;
import com.minecraftabnormals.biome_backlog.world.server.DeathTrackRequest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = BiomeBacklog.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof BlockHitResult blockHitResult) {
            Projectile projectile = event.getProjectile();
            Level level = projectile.level;
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
            Level level = projectile.level;
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
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(DeathTrackCapability.class);
    }

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Level> event) {
        event.addCapability(new ResourceLocation(BiomeBacklog.MODID, "death_track"), new DeathTrackCapability());
    }


    @SubscribeEvent
    public static void livingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity instanceof Player) {
            if (((Player) livingEntity).getLastDeathLocation().isPresent()) {
                if (livingEntity.level instanceof ServerLevel serverLevel) {
                    BlockPos pos = ((Player) livingEntity).getLastDeathLocation().get().pos();
                    livingEntity.level.getCapability(BiomeBacklog.TRUSTED_VULTURE_CAP).ifPresent(deathTrackCapability -> {
                        for (DeathTrackRequest request : deathTrackCapability.getDeathTrackRequestsFor()) {
                            loadChunksAround(serverLevel, request.getVultureUUID(), request.getChunkPosition(), true);
                            Entity entityFromChunk = serverLevel.getEntity(request.getVultureUUID());
                            if (entityFromChunk != null) {
                                entityFromChunk.teleportTo(pos.getX(), pos.getY() + 30, pos.getZ());
                                if (entityFromChunk instanceof Vulture) {
                                    ((Vulture) entityFromChunk).setCircle(false);
                                }
                            }
                            loadChunksAround(serverLevel, request.getVultureUUID(), request.getChunkPosition(), false);
                            deathTrackCapability.removeDeathTrackRequestAll(deathTrackCapability.getDeathTrackRequestsFor());
                        }
                    });
                }
            }
        }
    }

    private static void loadChunksAround(ServerLevel serverLevel, UUID ticket, BlockPos center, boolean load) {
        ChunkPos chunkPos = new ChunkPos(center);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                ForgeChunkManager.forceChunk(serverLevel, BiomeBacklog.MODID, ticket, chunkPos.x + i, chunkPos.z + j, load, true);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity living) {
            if (!event.getLevel().isClientSide && living.isAlive() && living instanceof Vulture && ((Vulture) living).isTame() && ((Vulture) living).hasCircle()) {
                UUID ownerUUID = ((Vulture) living).getOwnerUUID();
                event.getLevel().getCapability(BiomeBacklog.TRUSTED_VULTURE_CAP).ifPresent(cap -> {
                    DeathTrackRequest request = new DeathTrackRequest(living.getUUID(), Registry.ENTITY_TYPE.getKey(event.getEntity().getType()).toString(), ownerUUID, living.blockPosition(), event.getEntity().level.dayTime());
                    cap.addDeathTrackRequest(request);
                });
            }
        }
    }
}
