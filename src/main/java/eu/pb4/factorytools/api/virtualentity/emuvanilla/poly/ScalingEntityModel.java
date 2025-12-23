package eu.pb4.factorytools.api.virtualentity.emuvanilla.poly;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.PolyModelInstance;
import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Matrix4f;

public class ScalingEntityModel<T extends LivingEntity> extends SimpleEntityModel<T> {
    public ScalingEntityModel(T entity, PolyModelInstance<EntityModel<T>> model) {
        super(entity, model);
    }

    @Override
    protected void scale(Matrix4f matrix4f) {
        matrix4f.scale(this.entity.getAgeScale());
    }
}
