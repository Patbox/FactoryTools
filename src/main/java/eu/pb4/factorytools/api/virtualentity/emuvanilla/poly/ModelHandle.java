package eu.pb4.factorytools.api.virtualentity.emuvanilla.poly;

import eu.pb4.factorytools.api.virtualentity.emuvanilla.PolyModelInstance;
import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.EntityModel;
import eu.pb4.factorytools.api.virtualentity.emuvanilla.model.ModelPart;
import eu.pb4.factorytools.api.virtualentity.ItemDisplayElementUtil;
import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

import java.util.IdentityHashMap;
import java.util.Map;

public class ModelHandle<T extends Entity> {
    private final Map<ModelPart, ItemDisplayElement> elements = new IdentityHashMap<>();
    private final ElementHolder holder;
    private final Vec3d offset;
    private PolyModelInstance<EntityModel<T>> model;
    private boolean hurt = false;

    private boolean hidden = false;

    public <X extends EntityModel<T>> ModelHandle(ElementHolder holder, PolyModelInstance<X> model, EntityDimensions dimensions, Vec3d offset) {
        this(holder, offset);
        this.setModel(model, dimensions);
    }

    public ModelHandle(ElementHolder holder, Vec3d offset) {
        this.holder = holder;
        this.offset = offset;
    }

    public <X extends EntityModel<T>> void setModel(PolyModelInstance<X> model, EntityDimensions dimensions) {
        if (this.model == model) {
            return;
        }

        if (!this.hidden) {
            updateElements(model, dimensions);
        }
        this.model = (PolyModelInstance<EntityModel<T>>) model;
    }

    public <X extends EntityModel<T>> void setModelAndShow(PolyModelInstance<X> model, EntityDimensions dimensions) {
        if (this.model != model || this.hidden) {
            updateElements(model, dimensions);
        }
        this.hidden = false;
        this.model = (PolyModelInstance<EntityModel<T>>) model;
    }


    public void setHidden(boolean hidden, EntityDimensions dimensions) {
        if (this.hidden == hidden) {
            return;
        }
        this.hidden = hidden;

        if (!hidden) {
            updateElements(this.model, dimensions);
        } else {
            removeAllElements();
        }
    }

    private void removeAllElements() {
        for (var old : this.elements.values()) {
            this.holder.removeElement(old);
        }
        this.elements.clear();
    }

    private <X extends EntityModel<T>> void updateElements(PolyModelInstance<X> model, EntityDimensions dimensions) {
        var oldElements = new IdentityHashMap<>(this.elements);
        this.elements.clear();

        for (var part : model.model().getParts()) {
            var stack = model.modelParts().apply(part);
            if (stack != null) {
                var element = oldElements.get(part);
                if (element == null) {
                    element = ItemDisplayElementUtil.createSimple(stack);
                    element.setDisplaySize(dimensions.width() * 2, dimensions.height() * 2);
                    element.setInterpolationDuration(1);
                    element.setTeleportDuration(3);
                    element.setViewRange(2);
                    element.setOffset(this.offset);
                } else {
                    element.setItem(stack);
                    oldElements.remove(part);
                }
                this.elements.put(part, element);
                this.holder.addElement(element);
            }
        }
        for (var old : oldElements.values()) {
            this.holder.removeElement(old);
        }
    }

    public void update(T entity, Matrix4fStack mat) {
        mat.pushMatrix();

        if (entity instanceof LivingEntity livingEntity) {
            var hurt = livingEntity.hurtTime > 0 || livingEntity.deathTime > 0;
            if (this.hurt != hurt) {
                this.hurt = hurt;

                var map = hurt ? this.model.damagedModelParts() : this.model.modelParts();

                for (var entry : elements.entrySet()) {
                    entry.getValue().setItem(map.apply(entry.getKey()));
                }
            }
        }

        this.model.model().setAngles(entity);
        this.model.model().render(mat, this::updateElement);
        mat.popMatrix();
    }

    private void updateElement(ModelPart part, Matrix4f matrix4f, boolean hidden) {
        var element = this.elements.get(part);
        if (element == null) {
            return;
        }

        if (hidden) {
            this.holder.removeElement(element);
        } else {
            element.setTransformation(matrix4f);
            element.startInterpolationIfDirty();
            this.holder.addElement(element);
        }
    }
}
