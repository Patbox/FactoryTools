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
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.IdentityHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.MapItemColor;
import net.minecraft.world.phys.Vec3;

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
            var stack = ItemDisplayElementUtil.getModel(texture.withSuffix("/part_" + (id++)));
            map.put(part, stack);
            stack = stack.copy();
            stack.set(DataComponents.MAP_COLOR, new MapItemColor(0xff7e7e));
            damagedMap.put(part, stack);
        }
        return new PolyModelInstance<>(model, data, texture, map::get, damagedMap::get);
    }

    public void generateAssets(BiConsumer<String, byte[]> writer, AtlasAsset.Builder atlas) {
        atlas.single(texture);

        int id = 0;

        for (var part : model.getParts()) {
            if (part.isEmpty()) continue;
            var modelId = texture.withSuffix("/part_" + (id++));
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


                    var b = ModelElement.builder(new Vec3(min.x, min.y, min.z).scale(0.25).add(8), new Vec3(max.x, max.y, max.z).scale(0.25).add(8));
                    for (var dir : Direction.values()) {
                        b.face(dir, "#empty");
                    }

                    var dir = Direction.getNearest((int) quad.direction().x, (int) quad.direction().y, (int) quad.direction().z, null);

                    if ((dir.getAxisDirection() == Direction.AxisDirection.NEGATIVE) == (dir.getAxis() == Direction.Axis.Z)) {
                       dir = dir.getOpposite();
                    }

                    b.face(dir, v1.u() * 16, v2.v() * 16, v2.u() * 16, v1.v() * 16, "#txt", dir, 0, 0);
                    b.face(dir.getOpposite(), v2.u() * 16, v2.v() * 16, v1.u() * 16, v1.v() * 16, "#txt", dir.getOpposite(), 0, 0);


                    model.element(b.build());
                }
            });

            model.transformation(ItemDisplayContext.FIXED, new ModelTransformation(new Vec3(0, 180, 0), Vec3.ZERO, new Vec3(4, 4, 4)));

            writer.accept(AssetPaths.model(modelId) + ".json", model.build().toBytes());
        }
    }

}
