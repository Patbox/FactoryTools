package eu.pb4.factorytools.api.block;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import xyz.nucleoid.packettweaker.PacketContext;

public interface BarrierBasedWaterloggable extends QuickWaterloggable, PolymerBlock {
    @Override
    default BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return Blocks.BARRIER.getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED));
    }
}
