package eu.pb4.factorytools.api.virtualentity.emuvanilla2;

import org.joml.Matrix4f;
import com.mojang.math.Axis;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class EntityModelTransforms {

    public static void livingEntityTransform(LivingEntity entity, Matrix4f mat) {
        livingEntityTransform(entity, mat, (mt) -> {});
    }

    public static void livingEntityTransform(LivingEntity entity, Matrix4f mat, Consumer<Matrix4f> scale) {
        livingEntityTransform(entity, mat, EntityValueExtraction.getBodyYaw(entity), scale);
    }

    public static void livingEntityTransform(LivingEntity entity, Matrix4f mat, float bodyYaw, Consumer<Matrix4f> scale) {
        if (entity.hasPose(Pose.SLEEPING)) {
            Direction direction = entity.getBedOrientation();
            if (direction != null) {
                float f = EntityValueExtraction.getStandingHeight(entity) - 0.1F;
                mat.translate((float) (-direction.getStepX()) * f, 0.0F, (float) (-direction.getStepZ()) * f);
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
        if (entity.isFullyFrozen()) {
            bodyYaw += (float) (Math.cos((float) Mth.floor(entity.tickCount) * 3.25F) * Math.PI * 0.4000000059604645);
        }

        if (!entity.hasPose(Pose.SLEEPING)) {
            matrices.rotate(Axis.YP.rotationDegrees(180.0F - bodyYaw));
        }

        if (entity.deathTime > 0.0F) {
            float f = (entity.deathTime - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }

            matrices.rotate(Axis.ZP.rotationDegrees(f * 90));
        } else if (entity.isAutoSpinAttack()) {
            matrices.rotate(Axis.XP.rotationDegrees(-90.0F - entity.getXRot()));
            matrices.rotate(Axis.YP.rotationDegrees(entity.tickCount * -75.0F));
        } else if (entity.hasPose(Pose.SLEEPING)) {
            Direction direction = entity.getBedOrientation();
            float g = direction != null ? getYaw(direction) : bodyYaw;
            matrices.rotate(Axis.YP.rotationDegrees(g));
            matrices.rotate(Axis.ZP.rotationDegrees(90));
            matrices.rotate(Axis.YP.rotationDegrees(270.0F));
        } else if (entity.hasCustomName()) {
            var name = entity.getCustomName().getString();
            if ("Dinnerbone".equals(name) || "Grumm".equals(name)) {
                matrices.translate(0.0F, (entity.getBbHeight() + 0.1F) / baseHeight, 0.0F);
                matrices.rotate(Axis.ZP.rotationDegrees(180.0F));
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
