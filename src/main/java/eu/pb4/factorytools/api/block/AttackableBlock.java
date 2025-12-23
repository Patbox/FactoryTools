package eu.pb4.factorytools.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface AttackableBlock {
    InteractionResult onPlayerAttack(BlockState state, Player player, Level world, BlockPos pos, Direction direction);
}
