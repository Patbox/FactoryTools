package eu.pb4.factorytools.api.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.nucleoid.packettweaker.PacketContext;

public interface DefinedPolymerItem extends PolymerItem {
    @Override
    default Item getPolymerItem(ItemStack itemStack, PacketContext context) {
        return Items.MUSIC_DISC_11;
    }
}
