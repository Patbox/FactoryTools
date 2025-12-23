package eu.pb4.factorytools.mixin;

import eu.pb4.factorytools.api.block.CustomBreakingParticleBlock;
import eu.pb4.polymer.core.api.utils.PolymerSyncedObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 600)
public class BlockMixin {
    @Inject(method = "spawnDestroyParticles", at = @At("HEAD"), cancellable = true)
    private void customSpawnBreakParticles(Level world, Player player, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (PolymerSyncedObject.getSyncedObject(BuiltInRegistries.BLOCK, state.getBlock()) instanceof CustomBreakingParticleBlock customBreakingParticleBlock) {
            if (world.isClientSide()) {
                return;
            }
            var group = state.getSoundType();
            world.playSound(null, pos, group.getBreakSound(), SoundSource.BLOCKS, (group.getVolume() + 1.0f) / 2.0f, group.getPitch() * 0.8f);
            var particle = customBreakingParticleBlock.getBreakingParticle(state);
            if (!state.isAir() && state.shouldSpawnTerrainParticles()) {
                var shape = state.getShape(world, pos);
                shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                    double d = Math.min(1.0, maxX - minX);
                    double e = Math.min(1.0, maxY - minY);
                    double f = Math.min(1.0, maxZ - minZ);
                    int i = Math.max(2, Mth.ceil(d / 0.25));
                    int j = Math.max(2, Mth.ceil(e / 0.25));
                    int k = Math.max(2, Mth.ceil(f / 0.25));

                    for(int l = 0; l < i; ++l) {
                        for(int m = 0; m < j; ++m) {
                            for(int n = 0; n < k; ++n) {
                                var velX = ((double)l + 0.5) / (double)i;
                                var velY = ((double)m + 0.5) / (double)j;
                                var velZ = ((double)n + 0.5) / (double)k;
                                var x = velX * d + minX;
                                var y = velY * e + minY;
                                var z = velZ * f + minZ;
                                velX -= 0.5; velY -= 0.5; velZ -= 0.5;

                                velX += (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
                                velY += (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
                                velZ += (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
                                double p = (Math.random() + Math.random() + 1.0) * 0.15000000596046448;
                                double o = Math.sqrt(velX * velX + velY * velY + velZ * velZ);
                                velX = velX / o * p * 0.4000000059604645;
                                velY = velY / o * p * 0.4000000059604645 + 0.10000000149011612;
                                velZ = velZ / o * p * 0.4000000059604645;
                                
                                ((ServerLevel) world).sendParticles(particle, (double)pos.getX() + x, (double)pos.getY() + y, (double)pos.getZ() + z, 0,
                                        velX, velY, velZ, 1);
                            }
                        }
                    }

                });
            }

            ci.cancel();
        }
    }
}
