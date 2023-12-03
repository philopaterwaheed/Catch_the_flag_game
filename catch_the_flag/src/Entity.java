import java.util.ArrayList;
import java.util.Arrays;

public abstract class Entity {
    int x = -1, y = -1; // place on the field ;
    int width = 0; // width of the entity on the screen
    boolean render = false, destroyed = false;

    hitBox hitbox = null;
    ArrayList<String> Textures = new ArrayList<>();

    Entity(int x, int y, boolean render, String[] textures) {
        this.x = x;
        this.y = y;
        this.hitbox = new hitBox(x, y, this.width);
        if (render) {
            Textures.addAll(Arrays.asList(textures));
            addTextures(Textures);
        }
    }

    abstract public void update();

    abstract public void addTextures(ArrayList<String> textures); // to add the object's textures ;

    abstract public void render(); // a function to render entity on the screen

    abstract public void destroy(); // to destroy an entity at the end of its life span ;
}