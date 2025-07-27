package eu.pb4.factorytools.api.virtualentity.emuvanilla.animation;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.ModelPart;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Animation {
    private final AnimationDefinition definition;
    private final List<TransformationEntry> entries;
    private final Vector3f vec = new Vector3f();

    private Animation(AnimationDefinition definition, List<TransformationEntry> entries) {
        this.definition = definition;
        this.entries = entries;
    }

    static Animation of(ModelPart root, AnimationDefinition definition) {
        List<TransformationEntry> list = new ArrayList();
        Function<String, ModelPart> function = root.createPartGetter();
        Iterator var4 = definition.boneAnimations().entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, List<Transformation>> entry = (Map.Entry) var4.next();
            String string = entry.getKey();
            List<Transformation> list2 = entry.getValue();
            ModelPart modelPart = function.apply(string);
            if (modelPart == null) {
                throw new IllegalArgumentException("Cannot animate " + string + ", which does not exist in model");
            }

            Iterator var9 = list2.iterator();

            while (var9.hasNext()) {
                Transformation transformation = (Transformation) var9.next();
                list.add(new TransformationEntry(modelPart, transformation.target(), transformation.keyframes()));
            }
        }

        return new Animation(definition, List.copyOf(list));
    }

    public void applyStatic() {
        this.apply(0L, 1.0F);
    }

    public void applyWalking(float limbSwingAnimationProgress, float limbSwingAmplitude, float f, float g) {
        long l = (long) (limbSwingAnimationProgress * 50.0F * f);
        float h = Math.min(limbSwingAmplitude * g, 1.0F);
        this.apply(l, h);
    }

    public void apply(AnimationState animationState, float age) {
        this.apply(animationState, age, 1.0F);
    }

    public void apply(AnimationState animationState, float age, float speedMultiplier) {
        animationState.run((state) -> {
            this.apply((long) ((float) state.getTimeInMilliseconds(age) * speedMultiplier), 1.0F);
        });
    }

    public void apply(long timeInMilliseconds, float scale) {
        float f = this.getRunningSeconds(timeInMilliseconds);
        Iterator var5 = this.entries.iterator();

        while (var5.hasNext()) {
            TransformationEntry transformationEntry = (TransformationEntry) var5.next();
            transformationEntry.apply(f, scale, this.vec);
        }

    }

    private float getRunningSeconds(long timeInMilliseconds) {
        float f = (float) timeInMilliseconds / 1000.0F;
        return this.definition.looping() ? f % this.definition.lengthInSeconds() : f;
    }

    private record TransformationEntry(ModelPart part, Transformation.Target target, Keyframe[] keyframes) {
        public void apply(float runningSeconds, float scale, Vector3f vec) {
            int i = Math.max(0, MathHelper.binarySearch(0, this.keyframes.length, (index) -> {
                return runningSeconds <= this.keyframes[index].timestamp();
            }) - 1);
            int j = Math.min(this.keyframes.length - 1, i + 1);
            Keyframe keyframe = this.keyframes[i];
            Keyframe keyframe2 = this.keyframes[j];
            float f = runningSeconds - keyframe.timestamp();
            float g;
            if (j != i) {
                g = MathHelper.clamp(f / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
            } else {
                g = 0.0F;
            }

            keyframe2.interpolation().apply(vec, g, this.keyframes, i, j, scale);
            this.target.apply(this.part, vec);
        }

        public ModelPart part() {
            return this.part;
        }

        public Transformation.Target target() {
            return this.target;
        }

        public Keyframe[] keyframes() {
            return this.keyframes;
        }
    }
}