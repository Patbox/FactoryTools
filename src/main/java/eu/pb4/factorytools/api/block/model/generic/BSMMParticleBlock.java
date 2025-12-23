package eu.pb4.factorytools.api.block.model.generic;

import eu.pb4.factorytools.api.block.CustomBreakingParticleBlock;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public interface BSMMParticleBlock extends CustomBreakingParticleBlock {

    @Override
    default ParticleOptions getBreakingParticle(BlockState blockState) {
        return BlockStateModelManager.getParticle(blockState);
    }
}
