package eu.pb4.factorytools.api.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
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
import xyz.nucleoid.packettweaker.PacketContext;

public interface FireworkStarColoredItem extends PolymerItem {
    @Override
    default ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, PacketContext context) {
        var stack = PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, context);
        stack.set(DataComponentTypes.FIREWORK_EXPLOSION,
                new FireworkExplosionComponent(FireworkExplosionComponent.Type.BURST,
                        IntList.of(getItemColor(itemStack)), IntList.of(), false, false));
        return stack;
    }

    @Override
    default Item getPolymerItem(ItemStack stack, PacketContext context) {
        return Items.FIREWORK_STAR;
    }

    int getItemColor(ItemStack stack);
}
