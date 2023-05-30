package org.asteria.nomorecreepers;

import org.bukkit.entity.EntityType;

public enum Creatures {
    SKELETON(EntityType.SKELETON),
    CREEPER(EntityType.CREEPER),
    ZOMBIE(EntityType.ZOMBIE),
    ENDERMAN(EntityType.ENDERMAN),
    SPIDER(EntityType.SPIDER);

    private boolean SpawnEnabled;
    private boolean PowerEnabled;
    private EntityType Type;

    Creatures(EntityType Type) {this.Type = Type;}


    public boolean isSpawnEnabled() {
        return SpawnEnabled;
    }

    public void setSpawnEnabled(boolean spawnEnabled) {
        SpawnEnabled = spawnEnabled;
    }

    public boolean isPowerEnabled() {
        return PowerEnabled;
    }

    public void setPowerEnabled(boolean powerEnabled) {
        PowerEnabled = powerEnabled;
    }

    public EntityType getType() {
        return Type;
    }
}
