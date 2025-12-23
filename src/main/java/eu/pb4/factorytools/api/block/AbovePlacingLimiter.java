package eu.pb4.factorytools.api.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public interface AbovePlacingLimiter {
    boolean canPlaceAbove(BlockState self, BlockPlaceContext context, BlockState state);
}
