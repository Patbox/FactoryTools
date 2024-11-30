package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        return getModelGeneric(model, ITEM_MODEL_MAP, Items.TRIAL_KEY, (item) -> item.getComponents().get(DataComponentTypes.ITEM_MODEL));
    }

    public static ItemStack getModel(Identifier model) {
        return getModelGeneric(model, ID_MODEL_MAP, Items.TRIAL_KEY, ResourcePackExtras::bridgeModel);
    }

    public static ItemStack getModelCopy(Item model) {
        return getModel(model).copy();
    }

    public static ItemStack getModelCopy(Identifier model) {
        return getModel(model).copy();
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

    private static <T> ItemStack getModelGeneric(T model, Map<T, ItemStack> map, Item baseItem, Function<T, Identifier> itemModelfier) {
        ItemStack stack;
        while (true) {
            try {
                stack = map.get(model);
                break;
            } catch (Throwable ignore) {}
        }

        if (stack == null) {
            stack = new ItemStack(baseItem);
            stack.set(DataComponentTypes.ITEM_MODEL, itemModelfier.apply(model));

            synchronized (map) {
                map.put(model, stack);
            }
        }
        return stack;
    }
}
