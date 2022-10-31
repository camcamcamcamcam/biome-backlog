package com.minecraftabnormals.biome_vote_losers.world.level.entity.goal;

import com.minecraftabnormals.biome_vote_losers.world.level.entity.Vulture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class LongTemptGoal extends Goal {
	private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(64.0D).ignoreLineOfSight();
	private final TargetingConditions targetingConditions;
	protected final Vulture mob;
	private double px;
	private double py;
	private double pz;
	private double pRotX;
	private double pRotY;
	@Nullable
	protected Player player;
	private int calmDown;
	private boolean isRunning;
	private final Ingredient items;
	private final boolean canScare;

	public LongTemptGoal(Vulture p_25939_, Ingredient p_25941_, boolean p_25942_) {
		this.mob = p_25939_;
		this.items = p_25941_;
		this.canScare = p_25942_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
	}

	public boolean canUse() {
		if (this.calmDown > 0) {
			--this.calmDown;
			return false;
		} else {
			this.player = this.mob.level.getNearestPlayer(this.targetingConditions, this.mob);
			return this.player != null;
		}
	}

	private boolean shouldFollow(LivingEntity p_148139_) {
		return this.items.test(p_148139_.getMainHandItem()) || this.items.test(p_148139_.getOffhandItem());
	}

	public boolean canContinueToUse() {
		if (this.canScare()) {
			if (this.mob.distanceToSqr(this.player) < 36.0D) {
				if (this.player.distanceToSqr(this.px, this.py, this.pz) > 0.010000000000000002D) {
					return false;
				}

				if (Math.abs((double) this.player.getXRot() - this.pRotX) > 5.0D || Math.abs((double) this.player.getYRot() - this.pRotY) > 5.0D) {
					return false;
				}
			} else {
				this.px = this.player.getX();
				this.py = this.player.getY();
				this.pz = this.player.getZ();
			}

			this.pRotX = (double) this.player.getXRot();
			this.pRotY = (double) this.player.getYRot();
		}

		return this.canUse();
	}

	protected boolean canScare() {
		return this.canScare;
	}

	public void start() {
		this.px = this.player.getX();
		this.py = this.player.getY();
		this.pz = this.player.getZ();
		this.isRunning = true;
	}

	public void stop() {
		this.player = null;
		this.mob.getNavigation().stop();
		this.calmDown = reducedTickDelay(100);
		this.isRunning = false;
	}

	public void tick() {
		this.mob.getLookControl().setLookAt(this.player, (float) (this.mob.getMaxHeadYRot() + 20), (float) this.mob.getMaxHeadXRot());
		this.mob.moveTargetPoint = player.position();
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}