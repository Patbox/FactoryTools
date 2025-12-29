package eu.pb4.factorytools.api.block.model;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;

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
        this.main.setYaw(state.getBlock() instanceof SignBlock signBlock ? signBlock.getYRotationDegrees(state) : 0);
    }

    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        super.notifyUpdate(updateType);
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            updateRotation(this.blockState());
            this.tick();
        }
    }

    @Deprecated
    public static void setModel(Block block, Identifier model) {
        MODEL_MAP.put(block, ItemDisplayElementUtil.getTransparentModel(model));
    }

    public static void setSolidModel(Block block, Identifier model) {
        MODEL_MAP.put(block, ItemDisplayElementUtil.getSolidModel(model));
    }

    public static void setTransparentModel(Block block, Identifier model) {
        MODEL_MAP.put(block, ItemDisplayElementUtil.getTransparentModel(model));
    }
}
