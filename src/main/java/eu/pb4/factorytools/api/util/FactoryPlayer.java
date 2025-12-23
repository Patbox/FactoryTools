package eu.pb4.factorytools.api.util;

import com.mojang.authlib.GameProfile;
import eu.pb4.factorytools.mixin.player.PlayerEntityAccessor;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FactoryPlayer extends FakePlayer {
    private final SlotAccess toolReference;

    public FactoryPlayer(SlotAccess toolReference, ServerLevel world, BlockPos pos, GameProfile gameProfile) {
        super(world, gameProfile);
        this.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
        this.toolReference = toolReference;
        ((PlayerEntityAccessor) this).setInventory(new FakeInventory(this, this.equipment));
    }

    @Override
    public ItemStack getItemInHand(InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            return this.toolReference.get();
        }

        return super.getItemInHand(hand);
    }

    @Override
    public void setItemInHand(InteractionHand hand, ItemStack stack) {
        if (hand == InteractionHand.MAIN_HAND) {
            this.toolReference.set(stack);
            return;
        }

        super.setItemInHand(hand, stack);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.toolReference.get();
        }

        return super.getItemBySlot(slot);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            this.toolReference.set(stack);
            return;
        }

        super.setItemSlot(slot, stack);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    public class FakeInventory extends Inventory {
        public FakeInventory(Player player, EntityEquipment entityEquipment) {
            super(player, entityEquipment);
        }

        @Override
        public ItemStack getSelectedItem() {
            return FactoryPlayer.this.toolReference.get();
        }

        @Override
        public ItemStack setSelectedItem(ItemStack stack) {
            var old = FactoryPlayer.this.toolReference.get();
            FactoryPlayer.this.toolReference.set(stack);
            return old;
        }

        @Override
        public void placeItemBackInInventory(ItemStack stack, boolean notifiesClient) {
            FactoryPlayer.this.level().addFreshEntity(new ItemEntity(FactoryPlayer.this.level(), FactoryPlayer.this.getX(), FactoryPlayer.this.getY(), FactoryPlayer.this.getZ(), stack));
        }


        /*
        @Override
        public float getBlockBreakingSpeed(BlockState block) {
            return this.getMainHandStack().getMiningSpeedMultiplier(block);
        }*/
    }
}
