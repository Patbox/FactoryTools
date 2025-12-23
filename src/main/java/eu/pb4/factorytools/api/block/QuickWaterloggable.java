package eu.pb4.factorytools.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface QuickWaterloggable extends SimpleWaterloggedBlock {
    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    default BlockState waterLog(BlockPlaceContext ctx, BlockState state) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return state.setValue(WATERLOGGED, bl);
    }

    default void tickWater(BlockState state, LevelReader worldView, ScheduledTickAccess tickView, BlockPos pos) {
        if ((Boolean)state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(worldView));
        }
    }
}
