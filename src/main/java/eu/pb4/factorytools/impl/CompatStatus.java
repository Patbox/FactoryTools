package eu.pb4.factorytools.impl;

import net.fabricmc.loader.api.FabricLoader;

public interface CompatStatus {
    boolean C2ME = FabricLoader.getInstance().isModLoaded("c2me");
}
