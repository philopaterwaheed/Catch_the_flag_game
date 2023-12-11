/*
a class to handle collisions between entities
 */
public class collision {
    public static boolean isColliding (Entity A , Entity B){
        hitBox AA = A.hitbox  , BB = B.hitbox ;
        double distance = Math.sqrt(Math.pow((AA.x)-(BB.x),2)+Math.pow((AA.y)-(BB.y),2)); // the distance between the two collidding Areas
        System.out.println(AA.width);
        if (distance > AA.width + BB.width )
            return false ;
        return true;
    }

}
/*
 a hit box added to each entity to enable collision
 */
class hitBox  {
    // a square represents the place of the object on the screen
    int x , y ;
    double width ;

    hitBox ( int x , int y , double width)
    {
        this.x = x   ; this .y = y ; this.width = width;
    }
}