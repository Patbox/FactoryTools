package eu.pb4.factorytools.api.block;

import eu.pb4.factorytools.api.util.VirtualDestroyStage;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface FactoryBlock extends PolymerBlock, BlockWithElementHolder, VirtualDestroyStage.Marker {

    @Override
    default @Nullable ElementHolder createMovingElementHolder(ServerLevel world, BlockPos blockPos, BlockState blockState, @Nullable ElementHolder oldStaticElementHolder) {
        return oldStaticElementHolder instanceof BlockModel b ? b : null;
    }

    @Override
    @Nullable
    default ElementHolder createStaticElementHolder(ServerLevel world, BlockPos blockPos, BlockState blockState, @Nullable ElementHolder oldMovingElementHolder) {
        return oldMovingElementHolder instanceof BlockModel b ? b : null;
    }
}
