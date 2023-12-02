import java.util.ArrayList;

public class entityManager {
    static ArrayList<Entity> entities  = new ArrayList<>() ;
    static void  addEntity (Entity e) {
        entities.add(e);
    }
    public static void  update () {
        for (Entity  e: entities)
        {
           e.update();
        }
    }
    public static void render () {
        for (Entity  e: entities)
        {
            e.update();
        }
    }
    public static void  entityDestroy(Entity e){
        if (e != null){
            e.destroy();
        }

    }
}
