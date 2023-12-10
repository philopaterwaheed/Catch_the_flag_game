/*
a class to handle collisions between entities
 */
public class collision {
    public static boolean isColliding (Entity A , Entity B){
        return true;
    }
}
/*
 a hit box added to each entity to enable collision
 */
class hitBox  {
    // a square represents the place of the object on the screen
    int x , y , width ;

    hitBox ( int x , int y , int width)
    {
        this.x = x   ; this .y = y ; this.width = width;
    }
}