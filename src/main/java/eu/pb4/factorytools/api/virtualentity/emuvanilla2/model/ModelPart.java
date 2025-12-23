package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

import eu.pb4.factorytools.api.virtualentity.emuvanilla2.CubeConsumer;
import org.joml.*;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.Nullable;

public final class ModelPart {
    public static final float DEFAULT_SCALE = 1.0F;
    public float x;
    public float y;
    public float z;
    public float xRot;
    public float yRot;
    public float zRot;
    public float xScale = 1.0F;
    public float yScale = 1.0F;
    public float zScale = 1.0F;
    public boolean visible = true;
    public boolean skipDraw;
    private final List<ModelPart.Cube> cubes;
    private final Map<String, ModelPart> children;
    private PartPose initialPose = PartPose.ZERO;

    public ModelPart(final List<ModelPart.Cube> cubes, final Map<String, ModelPart> children) {
        this.cubes = cubes;
        this.children = children;
    }

    public void render(Matrix4fStack matrices, CubeConsumer vertices) {
        if (this.visible) {
            if (!this.cubes.isEmpty() || !this.children.isEmpty()) {
                matrices.pushMatrix();
                this.translateAndRotate(matrices);
                vertices.consume(this, matrices, this.skipDraw);

                for (var child : this.children.values()) {
                    child.render(matrices, vertices);
                }

                matrices.popMatrix();
            }
        } else {
            vertices.consume(this, matrices, true);
            this.children.forEach((a, part) -> vertices.consume(part, matrices, true));
        }
    }
    public void forEachCuboid(Consumer<ModelPart.Cube> consumer) {
        this.cubes.forEach(consumer);
    }

    public void translateAndRotate(final Matrix4fStack poseStack) {
        poseStack.translate(this.x / 16.0F, this.y / 16.0F, this.z / 16.0F);
        if (this.xRot != 0.0F || this.yRot != 0.0F || this.zRot != 0.0F) {
            poseStack.rotate(new Quaternionf().rotationZYX(this.zRot, this.yRot, this.xRot));
        }

        if (this.xScale != 1.0F || this.yScale != 1.0F || this.zScale != 1.0F) {
            poseStack.scale(this.xScale, this.yScale, this.zScale);
        }
    }

    public PartPose storePose() {
        return PartPose.offsetAndRotation(this.x, this.y, this.z, this.xRot, this.yRot, this.zRot);
    }

    public PartPose getInitialPose() {
        return this.initialPose;
    }

    public void setInitialPose(final PartPose initialPose) {
        this.initialPose = initialPose;
    }

    public void resetPose() {
        this.loadPose(this.initialPose);
    }

    public void loadPose(final PartPose pose) {
        this.x = pose.x();
        this.y = pose.y();
        this.z = pose.z();
        this.xRot = pose.xRot();
        this.yRot = pose.yRot();
        this.zRot = pose.zRot();
        this.xScale = pose.xScale();
        this.yScale = pose.yScale();
        this.zScale = pose.zScale();
    }

    public boolean hasChild(final String name) {
        return this.children.containsKey(name);
    }

    public ModelPart getChild(final String name) {
        ModelPart result = (ModelPart)this.children.get(name);
        if (result == null) {
            throw new NoSuchElementException("Can't find part " + name);
        } else {
            return result;
        }
    }

