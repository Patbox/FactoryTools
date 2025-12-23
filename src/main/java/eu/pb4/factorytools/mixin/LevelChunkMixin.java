package eu.pb4.factorytools.mixin;

import eu.pb4.factorytools.api.block.BlockEntityExtraListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {
    @Inject(method = "addGameEventListener", at = @At("TAIL"))
    private void callMethod(BlockEntity blockEntity, ServerLevel world, CallbackInfo ci) {
        if (blockEntity instanceof BlockEntityExtraListener listener) {
            listener.onListenerUpdate((LevelChunk) (Object) this);
        }
    }
}
