package com.camcamcamcamcam.biome_backlog.world.level.entity;

import com.camcamcamcamcam.biome_backlog.register.ModItems;
import com.camcamcamcamcam.biome_backlog.register.ModParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class CalcitePowderReaction extends Entity {
    /* Measured in ticks. */
    private static final int LIFETIME = 15 * 20;

    private int age = 0;

    public CalcitePowderReaction(EntityType<? extends CalcitePowderReaction> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    protected void defineSynchedData() {
        /* Stub implementation. */
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag data) {
        this.age = data.getShort("Age");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag data) {
        data.putShort("Age", (short) this.age);
    }

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

    @Override
    public void tick() {
        super.tick();

        if (this.level.isClientSide) {
            if (this.random.nextInt(3) == 0) {
                final var x = this.getRandomX(0.5);
                final var z = this.getRandomZ(0.5);

                this.level.addAlwaysVisibleParticle(ModParticles.CALCITE_POWDER_BUBBLE.get(), x, this.getY() + 0.1, z, 0.0, 0.0, 0.0);
            }
        } else {
            if (!this.level.getBlockState(this.getOnPos()).is(Blocks.CALCITE)) {
                this.discard();
                return;
            }

            ++this.age;
            if (this.age >= LIFETIME) {
                this.discard();
                return;
            }
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        final var item = player.getItemInHand(hand);

        if (!item.is(Items.GLASS_BOTTLE)) {
            return InteractionResult.PASS;
        }

        item.shrink(1);
        player.addItem(new ItemStack(ModItems.CALCITE_POWDER_BOTTLE.get()));

        this.discard();

        return InteractionResult.SUCCESS;
    }
}
