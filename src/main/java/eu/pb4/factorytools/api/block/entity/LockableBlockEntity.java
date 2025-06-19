package eu.pb4.factorytools.api.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public abstract class LockableBlockEntity extends BlockEntity {
    private ContainerLock lock;
    @Nullable
    private Text customName;

    protected LockableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.lock = ContainerLock.EMPTY;
    }

    public static boolean checkUnlocked(PlayerEntity player, ContainerLock lock, Text containerName) {
        return checkUnlocked(player, lock, containerName, true);
    }

    public static boolean checkUnlocked(PlayerEntity player, ContainerLock lock, Text containerName, boolean display) {
        if (!player.isSpectator() && !lock.canOpen(player.getMainHandStack())) {
            if (display) {
                player.sendMessage(Text.translatable("container.isLocked", containerName), true);
                player.playSoundToPlayer(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void readData(ReadView view) {
        super.readData(view);
        this.lock = ContainerLock.read(view);
        this.customName = tryParseCustomName(view, "CustomName");

        this.readDataMixin(view);
    }

    private void readDataMixin(ReadView view) {}

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        this.lock.write(view);
        if (this.customName != null) {
            view.put("CustomName", TextCodecs.CODEC, this.customName);
        }
        this.writeDataMixin(view);
    }

    private void writeDataMixin(WriteView view) {}

    public Text getName() {
        return this.customName != null ? this.customName : this.getContainerName();
    }

    public Text getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    public void setCustomName(Text customName) {
        this.customName = customName;
    }

    public ContainerLock getContainerLock() {
        return this.lock;
    }

    public void getContainerLock(ContainerLock lock) {
        this.lock = lock;
        this.markDirty();
    }

    protected Text getContainerName() {
        return this.getCachedState().getBlock().getName();
    }

    public boolean checkUnlocked(PlayerEntity player) {
        return checkUnlocked(player, true);
    }

    public boolean checkUnlocked(PlayerEntity player, boolean display) {
        return checkUnlocked(player, this.lock, this.getDisplayName(), display) && checkUnlockedMixin(player, display);
    }

    protected boolean checkUnlockedMixin(PlayerEntity player, boolean display) {
        return true;
    }

    protected boolean hasCheckUnlockedMixin() {
        return false;
    }

    @Nullable
    public final void openGui(ServerPlayerEntity playerEntity) {
        if (this.checkUnlocked(playerEntity)) {
            this.createGui(playerEntity);
        }
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        if (this.lock != null) {
            componentMapBuilder.add(DataComponentTypes.LOCK, this.lock);
        }
        if (this.customName != null) {
            componentMapBuilder.add(DataComponentTypes.CUSTOM_NAME, this.customName);
        }
    }

    @Override
    protected void readComponents(ComponentsAccess components) {

        this.lock = components.getOrDefault(DataComponentTypes.LOCK, this.lock);
        this.customName = components.get(DataComponentTypes.CUSTOM_NAME);
    }

    @Override
    public void removeFromCopiedStackData(WriteView view) {
        super.removeFromCopiedStackData(view);
        view.remove("CustomName");
        view.remove("Lock");
    }

    protected void createGui(ServerPlayerEntity playerEntity) {};
}
