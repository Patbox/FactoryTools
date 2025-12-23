package eu.pb4.factorytools.impl;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public interface ExtraItemPredicate {
    static ItemPredicate.Builder withStatic(ItemPredicate.Builder builder, Identifier id) {
        ((ExtraItemPredicate) builder).factorytools$setStaticPredicate(id);


        return builder;
    }

    void factorytools$setStaticPredicate(Identifier identifier);
    @Nullable
    Identifier factorytools$getStaticPredicate();
}
