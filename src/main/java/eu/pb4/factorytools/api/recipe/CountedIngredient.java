package eu.pb4.factorytools.api.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import eu.pb4.factorytools.api.util.ItemComponentPredicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.ComponentPredicate;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

import java.util.List;

public record CountedIngredient(Ingredient ingredient, ItemComponentPredicate component, int count, ItemStack leftOver) {

    public CountedIngredient(Ingredient ingredient, int count, ItemStack leftOver) {
        this(ingredient, ItemComponentPredicate.EMPTY, count, leftOver);
    }
    public static final CountedIngredient EMPTY = new CountedIngredient(Ingredient.EMPTY, ItemComponentPredicate.EMPTY, 0, ItemStack.EMPTY);

    public static final Codec<CountedIngredient> CODEC_SELF = RecordCodecBuilder.create(x -> x.group(
                    LazyRecipeSerializer.INGREDIENT_CODEC.fieldOf("base").forGetter(CountedIngredient::ingredient),
                    ItemComponentPredicate.CODEC.optionalFieldOf("component", ItemComponentPredicate.EMPTY).forGetter(CountedIngredient::component),
                    Codec.INT.optionalFieldOf("count", 1).forGetter(CountedIngredient::count),
                    ItemStack.CODEC.optionalFieldOf("leftover", ItemStack.EMPTY).forGetter(CountedIngredient::leftOver)
            ).apply(x, CountedIngredient::new)
    );

    public static final Codec<CountedIngredient> CODEC_SELF_UNCOUNTED = RecordCodecBuilder.create(x -> x.group(
                    LazyRecipeSerializer.INGREDIENT_CODEC.fieldOf("base").forGetter(CountedIngredient::ingredient),
                    ItemComponentPredicate.CODEC.optionalFieldOf("component", ItemComponentPredicate.EMPTY).forGetter(CountedIngredient::component),
                    MapCodec.unit(1).forGetter(CountedIngredient::count),
                    ItemStack.CODEC.optionalFieldOf("leftover", ItemStack.EMPTY).forGetter(CountedIngredient::leftOver)
            ).apply(x, CountedIngredient::new)
    );

    public static final Codec<CountedIngredient> CODEC = Codec.withAlternative(CODEC_SELF, Ingredient.ALLOW_EMPTY_CODEC,
            y -> new CountedIngredient(y, ItemComponentPredicate.EMPTY, 1, tryGettingLeftover(y)));

    public static final Codec<CountedIngredient> CODEC_UNCOUNTED = Codec.withAlternative(CODEC_SELF_UNCOUNTED, Ingredient.ALLOW_EMPTY_CODEC,
            y -> new CountedIngredient(y, ItemComponentPredicate.EMPTY, 1, tryGettingLeftover(y)));

    public static ItemStack tryGettingLeftover(Ingredient y) {
        if (y.getMatchingStacks().length == 1) {
            return y.getMatchingStacks()[0].getRecipeRemainder();
        }

        return ItemStack.EMPTY;
    }

    public static final Codec<List<CountedIngredient>> LIST_CODEC = Codec.either(CODEC, Codec.list(CODEC))
            .xmap(x -> x.map(y -> List.of(y), y -> y), x -> x.size() == 1 ? Either.left(x.get(0)) : Either.right(x));

    public static CountedIngredient ofItems(int count, ItemConvertible... items) {
        return new CountedIngredient(Ingredient.ofItems(items), ItemComponentPredicate.EMPTY, count, ItemStack.EMPTY);
    }

    public static CountedIngredient ofItemWithPredicate(int count, ItemConvertible item, ItemComponentPredicate predicate) {
        return new CountedIngredient(Ingredient.ofItems(item), predicate, count, ItemStack.EMPTY);
    }

    public static CountedIngredient ofItemsRemainder(int count, ItemConvertible item, ItemConvertible remainder) {
        return new CountedIngredient(Ingredient.ofItems(item), ItemComponentPredicate.EMPTY, count, new ItemStack(remainder.asItem(), count));
    }

    public static CountedIngredient ofStacks(int count, ItemStack... stacks) {
        return new CountedIngredient(Ingredient.ofStacks(stacks), ItemComponentPredicate.EMPTY, count, ItemStack.EMPTY);
    }

    public static CountedIngredient fromTag(int count, TagKey<Item> tag) {
        return new CountedIngredient(Ingredient.fromTag(tag), ItemComponentPredicate.EMPTY, count, ItemStack.EMPTY);
    }

    public boolean test(ItemStack stack) {
        return stack.getCount() >= this.count && this.ingredient.test(stack) && this.component.test(stack);
    }

}