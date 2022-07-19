package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import javassist.bytecode.stackmap.BasicBlock.Catch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DungeonManiaController implements Serializable{
    private int tickCount;
    private List<Entity> listOfEntities = new ArrayList<>();
    private HashMap<String, String> configMap = new HashMap<>();
    private String dungeonId;
    private String dungeonName;
    private HashMap<String, Integer> mapOfMinAndMaxValues = new HashMap<>();
    private List<Battle> listOfBattles = new ArrayList<>();
    private List<String> buildables = new ArrayList<>();
    private Inventory inventory = new Inventory();
    private Statistics statistics;

    public List<Entity> getListOfEntities() {
        return listOfEntities;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }


    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        setTickCount(0);
        try {
            String dungeonJSONString = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
            String configJSONString = FileLoader.loadResourceFile("/configs/" + configName + ".json");
            generateConfigMap(configJSONString);

            /* Reading Dungeon JSON file */
            JsonObject dungeonJsonObj = JsonParser.parseString(dungeonJSONString).getAsJsonObject();
            List<EntityResponse> listOfEntityResponses = createListOfEntsAndResp(dungeonJsonObj);

            JsonElement jsonGoal = dungeonJsonObj.get("goal-condition");
            JsonObject jsonObj = jsonGoal.getAsJsonObject();
            // TODO this will change when we implement complex goals.
            HashMap<String, Boolean> goals = new HashMap<String, Boolean>();
            String goal = ":" + jsonObj.get("goal").getAsString();
            goals.put(goal, false);
            statistics = new Statistics(goals, listOfEntities, configMap);
 
            // TODO replace "buildables" with your actual buildables lists.
            this.dungeonId = UUID.randomUUID().toString();
            this.dungeonName = dungeonName;
            DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, listOfEntityResponses, getInventoryResponse(), getBattleResponse(), buildables, getGoalsResponse());
            mapOfMinAndMaxValues = findMinAndMaxValues();

            return dungeonResp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    private String getGoalsResponse() {
        // TODO this will change when complex goals is implemented.
        HashMap<String, Boolean> goals = statistics.getGoals();
        String incomplete = "";
        if (goals.size() > 0) {
            for (String key : goals.keySet()) {
                if (!goals.get(key)) {
                    incomplete = incomplete + key + " ";
                }
            }
        }

        return incomplete;
    }

    /* Reading Config file */
    private void generateConfigMap(String configJSONString) {
        JsonObject configJsonObj = JsonParser.parseString(configJSONString).getAsJsonObject();
        Set<String> configKeySet = configJsonObj.keySet();
        configKeySet.forEach((key) -> configMap.put(key, configJsonObj.get(key).toString()));
    }

    private List<EntityResponse> createListOfEntsAndResp(JsonObject dungeonJsonObj) {
        JsonArray jsonEntities = dungeonJsonObj.get("entities").getAsJsonArray();
        List<EntityResponse> listOfEntityResponses = new ArrayList<>();

        for (JsonElement currElement : jsonEntities) {
            JsonObject jsonObjElement = currElement.getAsJsonObject();
            String type = jsonObjElement.get("type").getAsString();
            int x = jsonObjElement.get("x").getAsInt();
            int y = jsonObjElement.get("y").getAsInt();
            int key = Integer.MAX_VALUE;
            String colour = " ";
            if (jsonObjElement.get("key") != null) key = jsonObjElement.get("key").getAsInt();
            if (jsonObjElement.get("colour") != null) colour = jsonObjElement.get("colour").getAsString();

            Entity entityCreated = createEntity(type, x, y, key, colour);
            if (entityCreated != null) {
                listOfEntities.add(entityCreated);
                listOfEntityResponses.add(new EntityResponse(entityCreated.getEntityID(), entityCreated.getEntityType(), entityCreated.getCurrentLocation(), entityCreated.isInteractable()));
            } else
                listOfEntityResponses.add(new EntityResponse(UUID.randomUUID().toString(), type, new Position(x, y), false));
        }

        return listOfEntityResponses;
    }

    private List<BattleResponse> getBattleResponse() {
        List<BattleResponse> battleRespList = new ArrayList<>();

        for (Battle currBattle : listOfBattles) {
            battleRespList.add(new BattleResponse(currBattle.getEnemyType(), getRoundsResponse(currBattle.getRounds()), currBattle.getInitPlayerHealth(), currBattle.getInitEnemyHealth()));
        }

        return battleRespList;
    }

    private List<RoundResponse> getRoundsResponse(ArrayList<Round> rounds) {
        List<RoundResponse> roundRespList = new ArrayList<>();

        for (Round round : rounds) {
            ArrayList<ItemResponse> items = new ArrayList<>();
            for (Entity weapon : round.getWeaponryUsed()) {
                items.add(new ItemResponse(weapon.getEntityID(), weapon.getEntityType()));
            }

            roundRespList.add(new RoundResponse(round.getDeltaCharacterHealth(), round.getDeltaEnemyHealth(), items));
        }

        return roundRespList;
    }

    private List<ItemResponse> getInventoryResponse() {
        ArrayList<Entity> invList = inventory.getInventory();
        
        List<ItemResponse> invResponse = new ArrayList<ItemResponse>();

        for (Entity entity : invList) {
            invResponse.add(new ItemResponse(entity.getEntityID(), entity.getEntityType()));
        }

        return invResponse;
    }

    // helper function that creates entities, which will later be stored in the list of entities
    private Entity createEntity(String type, int x, int y, int key, String colour) {
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
        }
        
        return null;
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return createDungeonResponse();
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        
        Optional<Entity> itemInInv = inventory.getInventory().stream().filter(e -> e.getEntityID().startsWith(itemUsedId)).findFirst();
        // exception cases
            if (itemInInv.isEmpty()) {
                throw new InvalidActionException(itemUsedId);
            } else if (!itemInInv.get().getEntityType().equalsIgnoreCase("bomb")) {
                throw new IllegalArgumentException("itemUsed must be one of bomb, invincibility_potion, invisibility_potion");
            }

            Entity item = itemInInv.get();

            // remove item from inventory
            inventory.removeItem(item);

            if (item.getEntityType().equalsIgnoreCase("bomb")) {
                Bomb b = (Bomb) item;
                b.use(getPlayer(), listOfEntities, inventory);
            }

            checkBombs();

        setTickCount(getTickCount() + 1);
        int xSpi = Integer.parseInt(configMap.get("spider_spawn_rate"));
        int xZomb = Integer.parseInt(configMap.get("zombie_spawn_rate"));

        Spider newSpider = spawnASpider(xSpi, getPlayer());
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equalsIgnoreCase("player") || (newSpider != null && currEntity.getEntityID().equalsIgnoreCase(newSpider.getEntityID())))
                continue;

            if (currEntity.isMovingEntity()) {
                ((MovingEntity) currEntity).move(listOfEntities,null, getPlayer(), inventory, statistics);
            }
        }

        if (xZomb != 0 && getTickCount() % xZomb == 0)
            processZombieSpawner();

        // Process any battles.
        checkBattles();

        checkBombs();

        return createDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        setTickCount(getTickCount() + 1);
        int xSpi = Integer.parseInt(configMap.get("spider_spawn_rate"));
        int xZomb = Integer.parseInt(configMap.get("zombie_spawn_rate"));

        // Move player.
        Player player = getPlayer();
        player.setPrevPos(player.getCurrentLocation()); // a bribed mercenary occupies the player's previous position
        playerMovesBoulder(movementDirection, player);
        player.move(listOfEntities, movementDirection, player, inventory, statistics); 
        boulderCheck();
        checkBattles();
        portalCheck(listOfEntities, player);
        Spider newSpider = spawnASpider(xSpi, player);
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equalsIgnoreCase("player") || (newSpider != null && currEntity.getEntityID().equalsIgnoreCase(newSpider.getEntityID())))
                continue;

            if (currEntity.isMovingEntity()) {
                ((MovingEntity) currEntity).move(listOfEntities, movementDirection, getPlayer(), inventory, statistics);
            }
        }

        if (xZomb != 0 && getTickCount() % xZomb == 0)
            processZombieSpawner();

        // Process any battles.
        checkBattles();

        checkBombs();

        return createDungeonResponse();
    }

    // Checks whether or not player is on a portal and then runs teleport method.
    private void portalCheck(List<Entity> listOfEntities, Player player) {
        for (Entity currEntity: listOfEntities) {
            if (currEntity.getEntityType() == "portal" && currEntity.getCurrentLocation().equals(player.getCurrentLocation())) {
                ((Portal) currEntity).teleport(listOfEntities, player);
            }
        }
    }

    // Checks all floor switches if they have a boulder on them. If they do, it updates the state of the switch to trigger it. It they don't it updates
    // the switch to untrigger.
    private void boulderCheck() {
        for (Entity currSwitch : listOfEntities) {
            if (currSwitch.getEntityType() != "switch") {
                continue;
            }

            for (Entity currBoulder : listOfEntities) {
                if (currBoulder.getEntityType() != "boulder") {
                    continue;
                }

                if (currSwitch.getCurrentLocation().equals(currBoulder.getCurrentLocation())) {
                    ((FloorSwitch) currSwitch).trigger(listOfEntities);
                    statistics.addFloorSwitch();
                } else {
                    ((FloorSwitch) currSwitch).untrigger(listOfEntities);
                    statistics.removeFloorSwitch();
                }
            }
        }
    }


    private void playerMovesBoulder(Direction movementDirection, Player player) {
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equals("boulder")) {
                ((Boulder) currEntity).move(listOfEntities, movementDirection, player);
            }
        }
    }

    // Spawns a spider within the specified box (from minX to maxX and from minY to maxY)
    private Spider spawnASpider(int xSpi, Player player) {
        Spider newSpider = null;
        if (xSpi != 0 && getTickCount() % xSpi == 0) {
            newSpider = new Spider(mapOfMinAndMaxValues.get("minX"), mapOfMinAndMaxValues.get("maxX"),
                            mapOfMinAndMaxValues.get("minY"), mapOfMinAndMaxValues.get("maxY"), configMap);
            newSpider.spawn(listOfEntities, player);
        }

        return newSpider;
    }

    // Spawner creates a new zombie
    private void processZombieSpawner() {
        List<Entity> originalList = new ArrayList<>(listOfEntities);

        originalList.stream()
                    .filter(currEntity -> currEntity.getEntityType().equalsIgnoreCase("zombie_toast_spawner"))
                    .forEach((ent) -> ((ZombieToastSpawner)ent).spawnZombie(listOfEntities, configMap));
    }

    /*
     * Find and fulfill all burgeoning battles.
     */
    private void checkBattles() {
        List<Entity> monstersHere = getMonstersHere();
        Player player = getPlayer();

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


    // Helper function that creates a new DungeonResponse because some entities can change positions. This new information needs to
    // be included in the listOfEntities and DungeonResponse.
    private DungeonResponse createDungeonResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        listOfEntities.forEach((currEntity) -> entities.add(new EntityResponse(currEntity.getEntityID(), currEntity.getEntityType(), currEntity.getCurrentLocation(), currEntity.isInteractable())));

        DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, entities, getInventoryResponse(), getBattleResponse(), buildables, getGoalsResponse());
        return dungeonResp;
    }

    private List<Entity> getMonstersHere() {
        Player player = getPlayer();
        List<Entity> entitiesHere = listOfEntities.stream().filter(e -> e.getCurrentLocation().equals(player.getCurrentLocation()) && !e.getEntityType().equals("player")).collect(Collectors.toList());

        List<Entity> monstersHere = entitiesHere.stream().filter(e -> e.isMovingEntity() && !((MovingEntity)e).isAlly()).collect(Collectors.toList());

        return monstersHere;
    }

    private Player getPlayer() {
        for (Entity entity : listOfEntities) {
            if (entity.getEntityType().equalsIgnoreCase("player")) {
                Player player = (Player) entity;
                return player;
            }
        }
        return null;
    }

    private Entity getEntity(String id) {
        for (Entity entity : listOfEntities) {
            if (entity.getEntityID().equals(id)) {
                return entity;
            }
        }
        return null;
    }


    // finds minX, maxX, minY and maxY based on the Dungeon map's coordinates.
    public HashMap<String, Integer> findMinAndMaxValues() {
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
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return createDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        // Get the entity.
        Entity entity = getEntity(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("EntityId does not refer to a valid entity.");
        }

        Player player = getPlayer();

        if (entity.getEntityType() == "mercenary") {
            bribery((Mercenary) entity, player);
        } else if (entity.getEntityType() == "zombie_toast_spawner") {
            destroySpawner((ZombieToastSpawner) entity, player);
        }

        return createDungeonResponse();
    }

    private void destroySpawner(ZombieToastSpawner spawner, Player player) throws InvalidActionException {
        // Check player is cardinally adjacent to spawner.
        if (!isCardinallyAdjacent(spawner, player)) {
            throw new InvalidActionException("Player isn't cardinally adjacent to spawner.");
        }
        
        // Check player has sword.
        if (!inventory.itemExists("sword")) {
            throw new InvalidActionException("Player cannot destroy spawner by willpower alone.");
        }

        listOfEntities.remove(spawner);
        statistics.addSpawnerDestroyed();
    }




    private void bribery(Mercenary merc, Player player) throws InvalidActionException {

        // Check player is within radius of mercenary.
        int radius = Integer.parseInt(configMap.get("bribe_radius"));
        if (getDistance(player.getCurrentLocation(), merc.getCurrentLocation()) > radius) {
            throw new InvalidActionException("Mercenary is too far away to bribe.");
        }

        // Check player has sufficient gold - if so, deduct the right amount of gold from player.
        ArrayList<Entity> inventList = inventory.getInventory();
        List<Entity> treasure = inventList.stream().filter(e -> e.getEntityType().equals("treasure")).collect(Collectors.toList());

        int bribe = Integer.parseInt(configMap.get("bribe_amount"));
        if (treasure.size() < bribe) {
            throw new InvalidActionException("Player lacks the requisite funds to bribe.");
        }

        // Remove gold from inventory.
        for (int i = 0; i < bribe; i++) {
            inventory.removeItem(treasure.get(i));
        } 

        // Make mercenary into ally.
        merc.setAlly(true);
        player.addAlly();
        merc.setInteractable(false); // according to the spec
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        String path = "src/main/java/dungeonmania/saves/" + name + ".ser";
        HashMap<String, ArrayList<Integer>> hm = new HashMap<String, ArrayList<Integer>>();
        HashMap<DungeonManiaController, HashMap<String, ArrayList<Integer>>> toBeSerialized = new HashMap<DungeonManiaController, HashMap<String, ArrayList<Integer>>>();

        // store entity id against a array list where ["Position in X", "Position in Y"]
        for (Entity e : listOfEntities) {
            if (e.getEntityID() == getPlayer().getEntityID()) {
                hm.put("PrevPosPlayer", new ArrayList<Integer>(Arrays.asList(getPlayer().getCurrentLocation().getX(), getPlayer().getCurrentLocation().getY())));
            } else {
                hm.put(e.getEntityID(), new ArrayList<Integer>(Arrays.asList(e.getCurrentLocation().getX(), e.getCurrentLocation().getY())));
            }
        }
        
        toBeSerialized.put(this, hm);


        try {
            FileOutputStream fOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            out.writeObject(toBeSerialized);
            out.close();
            fOut.close();
        } catch (IOException excep) {
            excep.printStackTrace();
        }
        return getDungeonResponseModel();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException();
        }
        DungeonManiaController dmc = null;

        String path = "src/main/java/dungeonmania/saves/" + name + ".ser";

        // try to open file Pointer and read 
        try {
            FileInputStream fIn = new FileInputStream(path);
            ObjectInputStream In = new ObjectInputStream(fIn);
            dmc = (DungeonManiaController) In.readObject();
            In.close();
            fIn.close();
        } catch (IOException excep) {
            excep.printStackTrace();
            return null;
        } catch (ClassNotFoundException excep) {
            excep.printStackTrace();
            return null;
        }

        // return dungeonresponsemodel of the retrieved DMC
        return dmc.getDungeonResponseModel();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        String path = "src/main/java/dungeonmania/saves/";
        List<String> GameNames = new ArrayList<String>();

        for (File f : new File(path).listFiles()) {
            GameNames.add(f.getName().replace(".ser", ""));
        }

        return GameNames;
    }

    /*
     * @params Entity entity1, Entity entity2.
     * @returns true if entity2 is cardinally adjacent to entity1, false otherwise.
     */
    private boolean isCardinallyAdjacent(Entity entity1, Entity entity2) {
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


    /*
     * @returns int distance, indicating the distance between the two x coordinates, or y
     * coordinates, depending on which is larger.
     */
    private int getDistance(Position a, Position b) {
        int x_diff = Math.abs(a.getX() - b.getX());
        int y_diff = Math.abs(a.getY() - b.getY());
        if (x_diff > y_diff) {
            return x_diff;
        } else {
            return y_diff;
        }
    }

    private void checkBombs() {
        List<Entity> bombs = listOfEntities.stream().filter(e -> e.getEntityType().equals("bomb")).collect(Collectors.toList());

        for (Entity b : bombs) {
            Bomb bo = (Bomb) b;
            if (bo.isUsed()) {
                bo.checkBombStatus(listOfEntities, getPlayer());
            }
        }
    }

}
