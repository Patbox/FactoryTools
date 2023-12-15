package eu.pb4.factorytools.api.resourcepack;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class BaseItemProvider {
    private static final Item[] ITEMS = new Item[] {
            Items.PAPER,
            Items.FEATHER,
            Items.POPPED_CHORUS_FRUIT,
            Items.BRICK,
            Items.NETHER_BRICK,
            Items.FLINT,
            Items.CLAY_BALL,
            Items.SCUTE,
            Items.LEATHER,
            Items.RABBIT_HIDE,
            Items.RABBIT_FOOT,
            Items.PHANTOM_MEMBRANE,
            Items.GUNPOWDER,
            Items.SUGAR,
            Items.GHAST_TEAR
    };

    private static final Item[] METALIC_ITEMS = new Item[] {
            Items.IRON_NUGGET,
            Items.GOLD_NUGGET,
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.COPPER_INGOT
    };
    private static int currentItem = 0;

    private static final Item[] MODELS = new Item[] {
            Items.WHITE_WOOL,
            Items.ORANGE_WOOL,
            Items.MAGENTA_WOOL,
            Items.LIGHT_BLUE_WOOL,
            Items.YELLOW_WOOL,
            Items.LIME_WOOL,
            Items.PINK_WOOL,
            Items.GRAY_WOOL,
            Items.LIGHT_GRAY_WOOL,
            Items.CYAN_WOOL,
            Items.PURPLE_WOOL,
            Items.BLUE_WOOL,
            Items.BROWN_WOOL,
            Items.GREEN_WOOL,
            Items.RED_WOOL,
            Items.BLACK_WOOL,
            Items.WHITE_TERRACOTTA,
            Items.ORANGE_TERRACOTTA,
            Items.MAGENTA_TERRACOTTA,
            Items.LIGHT_BLUE_TERRACOTTA,
            Items.YELLOW_TERRACOTTA,
            Items.LIME_TERRACOTTA,
            Items.PINK_TERRACOTTA,
            Items.GRAY_TERRACOTTA,
            Items.LIGHT_GRAY_TERRACOTTA,
            Items.CYAN_TERRACOTTA,
            Items.PURPLE_TERRACOTTA,
            Items.BLUE_TERRACOTTA,
            Items.BROWN_TERRACOTTA,
            Items.GREEN_TERRACOTTA,
            Items.RED_TERRACOTTA,
            Items.BLACK_TERRACOTTA
    };

    private static int currentModels = 0;


    public static Item requestItem() {
        return ITEMS[currentItem++ % ITEMS.length];
    }

    /**
     * Use for shiny items when used with iris shaders or something
     */
    public static Item requestMetalicItem() {
        return METALIC_ITEMS[currentItem++ % METALIC_ITEMS.length];
    }

    public static Item requestModel() {
        return MODELS[currentModels++ % MODELS.length];
    }

    public static ItemStack requestModel(Identifier model) {
        return requestModel(requestItem(), model);
    }

    public static ItemStack requestModel(Item item, Identifier model) {
        var stack = new ItemStack(item);
        stack.getOrCreateNbt().putInt("CustomModelData", PolymerResourcePackUtils.requestModel(stack.getItem(), model).value());
        return stack;
    }
}
