package eu.pb4.factorytools.api.block;

import eu.pb4.factorytools.mixin.UseOnContextAccessor;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;

public abstract class MultiBlock extends Block implements PolymerBlock {
    private static IntegerProperty[] currentProperties;
    @Nullable
    public final IntegerProperty partX, partY, partZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final int centerBlockX;
    private final int centerBlockY;
    private final int centerBlockZ;

    public MultiBlock(int x, int y, int z, Properties settings) {
        this(x - 1, y - 1, z - 1, hackPass(x - 1, y - 1, z - 1), settings.pushReaction(PushReaction.BLOCK));
    }

    private MultiBlock(int x, int y, int z, IntegerProperty[] hackPass, Properties settings) {
        super(settings);
        partX = hackPass[0];
        partY = hackPass[1];
        partZ = hackPass[2];
        this.maxX = Math.max(x, 0);
        this.maxY = Math.max(y, 0);
        this.maxZ = Math.max(z, 0);

        this.centerBlockX = this.maxX / 2;
        this.centerBlockY = this.maxY / 2;
        this.centerBlockZ = this.maxZ / 2;
    }

    private static IntegerProperty[] hackPass(int x, int y, int z) {
        var a = new IntegerProperty[3];
        if (x > 0) {
            a[0] = IntegerProperty.create("x", 0, x);
        }

        if (y > 0) {
            a[1] = IntegerProperty.create("y", 0, y);
        }

        if (z > 0) {
            a[2] = IntegerProperty.create("z", 0, z);
        }
        currentProperties = a;
        return a;
    }

    protected boolean isValid(BlockState state, int x, int y, int z) {
        return true;
    }

    protected int getCenterBlockX(BlockState state) {
        return this.centerBlockX;
    }

    protected int getCenterBlockY(BlockState state) {
        return this.centerBlockY;
    }

    protected int getCenterBlockZ(BlockState state) {
        return this.centerBlockZ;
    }

    protected int getMaxX(BlockState state) {
        return this.maxX;
    }

    protected int getMaxY(BlockState state) {
        return this.maxY;
    }

    protected int getMaxZ(BlockState state) {
        return this.maxZ;
    }

