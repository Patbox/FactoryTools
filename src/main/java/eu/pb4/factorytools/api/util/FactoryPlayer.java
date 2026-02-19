package eu.pb4.factorytools.api.util;

import com.mojang.authlib.GameProfile;
import eu.pb4.factorytools.mixin.player.PlayerAccessor;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityEquipment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class FactoryPlayer extends FakePlayer {
    private final SlotAccess toolReference;

    public FactoryPlayer(SlotAccess toolReference, ServerLevel world, BlockPos pos, GameProfile gameProfile) {
        super(world, gameProfile);
        this.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
        this.toolReference = toolReference;
        ((PlayerAccessor) this).setInventory(new FakeInventory(this, this.equipment, toolReference));
    }

    public FakeInventory getFakeInventory() {
        return (FakeInventory) super.getInventory();
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
        SlotAccess toolReference;
        private NonNullList<ItemStack> originalList;
        private boolean toolWrappingEnabled = true;

        public FakeInventory(Player player, EntityEquipment entityEquipment, SlotAccess toolReference) {
            super(player, entityEquipment);
            this.toolReference = toolReference;
        }

        @Override
        public @NonNull ItemStack getSelectedItem() {
            return FactoryPlayer.this.toolReference.get();
        }

        @Override
        public @NonNull ItemStack setSelectedItem(ItemStack stack) {
            var old = FactoryPlayer.this.toolReference.get();
            FactoryPlayer.this.toolReference.set(stack);
            return old;
        }

        @Override
        public void placeItemBackInInventory(ItemStack stack, boolean notifiesClient) {
            FactoryPlayer.this.level().addFreshEntity(new ItemEntity(FactoryPlayer.this.level(), FactoryPlayer.this.getX(), FactoryPlayer.this.getY(), FactoryPlayer.this.getZ(), stack));
        }

        @Override
        public void setSelectedSlot(int slot) {}

        public boolean toolWrappingEnabled() {
            return this.toolWrappingEnabled;
        }

        public void setToolWrapping(boolean toolWrapping) {
            this.toolWrappingEnabled = toolWrapping;
        }

        public NonNullList<ItemStack> getOriginalList() {
            return this.originalList;
        }

        public NonNullList<ItemStack> wrapList(NonNullList<ItemStack> original) {
            this.originalList = original;
            return new NonNullList<>(original, ItemStack.EMPTY) {
                @Override
                public ItemStack set(int i, ItemStack object) {
                    if (i == 0 && toolWrappingEnabled) {
                        var old = toolReference.get();
                        toolReference.set(object);
                        return old;
                    }
                    return super.set(i, object);
                }

                @Override
                public ItemStack get(int i) {
                    if (i == 0 && toolWrappingEnabled) {
                        return toolReference.get();
                    }

                    return super.get(i);
                }
            };
        }

        /*
        @Override
        public float getBlockBreakingSpeed(BlockState block) {
            return this.getMainHandStack().getMiningSpeedMultiplier(block);
        }*/
    }
}
