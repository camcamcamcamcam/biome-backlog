package com.camcamcamcamcam.biome_backlog.world.level.entity;

import com.camcamcamcamcam.biome_backlog.register.ModBlocks;
import com.camcamcamcamcam.biome_backlog.register.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class Tumbleweed extends Entity {
    private static final EntityDataAccessor<Integer> DAMAGE_COOLDOWN_ID = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float>   WIND_DIRECTION_ID  = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float>   ROLL_ID            = SynchedEntityData.defineId(Tumbleweed.class, EntityDataSerializers.FLOAT);

    private static final double COLLISION_EPSILON = 1.0E-7D;

    private static final String TAG_TUMBLEWEED           = "Tumbleweed";
    private static final String TAG_DAMAGE_COOLDOWN      = "DamageCooldown";
    private static final String TAG_HAS_ALREADY_COLLIDED = "HasAlreadyCollided";
    private static final String TAG_ROLL                 = "Roll";

    private static final int DAMAGE_COOLDOWN_MAX = 5 * 20;

    private static final double GRAVITY      = -0.005;
    public  static final double BOUNCE_SPEED = 0.1;

    private static final double WIND_NOISE_SCALE = 0.01;
    private static final double WIND_SPEED = 0.08;

    private boolean hasAlreadyCollided = false;

    @Nullable
    private final SimplexNoise windNoise;

    public Tumbleweed(EntityType<? extends Tumbleweed> type, Level level) {
        super(type, level);

        if (level.isClientSide) {
            this.windNoise = null;
        } else {
            this.windNoise = new SimplexNoise(RandomSource.create(((ServerLevel) level).getSeed()));
        }
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DAMAGE_COOLDOWN_ID, DAMAGE_COOLDOWN_MAX);
        this.entityData.define(WIND_DIRECTION_ID,  0.0F);
        this.entityData.define(ROLL_ID,            0.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag data) {
        final var tumbleweed = new CompoundTag();

        tumbleweed.putShort(TAG_DAMAGE_COOLDOWN, (short) this.getDamageCooldown());
        tumbleweed.putBoolean(TAG_HAS_ALREADY_COLLIDED, this.hasAlreadyCollided);
        tumbleweed.putFloat(TAG_ROLL, this.getRoll());

        data.put(TAG_TUMBLEWEED, tumbleweed);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag data) {
        final var tumbleweed = data.getCompound(TAG_TUMBLEWEED);

        this.setDamageCooldown(tumbleweed.getShort(TAG_DAMAGE_COOLDOWN));
        this.hasAlreadyCollided = tumbleweed.getBoolean(TAG_HAS_ALREADY_COLLIDED);
        this.setRoll(tumbleweed.getFloat(TAG_ROLL));
    }

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

    @Override
    public boolean isPushable() {
        return true;
    }

    private int getDamageCooldown() {
        return this.entityData.get(DAMAGE_COOLDOWN_ID);
    }

    private void setDamageCooldown(int value) {
        this.entityData.set(DAMAGE_COOLDOWN_ID, value);
    }

    /* Returns true when damaging entities is possible. */
    private boolean decrementDamageCooldown() {
        final var cooldown = this.getDamageCooldown();
        if (cooldown == 0) {
            return true;
        }

        this.setDamageCooldown(cooldown - 1);

        return cooldown == 1;
    }

    private void playAmbientSound(SoundEvent sound) {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), sound, SoundSource.AMBIENT, 1.0F, 1.0F);
    }

    private void dropSeeds(boolean destroy) {
        final var item = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.TUMBLEWEED_SEED.get()));
        this.level.addFreshEntity(item);

        if (destroy) {
            this.playAmbientSound(SoundEvents.SWEET_BERRY_BUSH_BREAK);
            this.kill();
        } else {
            this.playAmbientSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES);
        }
    }

    private void checkEntityCollision() {
        if (this.decrementDamageCooldown()) {
            final var collidingEntities = this.level.getEntitiesOfClass(
                LivingEntity.class,
                this.getBoundingBox().inflate(COLLISION_EPSILON)
            );

            if (collidingEntities.isEmpty()) {
                return;
            }

            this.setDamageCooldown(DAMAGE_COOLDOWN_MAX);

            for (final var entity : collidingEntities) {
                entity.hurt(this.damageSources().cactus(), 2.0F);
            }

            if (!this.level.isClientSide) {
                final var pos = this.blockPosition();
                final var state = this.level.getBlockState(pos);

                if (!this.hasAlreadyCollided && ModBlocks.TUMBLEWEED.get().canSurvive(state, this.level, pos)) {
                    this.level.setBlock(pos, ModBlocks.TUMBLEWEED.get().defaultBlockState(), Block.UPDATE_CLIENTS);
                    this.level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                    this.hasAlreadyCollided = true;
                } else if (!this.hasAlreadyCollided) {
                    this.dropSeeds(false);

                    this.hasAlreadyCollided = true;
                } else {
                    this.dropSeeds(true);
                }
            }
        }
    }

    public float getWindDirection() {
        if (this.windNoise == null) {
            return this.entityData.get(WIND_DIRECTION_ID);
        } else {
            final var value = (float) this.windNoise.getValue(
                WIND_NOISE_SCALE * this.getX(),
                WIND_NOISE_SCALE * this.getY(),
                WIND_NOISE_SCALE * this.getZ()
            );

            final var angle = 360.0F * (value + 1.0F) / 2.0F;

            this.entityData.set(WIND_DIRECTION_ID, angle);

            return angle;
        }
    }

    public float getRoll() {
        return this.entityData.get(ROLL_ID);
    }

    private void setRoll(float roll) {
        this.entityData.set(ROLL_ID, roll);
    }

    private void tumble() {
        final var wind_angle = this.getWindDirection();

        final var velocity = this.getDeltaMovement();

        final var prevPosition = this.position();
        this.move(MoverType.SELF, velocity);
        final var realVelocity = this.position().subtract(prevPosition);

        if (this.isInFluidType()) {
            this.dropSeeds(true);
            return;
        }

        final var shouldRoll = (
            realVelocity.x != 0.0 ||
            realVelocity.z != 0.0
        );

        if (shouldRoll) {
            this.setRoll(this.getRoll() + 1.0F);
        }

        final double newVerticalSpeed;
        if (this.onGround && shouldRoll) {
            newVerticalSpeed = BOUNCE_SPEED;
        } else {
            newVerticalSpeed = realVelocity.y + GRAVITY;
        }

        /* NOTE: We do not apply any friction. */

        this.setDeltaMovement(new Vec3(0.0, newVerticalSpeed, WIND_SPEED).yRot(wind_angle * Mth.DEG_TO_RAD));
    }

    @Override
    public void tick() {
        super.tick();

        this.tumble();
        this.checkEntityCollision();
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (this.isRemoved()) {
            return false;
        }

        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            this.dropSeeds(true);
            return true;
        }

        if (!(source.getDirectEntity() instanceof Player)) {
            return false;
        }

        final var player = (Player) source.getDirectEntity();

        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(Tags.Items.TOOLS_HOES)) {
            return false;
        }

        this.dropSeeds(true);

        return true;
    }
}
