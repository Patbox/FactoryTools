package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.CubeConsumer;
import org.joml.Matrix4fStack;

import java.util.List;

public abstract class Model {
    protected final ModelPart root;
    private final List<ModelPart> parts;

    public Model(ModelPart root) {
        this.root = root;
        this.parts = root.traverse();
    }

    public void render(Matrix4fStack matrices, CubeConsumer vertices) {
        this.getRootPart().render(matrices, vertices);
    }

    public final ModelPart getRootPart() {
        return this.root;
    }

    public final List<ModelPart> getParts() {
        return this.parts;
    }

    public final void resetTransforms() {
        for (var part : this.parts) {
            part.resetTransform();
        }
    }
}