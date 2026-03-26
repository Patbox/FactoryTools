package eu.pb4.factorytools.api.util;

import java.util.HashMap;
import java.util.function.Predicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemInstance;

public class ExtraItemPredicates {
    public static HashMap<Identifier, Predicate<ItemInstance>> PREDICATES = new HashMap<>();

    public static void register() {

    }

}
