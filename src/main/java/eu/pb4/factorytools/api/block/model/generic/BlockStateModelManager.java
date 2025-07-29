package eu.pb4.factorytools.api.block.model.generic;

import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import eu.pb4.factorytools.api.util.ResourceUtils;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.resourcepack.extras.api.format.blockstate.BlockStateAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.blockstate.StateModelVariant;
import eu.pb4.polymer.resourcepack.extras.api.format.blockstate.StateMultiPartDefinition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.collection.Weighted;
import net.minecraft.util.collection.Weighting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiConsumer;

public class BlockStateModelManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockStateModelManager.class);

    private static final Map<BlockState, List<ModelGetter>> MAP = new HashMap<>();
    private static final Map<BlockState, ParticleEffect> PARTICLE = new HashMap<>();
    public static final Map<String, Map<String, List<StateModelVariant>>> UV_LOCKED_MODELS = new HashMap<>();

    public static List<ModelGetter> get(BlockState state) {
        return MAP.getOrDefault(state, List.of());
    }
    public static ParticleEffect getParticle(BlockState state) {
        return PARTICLE.getOrDefault(state, ParticleTypes.ANGRY_VILLAGER);
    }

    public static void addBlock(Identifier identifier, Block block) {
        try {
            var rand = Random.create(123);
            var data = ResourceUtils.getJarData("assets/" + identifier.getNamespace() + "/blockstates/" + identifier.getPath() + ".json");

            var decoded = BlockStateAsset.CODEC.decode(JsonOps.INSTANCE, JsonParser.parseString(new String(data, StandardCharsets.UTF_8)));
            var modelDef = decoded.getOrThrow().getFirst();

            if (modelDef.variants().isPresent()) {
                var list = new ArrayList<Pair<BlockStatePredicate, List<ModelData>>>();
                parseVariants(block, modelDef.variants().get(), list);

                for (var pair : list) {
                    for (var state : block.getStateManager().getStates()) {
                        if (pair.getLeft().test(state)) {
                            MAP.put(state, List.of(ModelGetter.of(pair.getRight())));
                            if (!pair.getRight().isEmpty()) {
                                PARTICLE.put(state, new ItemStackParticleEffect(ParticleTypes.ITEM, pair.getRight().getFirst().stack));
                            }
                        }
                    }
                }
            }

            if (modelDef.multipart().isPresent()) {
                var list = new ArrayList<Pair<List<BlockStatePredicate>, List<ModelData>>>();
                parseMultipart(block, modelDef.multipart().get(), list);

                for (var pair : list) {
                    for (var state : block.getStateManager().getStates()) {
                        for (var pred : pair.getLeft()) {
                            if (pred.test(state)) {
                                var objects = new ArrayList<ModelGetter>();
                                if (MAP.containsKey(state)) {
                                    objects.addAll(MAP.get(state));
                                }
                                objects.add(ModelGetter.of(pair.getRight()));
                                MAP.put(state, objects);
                                if (!objects.isEmpty() && !PARTICLE.containsKey(state)) {
                                    PARTICLE.put(state, new ItemStackParticleEffect(ParticleTypes.ITEM, objects.getFirst().getModel(rand).stack));
                                }

                                break;
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) {
            LOGGER.warn("Failed to decode model for {}", identifier, e);
        }
    }

    private static void parseMultipart(Block block, List<StateMultiPartDefinition> multiPartDefinition, ArrayList<Pair<List<BlockStatePredicate>, List<ModelData>>> list) {
        for (var part : multiPartDefinition) {
            var preds = new ArrayList<BlockStatePredicate>();

            if (part.when().or().isPresent()) {
                for (var x : part.when().or().get()) {
                    var predicate = BlockStatePredicate.forBlock(block);
                    applyWhenMultipart(predicate, block, x);
                    preds.add(predicate);
                }
            }

            if (part.when().and().isPresent()) {
                var predicate = BlockStatePredicate.forBlock(block);
                for (var x : part.when().or().get()) {
                    applyWhenMultipart(predicate, block, x);
                }
                preds.add(predicate);
            }

            if (part.when().base().isPresent()) {
                var predicate = BlockStatePredicate.forBlock(block);
                applyWhenMultipart(predicate, block, part.when().base().get());
                preds.add(predicate);
            }

            if (preds.isEmpty()) {
                preds.add(BlockStatePredicate.forBlock(block));
            }

            var modelData = parseBaseVariants(part.apply());
            list.add(new Pair<>(preds, modelData));
        }
    }

    private static void applyWhenMultipart(BlockStatePredicate predicate, Block block, Map<String, String> x) {
        for (var entry : x.entrySet()) {
            //noinspection rawtypes
            var prop = (Property) block.getStateManager().getProperty(entry.getKey());

            if (prop == null) {
                continue;
            }

            var split = Set.of(entry.getValue().split("\\|"));

            //noinspection rawtypes,unchecked
            predicate.with(prop, y -> split.contains(prop.name((Comparable) y)));
        }
    }


    private static void parseVariants(Block block, Map<String, List<StateModelVariant>> modelDef, ArrayList<Pair<BlockStatePredicate, List<ModelData>>> list) {
        parseVariants(block, modelDef, (a, b) -> {
            var modelData = parseBaseVariants(b);
            list.add(new Pair<>(a, modelData));
        });
    }
    public static void parseVariants(Block block, Map<String, List<StateModelVariant>> modelDef, BiConsumer<BlockStatePredicate, List<StateModelVariant>> consumer) {
        start:
        for (var pair : modelDef.entrySet()) {
            var stateMap = pair.getKey().split(",");

            var predicate = BlockStatePredicate.forBlock(block);


            for (var statePair : stateMap) {
                if (!statePair.isEmpty()) {
                    var split = statePair.split("=", 2);
                    var prop = (Property) block.getStateManager().getProperty(split[0]);

                    if (prop == null) {
                        continue start;
                    }

                    predicate.with(prop, x -> prop.name((Comparable) x).equals(split[1]));
                }
            }

            consumer.accept(predicate, pair.getValue());
        }
    }

    private static List<ModelData> parseBaseVariants(List<StateModelVariant> value) {
        var modelData = new ArrayList<ModelData>();

        for (var v : value) {
            if (v.uvlock()) {
                var modelId = v.model().withSuffixedPath("_uvlock_" + v.x() + "_" + v.y());

                var stack = ItemDisplayElementUtil.getModel(modelId);
                modelData.add(new ModelData(stack, new Quaternionf(), v.weigth()));

                UV_LOCKED_MODELS.computeIfAbsent(v.model().getNamespace(), x -> new HashMap<>()).computeIfAbsent(v.model().getPath(), x -> new ArrayList<>()).add(v);
            } else {
                var stack = ItemDisplayElementUtil.getModel(v.model());
                modelData.add(new ModelData(stack, new Quaternionf()
                        .rotateY(-MathHelper.RADIANS_PER_DEGREE * v.y())
                        .rotateX(MathHelper.RADIANS_PER_DEGREE * v.x()),
                        v.weigth()
                ));
            }
        }

        return modelData;
    }

    public interface ModelGetter {
        ModelData getModel(Random random);

        static ModelGetter of(List<ModelData> data) {
            if (data.size() == 1) {
                return new SingleGetter(data.get(0));
            }

            return WeightedGetter.create(data);
        }
    }


    private record SingleGetter(ModelData data) implements ModelGetter {
        @Override
        public ModelData getModel(Random random) {
            return this.data;
        }
    }

    private record WeightedGetter(Pool<ModelData> data, int weightedSum) implements ModelGetter {
        public static ModelGetter create(List<ModelData> data) {
            var list = new ArrayList<Weighted<ModelData>>();
            for (var d : data) {
                list.add(new Weighted<>(d, d.weight));
            }
            var x = Weighting.getWeightSum(list, Weighted::weight);

            return new WeightedGetter(Pool.of(list), x);
        }

        @Override
        public ModelData getModel(Random random) {
            return this.data.get(random);
        }
    }
    public record ModelData(ItemStack stack, Quaternionfc quaternionfc, int weight) {}
}
