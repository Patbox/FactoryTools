package eu.pb4.factorytools.mixin.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import eu.pb4.factorytools.api.util.FactoryPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Inventory.class)
public class InventoryMixin {
    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/NonNullList;withSize(ILjava/lang/Object;)Lnet/minecraft/core/NonNullList;"))
    private NonNullList<ItemStack> replaceTheList(NonNullList<ItemStack> original) {
        return ((Object) this) instanceof FactoryPlayer.FakeInventory fakeInventory ? fakeInventory.wrapList(original) : original;
    }
}
