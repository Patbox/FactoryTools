package eu.pb4.factorytools.api.util;

import eu.pb4.factorytools.impl.CompatStatus;
import org.joml.*;


public final class SharedMatrix4f {
    private final Matrix4f main = new Matrix4f();
    private final ThreadLocal<Matrix4f> threaded = ThreadLocal.withInitial(Matrix4f::new);

    public Matrix4f get() {
        return threaded.get();
    }

    public Matrix4f main() {
        return (CompatStatus.BROKEN_THREADING ? threaded.get() : main).identity();
    }
}