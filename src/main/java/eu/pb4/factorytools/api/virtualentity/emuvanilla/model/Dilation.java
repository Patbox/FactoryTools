package eu.pb4.factorytools.api.virtualentity.emuvanilla.model;

public class Dilation {
    public static final Dilation NONE = new Dilation(0.0F);
    final float radiusX;
    final float radiusY;
    final float radiusZ;

    public Dilation(float radiusX, float radiusY, float radiusZ) {
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        this.radiusZ = radiusZ;
    }

    public Dilation(float radius) {
        this(radius, radius, radius);
    }

    public Dilation add(float radius) {
        return new Dilation(this.radiusX + radius, this.radiusY + radius, this.radiusZ + radius);
    }

    public Dilation add(float radiusX, float radiusY, float radiusZ) {
        return new Dilation(this.radiusX + radiusX, this.radiusY + radiusY, this.radiusZ + radiusZ);
    }
}