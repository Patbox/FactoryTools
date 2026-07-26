package eu.pb4.factorytools.api.util;

import eu.pb4.factorytools.impl.CompatStatus;
import org.joml.*;


public final class SharedMatrix4f {
    private final int size;
    private final Matrix4fStack main;
    private final ThreadLocal<Matrix4fStack> threaded;

    public SharedMatrix4f() {
        this(8);
    }

    public SharedMatrix4f(int size) {
        this.size = size;
        this.main = new Matrix4fStack(this.size);
        this.threaded = ThreadLocal.withInitial(() -> new Matrix4fStack(this.size));
    }

    public Matrix4f get() {
        return getStack();
    }

    public Matrix4f main() {
        return mainStack();
    }

    public Matrix4fStack getStack() {
        return threaded.get();
    }

    public Matrix4fStack mainStack() {
        return (CompatStatus.BROKEN_THREADING ? threaded.get() : main).clear();
    }
}