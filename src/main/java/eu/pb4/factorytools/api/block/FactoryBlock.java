package eu.pb4.factorytools.api.block;

import eu.pb4.factorytools.api.util.VirtualDestroyStage;
import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.virtualentity.api.BlockWithElementHolder;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public interface FactoryBlock extends PolymerBlock, BlockWithElementHolder, VirtualDestroyStage.Marker {

    @Override
    default @Nullable ElementHolder createMovingElementHolder(ServerWorld world, BlockPos blockPos, BlockState blockState, @Nullable ElementHolder oldStaticElementHolder) {
        return oldStaticElementHolder instanceof BlockModel b ? b : null;
    }

    @Override
    @Nullable
    default ElementHolder createStaticElementHolder(ServerWorld world, BlockPos blockPos, BlockState blockState, @Nullable ElementHolder oldMovingElementHolder) {
        return oldMovingElementHolder instanceof BlockModel b ? b : null;
    }
}
