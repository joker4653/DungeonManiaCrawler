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

import java.io.IOException;
import java.util.ArrayList;
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

public class DungeonManiaController {
    private int tickCount;
    private List<Entity> listOfEntities = new ArrayList<>();
    private List<String> listOfGoals = new ArrayList<>();
    private HashMap<String, String> configMap = new HashMap<>();
    private String dungeonId;
    private String dungeonName;
    private String goals;
    private HashMap<String, Integer> mapOfMinAndMaxValues = new HashMap<>();
    List<Battle> listOfBattles = new ArrayList<>();
    List<String> buildables = new ArrayList<>();
    Inventory inventory = new Inventory();
 
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

            // TODO!!!!! Holly already added the simple goal, BUT NOT the complex goals!!!!!!!!!!!!!!!!!!!!!!!!!!
            JsonElement jsonGoal = dungeonJsonObj.get("goal-condition");
            JsonObject jsonObj = jsonGoal.getAsJsonObject();
            goals = jsonObj.get("goal").getAsString();

            // TODO!!!!! replace "buildables" and "goals" with your ACTUAL buildables/goals lists.
            this.dungeonId = UUID.randomUUID().toString();
            this.dungeonName = dungeonName;
            DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, listOfEntityResponses, getInventoryResponse(), getBattleResponse(), buildables, goals);
            mapOfMinAndMaxValues = findMinAndMaxValues();

            return dungeonResp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
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

            if (jsonObjElement.get("key") != null) key = jsonObjElement.get("key").getAsInt();

            Entity entityCreated = createEntity(type, x, y, key);
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

            return tick(Direction.STILL); 
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
        player.move(listOfEntities, movementDirection, player, inventory); 
        exitCheck(player);
        boulderCheck();
        checkBattles();
        Spider newSpider = spawnASpider(xSpi, player);
        for (Entity currEntity : listOfEntities) {
            if (currEntity.getEntityType().equalsIgnoreCase("player") || (newSpider != null && currEntity.getEntityID().equalsIgnoreCase(newSpider.getEntityID())))
                continue;

            if (currEntity.isMovingEntity()) {
                ((MovingEntity) currEntity).move(listOfEntities, movementDirection, player, inventory); 
            }
        }

        if (xZomb != 0 && getTickCount() % xZomb == 0)
            processZombieSpawner();

        // Process any battles.
        checkBattles();

        checkBombs();

        return createDungeonResponse();
    }

    // Checks all floor switches if they have a boulder on them. If they do, it updates the state of the switch to trigger it. It they don't it updates
    // the switch to untrigger.
    private void boulderCheck() {
        for (Entity currSwitch : listOfEntities) {
            if (currSwitch.getEntityType() == "switch") {
                for (Entity currBoulder : listOfEntities) {
                    if (currBoulder.getEntityType() == "boulder") {
                        if (currSwitch.getCurrentLocation().equals(currBoulder.getCurrentLocation())) {
                            ((FloorSwitch) currSwitch).trigger(listOfEntities);
                        } else {
                            ((FloorSwitch) currSwitch).untrigger(listOfEntities);
                        }
                    }
                }
            }
        }
    }

   // Checks whether or not player is on exit. If they are, it updates the exitState.
    private void exitCheck(Player player) {
        for (Entity currEntity: listOfEntities) {
            if (currEntity.getEntityType() == "exit" && currEntity.getCurrentLocation().equals(player.getCurrentLocation())) {
                ((Exit) currEntity).setExitState(true);
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
                // TODO Player Death?!
                listOfEntities.remove(player);
                break;
            } else {
                // Monster died.
                listOfEntities.remove(monster);
            }
        }
    }


    // Helper function that creates a new DungeonResponse because some entities can change positions. This new information needs to
    // be included in the listOfEntities and DungeonResponse.
    private DungeonResponse createDungeonResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        listOfEntities.forEach((currEntity) -> entities.add(new EntityResponse(currEntity.getEntityID(), currEntity.getEntityType(), currEntity.getCurrentLocation(), currEntity.isInteractable())));

        DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, entities, getInventoryResponse(), getBattleResponse(), buildables, goals);
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
        Player player = getPlayer();
        // Get the entity.
        Entity entity = getEntity(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("EntityId does not refer to a valid entity.");
        }
        Mercenary merc = (Mercenary) entity;

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

        return createDungeonResponse();
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
