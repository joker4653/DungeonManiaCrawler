package dungeonmania;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import dungeonmania.response.models.DungeonResponse;


public class PersistenceTests {
    
    @Test
    @DisplayName("Saving a game saves it to a file")
    public void saveGame() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_treasureTest_basicSpawn", "c_treasureTest_basicSpawn");
        String saveName = "hi";
        dmc.saveGame(saveName);

        String path = "src/main/java/dungeonmania/saves/" + saveName + ".ser";
    
        File f = new File(path);
        assertTrue(f.exists());
    }

    @Test
    @DisplayName("Load a game after exiting")
    public void LoadGame() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse SavedResponse = dmc.newGame("d_treasureTest_basicSpawn", "c_treasureTest_basicSpawn");
        String saveName = "hello";
        dmc.saveGame(saveName);


        DungeonResponse LoadedResponse = dmc.loadGame(saveName);

        assertEquals(SavedResponse.getEntities(), LoadedResponse.getEntities());

    
    }

    @Test
    @DisplayName("Get all game names that currently exist")
    public void GetGameNames() {
        List<String> l = new ArrayList<String>(Arrays.asList("howdy", "hiya"));

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse SavedResponse = dmc.newGame("d_treasureTest_basicSpawn", "c_treasureTest_basicSpawn");
    
        dmc.saveGame("howdy");
        dmc.saveGame("hiya");

        List<String> GameNames = dmc.allGames();

        for (String name : l) {
            assertTrue(GameNames.contains(name));
        }
    }


    @Test
    @DisplayName("Raise Exception is game dosent exist in LoadGame")
    public void RaiseException() {
        DungeonManiaController dmc = new DungeonManiaController();

        assertThrows(IllegalArgumentException.class, 
                    () -> {
                        dmc.loadGame("I Dont Exist");
                    });
    }
}
