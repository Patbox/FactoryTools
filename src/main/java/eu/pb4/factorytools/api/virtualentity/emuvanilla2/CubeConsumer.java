package eu.pb4.factorytools.api.virtualentity.emuvanilla2;

import eu.pb4.factorytools.api.virtualentity.emuvanilla2.model.ModelPart;
import org.joml.Matrix4f;

public interface CubeConsumer {
    void consume(ModelPart part, Matrix4f matrix4f, boolean hidden);
}