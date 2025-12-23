package eu.pb4.factorytools.api.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.predicates.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record ItemComponentPredicate(DataComponentExactPredicate components, Map<DataComponentPredicate.Type<?>, DataComponentPredicate> subPredicates) implements Predicate<ItemStack> {
    public static final MapCodec<ItemComponentPredicate> MAP_CODEC = new MapCodec<>() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> RecordBuilder<T> encode(ItemComponentPredicate input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
            for (var x : input.components.asPatch().entrySet()) {
                prefix = prefix.add(Objects.requireNonNull(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(x.getKey())).toString(),
                        ((Codec<Object>) (Object) x.getKey().codecOrThrow()).encodeStart(ops, x.getValue()).getOrThrow());
            }

            for (var x : input.subPredicates.entrySet()) {
                prefix = prefix.add("~" + BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE.getKey(x.getKey()),
                        ((Codec<Object>) x.getKey().codec()).encodeStart(ops, x.getValue()).getOrThrow());
            }

            return prefix;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> DataResult<ItemComponentPredicate> decode(DynamicOps<T> ops, MapLike<T> input) {
            try {
                var componentBuilder = DataComponentExactPredicate.builder();
                var subPredicateBuilder = new ImmutableMap.Builder<DataComponentPredicate.Type<?>, DataComponentPredicate>();
                input.entries().forEach((pair) -> {
                    var key = ops.getStringValue(pair.getFirst()).getOrThrow();
                    if (key.charAt(key.length() - 1) == '~') {
                        var type = BuiltInRegistries.DATA_COMPONENT_PREDICATE_TYPE.getValue(Identifier.tryParse(key.substring(0, key.length() - 1)));
                        if (type != null) {
                            subPredicateBuilder.put(type, type.codec().decode(ops, pair.getSecond()).getOrThrow().getFirst());
                        }
                    } else {
                        var type = (DataComponentType<Object>) BuiltInRegistries.DATA_COMPONENT_TYPE.getValue(Identifier.tryParse(key));
                        if (type != null) {
                            componentBuilder.expect(type, type.codecOrThrow().decode(ops, pair.getSecond()).getOrThrow().getFirst());
                        }
                    }
                });
                return DataResult.success(new ItemComponentPredicate(componentBuilder.build(), subPredicateBuilder.build()));

            } catch (Throwable e) {
                return DataResult.error(e::getMessage);
            }
        }

        @Override
        public <T> Stream<T> keys(DynamicOps<T> ops) {
            return Stream.empty();
        }
    };

    public static final Codec<ItemComponentPredicate> CODEC = MAP_CODEC.codec();
    public static final ItemComponentPredicate EMPTY = new ItemComponentPredicate(DataComponentExactPredicate.EMPTY, Map.of());

    @Override
    public boolean test(ItemStack stack) {
        if (!this.components.test(stack)) {
            return false;
        } else {
            Iterator var2 = this.subPredicates.values().iterator();

            DataComponentPredicate itemSubPredicate;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                itemSubPredicate = (DataComponentPredicate) var2.next();
            } while(itemSubPredicate.matches(stack));

            return false;
        }
    }
}
