package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.tracker.DataTrackerLike;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import eu.pb4.polymer.virtualentity.api.tracker.SimpleDataTracker;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class LodItemDisplayElement extends ItemDisplayElement {
    protected static final Set<EntityDataAccessor<?>> DEFAULT_LOD = Set.of(
            DisplayTrackedData.START_INTERPOLATION,
            DisplayTrackedData.INTERPOLATION_DURATION,
            DisplayTrackedData.TRANSLATION,
            DisplayTrackedData.SCALE,
            DisplayTrackedData.LEFT_ROTATION,
            DisplayTrackedData.RIGHT_ROTATION
    );
    public static boolean isEnabled = true;
    public static boolean isDisabled = false;
    // MovingElementTrackers
    public final DataTrackerLike nearTracker = new SimpleDataTracker(this.getEntityType());
    public final DataTrackerLike mediumTracker = new SimpleDataTracker(this.getEntityType());
    public final DataTrackerLike mainTracker = new SimpleDataTracker(this.getEntityType());

    protected Set<EntityDataAccessor<?>> lodTracked = DEFAULT_LOD;
    private int updateTick = 0;
    protected double nearDistanceSquared = 50 * 50;
    protected float farDistanceSquared = 90 * 90;

    public LodItemDisplayElement(ItemStack stack) {
        super();
        this.getDataTracker().set(DisplayTrackedData.Item.ITEM, stack);
    }

    public LodItemDisplayElement() {
        super();
    }

    public static LodItemDisplayElement createSimple(Item model) {
        return createSimple(ItemDisplayElementUtil.getModel(model));
    }

    public static LodItemDisplayElement createSimple(Identifier model) {
        return createSimple(ItemDisplayElementUtil.getModel(model));
    }

    public static LodItemDisplayElement createSimple(ItemStack model) {
        var element = createSimple();
        element.setItem(model);
        return element;
    }

    public static LodItemDisplayElement createSimple(ItemStack model, int updateRate, float qualityMultiplier, float farQualityDistanceMultiplier) {
        var element = createSimple(model, updateRate);
        element.nearDistanceSquared = 50 * 50 * qualityMultiplier * qualityMultiplier;
        element.farDistanceSquared = 90 * 90 * farQualityDistanceMultiplier * farQualityDistanceMultiplier;
        return element;
    }

    public static LodItemDisplayElement createSimple(ItemStack model, int updateRate, float qualityMultiplier) {
        var element = createSimple(model, updateRate);
        element.nearDistanceSquared = 50 * 50 * qualityMultiplier * qualityMultiplier;
        return element;
    }
    public static LodItemDisplayElement createSimple(Item model, int updateRate) {
        return createSimple(ItemDisplayElementUtil.getModel(model), updateRate);
    }

    public static LodItemDisplayElement createSimple(Identifier model, int updateRate) {
        return createSimple(ItemDisplayElementUtil.getModel(model), updateRate);
    }

    public static LodItemDisplayElement createSimple(ItemStack model, int updateRate) {
        var element = createSimple(model);
        element.setInterpolationDuration(updateRate);
        return element;
    }

    public static LodItemDisplayElement createSimple() {
        var element = new LodItemDisplayElement();
        element.setDisplaySize(2, 2);
        element.setViewRange(0.8f);
        element.setItemDisplayContext(ItemDisplayContext.FIXED);
        element.setTeleportDuration(1);
        element.setInvisible(true);
        return element;
    }

    public void addLodData(EntityDataAccessor<?> data) {
        if (this.lodTracked == DEFAULT_LOD) {
            this.lodTracked = new HashSet<>(this.lodTracked);
        }
        this.lodTracked.add(data);
    }

    @Override
    protected DataTrackerLike createDataTracker() {
        return new DataTrackerLike() {
            @Override
            public <T> @Nullable T get(EntityDataAccessor<T> data) {
                return lodTracked.contains(data) ? nearTracker.get(data) : mainTracker.get(data);
            }

            @Override
            public <T> void set(EntityDataAccessor<T> key, T value, boolean forceDirty) {
                if (lodTracked.contains(key)) {
                    nearTracker.set(key, value, forceDirty);
                    if (key != DisplayTrackedData.START_INTERPOLATION) {
                        mediumTracker.set(key, value, forceDirty);
                    }
                } else {
                    mainTracker.set(key, value, forceDirty);
                }
            }

            @Override
            public <T> void setDirty(EntityDataAccessor<T> key, boolean isDirty) {
                set(key, get(key), isDirty);
            }

            @Override
            public boolean isDirty() {
                return nearTracker.isDirty() || mainTracker.isDirty();
            }

            @Override
            public boolean isDirty(EntityDataAccessor<?> key) {
                return nearTracker.isDirty(key) || mainTracker.isDirty(key);
            }

            @Override
            public @Nullable List<SynchedEntityData.DataValue<?>> getDirtyEntries() {
                return mainTracker.getDirtyEntries();
            }

            @Override
            public @Nullable List<SynchedEntityData.DataValue<?>> getChangedEntries() {
                var x = new ArrayList<SynchedEntityData.DataValue<?>>();

                var a = nearTracker.getChangedEntries();
                if (a != null) {
                    x.addAll(a);
                }

                var b = mainTracker.getChangedEntries();
                if (b != null) {
                    x.addAll(b);
                }

                return x.isEmpty() ? null : x;
            }
        };
    }

    @Override
    protected void sendTrackerUpdates() {
        if (isDisabled) {
            if (this.nearTracker.isDirty()) {
                this.getHolder().sendPacket(new ClientboundSetEntityDataPacket(this.getEntityId(), this.nearTracker.getDirtyEntries()));
            }
            if (this.mainTracker.isDirty()) {
                this.getHolder().sendPacket(new ClientboundSetEntityDataPacket(this.getEntityId(), this.mainTracker.getDirtyEntries()));
            }
        } else {
            Packet<ClientGamePacketListener> nearPacket = null;
            Packet<ClientGamePacketListener> mediumPacket = null;
            if (this.mainTracker.isDirty()) {
                this.getHolder().sendPacket(new ClientboundSetEntityDataPacket(this.getEntityId(), this.mainTracker.getDirtyEntries()));
            }

            if (this.nearTracker.isDirty()) {
                nearPacket = new ClientboundSetEntityDataPacket(this.getEntityId(), this.nearTracker.getDirtyEntries());
            }

            if (this.mediumTracker.isDirty() && (updateTick++) % 10 == 0) {
                mediumPacket = new ClientboundSetEntityDataPacket(this.getEntityId(), this.mediumTracker.getDirtyEntries());
            }

            if (nearPacket == null && mediumPacket == null) {
                return;
            }

            for (var player : this.getHolder().getWatchingPlayers()) {
                var d = this.getSquaredDistance(player);
                if (d < this.nearDistanceSquared) {
                    if (nearPacket != null) {
                        player.send(nearPacket);
                    }
                } else if (d < this.farDistanceSquared) {
                    if (mediumPacket != null) {
                        player.send(mediumPacket);
                    }
                }
            }
        }
    }

    protected double getSquaredDistance(ServerGamePacketListenerImpl player) {
        return (Objects.requireNonNull(this.getHolder())).getPos().distanceToSqr(player.player.position());
    }
}
