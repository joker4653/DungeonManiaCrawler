package dungeonmania;

import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    
    public Boulder(int x, int y) {
        super.setCanSpiderBeOnThisEntity(false);
        super.setCurrentLocation(new Position(x, y));
    }
}
