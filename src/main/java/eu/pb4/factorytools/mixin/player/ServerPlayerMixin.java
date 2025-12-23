package eu.pb4.factorytools.mixin.player;

import eu.pb4.factorytools.api.util.FactoryPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ServerPlayer.class, priority = 2000)
public class ServerPlayerMixin {
    @Inject(method = "adjustSpawnLocation", at = @At("HEAD"), cancellable = true)
    private void cancelFinding(ServerLevel world, BlockPos basePos, CallbackInfoReturnable<BlockPos> cir) {
        if (((Object) this) instanceof FactoryPlayer) {
            cir.setReturnValue(BlockPos.ZERO);
        }
    }
}
