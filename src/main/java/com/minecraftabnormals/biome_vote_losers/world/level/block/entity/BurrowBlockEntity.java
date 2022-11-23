package com.minecraftabnormals.biome_vote_losers.world.level.block.entity;

import com.google.common.collect.Lists;
import com.minecraftabnormals.biome_vote_losers.register.ModBlockEntities;
import com.minecraftabnormals.biome_vote_losers.world.level.entity.Meerkat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BurrowBlockEntity extends BlockEntity {

    private static final List<String> IGNORED_TAGS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "CannotEnterBurrowTicks");
    private final List<BurrowData> stored = Lists.newArrayList();

    public BurrowBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BURROW.get(), pos, state);
    }

    public void load(CompoundTag p_155156_) {
        super.load(p_155156_);
        this.stored.clear();
        ListTag listtag = p_155156_.getList("Meerkats", 10);

        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            BurrowBlockEntity.BurrowData beehiveblockentity$beedata = new BurrowBlockEntity.BurrowData(compoundtag.getCompound("EntityData"), compoundtag.getInt("TicksInBurrow"), compoundtag.getInt("MinOccupationTicks"));
            this.stored.add(beehiveblockentity$beedata);
        }
    }

    protected void saveAdditional(CompoundTag p_187467_) {
        super.saveAdditional(p_187467_);
        p_187467_.put("Meerkats", this.writeMeerkats());
    }

    public ListTag writeMeerkats() {
        ListTag listtag = new ListTag();

        for (BurrowBlockEntity.BurrowData burrowData : this.stored) {
            CompoundTag compoundtag = burrowData.entityData.copy();
            CompoundTag compoundtag1 = new CompoundTag();
            compoundtag1.put("EntityData", compoundtag);
            compoundtag1.putInt("TicksInBurrow", burrowData.ticksInBurrow);
            compoundtag1.putInt("MinOccupationTicks", burrowData.minOccupationTicks);
            listtag.add(compoundtag1);
        }

        return listtag;
    }

    private static void tickOccupants(Level p_155150_, BlockPos p_155151_, BlockState p_155152_, List<BurrowBlockEntity.BurrowData> p_155153_) {
        boolean flag = false;

        BurrowBlockEntity.BurrowData burrowMeerkat;
        for (Iterator<BurrowBlockEntity.BurrowData> iterator = p_155153_.iterator(); iterator.hasNext(); ++burrowMeerkat.ticksInBurrow) {
            burrowMeerkat = iterator.next();
            if (burrowMeerkat.ticksInBurrow > burrowMeerkat.minOccupationTicks) {
                BurrowBlockEntity.BurrowReleaseStatus burrowReleaseStatus = BurrowReleaseStatus.BURROW_RELEASED;
                if (releaseOccupant(p_155150_, p_155151_, burrowMeerkat, (List<Entity>) null, burrowReleaseStatus)) {
                    flag = true;
                    iterator.remove();
                }
            }
        }

        if (flag) {
            setChanged(p_155150_, p_155151_, p_155152_);
        }

    }

    public static void serverTick(Level p_155145_, BlockPos p_155146_, BlockState p_155147_, BurrowBlockEntity p_155148_) {
        tickOccupants(p_155145_, p_155146_, p_155147_, p_155148_.stored);
        if (!p_155148_.stored.isEmpty() && p_155145_.getRandom().nextDouble() < 0.005D) {
            p_155145_.levelEvent(2001, p_155146_, Block.getId(Blocks.SAND.defaultBlockState()));
        }
    }

    public void addOccupant(Entity p_58742_) {
        this.addOccupantWithPresetTicks(p_58742_, 0);
    }

    @VisibleForDebug
    public int getOccupantCount() {
        return this.stored.size();
    }

    public void addOccupantWithPresetTicks(Entity p_58745_, int p_58747_) {
        if (this.stored.size() < 3) {
            p_58745_.stopRiding();
            p_58745_.ejectPassengers();
            CompoundTag compoundtag = new CompoundTag();
            p_58745_.save(compoundtag);
            this.storeMeerkat(compoundtag, p_58747_);
            if (this.level != null) {

                BlockPos blockpos = this.getBlockPos();
                this.level.playSound((Player) null, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(p_58745_, this.getBlockState()));
            }

            p_58745_.discard();
            super.setChanged();
        } else {
            p_58745_.setPose(Pose.EMERGING);
        }
    }

    public void storeMeerkat(CompoundTag p_155158_, int p_155159_) {
        this.stored.add(new BurrowData(p_155158_, p_155159_, 2400));
    }

    public boolean isEmpty() {
        return this.stored.isEmpty();
    }

    public boolean isFull() {
        return this.stored.size() == 3;
    }

    public void emptyAllLivingFromBurrow(@Nullable Player p_58749_, BurrowBlockEntity.BurrowReleaseStatus p_58751_) {
        List<Entity> list = this.releaseAllOccupants(p_58751_);
        if (p_58749_ != null) {
            for (Entity entity : list) {
                if (entity instanceof Meerkat) {
                    Meerkat meerkat = (Meerkat) entity;
                    if (p_58749_.position().distanceToSqr(entity.position()) <= 16.0D) {
                        meerkat.setTarget(p_58749_);
                    }
                    meerkat.stayOutOfBurrowCountdown = 400;
                }
            }
        }

    }

    private List<Entity> releaseAllOccupants(BurrowReleaseStatus p_58761_) {
        List<Entity> list = Lists.newArrayList();
        this.stored.removeIf((p_58766_) -> {
            return releaseOccupant(this.level, this.worldPosition, p_58766_, list, p_58761_);
        });
        if (!list.isEmpty()) {
            super.setChanged();
        }

        return list;
    }

    private static boolean releaseOccupant(Level p_155137_, BlockPos p_155138_, BurrowBlockEntity.BurrowData p_155140_, @Nullable List<Entity> p_155141_, BurrowBlockEntity.BurrowReleaseStatus p_155142_) {
        if ((p_155137_.isNight() || p_155137_.isRaining()) && p_155142_ != BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY) {
            return false;
        } else {
            CompoundTag compoundtag = p_155140_.entityData.copy();
            removeIgnoredTags(compoundtag);
            Direction direction = Direction.UP;
            BlockPos blockpos = p_155138_.relative(direction);
            boolean flag = !p_155137_.getBlockState(blockpos).getCollisionShape(p_155137_, blockpos).isEmpty();
            if (flag && p_155142_ != BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY) {
                return false;
            } else {
                Entity entity = EntityType.loadEntityRecursive(compoundtag, p_155137_, (p_58740_) -> {
                    return p_58740_;
                });
                if (entity != null) {

                    if (entity instanceof Meerkat) {
                        Meerkat meerkat = (Meerkat) entity;

                        meerkat.stayOutOfBurrowCountdown = 400;
                        meerkat.setPose(Pose.EMERGING);
                        setReleaseData(p_155140_.ticksInBurrow, meerkat);
                        if (p_155141_ != null) {
                            p_155141_.add(meerkat);
                        }

                        float f = entity.getBbWidth();
                        double d0 = (double) p_155138_.getX() + 0.5D + (double) direction.getStepX();
                        double d1 = (double) p_155138_.getY() + 0.5D + (double) direction.getStepY();
                        double d2 = (double) p_155138_.getZ() + 0.5D + (double) direction.getStepZ();
                        entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());
                    }

                    p_155137_.playSound((Player) null, p_155138_, SoundEvents.SAND_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    p_155137_.gameEvent(GameEvent.BLOCK_CHANGE, p_155138_, GameEvent.Context.of(entity, p_155137_.getBlockState(p_155138_)));
                    p_155137_.addFreshEntity(entity);
                    return true;
                }
            }
        }
        return false;
    }

    private static void setReleaseData(int p_58737_, Meerkat p_58738_) {
        int i = p_58738_.getAge();
        if (i < 0) {
            p_58738_.setAge(Math.min(0, i + p_58737_));
        } else if (i > 0) {
            p_58738_.setAge(Math.max(0, i - p_58737_));
        }

        p_58738_.setInLoveTime(Math.max(0, p_58738_.getInLoveTime() - p_58737_));
    }

    static void removeIgnoredTags(CompoundTag p_155162_) {
        for (String s : IGNORED_TAGS) {
            p_155162_.remove(s);
        }
    }

    static class BurrowData {
        final CompoundTag entityData;
        int ticksInBurrow;
        final int minOccupationTicks;

        BurrowData(CompoundTag p_58786_, int p_58787_, int p_58788_) {
            BurrowBlockEntity.removeIgnoredTags(p_58786_);
            this.entityData = p_58786_;
            this.ticksInBurrow = p_58787_;
            this.minOccupationTicks = p_58788_;
        }
    }

    public static enum BurrowReleaseStatus {
        BURROW_RELEASED,
        EMERGENCY;
    }
}
