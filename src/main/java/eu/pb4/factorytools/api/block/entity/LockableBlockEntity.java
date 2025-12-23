package eu.pb4.factorytools.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public abstract class LockableBlockEntity extends BlockEntity {
    private LockCode lock;
    @Nullable
    private Component customName;

    protected LockableBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.lock = LockCode.NO_LOCK;
    }

    public static boolean checkUnlocked(Player player,  Level world, BlockPos pos, LockCode lock, Component containerName) {
        return checkUnlocked(player, world, pos, lock, containerName, true);
    }

    public static boolean checkUnlocked(Player player, Level world, BlockPos containerPos, LockCode lock, Component containerName, boolean display) {
        if (!player.isSpectator() && !lock.unlocksWith(player.getMainHandItem())) {
            if (display) {
                player.displayClientMessage(Component.translatable("container.isLocked", containerName), true);
                if (!world.isClientSide()) {
                    world.playSound((Entity)null, containerPos.getX(), containerPos.getY(), containerPos.getZ(), SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.lock = LockCode.fromTag(view);
        this.customName = parseCustomNameSafe(view, "CustomName");

        this.readDataMixin(view);
    }

    private void readDataMixin(ValueInput view) {}

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        this.lock.addToTag(view);
        if (this.customName != null) {
            view.store("CustomName", ComponentSerialization.CODEC, this.customName);
        }
        this.writeDataMixin(view);
    }

    private void writeDataMixin(ValueOutput view) {}

    public Component getName() {
        return this.customName != null ? this.customName : this.getContainerName();
    }

    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Component getCustomName() {
        return this.customName;
    }

    public void setCustomName(Component customName) {
        this.customName = customName;
    }

    public LockCode getContainerLock() {
        return this.lock;
    }

    public void getContainerLock(LockCode lock) {
        this.lock = lock;
        this.setChanged();
    }

    protected Component getContainerName() {
        return this.getBlockState().getBlock().getName();
    }

    public boolean checkUnlocked(Player player) {
        return checkUnlocked(player, true);
    }

    public boolean checkUnlocked(Player player, boolean display) {
        return checkUnlocked(player, this.level, this.worldPosition, this.lock, this.getDisplayName(), display) && checkUnlockedMixin(player, display);
    }

    protected boolean checkUnlockedMixin(Player player, boolean display) {
        return true;
    }

    protected boolean hasCheckUnlockedMixin() {
        return false;
    }

    @Nullable
    public final void openGui(ServerPlayer playerEntity) {
        if (this.checkUnlocked(playerEntity)) {
            this.createGui(playerEntity);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder componentMapBuilder) {
        if (this.lock != null) {
            componentMapBuilder.set(DataComponents.LOCK, this.lock);
        }
        if (this.customName != null) {
            componentMapBuilder.set(DataComponents.CUSTOM_NAME, this.customName);
        }
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {

        this.lock = components.getOrDefault(DataComponents.LOCK, this.lock);
        this.customName = components.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    public void removeComponentsFromTag(ValueOutput view) {
        super.removeComponentsFromTag(view);
        view.discard("CustomName");
        view.discard("Lock");
    }

    protected void createGui(ServerPlayer playerEntity) {};
}
