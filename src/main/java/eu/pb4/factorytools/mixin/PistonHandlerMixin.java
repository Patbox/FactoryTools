package eu.pb4.factorytools.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.factorytools.api.block.CustomPistonBehavior;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Based on carpet implementation
 */
@Mixin(PistonHandler.class)
public class PistonHandlerMixin {

    @Shadow
    @Final
    private World world;
    @Shadow
    @Final private Direction motionDirection;
    @Inject(method = "isBlockSticky", at = @At(value = "HEAD"), cancellable = true)
    private static void mightBeSticky(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.getBlock() instanceof CustomPistonBehavior pushable){
            cir.setReturnValue(pushable.isSticky(state));
        }
    }
    @WrapOperation(
            method = "tryMove",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/piston/PistonHandler;isAdjacentBlockStuck(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)Z"
            )
    )
    private boolean onAddBlockLineCanStickToEachOther(BlockState state, BlockState adjacentState, Operation<Boolean> original, @Local(ordinal = 1) BlockPos pos) {
        if (state.getBlock() instanceof CustomPistonBehavior pushable) {
            return pushable.isStickyToNeighbor(this.world, pos.offset(this.motionDirection), state, pos, adjacentState, this.motionDirection.getOpposite(), this.motionDirection);
        }

        return original.call(state, adjacentState);
    }


    @WrapOperation(
            method = "tryMoveAdjacentBlock",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/block/piston/PistonHandler;isAdjacentBlockStuck(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;)Z"
            )
    )
    private boolean onAddBranchingBlocksCanStickToEachOther(BlockState state, BlockState adjacentState, Operation<Boolean> original,
                                                            @Local(ordinal = 0) BlockPos pos,
                                                            @Local(ordinal = 1) BlockPos sidePos,
                                                            @Local Direction direction) {
        if (adjacentState.getBlock() instanceof CustomPistonBehavior pushable) {
            return pushable.isStickyToNeighbor(this.world, pos, adjacentState, sidePos, state, direction, this.motionDirection);
        }

        return original.call(state, adjacentState);
    }}

