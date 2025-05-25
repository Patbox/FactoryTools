package eu.pb4.factorytools.api.item;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import xyz.nucleoid.packettweaker.PacketContext;

public class FactoryBlockItem extends BlockItem implements PolymerItem {

    private final Item polymerItem;

    public <T extends Block & PolymerBlock> FactoryBlockItem(T block, Settings settings, Item item) {
        super(block, settings);
        this.polymerItem = item;
    }

    public <T extends Block & PolymerBlock> FactoryBlockItem(T block, Settings settings) {
        super(block, settings);
        this.polymerItem = Items.RABBIT_FOOT;
    }

    @Override
    public Item getPolymerItem(ItemStack stack, PacketContext context) {
        return this.polymerItem;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        try (var x = PolymerUtils.ignorePlaySoundExclusion()) {
            return super.useOnBlock(context);
        }
    }

    @Override
    public boolean isPolymerBlockInteraction(BlockState state, ServerPlayerEntity player, Hand hand, ItemStack stack, ServerWorld world, BlockHitResult blockHitResult, ActionResult actionResult) {
        return actionResult.isAccepted();
    }
}
