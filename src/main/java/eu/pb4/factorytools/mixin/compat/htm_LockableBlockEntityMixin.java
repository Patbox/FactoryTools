package eu.pb4.factorytools.mixin.compat;

import com.github.fabricservertools.htm.lock.HTMContainerLock;
import com.github.fabricservertools.htm.api.LockableObject;
import eu.pb4.factorytools.api.block.entity.LockableBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@SuppressWarnings("OverwriteAuthorRequired")
@Mixin(LockableBlockEntity.class)
public abstract class htm_LockableBlockEntityMixin implements LockableObject {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Unique
    public Optional<HTMContainerLock> htmContainerLock = Optional.empty();

    @Overwrite
    private void readDataMixin(ValueInput view) {
        htmContainerLock = view.read("htm_lock", HTMContainerLock.CODEC);
    }

    @Overwrite
    private void writeDataMixin(ValueOutput view) {
        if (htmContainerLock.isPresent()) {
            view.store("htm_lock", HTMContainerLock.CODEC, this.htmContainerLock.get());
        }
    }

    @Overwrite
    protected boolean checkUnlockedMixin(Player player, boolean display) {
        return player instanceof ServerPlayer serverPlayer
                && (display ? htmContainerLock.isEmpty() || htmContainerLock.get().canOpen(serverPlayer) : canOpenX(serverPlayer));
    }

    @Overwrite(remap = false)
    protected boolean hasCheckUnlockedMixin() {
        return true;
    }

    @Unique
    private boolean canOpenX(ServerPlayer player) {
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
