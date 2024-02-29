package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.factorytools.api.item.AutoModeledPolymerItem;
import eu.pb4.factorytools.impl.DebugData;
import eu.pb4.polymer.core.impl.PolymerImplUtils;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.tracker.DataTrackerLike;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import eu.pb4.polymer.virtualentity.api.tracker.SimpleDataTracker;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;

import javax.sound.midi.Track;
import java.util.*;

public class LodItemDisplayElement extends ItemDisplayElement {
    protected static final Set<TrackedData<?>> DEFAULT_LOD = Set.of(
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

    protected Set<TrackedData<?>> lodTracked = DEFAULT_LOD;
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
        return createSimple(getModel(model));
    }

    @Deprecated
    public static ItemStack getModel(Item model) {
        return ItemDisplayElementUtil.getModel(model);
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
        return createSimple(getModel(model), updateRate);
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
        element.setModelTransformation(ModelTransformationMode.FIXED);
        element.setTeleportDuration(1);
        element.setInvisible(true);
        return element;
    }

    public void addLodData(TrackedData<?> data) {
        if (this.lodTracked == DEFAULT_LOD) {
            this.lodTracked = new HashSet<>(this.lodTracked);
        }
        this.lodTracked.add(data);
    }

    @Override
    protected DataTrackerLike createDataTracker() {
        return new DataTrackerLike() {
            @Override
            public <T> @Nullable T get(TrackedData<T> data) {
                return lodTracked.contains(data) ? nearTracker.get(data) : mainTracker.get(data);
            }

            @Override
            public <T> void set(TrackedData<T> key, T value, boolean forceDirty) {
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
            public <T> void setDirty(TrackedData<T> key, boolean isDirty) {
                set(key, get(key), isDirty);
            }

            @Override
            public boolean isDirty() {
                return nearTracker.isDirty() || mainTracker.isDirty();
            }

            @Override
            public boolean isDirty(TrackedData<?> key) {
                return nearTracker.isDirty(key) || mainTracker.isDirty(key);
            }

            @Override
            public @Nullable List<DataTracker.SerializedEntry<?>> getDirtyEntries() {
                return mainTracker.getDirtyEntries();
            }

            @Override
            public @Nullable List<DataTracker.SerializedEntry<?>> getChangedEntries() {
                var x = new ArrayList<DataTracker.SerializedEntry<?>>();

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

            @Override
            public boolean isEmpty() {
                return nearTracker.isEmpty() || mainTracker.isEmpty();
            }
        };
    }

    @Override
    protected void sendTrackerUpdates() {
        if (isDisabled) {
            if (this.nearTracker.isDirty()) {
                this.getHolder().sendPacket(new EntityTrackerUpdateS2CPacket(this.getEntityId(), this.nearTracker.getDirtyEntries()));
            }
            if (this.mainTracker.isDirty()) {
                this.getHolder().sendPacket(new EntityTrackerUpdateS2CPacket(this.getEntityId(), this.mainTracker.getDirtyEntries()));
            }
        } else {
            Packet<ClientPlayPacketListener> nearPacket = null;
            Packet<ClientPlayPacketListener> mediumPacket = null;
            if (this.mainTracker.isDirty()) {
                this.getHolder().sendPacket(new EntityTrackerUpdateS2CPacket(this.getEntityId(), this.mainTracker.getDirtyEntries()));
            }

            if (this.nearTracker.isDirty()) {
                nearPacket = new EntityTrackerUpdateS2CPacket(this.getEntityId(), this.nearTracker.getDirtyEntries());
            }

            if (this.mediumTracker.isDirty() && (updateTick++) % 10 == 0) {
                mediumPacket = new EntityTrackerUpdateS2CPacket(this.getEntityId(), this.mediumTracker.getDirtyEntries());
            }

            if (nearPacket == null && mediumPacket == null) {
                return;
            }

            for (var player : this.getHolder().getWatchingPlayers()) {
                var d = this.getSquaredDistance(player);
                if (d < this.nearDistanceSquared) {
                    if (nearPacket != null) {
                        player.sendPacket(nearPacket);
                    }
                } else if (d < this.farDistanceSquared) {
                    if (mediumPacket != null) {
                        player.sendPacket(mediumPacket);
                    }
                }
            }
        }
    }

    protected double getSquaredDistance(ServerPlayNetworkHandler player) {
        return (Objects.requireNonNull(this.getHolder())).getPos().squaredDistanceTo(player.player.getPos());
    }
}
