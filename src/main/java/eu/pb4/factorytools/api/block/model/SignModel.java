package eu.pb4.factorytools.api.block.model;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class SignModel extends BlockModel {
    private static final Map<Block, ItemStack> MODEL_MAP = new HashMap<>();
    private final ItemDisplayElement main = new ItemDisplayElement();

    public SignModel(BlockState state, BlockPos pos) {
        this.main.setInvisible(true);
        this.main.setItem(MODEL_MAP.getOrDefault(state.getBlock(), ItemStack.EMPTY));
        this.updateRotation(state);
        this.addElement(this.main);
    }

    protected void updateRotation(BlockState state) {
        this.main.setYaw(state.getBlock() instanceof AbstractSignBlock signBlock ? signBlock.getRotationDegrees(state) : 0);
    }

    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        super.notifyUpdate(updateType);
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            updateRotation(this.blockState());
            this.tick();
        }
    }

    public static void setModel(Block block, Identifier model) {
        MODEL_MAP.put(block, ItemDisplayElementUtil.getModel(model));
    }
}
