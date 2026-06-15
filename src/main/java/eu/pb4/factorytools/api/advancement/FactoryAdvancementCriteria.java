package eu.pb4.factorytools.api.advancement;

import eu.pb4.factorytools.impl.ModInit;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class FactoryAdvancementCriteria {
    public static final TriggerCriterion TRIGGER = register("trigger", new TriggerCriterion());
    public static void register() {

    }


    public static <E extends CriterionTriggerInstance, T extends CriterionTrigger<E>> T register(String path, T item) {
        return Registry.register(BuiltInRegistries.TRIGGER_TYPES, Identifier.fromNamespaceAndPath(ModInit.ID, path), item);
    }
}
