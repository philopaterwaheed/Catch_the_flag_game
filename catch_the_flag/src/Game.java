
import java.util.Random;

public class Game {
    static int playersX[] = {25, 125};
    static int playersY[] = {50, 50};
    public static String[] player1Textures = {"old//Balloon1.png" , "old//Balloon2.png"},
     flagTexture = {"flag//flag animation1.png", "flag//flag animation2.png", "flag//flag animation3.png","flag//flag animation4.png", "flag//flag animation5.png"},
    backTextures = {"old//Back.png"};
    static int level = 0, fbs = 0, maxWidth = 150, maxHeight = 105,
            displayChanged = 0 , rightXBound = 3 , leftXBound = 141  , downYBound = 4 ,
            upYBound = 97,numofplayer=0;;


    static AI[] Ais = new AI[12];
    static  String name1,name2;
    static Player[] players = new Player[2];
    static flag[] flags = new flag[2];
    static Random random = new Random() ;
    static boolean running, won, lose, sound = true;
    static Music Mclick = new Music("sounds//soundEfects//Fruit collect 1.wav", false),
            Eclick = new Music("sounds//soundEfects//Text 1.wav", false),

    mainMusic = new Music("sounds//music//The Verdant Grove LOOP.wav", true);

}