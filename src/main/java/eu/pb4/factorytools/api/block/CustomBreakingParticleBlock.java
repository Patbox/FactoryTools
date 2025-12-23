package eu.pb4.factorytools.api.block;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.state.BlockState;

public interface CustomBreakingParticleBlock {
    ParticleOptions getBreakingParticle(BlockState state);
}
