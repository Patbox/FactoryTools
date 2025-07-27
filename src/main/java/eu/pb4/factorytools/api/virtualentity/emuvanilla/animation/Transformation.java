package eu.pb4.factorytools.api.virtualentity.emuvanilla.animation;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.ModelPart;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

public record Transformation(Target target, Keyframe... keyframes) {
    public Transformation(Target target, Keyframe... keyframes) {
        this.target = target;
        this.keyframes = keyframes;
    }

    public Target target() {
        return this.target;
    }

    public Keyframe[] keyframes() {
        return this.keyframes;
    }

    public interface Target {
        void apply(ModelPart modelPart, Vector3f vec);
    }

    public static class Interpolations {
        public static final Interpolation LINEAR = (dest, delta, keyframes, start, end, scale) -> {
            Vector3f vector3f = keyframes[start].target();
            Vector3f vector3f2 = keyframes[end].target();
            return vector3f.lerp(vector3f2, delta, dest).mul(scale);
        };
        public static final Interpolation CUBIC = (dest, delta, keyframes, start, end, scale) -> {
            Vector3f vector3f = keyframes[Math.max(0, start - 1)].target();
            Vector3f vector3f2 = keyframes[start].target();
            Vector3f vector3f3 = keyframes[end].target();
            Vector3f vector3f4 = keyframes[Math.min(keyframes.length - 1, end + 1)].target();
            dest.set(MathHelper.catmullRom(delta, vector3f.x(), vector3f2.x(), vector3f3.x(), vector3f4.x()) * scale, MathHelper.catmullRom(delta, vector3f.y(), vector3f2.y(), vector3f3.y(), vector3f4.y()) * scale, MathHelper.catmullRom(delta, vector3f.z(), vector3f2.z(), vector3f3.z(), vector3f4.z()) * scale);
            return dest;
        };

        public Interpolations() {
        }
    }

    public static class Targets {
        public static final Target MOVE_ORIGIN = ModelPart::moveOrigin;
        public static final Target ROTATE = ModelPart::rotate;
        public static final Target SCALE = ModelPart::scale;

        public Targets() {
        }
    }

    public interface Interpolation {
        Vector3f apply(Vector3f dest, float delta, Keyframe[] keyframes, int start, int end, float scale);
    }
}
