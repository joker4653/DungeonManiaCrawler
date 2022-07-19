package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.nio.file.Paths;
import java.io.File;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;


public class PersistenceTests {
    
    @Test
    @DisplayName("Saving a game saves it to a file")
    public void saveGame() {
        DungeonManiaController dmc = new DungeonManiaController();
        String saveName = "hi";
        dmc.saveGame(saveName);

        // path to main folder
        String main = Paths.get("...").toAbsolutePath().normalize().toString();

        String SavePath = main + "/java/dungeonmania/saves";
    
        File f = new File(SavePath + "/" + saveName + ".ser");
        assertTrue(f.exists());
    }

    @Test
    @DisplayName("Load a game after exiting")
    public void LoadGame() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse SavedResponse = dmc.getDungeonResponseModel();
        String saveName = "hello";
        dmc.saveGame(saveName);

        
        DungeonResponse LoadedResponse = dmc.loadGame(saveName);

        assertEquals(SavedResponse.getEntities(), LoadedResponse.getEntities());

    
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
