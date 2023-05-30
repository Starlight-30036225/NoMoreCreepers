package org.asteria.nomorecreepers;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class NoMoreCreepers extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        //saveDefaultConfig();

        //Tell events to check here
        Bukkit.getPluginManager().registerEvents(this, this);   //(Class that handles events, self)
    }

    @EventHandler
    public void OnCreatureSpawn(CreatureSpawnEvent e) {

        if (e.getEntityType() == EntityType.CREEPER) {      //Checks the entity attempting to spawn is a creeper
            e.setCancelled(true);       //Cancels the spawn event of the creeper
        }
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
