package eu.pb4.factorytools.impl;

import net.fabricmc.loader.api.FabricLoader;

public interface CompatStatus {
    boolean C2ME = FabricLoader.getInstance().isModLoaded("c2me");
    boolean ASYNC = FabricLoader.getInstance().isModLoaded("async");
    boolean WORLDTHREADER = FabricLoader.getInstance().isModLoaded("worldthreader");
    boolean MOONRISE = FabricLoader.getInstance().isModLoaded("moonrise");

    boolean BROKEN_THREADING = C2ME || ASYNC || WORLDTHREADER || MOONRISE;
}
