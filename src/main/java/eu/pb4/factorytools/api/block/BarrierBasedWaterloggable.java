package eu.pb4.factorytools.api.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nucleoid.packettweaker.PacketContext;

public interface BarrierBasedWaterloggable extends QuickWaterloggable, PolymerBlock {
    @Override
    default BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.BARRIER.defaultBlockState().setValue(WATERLOGGED, state.getValue(WATERLOGGED));
    }
}
