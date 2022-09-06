package com.minecraftabnormals.biome_vote_losers.register;

import com.minecraftabnormals.biome_vote_losers.BiomeVoteLosers;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiomeVoteLosers.MODID)
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
                switch(blockHitResult.getDirection()) {
                    case UP,SOUTH,EAST:
                        direction = direction.multiply(-1);
                    default:
                }
                direction = new Vec3i(direction.getX() == 0 ? 1 : direction.getX(), direction.getY() == 0 ? 1 : direction.getY(), direction.getZ() == 0 ? 1 : direction.getZ());
                projectile.setDeltaMovement(projectileMovement.multiply(new Vec3(direction.getX(), direction.getY(), direction.getZ())).multiply(0.5, 0.5, 0.5));
                level.playSound(null, blockHitResult.getBlockPos(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }
}
