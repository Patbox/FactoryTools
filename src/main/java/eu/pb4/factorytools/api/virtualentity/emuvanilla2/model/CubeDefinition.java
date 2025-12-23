package eu.pb4.factorytools.api.virtualentity.emuvanilla2.model;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Set;
import net.minecraft.core.Direction;

public final class CubeDefinition {
    @Nullable
    private final String comment;
    private final Vector3f origin;
    private final Vector3f dimensions;
    private final CubeDeformation grow;
    private final boolean mirror;
    private final UVPair texCoord;
    private final UVPair texScale;
    private final Set<Direction> visibleFaces;

    CubeDefinition(@Nullable String name, float textureX, float textureY, float offsetX, float offsetY, float offsetZ, float sizeX, float sizeY, float sizeZ, CubeDeformation extra, boolean mirror, float textureScaleX, float textureScaleY, Set<Direction> directions) {
        this.comment = name;
        this.texCoord = new UVPair(textureX, textureY);
        this.origin = new Vector3f(offsetX, offsetY, offsetZ);
        this.dimensions = new Vector3f(sizeX, sizeY, sizeZ);
        this.grow = extra;
        this.mirror = mirror;
        this.texScale = new UVPair(textureScaleX, textureScaleY);
        this.visibleFaces = directions;
    }

    public ModelPart.Cube bake(int textureWidth, int textureHeight) {
        return new ModelPart.Cube((int) this.texCoord.u(), (int) this.texCoord.v(), this.origin.x(), this.origin.y(), this.origin.z(), this.dimensions.x(), this.dimensions.y(), this.dimensions.z(), this.grow.growX, this.grow.growY, this.grow.growZ, this.mirror, (float) textureWidth * this.texScale.u(), (float) textureHeight * this.texScale.v(), this.visibleFaces);
    }
}