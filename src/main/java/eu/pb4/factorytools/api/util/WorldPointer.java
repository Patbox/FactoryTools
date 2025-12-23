package eu.pb4.factorytools.api.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class WorldPointer {
    private final ServerLevel world;
    private final BlockPos pos;
    private BlockState blockState;
    private BlockEntity blockEntity;
    private Container inventory;
    private boolean requireBlockEntityCheck = true;
    private boolean requireInventoryCheck = true;

    public WorldPointer(Level world, BlockPos pos) {
        this.world = (ServerLevel) world;
        this.pos = pos;
    }

    public double getX() {
        return this.pos.getX() + 0.5;
    }

    public double getY() {
        return this.pos.getY() + 0.5;
    }

    public double getZ() {
        return this.pos.getZ() + 0.5;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public BlockState getBlockState() {
        if (this.blockState == null) {
            this.blockState = this.world.getBlockState(this.pos);
        }
        return this.blockState;
    }

    public <T extends BlockEntity> T getBlockEntity() {
        if (this.requireBlockEntityCheck) {
            this.blockEntity = this.world.getBlockEntity(this.pos);
            this.requireBlockEntityCheck = false;
        }
        return (T) this.blockEntity;
    }

    public ServerLevel getWorld() {
        return this.world;
    }

    public Container getInventory() {
        if (this.requireInventoryCheck) {
            if (this.getBlockState().getBlock() instanceof WorldlyContainerHolder provider) {
                this.inventory = provider.getContainer(this.blockState, this.world, this.pos);
            } else {
                var be = this.getBlockEntity();

                if (be instanceof Container inventory) {
                    this.inventory = inventory;
                }
            }

            this.requireInventoryCheck = false;
        }
        return this.inventory;
    }
}
