package dungeonmania;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Save implements Serializable {
    DungeonManiaController dmc;
    HashMap<String, ArrayList<Integer>> entityPositions = new HashMap<String, ArrayList<Integer>>();
    ArrayList<Integer> PrevPlayerPos;
    HashMap<String, ArrayList<Integer>> Zombs = new HashMap<String, ArrayList<Integer>>();
    HashMap<String, ArrayList<Integer>> Spids = new HashMap<String, ArrayList<Integer>>();

    public Save(DungeonManiaController dmc) {
        this.dmc = dmc;
        CreateEntityPositions();
    }


    public DungeonManiaController getDmc() {
        return dmc;
    }

    public HashMap<String, ArrayList<Integer>> getEntityPositions() {
        return entityPositions;
    }

    public ArrayList<Integer> getPrevPlayerPos() {
        return PrevPlayerPos;
    }

    public HashMap<String, ArrayList<Integer>> getZombieToast() {
        return Zombs;
    }

    public HashMap<String, ArrayList<Integer>> getSpider() {
        return Spids;
    }

    private void CreateEntityPositions() {
        entityPositions.put("PrevPlayerPos", new ArrayList<Integer>(Arrays.asList(dmc.getPlayer().getPrevPos().getX(), dmc.getPlayer().getPrevPos().getY())));
        for (Entity e : dmc.getListOfEntities()) {
            entityPositions.put(e.getEntityID(), new ArrayList<Integer>(Arrays.asList(e.getCurrentLocation().getX(), e.getCurrentLocation().getY())));
        }

        CreateZombieAndSpiderPositions();
    }

    public void CreateZombieAndSpiderPositions() {
        for (Entity e : dmc.getListOfEntities()) {
            if (e.getEntityType().equals("zombie_toast")) {
                ZombieToast zomb = (ZombieToast) e;
                Zombs.put(zomb.getEntityID(), new ArrayList<Integer>(Arrays.asList(
                                                                            zomb.getSpawnLocation().getX(), 
                                                                            zomb.getSpawnLocation().getY(),
                                                                            zomb.getSpawnerLocation().getX(),
                                                                            zomb.getSpawnerLocation().getY()
                                                                            )));
            } else if (e.getEntityType().equals("spider")) {
                Spider spider = (Spider) e;
                Spids.put(spider.getEntityID(), new ArrayList<Integer>(Arrays.asList(
                                                                            spider.getSpawnLocation().getX(),
                                                                            spider.getSpawnLocation().getY()
                                                                            )));
            }
        }
    }



}