    public void setPos(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setRotation(final float xRot, final float yRot, final float zRot) {
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
    }


    public void rotateBy(final Quaternionf rotation) {
        Matrix3f oldRotation = new Matrix3f().rotationZYX(this.zRot, this.yRot, this.xRot);
        Matrix3f newRotation = oldRotation.rotate(rotation);
        Vector3f newAngles = newRotation.getEulerAnglesZYX(new Vector3f());
        this.setRotation(newAngles.x, newAngles.y, newAngles.z);
    }


    public ModelPart.Cube getRandomCube(final RandomSource random) {
        return (ModelPart.Cube)this.cubes.get(random.nextInt(this.cubes.size()));
    }

    public boolean isEmpty() {
        return this.cubes.isEmpty();
    }

    public void offsetPos(final Vector3f offset) {
        this.x = this.x + offset.x();
        this.y = this.y + offset.y();
        this.z = this.z + offset.z();
    }

    public void offsetRotation(final Vector3f offset) {
        this.xRot = this.xRot + offset.x();
        this.yRot = this.yRot + offset.y();
        this.zRot = this.zRot + offset.z();
    }

    public void offsetScale(final Vector3f offset) {
        this.xScale = this.xScale + offset.x();
        this.yScale = this.yScale + offset.y();
        this.zScale = this.zScale + offset.z();
    }

    public List<ModelPart> getAllParts() {
        List<ModelPart> allParts = new ArrayList();
        allParts.add(this);
        this.addAllChildren((name, part) -> allParts.add(part));
        return List.copyOf(allParts);
    }

    public Function<String, @Nullable ModelPart> createPartLookup() {
        Map<String, ModelPart> parts = new HashMap();
        parts.put("root", this);
        this.addAllChildren(parts::putIfAbsent);
        return parts::get;
    }

    private void addAllChildren(final BiConsumer<String, ModelPart> output) {
        for (var entry : this.children.entrySet()) {
            output.accept((String)entry.getKey(), (ModelPart)entry.getValue());
        }

        for (ModelPart part : this.children.values()) {
            part.addAllChildren(output);
        }
    }

    public static class Cube {
        public final ModelPart.Polygon[] polygons;
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Cube(
                final int xTexOffs,
                final int yTexOffs,
                float minX,
                float minY,
                float minZ,
                final float width,
                final float height,
                final float depth,
                final float growX,
                final float growY,
                final float growZ,
                final boolean mirror,
                final float xTexSize,
                final float yTexSize,
                final Set<Direction> visibleFaces
        ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = minX + width;
            this.maxY = minY + height;
            this.maxZ = minZ + depth;
            this.polygons = new ModelPart.Polygon[visibleFaces.size()];
            float maxX = minX + width;
            float maxY = minY + height;
            float maxZ = minZ + depth;
            minX -= growX;
            minY -= growY;
            minZ -= growZ;
            maxX += growX;
            maxY += growY;
            maxZ += growZ;
            if (mirror) {
                float tmp = maxX;
                maxX = minX;
                minX = tmp;
            }

            ModelPart.Vertex t0 = new ModelPart.Vertex(minX, minY, minZ, 0.0F, 0.0F);
            ModelPart.Vertex t1 = new ModelPart.Vertex(maxX, minY, minZ, 0.0F, 8.0F);
            ModelPart.Vertex t2 = new ModelPart.Vertex(maxX, maxY, minZ, 8.0F, 8.0F);
            ModelPart.Vertex t3 = new ModelPart.Vertex(minX, maxY, minZ, 8.0F, 0.0F);
            ModelPart.Vertex l0 = new ModelPart.Vertex(minX, minY, maxZ, 0.0F, 0.0F);
            ModelPart.Vertex l1 = new ModelPart.Vertex(maxX, minY, maxZ, 0.0F, 8.0F);
            ModelPart.Vertex l2 = new ModelPart.Vertex(maxX, maxY, maxZ, 8.0F, 8.0F);
            ModelPart.Vertex l3 = new ModelPart.Vertex(minX, maxY, maxZ, 8.0F, 0.0F);
            float u0 = xTexOffs;
            float u1 = xTexOffs + depth;
            float u2 = xTexOffs + depth + width;
            float u22 = xTexOffs + depth + width + width;
            float u3 = xTexOffs + depth + width + depth;
            float u4 = xTexOffs + depth + width + depth + width;
            float v0 = yTexOffs;
            float v1 = yTexOffs + depth;
            float v2 = yTexOffs + depth + height;
            int pos = 0;
            if (visibleFaces.contains(Direction.DOWN)) {
                this.polygons[pos++] = new ModelPart.Polygon(new ModelPart.Vertex[]{l1, l0, t0, t1}, u1, v0, u2, v1, xTexSize, yTexSize, mirror, Direction.DOWN);
            }

            if (visibleFaces.contains(Direction.UP)) {
                this.polygons[pos++] = new ModelPart.Polygon(new ModelPart.Vertex[]{t2, t3, l3, l2}, u2, v1, u22, v0, xTexSize, yTexSize, mirror, Direction.UP);
            }

            if (visibleFaces.contains(Direction.WEST)) {
                this.polygons[pos++] = new ModelPart.Polygon(new ModelPart.Vertex[]{t0, l0, l3, t3}, u0, v1, u1, v2, xTexSize, yTexSize, mirror, Direction.WEST);
            }

            if (visibleFaces.contains(Direction.NORTH)) {
                this.polygons[pos++] = new ModelPart.Polygon(new ModelPart.Vertex[]{t1, t0, t3, t2}, u1, v1, u2, v2, xTexSize, yTexSize, mirror, Direction.NORTH);
            }

            if (visibleFaces.contains(Direction.EAST)) {
                this.polygons[pos++] = new ModelPart.Polygon(new ModelPart.Vertex[]{l1, t1, t2, l2}, u2, v1, u3, v2, xTexSize, yTexSize, mirror, Direction.EAST);
            }

            if (visibleFaces.contains(Direction.SOUTH)) {
                this.polygons[pos] = new ModelPart.Polygon(new ModelPart.Vertex[]{l0, l1, l2, l3}, u3, v1, u4, v2, xTexSize, yTexSize, mirror, Direction.SOUTH);
            }
        }
    }

    public record Polygon(ModelPart.Vertex[] vertices, Vector3fc normal) {
        public Polygon(
                final ModelPart.Vertex[] vertices,
                final float u0,
                final float v0,
                final float u1,
                final float v1,
                final float xTexSize,
                final float yTexSize,
                final boolean mirror,
                final Direction facing
        ) {
            this(vertices, (mirror ? mirrorFacing(facing) : facing).getUnitVec3f());
            float us = 0.0F / xTexSize;
            float vs = 0.0F / yTexSize;
            vertices[0] = vertices[0].remap(u1 / xTexSize - us, v0 / yTexSize + vs);
            vertices[1] = vertices[1].remap(u0 / xTexSize + us, v0 / yTexSize + vs);
            vertices[2] = vertices[2].remap(u0 / xTexSize + us, v1 / yTexSize - vs);
            vertices[3] = vertices[3].remap(u1 / xTexSize - us, v1 / yTexSize - vs);
            if (mirror) {
                int length = vertices.length;

                for (int i = 0; i < length / 2; i++) {
                    ModelPart.Vertex tmp = vertices[i];
                    vertices[i] = vertices[length - 1 - i];
                    vertices[length - 1 - i] = tmp;
                }
            }
        }

        private static Direction mirrorFacing(final Direction facing) {
            return facing.getAxis() == Direction.Axis.X ? facing.getOpposite() : facing;
        }
    }

    public record Vertex(float x, float y, float z, float u, float v) {
        public static final float SCALE_FACTOR = 16.0F;

        public ModelPart.Vertex remap(final float u, final float v) {
            return new ModelPart.Vertex(this.x, this.y, this.z, u, v);
        }

        public float worldX() {
            return this.x / 16.0F;
        }

        public float worldY() {
            return this.y / 16.0F;
        }

        public float worldZ() {
            return this.z / 16.0F;
        }
    }
}
