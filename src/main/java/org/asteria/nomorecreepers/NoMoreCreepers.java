package org.asteria.nomorecreepers;

import com.sun.org.apache.bcel.internal.generic.Select;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
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
            switch(e.getEntityType()) {
                case SKELETON:  //the entity is a skeleton
                    Skeleton S = (Skeleton) e.getEntity();
                    if (!getConfig().getBoolean("SKELETON.BOW")) {
                        S.getEquipment().setItemInMainHand(null);
                    }
                case ZOMBIE:        //the entity is a skeleton
                    if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.REINFORCEMENTS) {      //tests if the entity is spawned because of a reinforcement call
                        e.setCancelled(!getConfig().getBoolean("ZOMBIE.REINFORCEMENTS"));      // checks the config file to see if reinforcments are allowed
                    }

            }


        }
    }

    @EventHandler
    public void OnCreeperExplode(ExplosionPrimeEvent e) {
        if (e.getEntityType() == EntityType.CREEPER) {

            e.setCancelled(!getConfig().getBoolean("CREEPER.EXPLODES"));
        }
    }
    @EventHandler
    public void OnTeleport(EntityTeleportEvent e) {
        if (e.getEntityType() == EntityType.ENDERMAN) {
            e.setCancelled(!getConfig().getBoolean("ENDERMAN.TELEPORT"));
        }
    }





    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
