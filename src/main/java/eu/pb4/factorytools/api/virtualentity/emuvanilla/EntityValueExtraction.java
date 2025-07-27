package eu.pb4.factorytools.api.virtualentity.emuvanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class EntityValueExtraction {

    public static float getStandingHeight(LivingEntity entity) {
        return entity.getEyeHeight(EntityPose.STANDING);
    }

    public static float getBodyYaw(LivingEntity entity) {
        return clampBodyYaw(entity, entity.getHeadYaw());
    }

    public static float getRelativeHeadYaw(LivingEntity entity) {
        float headYaw = entity.getHeadYaw();
        var bodyYaw = getBodyYaw(entity);
        return MathHelper.wrapDegrees(headYaw - bodyYaw);
    }

    private static float clampBodyYaw(LivingEntity entity, float degrees) {
        Entity var4 = entity.getVehicle();
        if (var4 instanceof LivingEntity livingEntity) {
            float bodyYaw = livingEntity.bodyYaw;
            float maxDiff = 85.0F;
            float clampedDegrees = MathHelper.clamp(MathHelper.wrapDegrees(degrees - bodyYaw), -maxDiff, maxDiff);
            bodyYaw = degrees - clampedDegrees;
            if (Math.abs(clampedDegrees) > 50.0F) {
                bodyYaw += clampedDegrees * 0.2F;
            }

            return bodyYaw;
        } else {
            return entity.bodyYaw;
        }
    }
}
