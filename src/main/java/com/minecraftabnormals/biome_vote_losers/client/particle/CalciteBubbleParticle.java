package com.minecraftabnormals.biome_vote_losers.client.particle;

import com.minecraftabnormals.biome_vote_losers.register.ModParticles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CalciteBubbleParticle extends TextureSheetParticle {
    protected CalciteBubbleParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);

        this.setLifetime(30 + this.random.nextInt(20));
    }

    @Override
    public void tick() {
        /* Make bubbles fall down when there's no block beneath them. This is a little hacky. */
        if (this.gravity == 0.0F && this.level.getBlockState(new BlockPos(this.x, this.y, this.z).below()).isAir()) {
            this.gravity = 0.5F * this.random.nextFloat();
        } else {
            this.gravity = 0.0F;
        }

        super.tick();

        if (!this.isAlive()) {
            this.level.addAlwaysVisibleParticle(ModParticles.CALCITE_POWDER_BUBBLE_POP.get(), this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double velX, double velY, double velZ) {
            final var particle = new CalciteBubbleParticle(level, x, y, z);
            particle.pickSprite(this.sprite);

            return particle;
        }
    }
}
