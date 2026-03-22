package eu.pb4.factorytools.api.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.factorytools.api.util.ItemComponentPredicate;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

public record CountedIngredient(Optional<Ingredient> ingredient, ItemComponentPredicate component, int count, Optional<ItemStackTemplate> leftOver) {
    public static final CountedIngredient EMPTY = new CountedIngredient(Optional.empty(), ItemComponentPredicate.EMPTY, 0, Optional.empty());

    public static final Codec<CountedIngredient> CODEC_SELF = RecordCodecBuilder.create(x -> x.group(
                    Ingredient.CODEC.optionalFieldOf("base").forGetter(CountedIngredient::ingredient),
                    ItemComponentPredicate.CODEC.optionalFieldOf("component", ItemComponentPredicate.EMPTY).forGetter(CountedIngredient::component),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(CountedIngredient::count),
                    ItemStackTemplate.CODEC.optionalFieldOf("leftover").forGetter(CountedIngredient::leftOver)
            ).apply(x, CountedIngredient::new)
    );

    public static final Codec<CountedIngredient> CODEC_SELF_UNCOUNTED = RecordCodecBuilder.create(x -> x.group(
            Ingredient.CODEC.optionalFieldOf("base").forGetter(CountedIngredient::ingredient),
                    ItemComponentPredicate.CODEC.optionalFieldOf("component", ItemComponentPredicate.EMPTY).forGetter(CountedIngredient::component),
                    MapCodec.unit(1).forGetter(CountedIngredient::count),
                    ItemStackTemplate.CODEC.optionalFieldOf("leftover").forGetter(CountedIngredient::leftOver)
            ).apply(x, CountedIngredient::new)
    );

    public static final Codec<CountedIngredient> CODEC = Codec.withAlternative(CODEC_SELF, Ingredient.CODEC,
            y -> new CountedIngredient(Optional.of(y), ItemComponentPredicate.EMPTY, 1, tryGettingLeftover(y)));

    public static final Codec<CountedIngredient> CODEC_UNCOUNTED = Codec.withAlternative(CODEC_SELF_UNCOUNTED, Ingredient.CODEC,
            y -> new CountedIngredient(Optional.of(y), ItemComponentPredicate.EMPTY, 1, tryGettingLeftover(y)));

    public static Optional<ItemStackTemplate> tryGettingLeftover(Ingredient y) {
        if (y.items().count() > 0) {
            return Optional.ofNullable(y.items().findFirst().get().value().getCraftingRemainder());
        }

        return Optional.empty();
    }

    public static final Codec<List<CountedIngredient>> LIST_CODEC = Codec.either(CODEC, Codec.list(CODEC))
            .xmap(x -> x.map(List::of, y -> y), x -> x.size() == 1 ? Either.left(x.get(0)) : Either.right(x));

    public static CountedIngredient ofItems(int count, ItemLike... items) {
        return new CountedIngredient(Optional.of(Ingredient.of(items)), ItemComponentPredicate.EMPTY, count, Optional.empty());
    }

    public static CountedIngredient ofItemWithPredicate(int count, ItemLike item, ItemComponentPredicate predicate) {
        return new CountedIngredient(Optional.of(Ingredient.of(item)), predicate, count, Optional.empty());
    }

    public static CountedIngredient ofItemsRemainder(int count, ItemLike item, ItemLike remainder) {
        return new CountedIngredient(Optional.of(Ingredient.of(item)), ItemComponentPredicate.EMPTY, count, Optional.of(new ItemStackTemplate(remainder.asItem(), count)));
    }

    @Deprecated
    public static CountedIngredient fromTag(int count, HolderSet<Item> tag) {
        return ofTag(count, tag);
    }

    public static CountedIngredient ofTag(int count, HolderSet<Item> tag) {
        return new CountedIngredient(Optional.of(Ingredient.of(tag)), ItemComponentPredicate.EMPTY, count, Optional.empty());
    }

    public boolean test(ItemStack stack) {
        return stack.getCount() >= this.count && (this.ingredient.isEmpty() || this.ingredient.get().test(stack)) && this.component.test(stack);
    }

}