package eu.pb4.factorytools.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.factorytools.api.block.CustomPistonBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Based on carpet implementation
 */
@Mixin(PistonStructureResolver.class)
public class PistonHandlerMixin {

    @Shadow
    @Final
    private Level level;
    @Shadow
    @Final private Direction pushDirection;
    @Inject(method = "isSticky", at = @At(value = "HEAD"), cancellable = true)
    private static void mightBeSticky(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof CustomPistonBehavior pushable){
            cir.setReturnValue(pushable.isSticky(state));
        }
    }
    @WrapOperation(
            method = "addBlockLine",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/piston/PistonStructureResolver;canStickToEachOther(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            )
    )
    private boolean onAddBlockLineCanStickToEachOther(BlockState state, BlockState adjacentState, Operation<Boolean> original, @Local(ordinal = 1) BlockPos pos) {
        if (state.getBlock() instanceof CustomPistonBehavior pushable) {
            return pushable.isStickyToNeighbor(this.level, pos.relative(this.pushDirection), state, pos, adjacentState, this.pushDirection.getOpposite(), this.pushDirection);
        }

        return original.call(state, adjacentState);
    }


    @WrapOperation(
            method = "addBranchingBlocks",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/piston/PistonStructureResolver;canStickToEachOther(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)Z"
            )
    )
    private boolean onAddBranchingBlocksCanStickToEachOther(BlockState state, BlockState adjacentState, Operation<Boolean> original,
                                                            @Local(ordinal = 0) BlockPos pos,
                                                            @Local(ordinal = 1) BlockPos sidePos,
                                                            @Local Direction direction) {
        if (adjacentState.getBlock() instanceof CustomPistonBehavior pushable) {
            return pushable.isStickyToNeighbor(this.level, pos, adjacentState, sidePos, state, direction, this.pushDirection);
        }

        return original.call(state, adjacentState);
    }
}

