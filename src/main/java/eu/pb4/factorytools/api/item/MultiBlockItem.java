package eu.pb4.factorytools.api.item;

import eu.pb4.factorytools.api.item.FactoryBlockItem;
import eu.pb4.factorytools.api.block.MultiBlock;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockItem extends FactoryBlockItem {
    private final MultiBlock multiBlock;

    public <T extends MultiBlock & PolymerBlock> MultiBlockItem(T block, Properties settings) {
        super(block, settings);
        this.multiBlock = block;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return this.multiBlock.place(context, state);
    }
}
