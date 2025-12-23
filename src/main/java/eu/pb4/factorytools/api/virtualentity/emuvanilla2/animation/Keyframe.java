package eu.pb4.factorytools.api.virtualentity.emuvanilla2.animation;

import org.joml.Vector3fc;

public record Keyframe(float timestamp, Vector3fc postTarget, Vector3fc preTarget, AnimationChannel.Interpolation interpolation) {
    public Keyframe(final float timestamp, final Vector3fc postTarget, final AnimationChannel.Interpolation interpolation) {
        this(timestamp, postTarget, postTarget, interpolation);
    }
}