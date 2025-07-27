package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.CubeConsumer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.joml.Matrix3f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ModelPart {
    private final List<Cuboid> cuboids;
    private final Map<String, ModelPart> children;
    public float originX;
    public float originY;
    public float originZ;
    public float pitch;
    public float yaw;
    public float roll;
    public float xScale = 1.0F;
    public float yScale = 1.0F;
    public float zScale = 1.0F;
    public boolean visible = true;
    public boolean hidden;
    private ModelTransform defaultTransform;

    public ModelPart(List<Cuboid> cuboids, Map<String, ModelPart> children) {
        this.defaultTransform = ModelTransform.NONE;
        this.cuboids = cuboids;
        this.children = children;
    }

    public ModelTransform getTransform() {
        return ModelTransform.of(this.originX, this.originY, this.originZ, this.pitch, this.yaw, this.roll);
    }

    public void setTransform(ModelTransform transform) {
        this.originX = transform.x();
        this.originY = transform.y();
        this.originZ = transform.z();
        this.pitch = transform.pitch();
        this.yaw = transform.yaw();
        this.roll = transform.roll();
        this.xScale = transform.xScale();
        this.yScale = transform.yScale();
        this.zScale = transform.zScale();
    }

    public ModelTransform getDefaultTransform() {
        return this.defaultTransform;
    }

    public void setDefaultTransform(ModelTransform transform) {
        this.defaultTransform = transform;
    }

    public void resetTransform() {
        this.setTransform(this.defaultTransform);
    }

    public void copyTransform(ModelPart part) {
        this.xScale = part.xScale;
        this.yScale = part.yScale;
        this.zScale = part.zScale;
        this.pitch = part.pitch;
        this.yaw = part.yaw;
        this.roll = part.roll;
        this.originX = part.originX;
        this.originY = part.originY;
        this.originZ = part.originZ;
    }

    public boolean hasChild(String child) {
        return this.children.containsKey(child);
    }

    public ModelPart getChild(String name) {
        ModelPart modelPart = this.children.get(name);
        if (modelPart == null) {
            throw new NoSuchElementException("Can't find part " + name);
        } else {
            return modelPart;
        }
    }

    public void setOrigin(float x, float y, float z) {
        this.originX = x;
        this.originY = y;
        this.originZ = z;
    }

    public void setAngles(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public void render(Matrix4fStack matrices, CubeConsumer vertices) {
        if (this.visible) {
            if (!this.cuboids.isEmpty() || !this.children.isEmpty()) {
                matrices.pushMatrix();
                this.applyTransform(matrices);
                vertices.consume(this, matrices, this.hidden);

                for (var child : this.children.values()) {
                    child.render(matrices, vertices);
                }

                matrices.popMatrix();
            }
        } else {
            vertices.consume(this, matrices, true);
            this.forEachChild((a, part) -> vertices.consume(part, matrices, true));
        }
    }

    public void rotate(Quaternionf quaternion) {
        Matrix3f matrix3f = (new Matrix3f()).rotationZYX(this.roll, this.yaw, this.pitch);
        Matrix3f matrix3f2 = matrix3f.rotate(quaternion);
        Vector3f vector3f = matrix3f2.getEulerAnglesZYX(new Vector3f());
        this.setAngles(vector3f.x, vector3f.y, vector3f.z);
    }

    public void forEachCuboid(Consumer<Cuboid> consumer) {
        this.cuboids.forEach(consumer);
    }

    public void applyTransform(Matrix4fStack matrices) {
        matrices.translate(this.originX / 16.0F, this.originY / 16.0F, this.originZ / 16.0F);
        if (this.pitch != 0.0F || this.yaw != 0.0F || this.roll != 0.0F) {
            matrices.rotate((new Quaternionf()).rotationZYX(this.roll, this.yaw, this.pitch));
        }

        if (this.xScale != 1.0F || this.yScale != 1.0F || this.zScale != 1.0F) {
            matrices.scale(this.xScale, this.yScale, this.zScale);
        }

    }

    public Cuboid getRandomCuboid(Random random) {
        return this.cuboids.get(random.nextInt(this.cuboids.size()));
    }

    public boolean isEmpty() {
        return this.cuboids.isEmpty();
    }

    public void moveOrigin(Vector3f vec3f) {
        this.originX += vec3f.x();
        this.originY += vec3f.y();
        this.originZ += vec3f.z();
    }

    public void rotate(Vector3f vec3f) {
        this.pitch += vec3f.x();
        this.yaw += vec3f.y();
        this.roll += vec3f.z();
    }

    public void scale(Vector3f vec3f) {
        this.xScale += vec3f.x();
        this.yScale += vec3f.y();
        this.zScale += vec3f.z();
    }

    public List<ModelPart> traverse() {
        List<ModelPart> list = new ArrayList<>();
        list.add(this);
        this.forEachChild((key, part) -> {
            list.add(part);
        });
        return List.copyOf(list);
    }

    public Function<String, ModelPart> createPartGetter() {
        Map<String, ModelPart> map = new HashMap<>();
        map.put("root", this);
        Objects.requireNonNull(map);
        this.forEachChild(map::putIfAbsent);
        Objects.requireNonNull(map);
        return map::get;
    }

    private void forEachChild(BiConsumer<String, ModelPart> partBiConsumer) {
        for (var entry : this.children.entrySet()) {
            partBiConsumer.accept(entry.getKey(), entry.getValue());
        }
        for (var modelPart : this.children.values()) {
            modelPart.forEachChild(partBiConsumer);
        }
    }

    public void forEachOwnChild(BiConsumer<String, ModelPart> partBiConsumer) {
        this.children.forEach(partBiConsumer);
    }

    public static class Cuboid {
        public final Quad[] sides;
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Cuboid(int u, int v, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float extraX, float extraY, float extraZ, boolean mirror, float textureWidth, float textureHeight, Set<Direction> sides) {
            this.minX = x;
            this.minY = y;
            this.minZ = z;
            this.maxX = x + sizeX;
            this.maxY = y + sizeY;
            this.maxZ = z + sizeZ;
            this.sides = new Quad[sides.size()];
            float f = x + sizeX;
            float g = y + sizeY;
            float h = z + sizeZ;
            x -= extraX;
            y -= extraY;
            z -= extraZ;
            f += extraX;
            g += extraY;
            h += extraZ;
            if (mirror) {
                float i = f;
                f = x;
                x = i;
            }

            Vertex vertex = new Vertex(x, y, z, 0.0F, 0.0F);
            Vertex vertex2 = new Vertex(f, y, z, 0.0F, 8.0F);
            Vertex vertex3 = new Vertex(f, g, z, 8.0F, 8.0F);
            Vertex vertex4 = new Vertex(x, g, z, 8.0F, 0.0F);
            Vertex vertex5 = new Vertex(x, y, h, 0.0F, 0.0F);
            Vertex vertex6 = new Vertex(f, y, h, 0.0F, 8.0F);
            Vertex vertex7 = new Vertex(f, g, h, 8.0F, 8.0F);
            Vertex vertex8 = new Vertex(x, g, h, 8.0F, 0.0F);
            float j = (float) u;
            float k = (float) u + sizeZ;
            float l = (float) u + sizeZ + sizeX;
            float m = (float) u + sizeZ + sizeX + sizeX;
            float n = (float) u + sizeZ + sizeX + sizeZ;
            float o = (float) u + sizeZ + sizeX + sizeZ + sizeX;
            float p = (float) v;
            float q = (float) v + sizeZ;
            float r = (float) v + sizeZ + sizeY;
            int s = 0;
            if (sides.contains(Direction.DOWN)) {
                this.sides[s++] = new Quad(new Vertex[]{vertex6, vertex5, vertex, vertex2}, k, p, l, q, textureWidth, textureHeight, mirror, Direction.DOWN);
            }

            if (sides.contains(Direction.UP)) {
                this.sides[s++] = new Quad(new Vertex[]{vertex3, vertex4, vertex8, vertex7}, l, q, m, p, textureWidth, textureHeight, mirror, Direction.UP);
            }

            if (sides.contains(Direction.WEST)) {
                this.sides[s++] = new Quad(new Vertex[]{vertex, vertex5, vertex8, vertex4}, j, q, k, r, textureWidth, textureHeight, mirror, Direction.WEST);
            }

            if (sides.contains(Direction.NORTH)) {
                this.sides[s++] = new Quad(new Vertex[]{vertex2, vertex, vertex4, vertex3}, k, q, l, r, textureWidth, textureHeight, mirror, Direction.NORTH);
            }

            if (sides.contains(Direction.EAST)) {
                this.sides[s++] = new Quad(new Vertex[]{vertex6, vertex2, vertex3, vertex7}, l, q, n, r, textureWidth, textureHeight, mirror, Direction.EAST);
            }

            if (sides.contains(Direction.SOUTH)) {
                this.sides[s] = new Quad(new Vertex[]{vertex5, vertex6, vertex7, vertex8}, n, q, o, r, textureWidth, textureHeight, mirror, Direction.SOUTH);
            }

        }
    }

    public record Quad(Vertex[] vertices, Vector3f direction, Direction face) {

        public Quad(Vertex[] vertices, float u1, float v1, float u2, float v2, float squishU, float squishV, boolean flip, Direction direction) {
            this(vertices, direction.getUnitVector(), direction);
            float f = 0.0F / squishU;
            float g = 0.0F / squishV;
            vertices[0] = vertices[0].remap(u2 / squishU - f, v1 / squishV + g);
            vertices[1] = vertices[1].remap(u1 / squishU + f, v1 / squishV + g);
            vertices[2] = vertices[2].remap(u1 / squishU + f, v2 / squishV - g);
            vertices[3] = vertices[3].remap(u2 / squishU - f, v2 / squishV - g);
            if (flip) {
                int i = vertices.length;

                for (int j = 0; j < i / 2; ++j) {
                    Vertex vertex = vertices[j];
                    vertices[j] = vertices[i - 1 - j];
                    vertices[i - 1 - j] = vertex;
                }
            }

            if (flip) {
                this.direction.mul(-1.0F, 1.0F, 1.0F);
            }

        }

        public Vertex[] vertices() {
            return this.vertices;
        }

        public Vector3f direction() {
            return this.direction;
        }
    }

    public record Vertex(Vector3f pos, float u, float v) {
        public Vertex(float x, float y, float z, float u, float v) {
            this(new Vector3f(x, y, z), u, v);
        }

        public Vertex remap(float u, float v) {
            return new Vertex(this.pos, u, v);
        }

        public Vector3f pos() {
            return this.pos;
        }

        public float u() {
            return this.u;
        }

        public float v() {
            return this.v;
        }
    }
}
