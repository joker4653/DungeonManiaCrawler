package dungeonmania;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.Entities.Entity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

public class ReadJSONFiles implements Serializable {

    // Reading Config File
    public static HashMap<String, String> readConfigFile(String configName) {
        HashMap<String, String> configMap = new HashMap<>();
        
        String configJSONString;
        try {
            configJSONString = FileLoader.loadResourceFile("/configs/" + configName + ".json");
            generateConfigMap(configJSONString, configMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return configMap;
    }

    // Reading Dungeon JSON file
    public static Statistics readDungeonFileAndGetStats(String dungeonName, HashMap<String, String> configMap,
    List<Entity> listOfEntities, List<EntityResponse> listOfEntityResponses) {
        String dungeonJSONString;
        try {
            dungeonJSONString = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
            JsonObject dungeonJsonObj = JsonParser.parseString(dungeonJSONString).getAsJsonObject();
            listOfEntityResponses = createListOfEntsAndResp(dungeonJsonObj, configMap, listOfEntities, listOfEntityResponses);
            return getGoals(dungeonJsonObj, listOfEntities, configMap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Read goals from dungeon file.
    private static Statistics getGoals(JsonObject dungeonJsonObj, List<Entity> listOfEntities, HashMap<String, String> configMap) {
        JsonElement jsonObj = dungeonJsonObj.get("goal-condition");
        JsonObject jsonGoals = jsonObj.getAsJsonObject();
        return new Statistics(jsonGoals, listOfEntities, configMap);
    }

    // Creates the config map
    public static void generateConfigMap(String configJSONString, HashMap<String, String> configMap) {
        JsonObject configJsonObj = JsonParser.parseString(configJSONString).getAsJsonObject();
        Set<String> configKeySet = configJsonObj.keySet();
        configKeySet.forEach((key) -> configMap.put(key, configJsonObj.get(key).toString()));
    }

    private static List<EntityResponse> createListOfEntsAndResp(JsonObject dungeonJsonObj, HashMap<String, String> configMap, List<Entity> listOfEntities, List<EntityResponse> listOfEntityResponses) {
        JsonArray jsonEntities = dungeonJsonObj.get("entities").getAsJsonArray();

        for (JsonElement currElement : jsonEntities) {
            JsonObject jsonObjElement = currElement.getAsJsonObject();
            String type = jsonObjElement.get("type").getAsString();
            int x = jsonObjElement.get("x").getAsInt();
            int y = jsonObjElement.get("y").getAsInt();
            int key = Integer.MAX_VALUE;
            int movementFactor = -1;
            String colour = " ";
            if (jsonObjElement.get("key") != null) key = jsonObjElement.get("key").getAsInt();
            if (jsonObjElement.get("colour") != null) colour = jsonObjElement.get("colour").getAsString();
            if (jsonObjElement.get("movement_factor") != null) movementFactor = jsonObjElement.get("movement_factor").getAsInt();

            Entity entityCreated = EntityFactory.createEntity(type, x, y, key, colour, configMap, movementFactor);
            if (entityCreated != null) {
                listOfEntities.add(entityCreated);
                listOfEntityResponses.add(new EntityResponse(entityCreated.getEntityID(), entityCreated.getEntityType(), entityCreated.getCurrentLocation(), entityCreated.isInteractable()));
            } else
                listOfEntityResponses.add(new EntityResponse(UUID.randomUUID().toString(), type, new Position(x, y), false));
        }

        return listOfEntityResponses;
    }
}
