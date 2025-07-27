package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

public record ModelTransform(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, float zScale) {
    public static final ModelTransform NONE = of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

    public static ModelTransform origin(float x, float y, float z) {
        return of(x, y, z, 0.0F, 0.0F, 0.0F);
    }

    public static ModelTransform rotation(float pitch, float yaw, float roll) {
        return of(0.0F, 0.0F, 0.0F, pitch, yaw, roll);
    }

    public static ModelTransform of(float originX, float originY, float originZ, float pitch, float yaw, float roll) {
        return new ModelTransform(originX, originY, originZ, pitch, yaw, roll, 1.0F, 1.0F, 1.0F);
    }

    public ModelTransform moveOrigin(float x, float y, float z) {
        return new ModelTransform(this.x + x, this.y + y, this.z + z, this.pitch, this.yaw, this.roll, this.xScale, this.yScale, this.zScale);
    }

    public ModelTransform withScale(float scale) {
        return new ModelTransform(this.x, this.y, this.z, this.pitch, this.yaw, this.roll, scale, scale, scale);
    }

    public ModelTransform scaled(float scale) {
        return scale == 1.0F ? this : this.scaled(scale, scale, scale);
    }

    public ModelTransform scaled(float xScale, float yScale, float zScale) {
        return new ModelTransform(this.x * xScale, this.y * yScale, this.z * zScale, this.pitch, this.yaw, this.roll, this.xScale * xScale, this.yScale * yScale, this.zScale * zScale);
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public float pitch() {
        return this.pitch;
    }

    public float yaw() {
        return this.yaw;
    }

    public float roll() {
        return this.roll;
    }

    public float xScale() {
        return this.xScale;
    }

    public float yScale() {
        return this.yScale;
    }

    public float zScale() {
        return this.zScale;
    }
}