package eu.pb4.factorytools.api.virtualentity;

import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemDisplayElementUtil {
    private static final Map<Item, ItemStack> TRANSPARENT_ITEM_MODEL_MAP = new Reference2ObjectOpenHashMap<>();
    private static final Map<Item, ItemStack> SOLID_ITEM_MODEL_MAP = new Reference2ObjectOpenHashMap<>();
    private static final Map<Identifier, ItemStack> TRANSPARENT_ID_MODEL_MAP = new HashMap<>();
    private static final Map<Identifier, ItemStack> SOLID_ID_MODEL_MAP = new HashMap<>();

    @Deprecated
    public static ItemDisplayElement createSimple(Item model) {
        return createSimple(getModel(model));
    }

    @Deprecated
    public static ItemDisplayElement createSimple(Identifier model) {
        return createSimple(getModel(model));
    }

    public static ItemDisplayElement createTransparent(Item model) {
        return createSimple(getModel(model));
    }

    public static ItemDisplayElement createTransparent(Identifier model) {
        return createSimple(getModel(model));
    }

    public static ItemDisplayElement createSolid(Item model) {
        return createSimple(getSolidModel(model));
    }

    public static ItemDisplayElement createSolid(Identifier model) {
        return createSimple(getSolidModel(model));
    }

    @Deprecated
    public static ItemStack getModel(Item model) {
        return getModelGeneric(model, TRANSPARENT_ITEM_MODEL_MAP, Items.TRIAL_KEY, (item) -> item.components().get(DataComponents.ITEM_MODEL));
    }

    @Deprecated
    public static ItemStack getModel(Identifier model) {
        return getModelGeneric(model, TRANSPARENT_ID_MODEL_MAP, Items.TRIAL_KEY, ResourcePackExtras::bridgeModel);
    }

    public static ItemStack getTransparentModel(Item model) {
        return getModelGeneric(model, TRANSPARENT_ITEM_MODEL_MAP, Items.TRIAL_KEY, (item) -> item.components().get(DataComponents.ITEM_MODEL));
    }

    public static ItemStack getTransparentModel(Identifier model) {
        return getModelGeneric(model, TRANSPARENT_ID_MODEL_MAP, Items.TRIAL_KEY, ResourcePackExtras::bridgeModel);
    }

    public static ItemStack getSolidModel(Item model) {
        return getModelGeneric(model, SOLID_ITEM_MODEL_MAP, Items.STONE, (item) -> item.components().get(DataComponents.ITEM_MODEL));
    }

    public static ItemStack getSolidModel(Identifier model) {
        return getModelGeneric(model, SOLID_ID_MODEL_MAP, Items.STONE, ResourcePackExtras::bridgeModel);
    }

    @Deprecated
    public static ItemStack getModelCopy(Item model) {
        return getModel(model).copy();
    }

    @Deprecated
    public static ItemStack getModelCopy(Identifier model) {
        return getModel(model).copy();
    }

    public static ItemStack getSolidModelCopy(Item model) {
        return getSolidModel(model).copy();
    }

    public static ItemStack getSolidModelCopy(Identifier model) {
        return getSolidModel(model).copy();
    }

    public static ItemStack getTransparentModelCopy(Item model) {
        return getTransparentModel(model).copy();
    }

    public static ItemStack getTransparentModelCopy(Identifier model) {
        return getTransparentModel(model).copy();
    }

    public static ItemDisplayElement createSimple(ItemStack model) {
        var element = createSimple();
        element.setItem(model);
        return element;
    }

    @Deprecated
    public static ItemDisplayElement createSimple(Item model, int updateRate) {
        return createSimple(getModel(model), updateRate);
    }
    @Deprecated

    public static ItemDisplayElement createSimple(Identifier model, int updateRate) {
        return createSimple(getModel(model), updateRate);
    }

    public static ItemDisplayElement createSolid(Item model, int updateRate) {
        return createSimple(getSolidModel(model), updateRate);
    }

    public static ItemDisplayElement createSolid(Identifier model, int updateRate) {
        return createSimple(getSolidModel(model), updateRate);
    }

    public static ItemDisplayElement createTransparent(Item model, int updateRate) {
        return createSimple(getTransparentModel(model), updateRate);
    }

    public static ItemDisplayElement createTransparent(Identifier model, int updateRate) {
        return createSimple(getTransparentModel(model), updateRate);
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
        element.setItemDisplayContext(ItemDisplayContext.FIXED);
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
            stack.set(DataComponents.ITEM_MODEL, itemModelfier.apply(model));

            synchronized (map) {
                map.put(model, stack);
            }
        }
        return stack;
    }
}
