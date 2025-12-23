package eu.pb4.factorytools.mixin.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.factorytools.api.block.SneakBypassingBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {
    @Shadow @Final protected ServerPlayer player;

    @Shadow protected ServerLevel level;

    @ModifyExpressionValue(
            method = "useItemOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isSecondaryUseActive()Z")
    )
    private boolean dontCancelForSome(boolean original, @Local BlockState state, @Local ServerPlayer player, @Local ItemStack stack, @Local(argsOnly = true) InteractionHand hand, @Local(argsOnly = true) BlockHitResult result) {
        return !(state.getBlock() instanceof SneakBypassingBlock b && b.bypassSneaking(state, player, result, stack, hand)) && original;
    }
}
