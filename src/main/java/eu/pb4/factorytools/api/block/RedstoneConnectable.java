package eu.pb4.factorytools.api.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface RedstoneConnectable {
    boolean canRedstoneConnect(BlockState state, @Nullable Direction dir);
}
