package dungeonmania;

import dungeonmania.Battling.Battle;
import dungeonmania.Entities.Entity;
import dungeonmania.Entities.Inventory;
import dungeonmania.Entities.Collectables.Bomb;
import dungeonmania.Entities.Moving.Assassin;
import dungeonmania.Entities.Moving.Mercenary;
import dungeonmania.Entities.Moving.MovingEntity;
import dungeonmania.Entities.Moving.Player;
import dungeonmania.Entities.Moving.Spider;
import dungeonmania.Entities.Static.ZombieToastSpawner;
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
import java.lang.reflect.Array;
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

    public HashMap<String, String> getConfigMap() {
        return configMap;
    }

    public String getDungeonId() {
        return dungeonId;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public HashMap<String, Integer> getMapOfMinAndMaxValues() {
        return mapOfMinAndMaxValues;
    }

    public List<Battle> getListOfBattles() {
        return listOfBattles;
    }

    public List<String> getBuildables() {
        return buildables;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Statistics getStatistics() {
        return statistics;
    }

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
        reintialisefields();
        try {
            String dungeonJSONString = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
            String configJSONString = FileLoader.loadResourceFile("/configs/" + configName + ".json");
            Helper.generateConfigMap(configJSONString, configMap);

            /* Reading Dungeon JSON file */
            JsonObject dungeonJsonObj = JsonParser.parseString(dungeonJSONString).getAsJsonObject();
            List<EntityResponse> listOfEntityResponses = Helper.createListOfEntsAndResp(dungeonJsonObj, configMap, listOfEntities);

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
            DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, listOfEntityResponses, Helper.getInventoryResponse(inventory), Helper.getBattleResponse(listOfBattles), buildables, getGoalsResponse());
            mapOfMinAndMaxValues = Helper.findMinAndMaxValues(listOfEntities);

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
        } else if (!itemInInv.get().isConsumable()) {
            throw new IllegalArgumentException("itemUsed must be one of bomb, invincibility_potion, invisibility_potion");
        }

        Entity item = itemInInv.get();

        // remove item from inventory
        inventory.removeItem(item);

        if (item.getEntityType().equalsIgnoreCase("bomb")) {
            Bomb b = (Bomb) item;
            b.use(getPlayer(), listOfEntities, inventory);
        }

        Helper.checkBombs(listOfEntities, getPlayer());
        setTickCount(getTickCount() + 1);
        Helper.moveEnemy(configMap, getPlayer(), mapOfMinAndMaxValues, listOfEntities, null,
        inventory, statistics, listOfBattles, tickCount);

        return createDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        setTickCount(getTickCount() + 1);

        // Move player.
        Player player = getPlayer();
        player.setPrevPos(player.getCurrentLocation()); // a bribed mercenary occupies the player's previous position
        Helper.playerMovesBoulder(movementDirection, player, listOfEntities);
        player.move(listOfEntities, movementDirection, player, inventory, statistics); 
        Helper.boulderCheck(listOfEntities, statistics);
        Helper.checkBattles(player, configMap, inventory, listOfBattles, listOfEntities, statistics);
        Helper.portalCheck(listOfEntities, player);

        Helper.moveEnemy(configMap, player, mapOfMinAndMaxValues, listOfEntities, movementDirection, inventory, statistics, 
        listOfBattles, tickCount);

        return createDungeonResponse();
    }


    // Helper function that creates a new DungeonResponse because some entities can change positions. This new information needs to
    // be included in the listOfEntities and DungeonResponse.
    private DungeonResponse createDungeonResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        listOfEntities.forEach((currEntity) -> entities.add(new EntityResponse(currEntity.getEntityID(), currEntity.getEntityType(), currEntity.getCurrentLocation(), currEntity.isInteractable())));

        DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, entities, Helper.getInventoryResponse(inventory), Helper.getBattleResponse(listOfBattles), buildables, getGoalsResponse());
        return dungeonResp;
    }


    public Player getPlayer() {
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

        if (entity.getEntityType().equalsIgnoreCase("mercenary") || entity.getEntityType().equalsIgnoreCase("assassin")) {
            ((Mercenary) entity).bribery((Mercenary) entity, player, inventory);
        } else if (entity.getEntityType().equalsIgnoreCase("zombie_toast_spawner")) {
            Helper.destroySpawner((ZombieToastSpawner) entity, player, inventory, listOfEntities, statistics);
        }

        return createDungeonResponse();
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        String path = "src/main/java/dungeonmania/saves/" + name + ".ser";
        Save save = new Save(this);

        try {
            FileOutputStream fOut = new FileOutputStream(path, false);
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            out.writeObject(save);
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
        Save UnSerailizedData;

        String path = "src/main/java/dungeonmania/saves/" + name + ".ser";

        // try to open file Pointer and read 
        try {
            FileInputStream fIn = new FileInputStream(path);
            ObjectInputStream In = new ObjectInputStream(fIn);
            UnSerailizedData = (Save) In.readObject();
            In.close();
            fIn.close();
        } catch (IOException excep) {
            excep.printStackTrace();
            return null;
        } catch (ClassNotFoundException excep) {
            excep.printStackTrace();
            return null;
        }

        DungeonManiaController LoadedDMC = UnSerailizedData.getDmc();
        HashMap<String, ArrayList<Integer>> positions = UnSerailizedData.getEntityPositions();
        List<Entity> Entities = LoadedDMC.getListOfEntities();

        Helper.setZombAndSpiderSpawnFields(UnSerailizedData, LoadedDMC);

        LoadedDMC.getPlayer().setPrevPos(new Position(positions.get("PrevPlayerPos").get(0), positions.get("PrevPlayerPos").get(1)));
        for (Entity e : Entities) {
            ArrayList<Integer> XandY = positions.get(e.getEntityID());
            e.setCurrentLocation(new Position(XandY.get(0), XandY.get(1)));
        }

        // overwrites current existing DMC with the loaded one from Deserialised Object
        reintialisefields(LoadedDMC);

        // return dungeonresponsemodel of the retrieved DMC
        return getDungeonResponseModel();
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


    private void reintialisefields() {
        tickCount = 0;
        listOfEntities = new ArrayList<>();
        configMap = new HashMap<>();
        dungeonId = null;
        dungeonName = null;
        mapOfMinAndMaxValues = new HashMap<>();
        listOfBattles = new ArrayList<>();
        buildables = new ArrayList<>();
        inventory = new Inventory();
        statistics = null;
    }

    private void reintialisefields(DungeonManiaController LoadedDMC) {
        tickCount = LoadedDMC.getTickCount();
        listOfEntities = LoadedDMC.getListOfEntities();
        configMap = LoadedDMC.getConfigMap();
        dungeonId = LoadedDMC.getDungeonId();
        dungeonName = LoadedDMC.getDungeonName();
        mapOfMinAndMaxValues = LoadedDMC.getMapOfMinAndMaxValues();
        listOfBattles = LoadedDMC.getListOfBattles();
        buildables = LoadedDMC.getBuildables();
        inventory = LoadedDMC.getInventory();
        statistics = LoadedDMC.getStatistics();
    }

}
