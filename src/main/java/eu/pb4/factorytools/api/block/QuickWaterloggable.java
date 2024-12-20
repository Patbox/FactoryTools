package eu.pb4.factorytools.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public interface QuickWaterloggable extends Waterloggable {
    BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    default BlockState waterLog(ItemPlacementContext ctx, BlockState state) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return state.with(WATERLOGGED, bl);
    }

    default void tickWater(BlockState state, WorldView worldView, ScheduledTickView tickView, BlockPos pos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldView));
        }
    }
}
