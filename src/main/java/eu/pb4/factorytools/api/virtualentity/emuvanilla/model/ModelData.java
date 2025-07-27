package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

import com.google.common.collect.ImmutableList;

import java.util.function.UnaryOperator;

public class ModelData {
    private final ModelPartData data;

    public ModelData() {
        this(new ModelPartData(ImmutableList.of(), ModelTransform.NONE));
    }

    private ModelData(ModelPartData data) {
        this.data = data;
    }

    public ModelPartData getRoot() {
        return this.data;
    }

    public ModelData transform(UnaryOperator<ModelTransform> transformer) {
        return new ModelData(this.data.applyTransformer(transformer));
    }

    public ModelData transformX(ModelTransformer transformer) {
        return transformer.apply(this);
    }
}