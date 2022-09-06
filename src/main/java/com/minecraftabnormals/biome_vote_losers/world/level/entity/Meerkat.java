package com.minecraftabnormals.biome_vote_losers.world.level.entity;

import com.minecraftabnormals.biome_vote_losers.register.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class Meerkat extends Animal {
	private static final EntityDataAccessor<Optional<UUID>> DATA_TRUSTED_ID_0 = SynchedEntityData.defineId(Fox.class, EntityDataSerializers.OPTIONAL_UUID);

	public static final Predicate<Meerkat> TRUSTED_SELECTOR = (p_30226_) -> {
		return p_30226_.isBaby() && !p_30226_.isInWater();
	};

	public Meerkat(EntityType<? extends Meerkat> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_TRUSTED_ID_0, Optional.empty());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.25D, true));
		this.goalSelector.addGoal(7, new BreedGoal(this, 0.85D));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this, Meerkat.class));

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.FOLLOW_RANGE, 24.0F);
	}

	@Override
	public boolean canAttack(LivingEntity p_21171_) {
		return (this.getTrustedLeaderUUID() != null && this.getTrustedLeaderUUID() != p_21171_.getUUID()) && super.canAttack(p_21171_);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		Meerkat meerkat = ModEntities.MEERKAT.get().create(p_146743_);

		UUID uuid = meerkat.getTrustedLeaderUUID();
		if (uuid != null) {
			meerkat.setTrustedLeaderUUID(uuid);
		}
		return meerkat;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
		boolean flag = false;
		UUID uuid = null;

		if (p_146749_ instanceof MeerkatGroupData meerkat$group) {
			uuid = meerkat$group.uuid;
			//when random success and group size is many. child is spawn
			if (meerkat$group.getGroupSize() >= 2 && p_146746_.getRandom().nextFloat() < 0.25F) {
				flag = true;
			}
		} else {
			p_146749_ = new MeerkatGroupData(this.getUUID());
		}
		//when group found. add leader
		if (uuid != null) {
			this.setTrustedLeaderUUID(uuid);
		}
		if (flag) {
			this.setAge(-24000);
		}

		return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
	}

	public int getMaxSpawnClusterSize() {
		return 8;
	}

	@Nullable
	protected UUID getTrustedLeaderUUID() {
		return this.entityData.get(DATA_TRUSTED_ID_0).orElse((UUID) null);
	}

	protected void setTrustedLeaderUUID(@javax.annotation.Nullable UUID p_28516_) {
		this.entityData.set(DATA_TRUSTED_ID_0, Optional.ofNullable(p_28516_));
	}

	//This thing write save datas(called nbt(CompoundTag) I guess)
	@Override
	public void addAdditionalSaveData(CompoundTag p_28518_) {
		super.addAdditionalSaveData(p_28518_);
		ListTag listtag = new ListTag();

		UUID uuid = this.getTrustedLeaderUUID();
		if (uuid != null) {
			listtag.add(NbtUtils.createUUID(uuid));
		}

		p_28518_.put("TrustedLeader", listtag);
	}

	//This thing read save datas(called nbt(CompoundTag) I guess)
	@Override
	public void readAdditionalSaveData(CompoundTag p_28493_) {
		super.readAdditionalSaveData(p_28493_);
		ListTag listtag = p_28493_.getList("Trusted", 11);

		for (int i = 0; i < listtag.size(); ++i) {
			this.setTrustedLeaderUUID(NbtUtils.loadUUID(listtag.get(i)));
		}
	}

	/*
	 *  group data class
	 */
	public static class MeerkatGroupData extends AgeableMob.AgeableMobGroupData {
		public final UUID uuid;

		public MeerkatGroupData(UUID p_28703_) {
			super(false);
			this.uuid = p_28703_;
		}
	}
}
