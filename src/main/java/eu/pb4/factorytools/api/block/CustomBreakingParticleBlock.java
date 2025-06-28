package eu.pb4.factorytools.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleEffect;

public interface CustomBreakingParticleBlock {
    ParticleEffect getBreakingParticle(BlockState state);
}
