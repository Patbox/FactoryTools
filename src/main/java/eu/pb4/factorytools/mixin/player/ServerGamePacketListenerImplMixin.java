package eu.pb4.factorytools.mixin.player;

import eu.pb4.factorytools.impl.ServerPlayNetExtF;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import eu.pb4.factorytools.api.util.VirtualDestroyStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin implements ServerPlayNetExtF {
    @Unique
    private final VirtualDestroyStage virtualDestroyStage = new VirtualDestroyStage();

    @Override
    public VirtualDestroyStage factorytools$getVirtualDestroyStage() {
        return this.virtualDestroyStage;
    }
}
