package net.minestom.server.item.metadata;

import net.minestom.server.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

// TODO for which item
public class SpawnEggMeta implements ItemMeta {

    private EntityType entityType;

    @Override
    public boolean hasNbt() {
        return entityType != null;
    }

    @Override
    public boolean isSimilar(@NotNull ItemMeta itemMeta) {
        if (!(itemMeta instanceof SpawnEggMeta))
            return false;
        final SpawnEggMeta spawnEggMeta = (SpawnEggMeta) itemMeta;
        return spawnEggMeta.entityType == entityType;
    }

    @Override
    public void read(@NotNull NBTCompound compound) {
        if (compound.containsKey("EntityTag")) {
            // TODO
        }
    }

    @Override
    public void write(@NotNull NBTCompound compound) {
        if (!hasNbt())
            return;
        NBTCompound entityCompound = new NBTCompound();
        if (entityType != null) {
            entityCompound.setString("id", entityType.getNamespaceID());
        }

    }

    @NotNull
    @Override
    public ItemMeta copy() {
        SpawnEggMeta spawnEggMeta = new SpawnEggMeta();
        spawnEggMeta.entityType = entityType;
        return spawnEggMeta;
    }
}
