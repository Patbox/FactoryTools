package eu.pb4.factorytools.api.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.ItemLike;

public record OutputStack(ItemStackTemplate stack, float chance, int roll) {
    public static final Codec<OutputStack> CODEC_SELF = RecordCodecBuilder.create(x -> x.group(
                    ItemStackTemplate.CODEC.fieldOf("item").forGetter(OutputStack::stack),
                    Codec.FLOAT.optionalFieldOf("chance", 1f).forGetter(OutputStack::chance),
                    Codec.INT.optionalFieldOf("roll", 1).forGetter(OutputStack::roll)
            ).apply(x, OutputStack::new)
    );

    public static final Codec<OutputStack> CODEC = Codec.either(CODEC_SELF, ItemStackTemplate.CODEC).xmap(x -> x.map(y -> y, y -> new OutputStack(y, 1f, 1)), Either::left);
    public static final Codec<List<OutputStack>> LIST_CODEC = Codec.either(CODEC, Codec.list(CODEC))
            .xmap(x -> x.map(y -> List.of(y), y -> y), x -> x.size() == 1 ? Either.left(x.get(0)) : Either.right(x));

    public static OutputStack of(ItemLike item) {
        return new OutputStack(new ItemStackTemplate(item.asItem()), 1, 1);
    }
    public static OutputStack of(ItemLike item, float chance, int roll) {
        return new OutputStack(new ItemStackTemplate(item.asItem()), chance, roll);
    }

    public static OutputStack of(ItemLike item, float chance) {
        return new OutputStack(new ItemStackTemplate(item.asItem()), chance, 1);
    }

    public static OutputStack of(ItemStackTemplate item) {
        return new OutputStack(item, 1, 1);
    }
    public static OutputStack of(ItemStackTemplate item, float chance, int roll) {
        return new OutputStack(item, chance, roll);
    }

    public static OutputStack of(ItemStackTemplate item, float chance) {
        return new OutputStack(item, chance, 1);
    }
}
