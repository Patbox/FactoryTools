package eu.pb4.factorytools.mixin.compat;

import com.github.fabricservertools.htm.HTMContainerLock;
import com.github.fabricservertools.htm.api.LockableObject;
import eu.pb4.factorytools.api.block.entity.LockableBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("OverwriteAuthorRequired")
@Mixin(LockableBlockEntity.class)
public abstract class htm_LockableBlockEntityMixin implements LockableObject {
    @Unique
    public HTMContainerLock htmContainerLock = new HTMContainerLock();

    @Overwrite
    private void readNbtMixin(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        htmContainerLock.fromTag(nbt, lookup);
    }

    @Overwrite
    private void writeNbtMixin(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        htmContainerLock.toTag(nbt, lookup);
    }


    @Overwrite
    protected boolean checkUnlockedMixin(PlayerEntity player, boolean display) {
        return player instanceof ServerPlayerEntity serverPlayer
                && (display ? htmContainerLock.canOpen(serverPlayer) : canOpen(serverPlayer));
    }

    @Overwrite(remap = false)
    protected boolean hasCheckUnlockedMixin() {
        return true;
    }

    @Unique
    private boolean canOpen(ServerPlayerEntity player) {
        if (htmContainerLock.getType() == null) return true;
        if (htmContainerLock.getType().canOpen(player, htmContainerLock)) return true;
        return htmContainerLock.isOwner(player);
    }

    @Override
    public HTMContainerLock getLock() {
        return htmContainerLock;
    }

    @Override
    public void setLock(HTMContainerLock lock) {
        htmContainerLock = lock;
    }
}
