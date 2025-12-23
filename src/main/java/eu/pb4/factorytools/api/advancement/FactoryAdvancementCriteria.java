package eu.pb4.factorytools.api.advancement;

import eu.pb4.factorytools.impl.ModInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;

public class FactoryAdvancementCriteria {
    public static final TriggerCriterion TRIGGER = register("trigger", new TriggerCriterion());
    public static void register() {

    }


    public static <E extends CriterionTriggerInstance, T extends CriterionTrigger<E>> T register(String path, T item) {
        CriteriaTriggers.register(ModInit.ID + ":" + path, item);
        return item;
    }
}
