package eu.pb4.factorytools.api.block;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface CustomPistonBehavior {
    /**
     * @return whether this block is sticky in any way when moved by pistons
     */
    boolean isSticky(BlockState state);

    /**
     * @return whether the neighboring block is pulled along if this block is moved by pistons
     */
    boolean isStickyToNeighbor(World world, BlockPos pos, BlockState state, BlockPos neighborPos, BlockState neighborState, Direction dir, Direction moveDir);


    static boolean isVanillaSticky(BlockState state) {
        return state.isOf(Blocks.HONEY_BLOCK) || state.isOf(Blocks.SLIME_BLOCK);
    }
}
