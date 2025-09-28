package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.factorytools.api.util.SharedMatrix4f;
import eu.pb4.factorytools.impl.CompatStatus;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/**
 * You should expect this element to move with pistones and falling blocks!
 */
public class BlockModel extends ElementHolder {
    private static int startTick = 0;
    private int updateTick = (startTick++) % 20;

    private static final SharedMatrix4f mat = new SharedMatrix4f();

    public static Matrix4f mat() {
        return mat.main();
    }

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

    protected double squaredDistance(ServerPlayNetworkHandler player) {
        return this.getPos().squaredDistanceTo(player.player.getEntityPos());
    }

    @Nullable
    protected BlockAwareAttachment blockAware() {
        return this.getAttachment() instanceof BlockAwareAttachment blockAwareAttachment ? blockAwareAttachment : null;
    }

    protected BlockState blockState() {
        var x = blockAware();
        return x != null ? x.getBlockState() : Blocks.AIR.getDefaultState();
    }

    protected BlockPos blockPos() {
        var x = blockAware();
        return x != null ? x.getBlockPos() : BlockPos.ORIGIN;
    }

    protected boolean inWorld() {
        var x = blockAware();
        return x != null && x.isPartOfTheWorld();
    }
}
