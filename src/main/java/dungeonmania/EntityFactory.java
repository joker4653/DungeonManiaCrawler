package dungeonmania;

import java.util.HashMap;

import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Collectables.Akey;
import dungeonmania.Entities.Collectables.Bomb;
import dungeonmania.Entities.Collectables.Sword;
import dungeonmania.Entities.Collectables.Treasure;
import dungeonmania.Entities.Collectables.Wood;
import dungeonmania.Entities.Collectables.SunStone;
import dungeonmania.Entities.Moving.Assassin;
import dungeonmania.Entities.Moving.Hydra;
import dungeonmania.Entities.Moving.Mercenary;
import dungeonmania.Entities.Moving.Player;
import dungeonmania.Entities.Moving.Spider;
import dungeonmania.Entities.Moving.ZombieToast;
import dungeonmania.Entities.Static.Boulder;
import dungeonmania.Entities.Static.Door;
import dungeonmania.Entities.Static.Exit;
import dungeonmania.Entities.Static.FloorSwitch;
import dungeonmania.Entities.Static.Portal;
import dungeonmania.Entities.Static.SwampTile;
import dungeonmania.Entities.Static.Wall;
import dungeonmania.Entities.Static.ZombieToastSpawner;

public class EntityFactory {

    public static Entity createEntity(String type, int x, int y, int key, String colour, HashMap<String, String> configMap) {
        if (type.equalsIgnoreCase("Player")) {
            return new Player(x, y, configMap);
        } else if (type.equalsIgnoreCase("Spider")) {
            return new Spider(x, y, configMap);
        } else if (type.equalsIgnoreCase("Boulder")) {
            return new Boulder(x, y);
        } else if (type.equalsIgnoreCase("Treasure")) {
            return new Treasure(x, y);
        } else if (type.equalsIgnoreCase("zombie_toast_spawner")) {
            return new ZombieToastSpawner(x, y);
        } else if (type.equalsIgnoreCase("wall")) {
            return new Wall(x, y);
        } else if (type.equalsIgnoreCase("door")) {
            return new Door(x, y, key);
        } else if (type.equalsIgnoreCase("zombie_toast")) {
            return new ZombieToast(x, y, configMap);
        } else if (type.equalsIgnoreCase("mercenary")) {
            return new Mercenary(x, y, configMap);
        } else if (type.equalsIgnoreCase("Treasure")) {
            return new Treasure(x, y);
        } else if (type.equalsIgnoreCase("sword")) {
            return new Sword(x, y, Integer.parseInt(configMap.get("sword_durability")), Integer.parseInt(configMap.get("sword_attack")));
        } else if (type.equalsIgnoreCase("switch")) {
            return new FloorSwitch(x, y);
        } else if (type.equalsIgnoreCase("wood")) {
            return new Wood(x, y);
        } else if (type.equalsIgnoreCase("bomb")) {
            return new Bomb(x, y, Integer.parseInt(configMap.get("bomb_radius")));
        } else if (type.equalsIgnoreCase("key")) {
            return new Akey(x, y, key);
        } else if (type.equalsIgnoreCase("exit")) {
            return new Exit(x, y);
        } else if (type.equalsIgnoreCase("portal")) {
            return new Portal(x, y, colour);
        } else if (type.equalsIgnoreCase("hydra")) {
            return new Hydra(x, y, configMap);
        } else if (type.equalsIgnoreCase("assassin")) {
            return new Assassin(x, y, configMap);
        } else if (type.equalsIgnoreCase("swamp_tile")) {
            return new SwampTile(x, y, configMap);
        } else if (type.equalsIgnoreCase("sun_stone")) {
            return new SunStone(x, y);
        }
        
        return null;
    }
}
