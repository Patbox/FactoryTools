package eu.pb4.factorytools.api.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public interface FactoryToolsTags {
    TagKey<Item> DEFAULT_PREVENT_USE = TagKey.of(RegistryKeys.ITEM, Identifier.of("factorytools", "default_prevent_use"));
}
