package eu.pb4.factorytools.api.util;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LazyItemStack implements Supplier<ItemStack> {
    private final Supplier<ItemStack> supply;
    private final List<Derivative<?>> derivatives = new ArrayList<>();
    private WeakReference<DataComponentMap> componentMapReference = null;
    private ItemStack stack = null;

    public LazyItemStack(Supplier<ItemStack> stack) {
        this.supply = stack;
    }

    public LazyItemStack(ItemStackTemplate template) {
        this(template::create);
    }

    @Override
    public ItemStack get() {
        if (this.componentMapReference == null || this.componentMapReference.refersTo(stack.typeHolder().components())) {
            this.stack = this.supply.get();
            this.componentMapReference = new WeakReference<>(stack.typeHolder().components());
            synchronized (derivatives) {
                for (var d : derivatives) {
                    d.value = null;
                }
            }
        }
        return this.stack;
    }

    public <T> Supplier<T> derivative(Function<ItemStack, T> function) {
        var d = new Derivative<T>();
        d.creator = function;
        synchronized (derivatives) {
            this.derivatives.add(d);
        }
        return d;
    }


    private final class Derivative<T> implements Supplier<T> {
        Function<ItemStack, T> creator;
        T value;

        public void update(ItemStack stack) {
            this.value = this.creator.apply(stack);
        }

        @Override
        public T get() {
            if (this.value == null) {
                this.value = this.creator.apply(LazyItemStack.this.get());
            }

            return this.value;
        }
    }
}
