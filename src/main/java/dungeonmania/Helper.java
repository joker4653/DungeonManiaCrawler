package dungeonmania;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dungeonmania.Battling.Battle;
import dungeonmania.Battling.Round;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.Entities.Collectables.Akey;
import dungeonmania.Entities.Collectables.Bomb;
import dungeonmania.Entities.Collectables.SunStone;
import dungeonmania.Entities.Collectables.Sword;
import dungeonmania.Entities.Collectables.Treasure;
import dungeonmania.Entities.Collectables.Wood;
import dungeonmania.Entities.Moving.Hydra;
import dungeonmania.Entities.Moving.Assassin;
import dungeonmania.Entities.Moving.Mercenary;
import dungeonmania.Entities.Moving.MovingEntity;
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
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Helper implements Serializable {
    /** 
     *  finds minX, maxX, minY and maxY based on the Dungeon map's coordinates.
     */
    public static HashMap<String, Integer> findMinAndMaxValues(List<Entity> listOfEntities) {
        HashMap<String, Integer> mapOfMinAndMaxValues = new HashMap<>();
        
        List<Integer> listOfXPositions = listOfEntities.stream()
                                                        .map(e -> e.getCurrentLocation().getX())
                                                        .collect(Collectors.toList());

        List<Integer> listOfYPositions = listOfEntities.stream()
                                                        .map(e -> e.getCurrentLocation().getY())
                                                        .collect(Collectors.toList());

        mapOfMinAndMaxValues.put("minX", Collections.min(listOfXPositions));
        mapOfMinAndMaxValues.put("maxX", Collections.max(listOfXPositions));
        mapOfMinAndMaxValues.put("minY", Collections.min(listOfYPositions));
        mapOfMinAndMaxValues.put("maxY", Collections.max(listOfYPositions));

        return mapOfMinAndMaxValues;
    }

    /**
     * getBattleResponse
     */
    public static List<BattleResponse> getBattleResponse(List<Battle> listOfBattles) {
        List<BattleResponse> battleRespList = new ArrayList<>();

        for (Battle currBattle : listOfBattles) {
            battleRespList.add(new BattleResponse(currBattle.getEnemyType(), getRoundsResponse(currBattle.getRounds()), currBattle.getInitPlayerHealth(), currBattle.getInitEnemyHealth()));
        }

        return battleRespList;
    }

    /**
     * getRoundsResponse
     */
    private static List<RoundResponse> getRoundsResponse(ArrayList<Round> rounds) {
        List<RoundResponse> roundRespList = new ArrayList<>();

        for (Round round : rounds) {
            ArrayList<ItemResponse> items = new ArrayList<>();
            for (HashMap<String, String> weaponInfo : round.getWeaponryUsed()) {
                items.add(new ItemResponse(weaponInfo.get("id"), weaponInfo.get("type")));
            }

            roundRespList.add(new RoundResponse(round.getDeltaCharacterHealth(), round.getDeltaEnemyHealth(), items));
        }

        return roundRespList;
    }

    /**
     * GetInventory Response
     */
    public static List<ItemResponse> getInventoryResponse(Inventory inventory) {
        ArrayList<Entity> invList = inventory.getInventory();
        
        List<ItemResponse> invResponse = new ArrayList<ItemResponse>();

        for (Entity entity : invList) {
            invResponse.add(new ItemResponse(entity.getEntityID(), entity.getEntityType()));
        }

        return invResponse;
    }

    /**
     * Checks whether or not player is on a portal and then runs teleport method. 
     */
    public static void portalCheck(List<Entity> listOfEntities, Player player) {
        for (Entity currEntity: listOfEntities) {
            if (currEntity.getEntityType().equals("portal") && currEntity.getCurrentLocation().equals(player.getCurrentLocation())) {
                ((Portal) currEntity).teleport(listOfEntities, player);
            }
        } 
        //listOfEntities.stream().filter(e -> (e.getEntityType() == "portal" && e.getCurrentLocation().equals(player.getCurrentLocation()))).forEach(e -> {((Portal) e).teleport(listOfEntities, player);});
    }

    /** 
    * Checks all floor switches if they have a boulder on them. If they do, it updates the state of the switch to trigger it. It they don't it updates
    * the switch to untrigger.
    */
    public static void boulderCheck(List<Entity> listOfEntities, Statistics statistics) {
        for (Entity curr : listOfEntities) {
            if (!curr.getEntityType().equals("switch")) {
                continue;
            }

            FloorSwitch currSwitch = (FloorSwitch) curr;

            boolean pressed = false;
            for (Entity currBoulder : listOfEntities) {
                if (!currBoulder.getEntityType().equals("boulder")) {
                    continue;
                }

                boolean location = currSwitch.getCurrentLocation().equals(currBoulder.getCurrentLocation());

                if (location && !currSwitch.isTriggered()) {
                    currSwitch.trigger(listOfEntities);
                    statistics.addFloorSwitch();
                    pressed = true;
                } else if (location) {
                    pressed = true;
                }
            }

            if (currSwitch.isTriggered() && !pressed) {
                currSwitch.untrigger(listOfEntities);
                statistics.removeFloorSwitch();
            }
        }
    }


    /*
     * Creates animation queue for all coins rotating.
     */
    public static ArrayList<AnimationQueue> getAnimations(List<Entity> listOfEntities) {
        ArrayList<AnimationQueue> animations = new ArrayList<AnimationQueue>();
        for (Entity entity : listOfEntities) {
            if (entity.getEntityType().equals("treasure")) {
                //animations.add(new AnimationQueue("PostTick", entity.getEntityID(), Arrays.asList("sprite treasure_00", "rotate 0, over 1s", "sprite treasure_03", "rotate 0, over 1s"), true, 10));
            }
        }
        return animations;
    }



    /**
     * helper method to move a boulder
     */
    public static void playerMovesBoulder(Direction movementDirection, Player player, List<Entity> listOfEntities) {
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("boulder") && currEntity.getCurrentLocation().equals(player.getCurrentLocation().translateBy(movementDirection))) {
                ((Boulder) currEntity).move(listOfEntities, movementDirection, player);
            }
        }
    }

    /**
     * Spawns a spider within the specified box (from minX to maxX and from minY to maxY)
     */
    public static Spider spawnASpider(int xSpi, int tickCount, Player player, HashMap<String, Integer> mapOfMinAndMaxValues, List<Entity> listOfEntities, HashMap<String, String> configMap) {
        Spider newSpider = null;
        if (xSpi != 0 && tickCount % xSpi == 0) {
            newSpider = new Spider(mapOfMinAndMaxValues.get("minX"), mapOfMinAndMaxValues.get("maxX"),
                            mapOfMinAndMaxValues.get("minY"), mapOfMinAndMaxValues.get("maxY"), configMap);
            newSpider.spawn(listOfEntities, player);
        }

        return newSpider;
    }

    /**
     * Find and fulfill all burgeoning battles.
     * @param configMap 
     * @param inventory 
     * @param listOfBattles 
     * @param listOfEntities 
     * @param statistics 
     */
    public static void checkBattles(Player play, HashMap<String, String> configMap, Inventory inventory, List<Battle> listOfBattles, List<Entity> listOfEntities, Statistics statistics) {
        List<Entity> monstersHere = Helper.getMonstersHere(play, listOfEntities);
        Player player = play;

        for (Entity monster : monstersHere) {
            Battle battle = new Battle(player, monster);
            boolean alive = battle.doBattle(configMap, inventory);

            listOfBattles.add(battle);

            if (!alive) {
                listOfEntities.remove(player);
                break;
            } else {
                // Monster died.
                statistics.addEnemyDestroyed();
                listOfEntities.remove(monster);
            }
        }
    }

    public static List<Entity> getMonstersHere(Player player, Collection<Entity> listOfEntities) {
        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(player.getCurrentLocation())).collect(Collectors.toList());

        entitiesHere.remove(player);

        List<Entity> monstersHere = entitiesHere.stream().filter(e -> e.isMovingEntity() && !((MovingEntity)e).isAlly()).collect(Collectors.toList());
        return monstersHere;
    }

    // Spawner creates a new zombie
    public static void processZombieSpawner(List<Entity> listOfEntities, HashMap<String, String> configMap) {
        List<Entity> originalList = new ArrayList<>(listOfEntities);

        originalList.stream()
                    .filter(currEntity -> currEntity.getEntityType().equalsIgnoreCase("zombie_toast_spawner"))
                    .forEach((ent) -> ((ZombieToastSpawner)ent).spawnZombie(listOfEntities, configMap));
    }

    /*
     * @returns int distance, indicating the distance between the two x coordinates, or y
     * coordinates, depending on which is larger.
     */
    public static int getDistance(Position a, Position b) {
        int x_diff = Math.abs(a.getX() - b.getX());
        int y_diff = Math.abs(a.getY() - b.getY());
        if (x_diff > y_diff) {
            return x_diff;
        } else {
            return y_diff;
        }
    }

    /**
     * Simply checks for any bombs that may be inactive
     *
     */
    public static void checkBombs(List<Entity> listOfEntities, Player play) {
        List<Entity> bombs = listOfEntities.stream().filter(e -> e.getEntityType().equals("bomb")).collect(Collectors.toList());

        for (Entity b : bombs) {
            Bomb bo = (Bomb) b;
            if (bo.isUsed()) {
                bo.checkBombStatus(listOfEntities, play);
            }
        }
    }

    public static void setZombAndSpiderSpawnFields(Save save, DungeonManiaController dmc) {
        HashMap<String, ArrayList<Integer>> zombs = save.getZombieToast();
        HashMap<String, ArrayList<Integer>> spids = save.getSpider();

        for (Entity e : dmc.getListOfEntities()) {
            if (e.getEntityType().equals("zombie_toast")) {
                ZombieToast zomb = (ZombieToast) e;
                zomb.setSpawnLocation(new Position(zombs.get(e.getEntityID()).get(0), zombs.get(e.getEntityID()).get(1)));
                zomb.setSpawnerLocation(new Position(zombs.get(e.getEntityID()).get(2), zombs.get(e.getEntityID()).get(3)));
            } else if (e.getEntityType().equals("spider")) {
                Spider spid = (Spider) e;
                spid.setSpawnLocation(new Position(spids.get(e.getEntityID()).get(0), spids.get(e.getEntityID()).get(1)));
            }
        }
    }


    /*
     * @params Entity entity1, Entity entity2.
     * @returns true if entity2 is cardinally adjacent to entity1, false otherwise.
     */
    public static boolean isCardinallyAdjacent(Entity entity1, Entity entity2) {
        ArrayList<Position> positions = new ArrayList<>();
        int x = entity1.getCurrentLocation().getX();
        int y = entity1.getCurrentLocation().getY();

        positions.add(new Position(x + 1, y));
        positions.add(new Position(x, y + 1));
        positions.add(new Position(x - 1, y));
        positions.add(new Position(x, y - 1));

        Position targetPos = entity2.getCurrentLocation();

        for (Position position : positions) {
            if (position.equals(targetPos)) {
                return true;
            }
        }

        return false;
    }

    public static void destroySpawner(ZombieToastSpawner spawner, Player player, Inventory inventory, List<Entity> listOfEntities, Statistics statistics) throws InvalidActionException {
        // Check player is cardinally adjacent to spawner.
        if (!Helper.isCardinallyAdjacent(spawner, player)) {
            throw new InvalidActionException("Player isn't cardinally adjacent to spawner.");
        }
        
        // Check player has sword.
        if (!inventory.itemExists("sword")) {
            throw new InvalidActionException("Player cannot destroy spawner by willpower alone.");
        }

        listOfEntities.remove(spawner);
        statistics.addSpawnerDestroyed();
    }

    public static void moveEnemy(HashMap<String, String> configMap, Player player, HashMap<String, Integer> mapOfMinAndMaxValues,
    List<Entity> listOfEntities, Direction movementDirection, Inventory inventory, Statistics statistics, List<Battle> listOfBattles,
    int tickCount) {
        int xSpi = Integer.parseInt(configMap.get("spider_spawn_rate"));
        int xZomb = Integer.parseInt(configMap.get("zombie_spawn_rate"));

        Spider newSpider = Helper.spawnASpider(xSpi, tickCount, player, mapOfMinAndMaxValues, listOfEntities, configMap);
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equalsIgnoreCase("player") || (newSpider != null && currEntity.getEntityID().equalsIgnoreCase(newSpider.getEntityID())))
                continue;

            if (currEntity.isMovingEntity()) {
                ((MovingEntity) currEntity).move(listOfEntities, movementDirection, player, inventory, statistics);
            }
        }

        if (xZomb != 0 && (tickCount % xZomb == 0))
            Helper.processZombieSpawner(listOfEntities, configMap);

        // Process any battles.
        Helper.checkBattles(player, configMap, inventory, listOfBattles, listOfEntities, statistics);

        Helper.checkBombs(listOfEntities, player);
    }

    public static void checkDoors(List<Entity> listOfEntities, Direction movementDirection, Inventory inventory, Player player) {
        List<Entity> keys = inventory.getInventory().stream().filter(e -> e.getEntityType().equals("key")).collect(Collectors.toList());

        List<Entity> Doors = listOfEntities.stream().filter(e -> (e.getEntityType().equals("door") && 
                                                                e.getCurrentLocation().equals(player.getCurrentLocation().translateBy(movementDirection))))
                                                                .collect(Collectors.toList());

        List<Entity> sunStones = inventory.getInventory().stream().filter(e -> e.getEntityType().equals("sun_stone")).collect(Collectors.toList());


        for (Entity d : Doors) {
            // if player has a sunstone always unlock door otherwise check if player has the key
            if (!sunStones.isEmpty()) { 
                ((Door) d).setLocked(false);
            } else {
                for (Entity k : keys) {
                    if (((Door) d).getKey().equals(((Akey) k).getKey())) {
                        ((Door) d).setLocked(false);
                        inventory.removeItem(k);
                    }

                }
            }
        }
    }
}
