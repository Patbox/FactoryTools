package eu.pb4.factorytools.api.block;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public interface CustomPistonBehavior {
    /**
     * @return whether this block is sticky in any way when moved by pistons
     */
    boolean isSticky(BlockState state);

    /**
     * @return whether the neighboring block is pulled along if this block is moved by pistons
     */
    boolean isStickyToNeighbor(Level world, BlockPos pos, BlockState state, BlockPos neighborPos, BlockState neighborState, Direction dir, Direction moveDir);


    static boolean isVanillaSticky(BlockState state) {
        return state.is(Blocks.HONEY_BLOCK) || state.is(Blocks.SLIME_BLOCK);
    }
}
