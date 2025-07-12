package eu.pb4.factorytools.mixin.compat;

import com.github.fabricservertools.htm.HTMContainerLock;
import com.github.fabricservertools.htm.api.LockableObject;
import eu.pb4.factorytools.api.block.entity.LockableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;

@SuppressWarnings("OverwriteAuthorRequired")
@Mixin(LockableBlockEntity.class)
public abstract class htm_LockableBlockEntityMixin implements LockableObject {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Unique
    public Optional<HTMContainerLock> htmContainerLock = Optional.empty();

    @Overwrite
    private void readDataMixin(ReadView view) {
        htmContainerLock = view.read("htm_lock", HTMContainerLock.CODEC);
    }

    @Overwrite
    private void writeDataMixin(WriteView view) {
        if (htmContainerLock.isPresent()) {
            view.put("htm_lock", HTMContainerLock.CODEC, this.htmContainerLock.get());
        }
    }

    @Overwrite
    protected boolean checkUnlockedMixin(PlayerEntity player, boolean display) {
        return player instanceof ServerPlayerEntity serverPlayer
                && (display ? htmContainerLock.isEmpty() || htmContainerLock.get().canOpen(serverPlayer) : canOpenX(serverPlayer));
    }

    @Overwrite(remap = false)
    protected boolean hasCheckUnlockedMixin() {
        return true;
    }

    @Unique
    private boolean canOpenX(ServerPlayerEntity player) {
        if (htmContainerLock.isEmpty()) return true;
        if (htmContainerLock.get().canOpen(player)) return true;
        return htmContainerLock.get().isOwner(player);
    }

    @Override
    public Optional<HTMContainerLock> getLock() {
        return htmContainerLock;
    }

    @Override
    public void setLock(HTMContainerLock lock) {
        htmContainerLock = Optional.ofNullable(lock);
    }
}
