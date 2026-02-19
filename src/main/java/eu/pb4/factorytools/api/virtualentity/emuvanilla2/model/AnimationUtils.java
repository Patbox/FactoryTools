package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;

public class AnimationUtils {
    public static void animateCrossbowHold(ModelPart rightArm, ModelPart leftArm, ModelPart head, boolean rightHanded) {
        ModelPart modelPart = rightHanded ? rightArm : leftArm;
        ModelPart modelPart2 = rightHanded ? leftArm : rightArm;
        modelPart.yRot = (rightHanded ? -0.3F : 0.3F) + head.yRot;
        modelPart2.yRot = (rightHanded ? 0.6F : -0.6F) + head.yRot;
        modelPart.xRot = (-(float) Math.PI / 2F) + head.xRot + 0.1F;
        modelPart2.xRot = -1.5F + head.xRot;
    }

    public static void animateCrossbowCharge(ModelPart modelPart, ModelPart modelPart2, float f, float g, boolean bl) {
        ModelPart modelPart3 = bl ? modelPart : modelPart2;
        ModelPart modelPart4 = bl ? modelPart2 : modelPart;
        modelPart3.yRot = bl ? -0.8F : 0.8F;
        modelPart3.xRot = -0.97079635F;
        modelPart4.xRot = modelPart3.xRot;
        float h = Mth.clamp(g, 0.0F, f);
        float i = h / f;
        modelPart4.yRot = Mth.lerp(i, 0.4F, 0.85F) * (float) (bl ? 1 : -1);
        modelPart4.xRot = Mth.lerp(i, modelPart4.xRot, (-(float) Math.PI / 2F));
    }

    public static void swingWeaponDown(ModelPart rightArm, ModelPart leftArm, HumanoidArm mainArm, float attackTime, float ageInTicks) {
        float f = Mth.sin(attackTime * (float) Math.PI);
        float g = Mth.sin((1.0F - (1.0F - attackTime) * (1.0F - attackTime)) * (float) Math.PI);
        rightArm.zRot = 0.0F;
        leftArm.zRot = 0.0F;
        rightArm.yRot = 0.15707964F;
        leftArm.yRot = -0.15707964F;
        if (mainArm == HumanoidArm.RIGHT) {
            rightArm.xRot = -1.8849558F + Mth.cos(ageInTicks * 0.09F) * 0.15F;
            leftArm.xRot = -0.0F + Mth.cos(ageInTicks * 0.19F) * 0.5F;
            rightArm.xRot += f * 2.2F - g * 0.4F;
            leftArm.xRot += f * 1.2F - g * 0.4F;
        } else {
            rightArm.xRot = -0.0F + Mth.cos(ageInTicks * 0.19F) * 0.5F;
            leftArm.xRot = -1.8849558F + Mth.cos(ageInTicks * 0.09F) * 0.15F;
            rightArm.xRot += f * 1.2F - g * 0.4F;
            leftArm.xRot += f * 2.2F - g * 0.4F;
        }

        bobArms(rightArm, leftArm, ageInTicks);
    }

    public static void bobModelPart(ModelPart modelPart, float ageInTicks, float multiplier) {
        modelPart.zRot += multiplier * (Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
        modelPart.xRot += multiplier * Mth.sin(ageInTicks * 0.067F) * 0.05F;
    }

    public static void bobArms(ModelPart rightArm, ModelPart leftArm, float ageInTicks) {
        bobModelPart(rightArm, ageInTicks, 1.0F);
        bobModelPart(leftArm, ageInTicks, -1.0F);
    }

    public static <T extends LivingEntity> void animateZombieArms(ModelPart modelPart, ModelPart modelPart2, boolean bl, T undeadRenderState) {
        boolean bl2 = undeadRenderState.swingingArm != null && undeadRenderState.getItemInHand(undeadRenderState.swingingArm).getOrDefault(DataComponents.SWING_ANIMATION, SwingAnimation.DEFAULT).type() != SwingAnimationType.STAB;
        if (bl2) {
            float f = undeadRenderState.swingTime;
            float g = -(float) Math.PI / (bl ? 1.5F : 2.25F);
            float h = Mth.sin(f * (float) Math.PI);
            float i = Mth.sin((1.0F - (1.0F - f) * (1.0F - f)) * (float) Math.PI);
            modelPart2.zRot = 0.0F;
            modelPart2.yRot = -(0.1F - h * 0.6F);
            modelPart2.xRot = g;
            modelPart2.xRot += h * 1.2F - i * 0.4F;
            modelPart.zRot = 0.0F;
            modelPart.yRot = 0.1F - h * 0.6F;
            modelPart.xRot = g;
            modelPart.xRot += h * 1.2F - i * 0.4F;
        }

        bobArms(modelPart2, modelPart, undeadRenderState.tickCount);
    }
}
