package eu.pb4.factorytools.api.virtualentity.emuvanilla;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.EntityModel;
import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.ModelPart;
import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.TexturedModelData;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.resourcepack.api.AssetPaths;
import eu.pb4.polymer.resourcepack.extras.api.format.atlas.AtlasAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelElement;
import eu.pb4.polymer.resourcepack.extras.api.format.model.ModelTransformation;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapColorComponent;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.IdentityHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

public record PolyModelInstance<T extends EntityModel<?>>(T model, TexturedModelData data, Identifier texture,
                                                          Function<ModelPart, @Nullable ItemStack> modelParts, Function<ModelPart, @Nullable ItemStack> damagedModelParts) {

    public static <T extends EntityModel<?>> PolyModelInstance<T> create(Function<ModelPart, T> modelCreator, TexturedModelData data, Identifier texture) {
        var model = modelCreator.apply(data.createModel());

        return of(model, data, texture);
    }

    public PolyModelInstance<T> withTexture(Identifier texture) {
        return of(this.model, this.data, texture);
    }

    private static <T extends EntityModel<?>> PolyModelInstance<T> of(T model, TexturedModelData data, Identifier texture) {
        var map = new IdentityHashMap<ModelPart, ItemStack>();
        var damagedMap = new IdentityHashMap<ModelPart, ItemStack>();
        int id = 0;
        for (var part : model.getParts()) {
            if (part.isEmpty()) continue;
            var stack = ItemDisplayElementUtil.getModel(texture.withSuffixedPath("/part_" + (id++)));
            map.put(part, stack);
            stack = stack.copy();
            stack.set(DataComponentTypes.MAP_COLOR, new MapColorComponent(0xff7e7e));
            damagedMap.put(part, stack);
        }
        return new PolyModelInstance<>(model, data, texture, map::get, damagedMap::get);
    }

    public void generateAssets(BiConsumer<String, byte[]> writer, AtlasAsset.Builder atlas) {
        atlas.single(texture);

        int id = 0;

        for (var part : model.getParts()) {
            if (part.isEmpty()) continue;
            var modelId = texture.withSuffixedPath("/part_" + (id++));
            var model = ModelAsset.builder();
            model.texture("txt", texture.toString());
            model.texture("empty", "factorytools:block/empty");
            model.texture("particle", "#txt");

            part.forEachCuboid(cuboid -> {
                for (var quad : cuboid.sides) {
                    var min = new Vector3f(Float.POSITIVE_INFINITY);
                    var max = new Vector3f(Float.NEGATIVE_INFINITY);
                    ModelPart.Vertex v1 = quad.vertices()[0];
                    ModelPart.Vertex v2 = quad.vertices()[0];

                    for (var vert : quad.vertices()) {
                        min.min(vert.pos());
                        max.max(vert.pos());
                    }

                    for (var vert : quad.vertices()) {
                        if (min.equals(vert.pos())) {
                            v1 = vert;
                        }
                        if (max.equals(vert.pos())) {
                            v2 = vert;
                        }
                    }


                    var b = ModelElement.builder(new Vec3d(min.x, min.y, min.z).multiply(0.25).add(8), new Vec3d(max.x, max.y, max.z).multiply(0.25).add(8));
                    for (var dir : Direction.values()) {
                        b.face(dir, "#empty");
                    }

                    var dir = Direction.fromVector((int) quad.direction().x, (int) quad.direction().y, (int) quad.direction().z, null);

                    if ((dir.getDirection() == Direction.AxisDirection.NEGATIVE) == (dir.getAxis() == Direction.Axis.Z)) {
                       dir = dir.getOpposite();
                    }

                    b.face(dir, v1.u() * 16, v2.v() * 16, v2.u() * 16, v1.v() * 16, "#txt", dir, 0, 0);
                    b.face(dir.getOpposite(), v2.u() * 16, v2.v() * 16, v1.u() * 16, v1.v() * 16, "#txt", dir.getOpposite(), 0, 0);


                    model.element(b.build());
                }
            });

            model.transformation(ItemDisplayContext.FIXED, new ModelTransformation(new Vec3d(0, 180, 0), Vec3d.ZERO, new Vec3d(4, 4, 4)));

            writer.accept(AssetPaths.model(modelId) + ".json", model.build().toBytes());
        }
    }

}
