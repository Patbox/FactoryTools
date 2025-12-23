package eu.pb4.factorytools.mixin.player;

import eu.pb4.factorytools.api.block.AbovePlacingLimiter;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {
    @Inject(method = "canPlace", at = @At("HEAD"), cancellable = true)
    private void canPlace(BlockPlaceContext context, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        var stateBelow = context.getLevel().getBlockState(context.getClickedPos().below());

        if (stateBelow.getBlock() instanceof AbovePlacingLimiter limiter && !limiter.canPlaceAbove(stateBelow, context, state)) {
            cir.setReturnValue(false);
        }
    }
}
