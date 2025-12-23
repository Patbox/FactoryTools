package eu.pb4.factorytools.api.util;

import java.util.HashMap;
import java.util.function.Predicate;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ExtraItemPredicates {
    public static HashMap<Identifier, Predicate<ItemStack>> PREDICATES = new HashMap<>();

    public static void register() {

    }

}
