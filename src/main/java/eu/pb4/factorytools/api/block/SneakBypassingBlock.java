package eu.pb4.factorytools.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public interface SneakBypassingBlock {
    default boolean bypassSneaking(BlockState state, ServerPlayerEntity player, BlockHitResult result, ItemStack stack, Hand hand) {
        return true;
    }
}
