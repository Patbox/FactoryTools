package eu.pb4.factorytools.api.virtualentity.emuvanilla;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

import java.util.function.Consumer;

public class EntityModelTransforms {

    public static void livingEntityTransform(LivingEntity entity, Matrix4f mat) {
        livingEntityTransform(entity, mat, (mt) -> {});
    }

    public static void livingEntityTransform(LivingEntity entity, Matrix4f mat, Consumer<Matrix4f> scale) {
        livingEntityTransform(entity, mat, EntityValueExtraction.getBodyYaw(entity), scale);
    }

    public static void livingEntityTransform(LivingEntity entity, Matrix4f mat, float bodyYaw, Consumer<Matrix4f> scale) {
        if (entity.isInPose(EntityPose.SLEEPING)) {
            Direction direction = entity.getSleepingDirection();
            if (direction != null) {
                float f = EntityValueExtraction.getStandingHeight(entity) - 0.1F;
                mat.translate((float) (-direction.getOffsetX()) * f, 0.0F, (float) (-direction.getOffsetZ()) * f);
            }
        }

        float scaleVal = entity.getScale();
        mat.scale(scaleVal);
        setupTransforms(entity, mat, bodyYaw, scaleVal);
        mat.scale(-1.0F, -1.0F, 1.0F);
        scale.accept(mat);
        mat.translate(0.0F, -1.501F, 0.0F);
    }

    protected static void setupTransforms(LivingEntity entity, Matrix4f matrices, float bodyYaw, float baseHeight) {
        if (entity.isFrozen()) {
            bodyYaw += (float) (Math.cos((float) MathHelper.floor(entity.age) * 3.25F) * Math.PI * 0.4000000059604645);
        }

        if (!entity.isInPose(EntityPose.SLEEPING)) {
            matrices.rotate(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - bodyYaw));
        }

        if (entity.deathTime > 0.0F) {
            float f = (entity.deathTime - 1.0F) / 20.0F * 1.6F;
            f = MathHelper.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            matrices.rotate(RotationAxis.POSITIVE_Z.rotationDegrees(f * 90));
        } else if (entity.isUsingRiptide()) {
            matrices.rotate(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F - entity.getPitch()));
            matrices.rotate(RotationAxis.POSITIVE_Y.rotationDegrees(entity.age * -75.0F));
        } else if (entity.isInPose(EntityPose.SLEEPING)) {
            Direction direction = entity.getSleepingDirection();
            float g = direction != null ? getYaw(direction) : bodyYaw;
            matrices.rotate(RotationAxis.POSITIVE_Y.rotationDegrees(g));
            matrices.rotate(RotationAxis.POSITIVE_Z.rotationDegrees(90));
            matrices.rotate(RotationAxis.POSITIVE_Y.rotationDegrees(270.0F));
        } else if (entity.hasCustomName()) {
            var name = entity.getCustomName().getString();
            if ("Dinnerbone".equals(name) || "Grumm".equals(name)) {
                matrices.translate(0.0F, (entity.getHeight() + 0.1F) / baseHeight, 0.0F);
                matrices.rotate(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
            }
        }
    }

    private static float getYaw(Direction direction) {
        return switch (direction) {
            case SOUTH -> 90.0F;
            case WEST -> 0.0F;
            case NORTH -> 270.0F;
            case EAST -> 180.0F;
            default -> 0.0F;
        };
    }
}
