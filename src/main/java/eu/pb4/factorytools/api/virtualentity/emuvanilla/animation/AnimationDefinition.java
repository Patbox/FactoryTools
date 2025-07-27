package eu.pb4.factorytools.api.virtualentity.emuvanilla.animation;

import com.google.common.collect.Maps;
import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.ModelPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record AnimationDefinition(float lengthInSeconds, boolean looping,
                                  Map<String, List<Transformation>> boneAnimations) {
    public Animation createAnimation(ModelPart root) {
        return Animation.of(root, this);
    }

    public float lengthInSeconds() {
        return this.lengthInSeconds;
    }

    public boolean looping() {
        return this.looping;
    }

    public Map<String, List<Transformation>> boneAnimations() {
        return this.boneAnimations;
    }

    public static class Builder {
        private final float lengthInSeconds;
        private final Map<String, List<Transformation>> transformations = Maps.newHashMap();
        private boolean looping;

        private Builder(float lengthInSeconds) {
            this.lengthInSeconds = lengthInSeconds;
        }

        public static Builder create(float lengthInSeconds) {
            return new Builder(lengthInSeconds);
        }

        public Builder looping() {
            this.looping = true;
            return this;
        }

        public Builder addBoneAnimation(String name, Transformation transformation) {
            this.transformations.computeIfAbsent(name, (namex) -> {
                return new ArrayList();
            }).add(transformation);
            return this;
        }

        public AnimationDefinition build() {
            return new AnimationDefinition(this.lengthInSeconds, this.looping, this.transformations);
        }
    }
}