package eu.pb4.factorytools.api.recipe;

import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

public interface NbtRecipeBuilder {
    static ShapedRecipeJsonBuilder with(ShapedRecipeJsonBuilder builder, @Nullable NbtCompound nbt) {
        ((NbtRecipeBuilder) builder).factorytools$setNbt(nbt);
        return builder;
    }

    static ShapelessRecipeJsonBuilder with(ShapelessRecipeJsonBuilder builder, @Nullable NbtCompound nbt) {
        ((NbtRecipeBuilder) builder).factorytools$setNbt(nbt);
        return builder;
    }

    void factorytools$setNbt(@Nullable NbtCompound nbt);
}
