package eu.pb4.factorytools.api.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public class TriggerCriterion extends SimpleCriterionTrigger<TriggerCriterion.Condition> {
    public static Criterion<?> of(Identifier powerHandCrank) {
        return FactoryAdvancementCriteria.TRIGGER.createCriterion(new Condition(powerHandCrank));
    }

    public static void trigger(ServerPlayer player, Identifier identifier) {
        FactoryAdvancementCriteria.TRIGGER.trigger(player, condition -> condition.equals(identifier));
    }

    @Override
    public Codec<Condition> codec() {
        return Condition.CODEC;
    }

    public record Condition(Identifier identifier) implements SimpleInstance {
        public static final Codec<Condition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Identifier.CODEC.fieldOf("trigger").forGetter(Condition::identifier)
        ).apply(instance, Condition::new));
        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}