    public boolean place(BlockPlaceContext context, BlockState state) {
        var startPlane = context.getClickedFace();
        var hit = ((UseOnContextAccessor) context).callGetHitResult();
        var vec3d = hit.getLocation().subtract(hit.getBlockPos().getX(), hit.getBlockPos().getY(), hit.getBlockPos().getZ());
        var mut = context.getClickedPos().mutable();

        if (startPlane.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
            mut.move(startPlane, this.getMax(state, startPlane));
        }
        mut.move(-this.getCenterBlockX(state) * startPlane.getAxis().choose(0, 1, 1),
                -this.getCenterBlockY(state) * startPlane.getAxis().choose(1, 0, 1),
                -this.getCenterBlockZ(state) * startPlane.getAxis().choose(1, 1, 0))
        ;


        var maxX = this.getMaxX(state);
        var maxY = this.getMaxY(state);
        var maxZ = this.getMaxZ(state);

        if (maxX % 2 == 1 && startPlane.getAxis() != Direction.Axis.X) {
            mut.move(vec3d.x < 0.5 ? -1 : 0, 0, 0);
        }

        if (maxY % 2 == 1 && startPlane.getAxis() != Direction.Axis.Y) {
            mut.move(0, vec3d.y < 0.5 ? -1 : 0, 0);
        }

        if (maxZ % 2 == 1 && startPlane.getAxis() != Direction.Axis.Z) {
            mut.move(0, 0, vec3d.z < 0.5 ? -1 : 0);
        }

        var corner = mut.immutable();

        var world = context.getLevel();

        Player playerEntity = context.getPlayer();
        var shapeContext = playerEntity == null ? CollisionContext.empty() : CollisionContext.of(playerEntity);

        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                for (int z = 0; z <= maxZ; z++) {
                    if (!this.isValid(state, x, y, z)) {
                        continue;
                    }
                    mut.set(corner).move(x, y, z);
                    var targetState = world.getBlockState(mut);
                    if (!targetState.canBeReplaced() || !state.canSurvive(world, mut) || !context.getLevel().isUnobstructed(state, mut, shapeContext)) {
                        return false;
                    }
                }
            }
        }

        for (int x = 0; x <= maxX; x++) {
            var posState = partX != null ? state.setValue(partX, x) : state;
            for (int y = 0; y <= maxY; y++) {
                posState = partY != null ? posState.setValue(partY, y) : posState;
                for (int z = 0; z <= maxZ; z++) {
                    if (!this.isValid(state, x, y, z)) {
                        continue;
                    }
                    posState = partZ != null ? posState.setValue(partZ, z) : posState;
                    context.getLevel().setBlockAndUpdate(mut.set(corner).move(x, y, z), posState);
                    this.onPlacedMultiBlock(world, mut, posState, context.getPlayer(), context.getItemInHand());
                }
            }
        }

        return true;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        if (this.canDropStackFrom(state)) {
            return super.getDrops(state, builder);
        }
        return List.of();
    }

    protected boolean canDropStackFrom(BlockState state) {
        return isCenter(state);
    }

    protected void onPlacedMultiBlock(Level world, BlockPos pos, BlockState state, Player player, ItemStack stack) {
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        var property = getForDirection(direction);
        if (property == null) {
            return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
        var value = state.getValue(property);
        var expectedSideValue = value + direction.getAxisDirection().getStep();

        if (expectedSideValue < 0 || expectedSideValue > getMax(state, direction)) {
            return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }

        var x = this.getX(state);
        var y = this.getY(state);
        var z = this.getZ(state);

        if (!this.isValid(state, x + direction.getStepX(), y + direction.getStepY(), z + direction.getStepZ())
                || (neighborState.is(this) && neighborState.getValue(property) == expectedSideValue)
                || this.ignoreNeighborUpdate(state, direction, pos, neighborPos, neighborState)
        ) {
            return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }

        return Blocks.AIR.defaultBlockState();
    }

    protected boolean ignoreNeighborUpdate(BlockState state, Direction direction, BlockPos selfPos, BlockPos neighborPos, BlockState neighborState) {
        return false;
    }

    protected int getX(BlockState state) {
        if (this.partX != null) {
            return state.getValue(this.partX);
        }
        return 0;
    }

    protected int getY(BlockState state) {
        if (this.partY != null) {
            return state.getValue(this.partY);
        }
        return 0;
    }

    protected int getZ(BlockState state) {
        if (this.partZ != null) {
            return state.getValue(this.partZ);
        }
        return 0;
    }

    @Nullable
    protected IntegerProperty getForDirection(Direction direction) {
        return switch (direction.getAxis()) {
            case X -> partX;
            case Y -> partY;
            case Z -> partZ;
        };
    }

    protected int getMax(BlockState state, Direction direction) {
        return switch (direction.getAxis()) {
            case X -> this.getMaxX(state);
            case Y -> this.getMaxY(state);
            case Z -> this.getMaxZ(state);
        };
    }

    public BlockPos getCenter(BlockState state, BlockPos pos) {
        var x = partX != null ? state.getValue(partX) : 0;
        var y = partY != null ? state.getValue(partY) : 0;
        var z = partZ != null ? state.getValue(partZ) : 0;

        return pos.offset(this.getCenterBlockX(state) - x, this.getCenterBlockY(state) - y, this.getCenterBlockZ(state) - z);
    }

    public boolean isCenter(BlockState state) {
        return getX(state) == getCenterBlockX(state) && getY(state) == getCenterBlockY(state) && getZ(state) == getCenterBlockZ(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        for (var currentProperty : currentProperties) {
            if (currentProperty != null) {
                builder.add(currentProperty);
            }
        }
    }
}
