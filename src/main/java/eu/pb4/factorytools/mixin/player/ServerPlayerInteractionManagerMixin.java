package eu.pb4.factorytools.mixin.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.factorytools.api.block.ItemUseLimiter;
import eu.pb4.factorytools.api.block.SneakBypassingBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {
    @Shadow @Final protected ServerPlayerEntity player;

    @Shadow protected ServerWorld world;

    @ModifyExpressionValue(
            method = "interactBlock",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;shouldCancelInteraction()Z")
    )
    private boolean dontCancelForSome(boolean original, @Local BlockState state) {
        return !(state.getBlock() instanceof SneakBypassingBlock) && original;
    }

    @Inject(
            method = "interactItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", shift = At.Shift.BEFORE),
            cancellable = true
    )
    private void preventItemUse(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.raycast(player.getBlockInteractionRange(), 0, false) instanceof BlockHitResult cast && cast.getType() != HitResult.Type.MISS) {
            var blockState = world.getBlockState(cast.getBlockPos());
            if (blockState.getBlock() instanceof ItemUseLimiter limiter && limiter.preventUseItemWhileTargetingBlock(player, blockState, world, cast, stack, hand)) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }

    }
}
