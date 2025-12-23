package eu.pb4.factorytools.api.virtualentity.emuvanilla2.poly;

import eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement;
import eu.pb4.polymer.virtualentity.api.tracker.EntityTrackedData;
import org.apache.commons.lang3.function.Consumers;

import java.util.List;
import java.util.function.Consumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class LeadAttachmentElement extends GenericEntityElement {
    public LeadAttachmentElement() {
        this.dataTracker.set(EntityTrackedData.SILENT, true);
        this.dataTracker.set(EntityTrackedData.NO_GRAVITY, true);
        this.dataTracker.set(EntityTrackedData.FLAGS, (byte) ((1 << EntityTrackedData.INVISIBLE_FLAG_INDEX)));
    }


    @Override
    public void startWatching(ServerPlayer player, Consumer<Packet<ClientGamePacketListener>> packetConsumer) {
        super.startWatching(player, packetConsumer);
        var scale = new AttributeInstance(Attributes.SCALE, Consumers.nop());
        scale.setBaseValue(0.25);
        packetConsumer.accept(new ClientboundUpdateAttributesPacket(this.getEntityId(), List.of(
                scale
        )));
    }

    @Override
    protected EntityType<? extends Entity> getEntityType() {
        return EntityType.VEX;
    }
}
