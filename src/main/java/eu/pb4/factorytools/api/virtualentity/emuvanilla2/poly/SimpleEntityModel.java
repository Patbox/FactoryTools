package eu.pb4.factorytools.api.virtualentity.emuvanilla2.poly;

import eu.pb4.factorytools.api.virtualentity.emuvanilla2.EntityModelTransforms;
import eu.pb4.factorytools.api.virtualentity.emuvanilla2.PolyModelInstance;
import eu.pb4.factorytools.api.virtualentity.emuvanilla2.model.EntityModel;
import eu.pb4.factorytools.mixin.LivingEntityAccessor;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.InteractionElement;
import eu.pb4.polymer.virtualentity.api.elements.VirtualElement;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public class SimpleEntityModel<T extends LivingEntity> extends ElementHolder {
    private static final Vec3 OFFSET = new Vec3(0, 0.2, 0);

    private static final Matrix4fStack STACK = new Matrix4fStack(64);
    public final InteractionElement interaction;
    public final LeadAttachmentElement leadAttachment = new LeadAttachmentElement();
    public final RideAttachmentElement rideAttachment = new RideAttachmentElement();
    protected final T entity;
    protected final ModelHandle<T> model;

    private boolean noTick = true;
    private float height = -1;

    public SimpleEntityModel(T entity, PolyModelInstance<EntityModel<T>> model) {
        this.entity = entity;
        this.model = new ModelHandle<>(this, model, entity.getType().getDimensions(), OFFSET);
        var interaction = VirtualElement.InteractionHandler.redirect(entity);
        this.interaction = new InteractionElement(interaction);
        this.interaction.setSendPositionUpdates(false);
        this.addElement(leadAttachment);
        this.addElement(rideAttachment);
        this.addPassengerElement(this.interaction);
    }

    public <X extends EntityModel<T>> void setModel(PolyModelInstance<X> model) {
        this.model.setModel(model, this.entity.getDimensions(this.entity.getPose()));
    }

    @Override
    public boolean startWatching(ServerGamePacketListenerImpl player) {
        if (noTick) {
            onTick();
        }
        return super.startWatching(player);
    }

    @Override
    protected void onTick() {
        noTick = false;
        this.rideAttachment.setMaxHealth(this.entity.getMaxHealth());
        this.rideAttachment.getDataTracker().set(LivingEntityAccessor.getDATA_HEALTH_ID(), this.entity.getHealth());
        this.interaction.setCustomName(this.entity.getCustomName());
        this.interaction.setCustomNameVisible(this.entity.isCustomNameVisible());
        this.rideAttachment.setYaw(entity.getYRot());
        if (entity.getBbHeight() != this.height) {
            this.height = entity.getBbHeight();
            this.interaction.setSize(entity.getBbWidth(), this.height);
            this.leadAttachment.setOffset(new Vec3(0, this.height / 2, 0));
            this.rideAttachment.setOffset(new Vec3(0, this.height, 0));
        }

        STACK.pushMatrix();

        STACK.translate(0.0F, -0.2f, 0.0F);
        EntityModelTransforms.livingEntityTransform(this.entity, STACK, this::scale);
        this.model.update(this.entity, STACK);

        STACK.popMatrix();
        super.onTick();
    }

    protected void scale(Matrix4f matrix4f) {
    }
}
