import java.sql.Statement;

public class Game {
   static int playersX [] = {25 , 125};
   static int playersY [] = {50 , 50};
    public static String[] player1Textures = {"old//Balloon1.png"};
    static int level=0 , fbs = 0 , maxWidth= 150 , maxHeight= 105  , displayChanged=1; ;
    static boolean running , won , lose  ,sound=true;
    static Music Mclick=new Music("sounds//soundEfects//Fruit collect 1.wav",false),
                 Eclick=new Music("sounds//soundEfects//Text 1.wav",false),

        mainMusic = new Music("sounds//music//The Verdant Grove LOOP.wav",true);
}