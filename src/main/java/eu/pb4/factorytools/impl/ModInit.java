package eu.pb4.factorytools.impl;

import eu.pb4.factorytools.api.advancement.FactoryAdvancementCriteria;
import eu.pb4.factorytools.api.block.AttackableBlock;
import eu.pb4.factorytools.api.item.RegistryCallbackItem;
import eu.pb4.factorytools.api.util.VirtualDestroyStage;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.factorytools.api.virtualentity.LodItemDisplayElement;
import eu.pb4.polymer.core.api.block.PolymerBlockUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class ModInit implements ModInitializer {
    public static final boolean DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();

    public static final String ID = "factorytools";
    public void onInitialize() {
        FactoryAdvancementCriteria.register();
        PolymerBlockUtils.BREAKING_PROGRESS_UPDATE.register(VirtualDestroyStage::updateState);

        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                ((ServerPlayNetExtF) serverPlayer.connection).factorytools$getVirtualDestroyStage().setState(-1);
            }
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            var state = world.getBlockState(pos);

            if (state.getBlock() instanceof AttackableBlock attackableBlock && hand == InteractionHand.MAIN_HAND) {
                return attackableBlock.onPlayerAttack(state, player, world, pos, direction);
            }

            return InteractionResult.PASS;
        });

        for (var item : BuiltInRegistries.ITEM) {
            if (item instanceof RegistryCallbackItem modeledPolymerItem) {
                modeledPolymerItem.onRegistered(BuiltInRegistries.ITEM.getKey(item));
                ItemDisplayElementUtil.getModel(item);
            }
        }
        RegistryEntryAddedCallback.event(BuiltInRegistries.ITEM).register((rawId, id, item) -> {
            if (item instanceof RegistryCallbackItem modeledPolymerItem) {
                modeledPolymerItem.onRegistered(id);
                ItemDisplayElementUtil.getModel(item);
            }
        });

        VirtualDestroyStage.destroy(null);
        PolymerResourcePackUtils.addModAssets(ID);
        PolymerResourcePackUtils.markAsRequired();
    }
}
