package eu.pb4.factorytools.api.item;

import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xyz.nucleoid.packettweaker.PacketContext;

public class FactoryBlockItem extends BlockItem implements PolymerItem {

    private final Item polymerItem;

    public <T extends Block & PolymerBlock> FactoryBlockItem(T block, Properties settings, Item item) {
        super(block, settings);
        this.polymerItem = item;
    }

    public <T extends Block & PolymerBlock> FactoryBlockItem(T block, Properties settings) {
        super(block, settings);
        this.polymerItem = Items.RABBIT_FOOT;
    }

    @Override
    public Item getPolymerItem(ItemStack stack, PacketContext context) {
        return this.polymerItem;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        try (var x = PolymerUtils.ignorePlaySoundExclusion()) {
            return super.useOn(context);
        }
    }

    @Override
    public boolean isPolymerBlockInteraction(BlockState state, ServerPlayer player, InteractionHand hand, ItemStack stack, ServerLevel world, BlockHitResult blockHitResult, InteractionResult actionResult) {
        return actionResult.consumesAction();
    }
}
