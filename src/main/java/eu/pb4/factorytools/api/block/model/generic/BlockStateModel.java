package eu.pb4.factorytools.api.block.model.generic;

import eu.pb4.factorytools.api.virtualentity.BlockModel;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.attachment.BlockAwareAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.List;

public class BlockStateModel extends BlockModel {
    private final List<ItemDisplayElement> modelElements = new ArrayList<>();
    private final float viewRange;

    public BlockStateModel(BlockState state, BlockPos pos, float viewRange) {
        var model = BlockStateModelManager.get(state);
        this.viewRange = viewRange;
        this.applyModel(model, pos);
    }

    public static BlockStateModel longRange(BlockState state, BlockPos pos) {
        return new BlockStateModel(state, pos, 100);
    }

    public static BlockStateModel midRange(BlockState state, BlockPos pos) {
        return new BlockStateModel(state, pos,3);
    }

    public static BlockStateModel shortRange(BlockState state, BlockPos pos) {
        return new BlockStateModel(state, pos,1.1f);
    }


    @Override
    public void notifyUpdate(HolderAttachment.UpdateType updateType) {
        super.notifyUpdate(updateType);
        if (updateType == BlockAwareAttachment.BLOCK_STATE_UPDATE) {
            applyModel(BlockStateModelManager.get(this.blockState()), this.blockPos());
            applyUpdates(this.blockState(), this.blockPos());
        }
    }

    protected void applyUpdates(BlockState blockState, BlockPos pos) {
    }

    private void applyModel(List<BlockStateModelManager.ModelGetter> models, BlockPos pos) {
        var random = Random.create(this.blockState().getRenderingSeed(pos));
        int i = 0;
        while (models.size() < modelElements.size()) {
            this.removeElement(this.modelElements.removeLast());
        }
        for (; i < models.size(); i++) {
            var newModel = false;
            ItemDisplayElement element;
            if (this.modelElements.size() <= i) {
                element = ItemDisplayElementUtil.createSimple();
                element.setViewRange(this.viewRange);
                element.setTeleportDuration(0);
                element.setItemDisplayContext(ItemDisplayContext.NONE);
                element.setYaw(180);
                this.setupElement(element, i);
                newModel = true;
                this.modelElements.add(element);
            } else {
                element = this.modelElements.get(i);
            }

            var model = models.get(i).getModel(random);

            element.setItem(model.stack());
            element.setLeftRotation(model.quaternionfc());

            if (newModel) {
                this.addElement(element);
            } else {
                element.tick();
            }
        }
    }

    protected void setupElement(ItemDisplayElement element, int i) {
    }
}
