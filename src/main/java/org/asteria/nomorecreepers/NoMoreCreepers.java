package org.asteria.nomorecreepers;


import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;


public final class NoMoreCreepers extends JavaPlugin implements Listener {

    Random rand;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //Tell events to check here
        Bukkit.getPluginManager().registerEvents(this, this);   //(Class that handles events, self)

        rand = new Random();
    }

    @EventHandler
    public void OnCreatureDeath(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.CREEPER && getConfig().getBoolean("FUN.MARTYRDOM")) {
            Creeper C = (Creeper) e.getEntity();
            C.explode();
        } else if (e.getEntityType() == EntityType.GIANT) {
            Giant G = (Giant) e.getEntity();
            Zombie Z = (Zombie) G.getPassengers().get(0);
            Z.remove();
        }

    }
    @EventHandler
    public void OnCreatureSpawn(CreatureSpawnEvent e) {

        boolean canSpawn;       //Holds whether the entity is allowed to spawn

        //Essentially a XAND gate here (Equivalent to a NXOR)
        canSpawn = !(getConfig().contains("SPAWN-PREVENTION."+e.getEntity().getName().toUpperCase()) ^ getConfig().getBoolean("SPAWN-PREVENTION." + e.getEntity().getName().toUpperCase()+".SPAWN"));
        //First checks if the entity is mentioned inside the config.yml,
        //Then checks the rule of its spawning inside the config


        e.setCancelled(!canSpawn);      //if it cant spawn, cancel the spawn event

        if (canSpawn) {     //If the entity is spawned, there may be additional events depending on the type.
            switch(e.getEntityType()) {
                case SKELETON:  //the entity is a skeleton#
                {

                    if (!getConfig().getBoolean("ADDITIONAL-FEATURES.SKELETON-BOWS")) {
                        Skeleton S = (Skeleton) e.getEntity();
                        S.getEquipment().setItemInMainHand(null);
                    }
                    break;
                }
                case ZOMBIE:        //the entity is a skeleton
                {
                    if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.REINFORCEMENTS) {      //tests if the entity is spawned because of a reinforcement call
                        e.setCancelled(!getConfig().getBoolean("ADDITIONAL-FEATURES.REINFORCEMENTS"));      // checks the config file to see if reinforcements are allowed
                    }
                    if (getConfig().getBoolean("FUN.SPAWN-GIANTS")) {
                        if (rand.nextInt(0,1000) == 1) {
                            Location temp = e.getLocation();
                            //e.setCancelled(true);
                            Giant G = (Giant) Bukkit.getWorld("world").spawnEntity(temp, EntityType.GIANT);
                            G.addPassenger(e.getEntity());
                            //e.getEntity().setInvisible(true);
                            e.getEntity().setInvulnerable(true);
                            //when the giant dies, I need to call back and kill its passenger
                            G.setAI(true);
                            //They can now move but have no attack, ill see what I can do about this when I am smarter
                        }
                    }
                    break;
                }
                case SHEEP:
                {
                    if (getConfig().getBoolean("FUN.RAINBOW-SHEEP")) {
                        Sheep S = (Sheep) e.getEntity();
                        S.setColor(DyeColor.MAGENTA);
                        S.setCustomName("jeb_");
                    }
                    break;
                }
                case VINDICATOR: {
                    Vindicator V = (Vindicator) e.getEntity();
                    V.setJohnny(getConfig().getBoolean("FUN.HERES-JOHNNY"));
                }
            }


        }
    }

    @EventHandler
    public void OnCreeperExplode(ExplosionPrimeEvent e) {
        if (e.getEntityType() == EntityType.CREEPER) {

            e.setCancelled(!getConfig().getBoolean("ADDITIONAL-FEATURES.CREEPERS-EXPLODE"));
        }
    }
    @EventHandler
    public void OnTeleport(EntityTeleportEvent e) {
        if (e.getEntityType() == EntityType.ENDERMAN) {
            e.setCancelled(!getConfig().getBoolean("ADDITIONAL-FEATURES.ENDERMAN-TELEPORT"));
        }
    }





    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
