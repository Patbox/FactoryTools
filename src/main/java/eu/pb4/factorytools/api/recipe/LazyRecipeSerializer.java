package eu.pb4.factorytools.api.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryOps;

public record LazyRecipeSerializer<T extends Recipe<?>>(MapCodec<T> codec) implements PolymerObject, RecipeSerializer<T> {
    public static final Codec<Ingredient> INGREDIENT_CODEC = Ingredient.ALLOW_EMPTY_CODEC;

    private static final DynamicRegistryManager STATIC_REGISTRIES = DynamicRegistryManager.of(Registries.REGISTRIES);

    @Override
    public PacketCodec<RegistryByteBuf, T> packetCodec() {
        return PacketCodec.unit(null);
    }
}
