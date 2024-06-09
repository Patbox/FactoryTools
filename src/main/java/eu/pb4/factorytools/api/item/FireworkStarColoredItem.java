package eu.pb4.factorytools.api.item;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public interface FireworkStarColoredItem extends AutoModeledPolymerItem {
    @Override
    default ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType context, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        var stack = AutoModeledPolymerItem.super.getPolymerItemStack(itemStack, context, lookup, player);
        stack.set(DataComponentTypes.FIREWORK_EXPLOSION,
                new FireworkExplosionComponent(FireworkExplosionComponent.Type.BURST,
                        IntList.of(getItemColor(itemStack)), IntList.of(), false, false));
        return stack;
    }

    @Override
    default Item getPolymerItem() {
        return Items.FIREWORK_STAR;
    }

    int getItemColor(ItemStack stack);
}
