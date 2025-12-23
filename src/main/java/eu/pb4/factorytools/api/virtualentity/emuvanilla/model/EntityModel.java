package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

import net.minecraft.world.entity.Entity;

public abstract class EntityModel<T extends Entity> extends Model {
    protected EntityModel(ModelPart root) {
        super(root);
    }

    public void setAngles(T state) {
        this.resetTransforms();
        this.root.visible = !state.isInvisible();
    }
}