package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.factorytools.api.item.AutoModeledPolymerItem;
import eu.pb4.factorytools.impl.DebugData;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import eu.pb4.polymer.virtualentity.api.tracker.DataTrackerLike;
import eu.pb4.polymer.virtualentity.api.tracker.DisplayTrackedData;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemDisplayElementUtil {
    private static final Map<Item, ItemStack> MODEL_MAP = new Reference2ObjectOpenHashMap<>();

    public static ItemDisplayElement createSimple(Item model) {
        return createSimple(getModel(model));
    }

    public static ItemStack getModel(Item model) {
        ItemStack stack;
        while (true) {
            try {
                stack = MODEL_MAP.get(model);
                break;
            } catch (Throwable ignore) {}
        }

        if (stack == null) {
            if (model instanceof AutoModeledPolymerItem simpleModeledPolymerItem) {
                stack = new ItemStack(simpleModeledPolymerItem.getPolymerItem());
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(simpleModeledPolymerItem.getPolymerCustomModelData()));
            } else {
                stack = new ItemStack(model);
            }
            synchronized (MODEL_MAP) {
                MODEL_MAP.put(model, stack);
            }
        }
        return stack;
    }

    public static ItemDisplayElement createSimple(ItemStack model) {
        var element = createSimple();
        element.setItem(model);
        return element;
    }

    public static ItemDisplayElement createSimple(Item model, int updateRate) {
        return createSimple(getModel(model), updateRate);
    }

    public static ItemDisplayElement createSimple(ItemStack model, int updateRate) {
        var element = createSimple(model);
        element.setInterpolationDuration(updateRate);
        return element;
    }

    public static ItemDisplayElement createSimple() {
        var element = new ItemDisplayElement();
        element.setDisplaySize(2, 2);
        element.setViewRange(0.8f);
        element.setModelTransformation(ModelTransformationMode.FIXED);
        element.setTeleportDuration(1);
        element.setInvisible(true);
        return element;
    }
}
