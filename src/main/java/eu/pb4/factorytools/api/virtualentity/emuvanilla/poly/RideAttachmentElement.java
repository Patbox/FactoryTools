package eu.pb4.factorytools.api.virtualentity.emuvanilla.poly;

import eu.pb4.polymer.virtualentity.api.elements.GenericEntityElement;
import eu.pb4.polymer.virtualentity.api.tracker.EntityTrackedData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.lang3.function.Consumers;

import java.util.List;
import java.util.function.Consumer;

public class RideAttachmentElement extends GenericEntityElement {
    private float maxHealth = 0;

    public RideAttachmentElement() {
        this.dataTracker.set(EntityTrackedData.SILENT, true);
        this.dataTracker.set(EntityTrackedData.NO_GRAVITY, true);
        this.dataTracker.set(EntityTrackedData.FLAGS, (byte) ((1 << EntityTrackedData.INVISIBLE_FLAG_INDEX)));
    }


    @Override
    public void startWatching(ServerPlayerEntity player, Consumer<Packet<ClientPlayPacketListener>> packetConsumer) {
        super.startWatching(player, packetConsumer);
        var scale = new EntityAttributeInstance(EntityAttributes.SCALE, Consumers.nop());
        scale.setBaseValue(0);
        var health = new EntityAttributeInstance(EntityAttributes.MAX_HEALTH, Consumers.nop());
        health.setBaseValue(this.maxHealth);
        packetConsumer.accept(new EntityAttributesS2CPacket(this.getEntityId(), List.of(
                scale, health
        )));
    }

    @Override
    protected EntityType<? extends Entity> getEntityType() {
        return EntityType.HORSE;
    }

    public void setMaxHealth(float maxHealth) {
        if (this.maxHealth == maxHealth) {
            return;
        }
        this.maxHealth = maxHealth;
        var att = new EntityAttributeInstance(EntityAttributes.MAX_HEALTH, Consumers.nop());
        att.setBaseValue(this.maxHealth);
        this.getHolder().sendPacket(new EntityAttributesS2CPacket(this.getEntityId(), List.of(att)));
    }
}
