package org.asteria.nomorecreepers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class NoMoreCreepers extends JavaPlugin implements Listener {

    Creatures MobHolder[];
    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //Tell events to check here
        Bukkit.getPluginManager().registerEvents(this, this);   //(Class that handles events, self)

    }


    @EventHandler
    public void OnCreatureSpawn(CreatureSpawnEvent e) {

        boolean canSpawn;       //Holds whether the entity is allowed to spawn

        //Essentially a XAND gate here (Equivelent to a NXOR)
        canSpawn = !(getConfig().contains(e.getEntity().getName().toUpperCase()) ^ getConfig().getBoolean(e.getEntity().getName().toUpperCase()+".SPAWN"));
        //First checks if the entity is mentioned inside the config.yml,
        //Then checks the rule of its spawning inside the config


        e.setCancelled(!canSpawn);      //if it cant spawn, cancel the spawn event

        if (canSpawn) {     //If the entity is spawned, there may be additional events depending on the type.
            //do nothing
        }
        else{


        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
