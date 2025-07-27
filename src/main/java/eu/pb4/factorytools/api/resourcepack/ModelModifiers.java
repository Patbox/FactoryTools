package eu.pb4.factorytools.api.resourcepack;

import eu.pb4.polymer.resourcepack.api.AssetPaths;
import eu.pb4.polymer.resourcepack.api.ResourcePackBuilder;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.AtlasAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.SingleAtlasSource;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2f;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelModifiers {
    public static ModelAsset expandModel(ModelAsset asset, Vec3d expansion) {
        return new ModelAsset(asset.parent(), asset.elements().map(x -> x.stream()
                .map(element -> new ModelElement(element.from().subtract(expansion), element.to().add(expansion),
                        element.faces().entrySet().stream().map(face -> {
                            var uv = face.getValue().uv();
                            if (uv.isEmpty()) {
                                Vector2f uv1, uv2;
                                if (face.getKey().getAxis() == Direction.Axis.Y) {
                                    uv1 = new Vector2f((float) element.from().getX(), (float) element.from().getZ());
                                    uv2 = new Vector2f((float) element.to().getX(), (float) element.to().getZ());
                                } else {
                                    uv1 = new Vector2f((float) element.from().getComponentAlongAxis(face.getKey().rotateYClockwise().getAxis()), 16 - (float) element.to().getY());
                                    uv2 = new Vector2f((float) element.to().getComponentAlongAxis(face.getKey().rotateYClockwise().getAxis()), 16 - (float) element.from().getY());
                                }

                                uv = List.of(Math.clamp(Math.min(uv1.x, uv2.x), 0, 16),
                                        Math.clamp(Math.min(uv1.y, uv2.y), 0, 16),
                                        Math.clamp(Math.max(uv1.x, uv2.x), 0, 16),
                                        Math.clamp(Math.max(uv1.y, uv2.y), 0, 16));

                                return Map.entry(face.getKey(), new ModelElement.Face(uv, face.getValue().texture(), face.getValue().cullface(),
                                        face.getValue().rotation(),
                                        face.getValue().tintIndex()));
                            }
                            return face;
                        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), element.rotation(), element.shade(), element.lightEmission())
                ).toList()), asset.textures(), asset.display(), asset.guiLight(), asset.ambientOcclusion());
    }

    public static ModelAsset expandModelAndRotateUVLocked(ModelAsset asset, Vec3d expansion, int x, int y) {
        return new ModelAsset(asset.parent(), asset.elements().map(a -> a.stream()
                .map(element -> rotateAndExpandElement(element, expansion, x, y)).toList()), asset.textures(), asset.display(), asset.guiLight(), asset.ambientOcclusion());
    }

    public static ModelElement rotateAndExpandElement(ModelElement element, Vec3d expand, int xrot, int yrot) {
        Vec3d from, to;

        {
            var tmp1 = element.from().subtract(8).rotateX(-xrot * MathHelper.RADIANS_PER_DEGREE).rotateY(-yrot * MathHelper.RADIANS_PER_DEGREE).add(8);
            var tmp2 = element.to().subtract(8).rotateX(-xrot * MathHelper.RADIANS_PER_DEGREE).rotateY(-yrot * MathHelper.RADIANS_PER_DEGREE).add(8);

            from = new Vec3d(Math.min(tmp1.x, tmp2.x), Math.min(tmp1.y, tmp2.y), Math.min(tmp1.z, tmp2.z)).subtract(expand);
            to = new Vec3d(Math.max(tmp1.x, tmp2.x), Math.max(tmp1.y, tmp2.y), Math.max(tmp1.z, tmp2.z)).add(expand);
        }

        var faces = new EnumMap<Direction, ModelElement.Face>(Direction.class);

        for (var face : element.faces().entrySet()) {
            var dir = face.getKey();

            {
                int tmp;
                if (dir.getAxis() != Direction.Axis.X) {
                    tmp = (360 + xrot) % 360;
                    while (tmp > 0) {
                        dir = dir.rotateCounterclockwise(Direction.Axis.X);
                        tmp -= 90;
                    }
                }
                if (dir.getAxis() != Direction.Axis.Y) {
                    tmp = (360 + yrot) % 360;
                    while (tmp > 0) {
                        dir = dir.rotateClockwise(Direction.Axis.Y);
                        tmp -= 90;
                    }
                }
            }

            var uv = face.getValue().uv();

            if (uv.isEmpty()) {
                Vector2f uv1, uv2;
                if (face.getKey().getAxis() == Direction.Axis.Y) {
                    uv1 = new Vector2f((float) from.getX(), (float) from.getZ());
                    uv2 = new Vector2f((float) to.getX(), (float) to.getZ());
                } else {
                    uv1 = new Vector2f((float) from.getComponentAlongAxis(face.getKey().rotateYClockwise().getAxis()), 16 - (float) to.getY());
                    uv2 = new Vector2f((float) to.getComponentAlongAxis(face.getKey().rotateYClockwise().getAxis()), 16 - (float) from.getY());
                }

                uv = List.of(Math.clamp(Math.min(uv1.x, uv2.x), 0, 16),
                        Math.clamp(Math.min(uv1.y, uv2.y), 0, 16),
                        Math.clamp(Math.max(uv1.x, uv2.x), 0, 16),
                        Math.clamp(Math.max(uv1.y, uv2.y), 0, 16));

                faces.put(dir, new ModelElement.Face(uv, face.getValue().texture(), face.getValue().cullface(), face.getValue().rotation(), face.getValue().tintIndex()));
            } else {
                int tmp;
                int rot = face.getValue().rotation();
                if (face.getKey().getAxis() == Direction.Axis.Y) {
                    tmp = yrot;
                    if (yrot == 90 || yrot == 270) {
                        rot = (rot + 180) % 360;
                        tmp += 180;
                    }
                } else {
                    tmp = 360 - xrot;
                }

                while (tmp > 0) {
                    var u1 = uv.get(0);
                    var v1 = uv.get(1);

                    var u2 = uv.get(2);
                    var v2 = uv.get(3);

                    uv = List.of(v2, 16-u1, v1, 16-u2);
                    tmp -= 90;
                }

                faces.put(dir, new ModelElement.Face(uv, face.getValue().texture(), face.getValue().cullface(), rot, face.getValue().tintIndex()));
            }
        }


        return new ModelElement(from, to, faces, element.rotation(), element.shade(), element.lightEmission());
    }

    public static void createSignModel(ResourcePackBuilder builder, String namespace, String name, AtlasAsset.Builder atlas) {
        var textureRegular = Identifier.of(namespace, "entity/signs/" + name);
        var textureHanging = Identifier.of(namespace, "entity/signs/hanging/" + name);

        atlas.add(new SingleAtlasSource(textureRegular, Optional.empty()));
        atlas.add(new SingleAtlasSource(textureHanging, Optional.empty()));

        builder.addData(AssetPaths.model(Identifier.of(namespace, "block_sign/" + name + "_sign.json")), ModelAsset.builder()
                .parent(Identifier.of("factorytools", "block_sign/template_sign"))
                .texture("sign", textureRegular.toString()).build());
        builder.addData(AssetPaths.model(Identifier.of(namespace, "block_sign/" + name + "_wall_sign.json")), ModelAsset.builder()
                .parent(Identifier.of("factorytools", "block_sign/template_wall_sign"))
                .texture("sign", textureRegular.toString()).build());
        builder.addData(AssetPaths.model(Identifier.of(namespace, "block_sign/" + name + "_hanging_sign.json")), ModelAsset.builder()
                .parent(Identifier.of("factorytools", "block_sign/template_hanging_sign"))
                .texture("sign", textureHanging.toString()).build());
        builder.addData(AssetPaths.model(Identifier.of(namespace, "block_sign/" + name + "_wall_hanging_sign.json")), ModelAsset.builder()
                .parent(Identifier.of("factorytools", "block_sign/template_wall_hanging_sign"))
                .texture("sign", textureHanging.toString()).build());
    }
}
