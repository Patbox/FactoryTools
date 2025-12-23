package eu.pb4.factorytools.api.recipe;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.core.api.utils.PolymerObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public record LazyRecipeSerializer<T extends Recipe<?>>(MapCodec<T> codec) implements PolymerObject, RecipeSerializer<T> {
    private static final RegistryAccess STATIC_REGISTRIES = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return StreamCodec.unit(null);
    }
}
