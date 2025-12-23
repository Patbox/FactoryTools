package eu.pb4.factorytools.api.block;

import net.minecraft.world.level.chunk.LevelChunk;

public interface BlockEntityExtraListener {
    void onListenerUpdate(LevelChunk chunk);
}
