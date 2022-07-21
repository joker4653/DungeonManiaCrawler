package dungeonmania;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    // Put your helper functions in here

    // finds minX, maxX, minY and maxY based on the Dungeon map's coordinates.
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
}
