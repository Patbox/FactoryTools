package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.factorytools.api.util.ThreadedMatrix4f;
import eu.pb4.factorytools.impl.CompatStatus;
import eu.pb4.factorytools.impl.DebugData;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockBoundAttachment;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.joml.Matrix4f;

/**
 * Use {@link BlockModel} instead
 */
@Deprecated
public class BaseModel extends ElementHolder {
    private static int startTick = 0;
    private int updateTick = (startTick++) % 20;

    // Shared matrix, no reason to create a new one every time. It gets reset to identity anyway
    protected static final Matrix4f mat = CompatStatus.C2ME ? new ThreadedMatrix4f() : new Matrix4f();

    public final int getTick() {
        return this.updateTick;
    }

    public boolean isTimeForMediumUpdate() {
        return updateTick % 2 == 0;
    }

    @Override
    public void tick() {
        super.tick();
        this.updateTick++;
    }

    protected double getSquaredDistance(ServerPlayNetworkHandler player) {
        return this.getPos().squaredDistanceTo(player.player.getPos());
    }

    protected BlockBoundAttachment blockBound() {
        return BlockBoundAttachment.get(this);
    }
}
