package eu.pb4.factorytools.mixin.player;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import eu.pb4.factorytools.api.util.FactoryPlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerPlayerEntity.class, priority = 2000)
public class ServerPlayerEntityMixin {
    @Inject(method = "getWorldSpawnPos", at = @At("HEAD"), cancellable = true)
    private void cancelFinding(ServerWorld world, BlockPos basePos, CallbackInfoReturnable<BlockPos> cir) {
        if (((Object) this) instanceof FactoryPlayer) {
            cir.setReturnValue(BlockPos.ORIGIN);
        }
    }
}
