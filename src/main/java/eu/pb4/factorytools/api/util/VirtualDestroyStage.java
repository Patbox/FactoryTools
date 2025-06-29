package eu.pb4.factorytools.api.util;

import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.factorytools.impl.ModInit;
import eu.pb4.factorytools.impl.ServerPlayNetExtF;
import eu.pb4.polymer.core.api.utils.PolymerSyncedObject;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import eu.pb4.polymer.resourcepack.extras.api.ResourcePackExtras;
import eu.pb4.polymer.resourcepack.extras.api.format.item.ItemAsset;
import eu.pb4.polymer.resourcepack.extras.api.format.item.model.BasicItemModel;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.nio.charset.StandardCharsets;

public class VirtualDestroyStage extends ElementHolder {
    public static final ItemStack[] MODELS = new ItemStack[10];
    private final ItemDisplayElement main = new ItemDisplayElement();
    private int state;


    public VirtualDestroyStage() {
        this.main.setItem(MODELS[0]);
        this.main.setScale(new Vector3f(1.01f));
        this.addElement(this.main);
    }

    @SuppressWarnings("SameReturnValue")
    public static boolean updateState(ServerPlayerEntity player, BlockPos pos, BlockState state, int i) {
        var self = ((ServerPlayNetExtF) player.networkHandler).factorytools$getVirtualDestroyStage();

        if (i == -1 || !(state.getBlock() instanceof Marker || PolymerSyncedObject.getSyncedObject(Registries.BLOCK, state.getBlock()) instanceof Marker)) {
            self.setState(-1);
            if (self.getAttachment() != null) {
                self.destroy();
            }
            return true;
        }

        var vecPos = Vec3d.ofCenter(pos);

        if (self.getAttachment() == null || !self.getAttachment().getPos().equals(vecPos)) {
            ChunkAttachment.of(self, player.getWorld(), vecPos);
        }

        self.setState(i);

        return true;
    }

    public static void destroy(@Nullable ServerPlayerEntity player) {
        if (player != null && player.networkHandler != null) {
            ((ServerPlayNetExtF) player.networkHandler).factorytools$getVirtualDestroyStage().destroy();
        }
    }

    public void setState(int i) {
        if (this.state == i) {
            return;
        }

        this.state = i;
        this.main.setItem(i < 0 ? ItemStack.EMPTY : MODELS[Math.min(i, MODELS.length - 1)]);
        this.tick();
    }


    static {
        ResourcePackExtras.forDefault().addBridgedModelsFolder(Identifier.of(ModInit.ID, "block/special"));

        for (int i = 0; i < MODELS.length; i++) {
            MODELS[i] = ItemDisplayElementUtil.getModel(Identifier.of(ModInit.ID, "block/special/destroy_stage_" + i));
        }

        var model =  """
                {
                  "parent": "minecraft:block/cube_all",
                  "textures": {
                    "all": "minecraft:block/destroy_stage_|ID|"
                  }
                }
                """;

        PolymerResourcePackUtils.RESOURCE_PACK_CREATION_EVENT.register(x -> {
            for (var i = 0; i < MODELS.length; i++) {
                x.addData("", new ItemAsset(new BasicItemModel(Identifier.of(ModInit.ID, "block/special/destroy_stage_" + i)), ItemAsset.Properties.DEFAULT).toJson().getBytes(StandardCharsets.UTF_8));
                x.addData("assets/factorytools/models/block/special/destroy_stage_" + i + ".json", model.replace("|ID|", "" + i).getBytes(StandardCharsets.UTF_8));
            }
        });
    }

    public interface Marker {}
}
