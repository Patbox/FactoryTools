package eu.pb4.factorytools.api.virtualentity.emuvanilla.animation;

import org.joml.Vector3f;

public record Keyframe(float timestamp, Vector3f target, Transformation.Interpolation interpolation) {
    public float timestamp() {
        return this.timestamp;
    }

    public Vector3f target() {
        return this.target;
    }

    public Transformation.Interpolation interpolation() {
        return this.interpolation;
    }
}