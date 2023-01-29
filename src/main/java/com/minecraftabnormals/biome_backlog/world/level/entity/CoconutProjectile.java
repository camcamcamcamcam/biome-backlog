package com.minecraftabnormals.biome_backlog.world.level.entity;

import com.minecraftabnormals.biome_backlog.register.ModBlocks;
import com.minecraftabnormals.biome_backlog.register.ModEntities;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CoconutProjectile extends ThrowableItemProjectile {


    public CoconutProjectile(EntityType<? extends CoconutProjectile> p_37391_, Level p_37392_) {
        super(p_37391_, p_37392_);
    }

    public CoconutProjectile(Level p_37399_, LivingEntity p_37400_) {
        super(ModEntities.COCONUT.get(), p_37400_, p_37399_);
    }

    private ParticleOptions getParticle() {
        ItemStack itemstack = this.getItemRaw();
        return (ParticleOptions)(itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleOption(ParticleTypes.ITEM, itemstack));
    }

    @Override
    protected Item getDefaultItem() {
        return ModBlocks.COCONUT.get().asItem();
    }

    public void handleEntityEvent(byte p_37402_) {
        if (p_37402_ == 3) {
            ParticleOptions particleoptions = this.getParticle();

            for(int i = 0; i < 8; ++i) {
                this.level.addParticle(particleoptions, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            if (hitResult instanceof BlockHitResult blockHitResult && blockHitResult.getDirection() == Direction.UP) {
                Vec3 deltaMovement = this.getDeltaMovement();
                Vec3 newDeltaMovement = new Vec3(deltaMovement.x(), 0.0, deltaMovement.z());
                this.setDeltaMovement(newDeltaMovement);
            } else {
                this.discard();
            }
        }

    }
}
