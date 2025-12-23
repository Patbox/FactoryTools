package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

import net.minecraft.world.entity.Entity;

public abstract class EntityModel<T extends Entity> extends Model<T> {
    public static final float MODEL_Y_OFFSET = -1.501F;

    protected EntityModel(ModelPart root) {
        super(root);
    }

    public void setupAnim(T state) {
        this.resetPose();
        this.root.visible = !state.isInvisible();
    }
}