/*
a class to handle collisions between entities
 */
public class collision {
    public static boolean isColliding (Entity A , Entity B){
        hitBox AA = A.hitbox  , BB = B.hitbox ;
        double distance = Math.pow(Math.pow(( ( AA.x)+AA.width/2 )-( (BB.x)+BB.width/2 ),2)+Math.pow( ( (AA.y) + AA.width/2 ) -((BB.y) +BB.width/2),2) , 1.0/2); // the distance between the two collidding Areas
        System.out.println(distance);
        if (distance >= AA.width-0.5)
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