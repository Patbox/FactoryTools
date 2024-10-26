package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ItemDisplayElementUtil {
    private static final Map<Item, ItemStack> ITEM_MODEL_MAP = new Reference2ObjectOpenHashMap<>();
    private static final Map<Identifier, ItemStack> ID_MODEL_MAP = new HashMap<>();

    public static ItemDisplayElement createSimple(Item model) {
        return createSimple(getModel(model));
    }
    public static ItemDisplayElement createSimple(Identifier model) {
        return createSimple(getModel(model));
    }

    public static ItemStack getModel(Item model) {
        ItemStack stack;
        while (true) {
            try {
                stack = ITEM_MODEL_MAP.get(model);
                break;
            } catch (Throwable ignore) {}
        }

        if (stack == null) {
            stack = new ItemStack(Items.TRIAL_KEY);
            stack.set(DataComponentTypes.ITEM_MODEL, model.getComponents().get(DataComponentTypes.ITEM_MODEL));

            synchronized (ITEM_MODEL_MAP) {
                ITEM_MODEL_MAP.put(model, stack);
            }
        }
        return stack;
    }

    public static ItemStack getModel(Identifier model) {
        ItemStack stack;
        while (true) {
            try {
                stack = ID_MODEL_MAP.get(model);
                break;
            } catch (Throwable ignore) {}
        }

        if (stack == null) {
            stack = new ItemStack(Items.TRIAL_KEY);
            stack.set(DataComponentTypes.ITEM_MODEL, PolymerResourcePackUtils.getBridgedModelId(model));

            synchronized (ID_MODEL_MAP) {
                ID_MODEL_MAP.put(model, stack);
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

    public static ItemDisplayElement createSimple(Identifier model, int updateRate) {
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
