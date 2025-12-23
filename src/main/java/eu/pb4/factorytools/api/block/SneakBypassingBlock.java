package eu.pb4.factorytools.api.block;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface SneakBypassingBlock {
    default boolean bypassSneaking(BlockState state, ServerPlayer player, BlockHitResult result, ItemStack stack, InteractionHand hand) {
        return true;
    }
}
