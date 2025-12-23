package eu.pb4.factorytools.api.virtualentity.emuvanilla2;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class EntityValueExtraction {

    public static float getStandingHeight(LivingEntity entity) {
        return entity.getEyeHeight(Pose.STANDING);
    }

    public static float getBodyYaw(LivingEntity entity) {
        return clampBodyYaw(entity, entity.getYHeadRot());
    }

    public static float getRelativeHeadYaw(LivingEntity entity) {
        float headYaw = entity.getYHeadRot();
        var bodyYaw = getBodyYaw(entity);
        return Mth.wrapDegrees(headYaw - bodyYaw);
    }

    private static float clampBodyYaw(LivingEntity entity, float degrees) {
        Entity var4 = entity.getVehicle();
        if (var4 instanceof LivingEntity livingEntity) {
            float bodyYaw = livingEntity.yBodyRot;
            float maxDiff = 85.0F;
            float clampedDegrees = Mth.clamp(Mth.wrapDegrees(degrees - bodyYaw), -maxDiff, maxDiff);
            bodyYaw = degrees - clampedDegrees;
            if (Math.abs(clampedDegrees) > 50.0F) {
                bodyYaw += clampedDegrees * 0.2F;
            }

            return bodyYaw;
        } else {
            return entity.yBodyRot;
        }
    }
}
