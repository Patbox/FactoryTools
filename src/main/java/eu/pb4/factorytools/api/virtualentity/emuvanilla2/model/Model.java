package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

import eu.pb4.factorytools.api.virtualentity.emuvanilla2.CubeConsumer;
import net.minecraft.util.Unit;
import org.joml.Matrix4fStack;

import java.util.List;

public abstract class Model<S> {
    protected final ModelPart root;
    private final List<ModelPart> parts;

    public Model(ModelPart root) {
        this.root = root;
        this.parts = root.getAllParts();
    }

    public void render(Matrix4fStack matrices, CubeConsumer vertices) {
        this.root().render(matrices, vertices);
    }

    public final ModelPart root() {
        return this.root;
    }

    public final List<ModelPart> allParts() {
        return this.parts;
    }

    public void setupAnim(final S state) {
        this.resetPose();
    }

    public final void resetPose() {
        for (var part : this.parts) {
            part.resetPose();
        }
    }

    public static class Simple extends Model<Unit> {
        public Simple(final ModelPart root) {
            super(root);
        }

        public void setupAnim(final Unit state) {
        }
    }
}