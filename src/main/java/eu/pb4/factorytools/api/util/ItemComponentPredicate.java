package eu.pb4.factorytools.api.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.*;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.component.ComponentMapPredicate;
import net.minecraft.predicate.component.ComponentPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public record ItemComponentPredicate(ComponentMapPredicate components, Map<ComponentPredicate.Type<?>, ComponentPredicate> subPredicates) implements Predicate<ItemStack> {
    public static final MapCodec<ItemComponentPredicate> MAP_CODEC = new MapCodec<>() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> RecordBuilder<T> encode(ItemComponentPredicate input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
            for (var x : input.components.toChanges().entrySet()) {
                prefix = prefix.add(Objects.requireNonNull(Registries.DATA_COMPONENT_TYPE.getId(x.getKey())).toString(),
                        ((Codec<Object>) (Object) x.getKey().getCodecOrThrow()).encodeStart(ops, x.getValue()).getOrThrow());
            }

            for (var x : input.subPredicates.entrySet()) {
                prefix = prefix.add("~" + Registries.DATA_COMPONENT_PREDICATE_TYPE.getId(x.getKey()),
                        ((Codec<Object>) x.getKey().getPredicateCodec()).encodeStart(ops, x.getValue()).getOrThrow());
            }

            return prefix;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> DataResult<ItemComponentPredicate> decode(DynamicOps<T> ops, MapLike<T> input) {
            try {
                var componentBuilder = ComponentMapPredicate.builder();
                var subPredicateBuilder = new ImmutableMap.Builder<ComponentPredicate.Type<?>, ComponentPredicate>();
                input.entries().forEach((pair) -> {
                    var key = ops.getStringValue(pair.getFirst()).getOrThrow();
                    if (key.charAt(key.length() - 1) == '~') {
                        var type = Registries.DATA_COMPONENT_PREDICATE_TYPE.get(Identifier.tryParse(key.substring(0, key.length() - 1)));
                        if (type != null) {
                            subPredicateBuilder.put(type, type.getPredicateCodec().decode(ops, pair.getSecond()).getOrThrow().getFirst());
                        }
                    } else {
                        var type = (ComponentType<Object>) Registries.DATA_COMPONENT_TYPE.get(Identifier.tryParse(key));
                        if (type != null) {
                            componentBuilder.add(type, type.getCodecOrThrow().decode(ops, pair.getSecond()).getOrThrow().getFirst());
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
    public static final ItemComponentPredicate EMPTY = new ItemComponentPredicate(ComponentMapPredicate.EMPTY, Map.of());

    @Override
    public boolean test(ItemStack stack) {
        if (!this.components.test(stack)) {
            return false;
        } else {
            Iterator var2 = this.subPredicates.values().iterator();

            ComponentPredicate itemSubPredicate;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                itemSubPredicate = (ComponentPredicate) var2.next();
            } while(itemSubPredicate.test(stack));

            return false;
        }
    }
}
