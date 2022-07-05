package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private String goals = "";
    //private DungeonResponse dungeonResp;

    // main function to test newGame()
    public static void main(String args[]) {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("advanced", "bomb_radius_2");

        dmc.tick(Direction.UP);
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

            JsonObject dungeonJsonObj = JsonParser.parseString(dungeonJSONString).getAsJsonObject();

            JsonArray jsonEntities = dungeonJsonObj.get("entities").getAsJsonArray();
            List<EntityResponse> listOfEntityResponses = new ArrayList<>(); 
            for (JsonElement currElement : jsonEntities) {
                JsonObject jsonObjElement = currElement.getAsJsonObject();
                String type = jsonObjElement.get("type").getAsString();
                int x = jsonObjElement.get("x").getAsInt();
                int y = jsonObjElement.get("y").getAsInt();

                Entity entityCreated = createEntity(type, x, y);
                if (entityCreated != null) {
                    listOfEntities.add(entityCreated);
                }
                
                if (type.equalsIgnoreCase("mercenary") || type.equalsIgnoreCase("zombie_toast_spawner")) {
                    listOfEntityResponses.add(new EntityResponse(UUID.randomUUID().toString(), type, new Position(x, y), true));
                } else {
                    listOfEntityResponses.add(new EntityResponse(UUID.randomUUID().toString(), type, new Position(x, y), false));
                }
            }

            // TODO!!!!! Add goals to listOfGoals or however you want to store them
            JsonElement jsonGoal = dungeonJsonObj.get("goal-condition");

            JsonObject configJsonObj = JsonParser.parseString(configJSONString).getAsJsonObject();
            Set<String> configKeySet = configJsonObj.keySet();

            for (String key : configKeySet) {
                configMap.put(key, configJsonObj.get(key).toString());
            }

            // TODO!!!!! replace the "null" inventory, battles and buildables with your lists.
            this.dungeonId = UUID.randomUUID().toString();
            this.dungeonName = dungeonName;
            DungeonResponse dungeonResp = new DungeonResponse(UUID.randomUUID().toString(), dungeonName, listOfEntityResponses, null, null, null, "");
            
            return dungeonResp;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    // helper function that creates entities, which will later be stored in the list of entities
    private Entity createEntity(String type, int x, int y) {
        if (type.equalsIgnoreCase("Spider")) {
            return new Spider(x, y);
            // when spiders are already present on the map, do they automatically move up from their spawn location?????????????????????????????????????????
        } else if (type.equalsIgnoreCase("Boulder")) {
            return new Boulder(x, y);
        }

        // add other entities here

        return null;
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        setTickCount(getTickCount() + 1);

        int x = Integer.parseInt(configMap.get("spider_spawn_rate"));
        HashMap<String, Integer> mapOfMinAndMaxValues = findMinAndMaxValues();

        if (getTickCount() % x == 0) {
            Spider newSpider = new Spider(mapOfMinAndMaxValues.get("minX"), mapOfMinAndMaxValues.get("maxX"), mapOfMinAndMaxValues.get("minY"), mapOfMinAndMaxValues.get("maxY"));
            newSpider.spawn(listOfEntities);
        } else {
            // all moving entities must move
            for (Entity currEntity : listOfEntities) {
                ((MovingEntity)currEntity).move(listOfEntities);
            }
        }

        // update listOfEntities and then dungeonResp
        return createDungeonResponse();
    }

    // helper function that creates a new DungeonResponse because some entities can change positions. This new information needs to
    // be included in the listOfEntities and DungeonResponse.
    private DungeonResponse createDungeonResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        for (Entity currEntity : listOfEntities) {
            entities.add(new EntityResponse(currEntity.getEntityID(), currEntity.getEntityType(), currEntity.getCurrentLocation(), currEntity.isInteractable()));
        }

        DungeonResponse dungeonResp = new DungeonResponse(dungeonId, dungeonName, entities, null, null, null, goals);
        return dungeonResp;
    }

    // finds minX, maxX, minY and maxY based on the Dungeon map's coordinates.
    public HashMap<String, Integer> findMinAndMaxValues() {
        HashMap<String, Integer> mapOfMinAndMaxValues = new HashMap<>();
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;

        for (Entity currEntity : listOfEntities) {
            int currPositionX = currEntity.getCurrentLocation().getX();
            int currPositionY = currEntity.getCurrentLocation().getY();

            if (currPositionX < minX)
                minX = currPositionX;

            if (currPositionX > maxX)
                maxX = currPositionX;
            
            if (currPositionY < minY)
                minY = currPositionY;

            if (currPositionY > maxY)
                maxY = currPositionY;
        }

        mapOfMinAndMaxValues.put("minX", minX);
        mapOfMinAndMaxValues.put("maxX", maxX);
        mapOfMinAndMaxValues.put("minY", minY);
        mapOfMinAndMaxValues.put("maxY", maxY);

        return mapOfMinAndMaxValues;
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
}
