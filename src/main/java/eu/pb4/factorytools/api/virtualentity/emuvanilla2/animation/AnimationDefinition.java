package eu.pb4.factorytools.api.virtualentity.emuvanilla2.animation;

import com.google.common.collect.Maps;
import eu.pb4.factorytools.api.virtualentity.emuvanilla2.model.ModelPart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record AnimationDefinition(float lengthInSeconds, boolean looping,
                                  Map<String, List<AnimationChannel>> boneAnimations) {
    public KeyframeAnimation bake(ModelPart root) {
        return KeyframeAnimation.bake(root, this);
    }

    public static class Builder {
        private final float length;
        private final Map<String, List<AnimationChannel>> animationsByBone = Maps.newHashMap();
        private boolean looping;

        private Builder(float lengthInSeconds) {
            this.length = lengthInSeconds;
        }

        public static Builder withLength(float lengthInSeconds) {
            return new Builder(lengthInSeconds);
        }

        public Builder looping() {
            this.looping = true;
            return this;
        }

        public Builder addAnimation(String name, AnimationChannel transformation) {
            this.animationsByBone.computeIfAbsent(name, (namex) -> {
                return new ArrayList();
            }).add(transformation);
            return this;
        }

        public AnimationDefinition build() {
            return new AnimationDefinition(this.length, this.looping, this.animationsByBone);
        }
    }
}