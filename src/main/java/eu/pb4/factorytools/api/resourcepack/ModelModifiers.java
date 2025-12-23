package eu.pb4.factorytools.api.resourcepack;

import eu.pb4.polymer.resourcepack.api.AssetPaths;
import eu.pb4.polymer.resourcepack.api.ResourcePackBuilder;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.AtlasAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.SingleAtlasSource;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelElement;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.math.*;
import net.minecraft.world.phys.Vec3;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelModifiers {
    public static ModelAsset expandModel(ModelAsset asset, Vec3 expansion) {
        return new ModelAsset(asset.parent(), asset.elements().map(x -> x.stream()
                .map(element -> new ModelElement(element.from().subtract(expansion), element.to().add(expansion),
                        element.faces().entrySet().stream().map(face -> {
                            if (face.getValue().uv().isEmpty()) {
                                return Map.entry(face.getKey(), new ModelElement.Face(getClampedDefaultUV(element.from(), element.to(), face.getKey()), face.getValue().texture(), face.getValue().cullface(),
                                        face.getValue().rotation(),
                                        face.getValue().tintIndex()));
                            }
                            return face;
                        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), element.rotation(), element.shade(), element.lightEmission())
                ).toList()), asset.textures(), asset.display(), asset.guiLight(), asset.ambientOcclusion());
    }

    public static ModelAsset expandModelAndRotateUVLocked(ModelAsset asset, Vec3 expansion, int x, int y) {
        return new ModelAsset(asset.parent(), asset.elements().map(a -> a.stream()
                .map(element -> rotateAndExpandElement(element, expansion, x, y)).toList()), asset.textures(), asset.display(), asset.guiLight(), asset.ambientOcclusion());
    }

    public static ModelElement rotateAndExpandElement(ModelElement element, Vec3 expand, int xrot, int yrot) {
        Vec3 from, to;
        {
            var tmp1 = element.from().subtract(8)
                    .xRot(Mth.DEG_TO_RAD * xrot)
                    .yRot(-Mth.DEG_TO_RAD * yrot)
                    .add(8);
            var tmp2 = element.to().subtract(8)
                    .xRot(Mth.DEG_TO_RAD * xrot)
                    .yRot(-Mth.DEG_TO_RAD * yrot)
                    .add(8);

            from = new Vec3(Math.min(tmp1.x, tmp2.x), Math.min(tmp1.y, tmp2.y), Math.min(tmp1.z, tmp2.z));
            to = new Vec3(Math.max(tmp1.x, tmp2.x), Math.max(tmp1.y, tmp2.y), Math.max(tmp1.z, tmp2.z));
        }

        var faces = new EnumMap<Direction, ModelElement.Face>(Direction.class);

        for (var face : element.faces().entrySet()) {
            var dir = face.getKey();
            var dirx = face.getKey();
            {
                int tmp;
                if (dir.getAxis() != Direction.Axis.X) {
                    tmp = (360 + xrot) % 360;
                    while (tmp > 0) {
                        dir = dir.getCounterClockWise(Direction.Axis.X);
                        tmp -= 90;
                    }
                }
                dirx = dir;
                if (dir.getAxis() != Direction.Axis.Y) {
                    tmp = (360 + yrot) % 360;
                    while (tmp > 0) {
                        dir = dir.getClockWise(Direction.Axis.Y);
                        tmp -= 90;
                    }
                }
            }

            var uv = face.getValue().uv();

            if (uv.isEmpty()) {
                uv = getClampedDefaultUV(from, to, dir);
            } else {
                var rot = 0;

                if (face.getKey().getAxis() == Direction.Axis.X) {
                    rot += xrot;
                }

                if (dirx.getAxis() == Direction.Axis.Y) {
                    rot -= yrot;
                }
                rot += 360;

                while (rot > 0) {
                    var u1 = uv.get(0);
                    var v1 = uv.get(1);

                    var u2 = uv.get(2);
                    var v2 = uv.get(3);

                    uv = List.of(v2, 16-u1, v1, 16-u2);
                    rot -= 90;
                }

                uv = List.of(Math.clamp(Math.min(uv.get(0), uv.get(2)), 0, 16),
                        Math.clamp(Math.min(uv.get(1), uv.get(3)), 0, 16),
                        Math.clamp(Math.max(uv.get(0), uv.get(2)), 0, 16),
                        Math.clamp(Math.max(uv.get(1), uv.get(3)), 0, 16));

            }

            faces.put(dir, new ModelElement.Face(uv, face.getValue().texture(), face.getValue().cullface(), face.getValue().rotation(), face.getValue().tintIndex()));
        }


        return new ModelElement(from.subtract(expand), to.add(expand), faces, element.rotation(), element.shade(), element.lightEmission());
    }

    public static void createSignModel(ResourcePackBuilder builder, String namespace, String name, AtlasAsset.Builder atlas) {
        var textureRegular = Identifier.fromNamespaceAndPath(namespace, "entity/signs/" + name);
        var textureHanging = Identifier.fromNamespaceAndPath(namespace, "entity/signs/hanging/" + name);

        atlas.add(new SingleAtlasSource(textureRegular, Optional.empty()));
        atlas.add(new SingleAtlasSource(textureHanging, Optional.empty()));

        builder.addData(AssetPaths.model(Identifier.fromNamespaceAndPath(namespace, "block_sign/" + name + "_sign.json")), ModelAsset.builder()
                .parent(Identifier.fromNamespaceAndPath("factorytools", "block_sign/template_sign"))
                .texture("sign", textureRegular.toString()).build());
        builder.addData(AssetPaths.model(Identifier.fromNamespaceAndPath(namespace, "block_sign/" + name + "_wall_sign.json")), ModelAsset.builder()
                .parent(Identifier.fromNamespaceAndPath("factorytools", "block_sign/template_wall_sign"))
                .texture("sign", textureRegular.toString()).build());
        builder.addData(AssetPaths.model(Identifier.fromNamespaceAndPath(namespace, "block_sign/" + name + "_hanging_sign.json")), ModelAsset.builder()
                .parent(Identifier.fromNamespaceAndPath("factorytools", "block_sign/template_hanging_sign"))
                .texture("sign", textureHanging.toString()).build());
        builder.addData(AssetPaths.model(Identifier.fromNamespaceAndPath(namespace, "block_sign/" + name + "_wall_hanging_sign.json")), ModelAsset.builder()
                .parent(Identifier.fromNamespaceAndPath("factorytools", "block_sign/template_wall_hanging_sign"))
                .texture("sign", textureHanging.toString()).build());
    }

    public static FloatList getClampedDefaultUV(Vec3 from, Vec3 to, Direction facing) {
        var list = getDefaultUV(from, to, facing);
        return FloatList.of(
                Math.clamp(list.getFloat(0), 0, 16),
                Math.clamp(list.getFloat(1), 0, 16),
                Math.clamp(list.getFloat(2), 0, 16),
                Math.clamp(list.getFloat(3), 0, 16)
        );
    }

    public static FloatList getDefaultUV(Vec3 from, Vec3 to, Direction facing) {
        return switch (facing) {
            case DOWN -> FloatList.of((float) from.x, 16.0F - (float) to.z, (float) to.x, 16.0F - (float) from.z);
            case UP -> FloatList.of((float) from.x, (float) from.z, (float) to.x, (float) to.z);
            case NORTH ->
                    FloatList.of(16.0F - (float) to.x, 16.0F - (float) to.y, 16.0F - (float) from.x, 16.0F - (float) from.y);
            case SOUTH -> FloatList.of((float) from.x, 16.0F - (float) to.y, (float) to.x, 16.0F - (float) from.y);
            case WEST -> FloatList.of((float) from.z, 16.0F - (float) to.y, (float) to.z, 16.0F - (float) from.y);
            case EAST ->
                    FloatList.of(16.0F - (float) to.z, 16.0F - (float) to.y, 16.0F - (float) from.z, 16.0F - (float) from.y);
        };
    }
}
