package eu.pb4.factorytools.api.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.FireworkExplosion;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

public interface FireworkStarColoredItem extends PolymerItem {
    @Override
    default ItemStack getPolymerItemStack(ItemStack itemStack, TooltipFlag tooltipType, PacketContext context) {
        var stack = PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, context);
        stack.set(DataComponents.FIREWORK_EXPLOSION,
                new FireworkExplosion(FireworkExplosion.Shape.BURST,
                        IntList.of(getItemColor(itemStack)), IntList.of(), false, false));
        return stack;
    }

    @Override
    default Item getPolymerItem(ItemStack stack, PacketContext context) {
        return Items.FIREWORK_STAR;
    }

    int getItemColor(ItemStack stack);
}
