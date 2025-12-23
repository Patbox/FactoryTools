package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.factorytools.api.virtualentity.LodItemDisplayElement;
import eu.pb4.polymer.common.impl.CommonImplUtils;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class FastItemDisplayElement extends LodItemDisplayElement {
    private final ObjectOpenCustomHashSet<ServerGamePacketListenerImpl> fastPlayers = new ObjectOpenCustomHashSet<>(CommonImplUtils.IDENTITY_HASH);
    private ItemStack fastItemStack = ItemStack.EMPTY;
    private int fastItemDistance = Integer.MAX_VALUE;
    private Packet<ClientGamePacketListener> fastPacket;
    private Packet<ClientGamePacketListener> slowPacket;

    public FastItemDisplayElement(ItemStack stack) {
        super();
        this.setItem(stack);
    }

    public FastItemDisplayElement() {
        super();
    }

    public void setFastItem(ItemStack stack, int distance) {
        this.fastItemStack = stack;
        this.fastPlayers.clear();
        this.getDataTracker().setDirty(DisplayTrackedData.Item.ITEM, true);
        this.fastItemDistance = distance * distance;
        this.fastPacket = new ClientboundSetEntityDataPacket(this.getEntityId(), List.of(SynchedEntityData.DataValue.create(DisplayTrackedData.Item.ITEM, this.fastItemStack)));
    }

    @Override
    public void setItem(ItemStack stack) {
        super.setItem(stack);
        this.slowPacket = new ClientboundSetEntityDataPacket(this.getEntityId(), List.of(SynchedEntityData.DataValue.create(DisplayTrackedData.Item.ITEM, this.getItem())));
    }

    @Override
    protected void sendChangedTrackerEntries(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> packetConsumer) {
        super.sendChangedTrackerEntries(player, packetConsumer);

        if (isEnabled) {
            var d = this.getSquaredDistance(player.connection);
            if (d > this.fastItemDistance) {
                packetConsumer.accept(this.fastPacket);
                this.fastPlayers.add(player.connection);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (isEnabled) {
            for (var player : Objects.requireNonNull(this.getHolder()).getWatchingPlayers()) {
                var d = this.getSquaredDistance(player);

                if (d > this.fastItemDistance) {
                    if (this.fastPlayers.add(player)) {
                        player.send(this.fastPacket);
                    }
                } else {
                    if (this.fastPlayers.remove(player)) {
                        player.send(this.slowPacket);
                    }
                }
            }
        } else {
            for (var player : this.fastPlayers) {
                player.send(this.slowPacket);
            }
            this.fastPlayers.clear();
        }
    }
}
