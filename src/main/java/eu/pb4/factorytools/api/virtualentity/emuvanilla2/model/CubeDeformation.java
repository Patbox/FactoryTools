package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

public class CubeDeformation {
    public static final CubeDeformation NONE = new CubeDeformation(0.0F);
    final float growX;
    final float growY;
    final float growZ;

    public CubeDeformation(float radiusX, float radiusY, float radiusZ) {
        this.growX = radiusX;
        this.growY = radiusY;
        this.growZ = radiusZ;
    }

    public CubeDeformation(float radius) {
        this(radius, radius, radius);
    }

    public CubeDeformation extend(float radius) {
        return new CubeDeformation(this.growX + radius, this.growY + radius, this.growZ + radius);
    }

    public CubeDeformation extend(float radiusX, float radiusY, float radiusZ) {
        return new CubeDeformation(this.growX + radiusX, this.growY + radiusY, this.growZ + radiusZ);
    }
}