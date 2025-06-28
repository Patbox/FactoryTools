package eu.pb4.factorytools.mixin;

import eu.pb4.factorytools.api.block.CustomBreakingParticleBlock;
import eu.pb4.polymer.core.api.utils.PolymerSyncedObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 600)
public class BlockMixin {
    @Inject(method = "spawnBreakParticles", at = @At("HEAD"), cancellable = true)
    private void customSpawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (PolymerSyncedObject.getSyncedObject(Registries.BLOCK, state.getBlock()) instanceof CustomBreakingParticleBlock customBreakingParticleBlock) {
            if (world.isClient()) {
                return;
            }
            var group = state.getSoundGroup();
            world.playSound(null, pos, group.getBreakSound(), SoundCategory.BLOCKS, (group.getVolume() + 1.0f) / 2.0f, group.getPitch() * 0.8f);
            var particle = customBreakingParticleBlock.getBreakingParticle(state);
            if (!state.isAir() && state.hasBlockBreakParticles()) {
                var shape = state.getOutlineShape(world, pos);
                shape.forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> {
                    double d = Math.min(1.0, maxX - minX);
                    double e = Math.min(1.0, maxY - minY);
                    double f = Math.min(1.0, maxZ - minZ);
                    int i = Math.max(2, MathHelper.ceil(d / 0.25));
                    int j = Math.max(2, MathHelper.ceil(e / 0.25));
                    int k = Math.max(2, MathHelper.ceil(f / 0.25));

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
                                
                                ((ServerWorld) world).spawnParticles(particle, (double)pos.getX() + x, (double)pos.getY() + y, (double)pos.getZ() + z, 0,
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
