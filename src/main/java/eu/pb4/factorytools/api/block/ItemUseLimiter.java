package eu.pb4.factorytools.api.block;

import eu.pb4.factorytools.api.item.FactoryToolsTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public interface ItemUseLimiter {
    default boolean preventUseItemWhileTargetingBlock(ServerPlayerEntity player, BlockState blockState, World world, BlockHitResult result, ItemStack stack, Hand hand) {
        return !player.shouldCancelInteraction() && stack.isIn(FactoryToolsTags.DEFAULT_PREVENT_USE);
    }

    interface All extends ItemUseLimiter {
        @Override
        default boolean preventUseItemWhileTargetingBlock(ServerPlayerEntity player, BlockState blockState, World world, BlockHitResult result, ItemStack stack, Hand hand) {
            return !player.shouldCancelInteraction();
        }
    }
}
