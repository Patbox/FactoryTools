package eu.pb4.factorytools.api.virtualentity.emuvanilla2.animation;

import eu.pb4.factorytools.api.virtualentity.emuvanilla2.model.ModelPart;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;

public class KeyframeAnimation {
    private final AnimationDefinition definition;
    private final List<Entry> entries;

    private KeyframeAnimation(AnimationDefinition definition, List<Entry> entries) {
        this.definition = definition;
        this.entries = entries;
    }

    static KeyframeAnimation bake(ModelPart root, AnimationDefinition definition) {
        List<Entry> list = new ArrayList();
        Function<String, ModelPart> function = root.createPartLookup();
        Iterator var4 = definition.boneAnimations().entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, List<AnimationChannel>> entry = (Map.Entry) var4.next();
            String string = entry.getKey();
            List<AnimationChannel> list2 = entry.getValue();
            ModelPart modelPart = function.apply(string);
            if (modelPart == null) {
                throw new IllegalArgumentException("Cannot animate " + string + ", which does not exist in model");
            }

            Iterator var9 = list2.iterator();

            while (var9.hasNext()) {
                AnimationChannel transformation = (AnimationChannel) var9.next();
                list.add(new Entry(modelPart, transformation.target(), transformation.keyframes()));
            }
        }

        return new KeyframeAnimation(definition, List.copyOf(list));
    }

    public void applyStatic() {
        this.apply(0L, 1.0F);
    }

    public void applyWalk(float limbSwingAnimationProgress, float limbSwingAmplitude, float f, float g) {
        long l = (long) (limbSwingAnimationProgress * 50.0F * f);
        float h = Math.min(limbSwingAmplitude * g, 1.0F);
        this.apply(l, h);
    }

    public void apply(AnimationState animationState, float age) {
        this.apply(animationState, age, 1.0F);
    }

    public void apply(AnimationState animationState, float age, float speedMultiplier) {
        animationState.ifStarted((state) -> {
            this.apply((long) ((float) state.getTimeInMillis(age) * speedMultiplier), 1.0F);
        });
    }

    public void apply(long timeInMilliseconds, float scale) {
        float f = this.getElapsedSeconds(timeInMilliseconds);
        Iterator var5 = this.entries.iterator();
        Vector3f vec = new Vector3f();

        while (var5.hasNext()) {
            Entry transformationEntry = (Entry) var5.next();
            transformationEntry.apply(f, scale, vec);
        }

    }

    private float getElapsedSeconds(long timeInMilliseconds) {
        float f = (float) timeInMilliseconds / 1000.0F;
        return this.definition.looping() ? f % this.definition.lengthInSeconds() : f;
    }

    private record Entry(ModelPart part, AnimationChannel.Target target, Keyframe[] keyframes) {
        public void apply(float runningSeconds, float scale, Vector3f vec) {
            int i = Math.max(0, Mth.binarySearch(0, this.keyframes.length, (index) -> {
                return runningSeconds <= this.keyframes[index].timestamp();
            }) - 1);
            int j = Math.min(this.keyframes.length - 1, i + 1);
            Keyframe keyframe = this.keyframes[i];
            Keyframe keyframe2 = this.keyframes[j];
            float f = runningSeconds - keyframe.timestamp();
            float g;
            if (j != i) {
                g = Mth.clamp(f / (keyframe2.timestamp() - keyframe.timestamp()), 0.0F, 1.0F);
            } else {
                g = 0.0F;
            }

            keyframe2.interpolation().apply(vec, g, this.keyframes, i, j, scale);
            this.target.apply(this.part, vec);
        }
    }
}