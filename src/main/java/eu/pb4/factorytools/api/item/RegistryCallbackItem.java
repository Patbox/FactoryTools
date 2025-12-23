package eu.pb4.factorytools.api.item;

import net.minecraft.resources.Identifier;

public interface RegistryCallbackItem {
    void onRegistered(Identifier selfId);
}
