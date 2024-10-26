package eu.pb4.factorytools.api.recipe;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;

public record LazyRecipeSerializer<T extends Recipe<?>>(MapCodec<T> codec) implements PolymerObject, RecipeSerializer<T> {
    private static final DynamicRegistryManager STATIC_REGISTRIES = DynamicRegistryManager.of(Registries.REGISTRIES);

    @Override
    public PacketCodec<RegistryByteBuf, T> packetCodec() {
        return PacketCodec.unit(null);
    }
}
