package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

@FunctionalInterface
public interface ModelTransformer {
    ModelTransformer NO_OP = (data) -> {
        return data;
    };

    static ModelTransformer scaling(float scale) {
        float f = 24.016F * (1.0F - scale);
        return (data) -> {
            return data.transform((transform) -> {
                return transform.scaled(scale).moveOrigin(0.0F, f, 0.0F);
            });
        };
    }

    ModelData apply(ModelData modelData);
}