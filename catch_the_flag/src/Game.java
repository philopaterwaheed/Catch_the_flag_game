import java.sql.Statement;
import java.util.ArrayList;

public class Game {
    ArrayList <Entity> Colliders = new ArrayList<>();
   static int playersX [] = {25 , 125};
   static int playersY [] = {50 , 50};
    public static String[] player1Textures = {"old//Balloon1.png"},
            redflagTextures = {"flag//flag animation1.png", "flag//flag animation2.png", "flag//flag animation3.png", "flag//flag animation4.png", "flag//flag animation5.png"};
    static int level=0 , fbs = 0 , maxWidth= 150 , maxHeight= 105   ;
    static boolean running , won , lose  ;
    static Music Mclick=new Music("sounds//soundEfects//Fruit collect 1.wav",false),
                 Eclick=new Music("sounds//soundEfects//Text 1.wav",false),
            mainMusic = new Music("sounds//music//The Verdant Grove LOOP.wav",true);
}