import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.swing.*;

import Texture.TextureReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.BitSet;

public class eventListener extends AnimListener implements MouseMotionListener, MouseListener {
    int frame = 0;

    static int maxWidth = 150;//to use int class player
    static int maxHeight = 150;

    Score score1 = new Score();
    Score score2 = new Score();

    String[] textureNames = {"flag//flag animation1.png", "flag//flag animation2.png", "flag//flag animation3.png",
            "old//redflagbb.png", "old//Balloon1.png", "flag//flag animation4.png", "flag//flag animation5.png", "old//Back.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    //start abanoub code======================================================================
    String[] textureNamesLevel = {"old//Level1.png", "old//Level2.png", "old//Level3.png", "old//R.png", "old//sound.png",
            "old//mute.png", "old//Exit0.png", "old//pause1.png", "old//information.png", "old//start.png"
            , "old//Untitled.png", "old//singleP.png", "old//twoP.png", "old//menu.png ", "old//Home.png ",
            "old//Resume.png ", "old//levels.png ", "old//Background.png", "old//Back100.png"};


    TextureReader.Texture[] texturelevel = new TextureReader.Texture[textureNamesLevel.length];
    int[] textureslevels = new int[textureNamesLevel.length];
    int MXL = 0, MYL = 0;
    double scaleML = 1;
    double Xchoose = 0, Ychoose = 0;


    flag flag;
    background back;


    static GL gl, gllevel;
    int x = 5, y = 70;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL();
        gl.glClearColor(1.5f, 0.5f, 0.5f, 0.0f);  // the color of the canvas ;
        //init background
        back = new background(0, 0, true, Game.backTextures);
        entityManager.addEntity(back);
        // init players

        for (int i = 0; i < 2; i++) {
            Game.players[i] = new Player(Game.playersX[i], Game.playersY[i], false, Game.player1Textures, 1, i != 0);
        }

        // init players

        for (int i = 0; i < 2; i++) {
            Game.players[i] = new Player(Game.playersX[i], Game.playersY[i], true, Game.player1Textures, 1, i != 0);
            entityManager.addEntity(Game.players[i]);
        }
        for (int k = 0; k < 12; k++) {

            int rx = 10 + (int) Math.random() * Game.maxWidth / (3 + Game.level);// 140
            int ry = 10 + (int) Math.random() * Game.maxHeight / (3 + Game.level);// 94
            System.out.println(rx + " " + ry);
            Game.Ais[k] = new AI(18, 5 + (k / 2) * Game.maxHeight / (2 + Game.level) + Game.maxHeight / ((2 + Game.level) * (3 + Game.level)), true, Game.player1Textures, 20 + rx, ry, k % 2, 1);

            entityManager.addEntity(Game.Ais[k]);

        }
//        int p = Game.random.nextInt(0, 35);
//        // init blue balls
//        for (int k = 0; k < 4 + 2 * Game.level; k++) {
//
//            int rx = 10 + (int) Math.random() * Game.maxWidth / (3 + Game.level);// 140
//            int ry = 10 + (int) Math.random() * Game.maxHeight / (3 + Game.level);// 94
//            System.out.println(rx + " " + ry);
//
//            if (k % 2 == 0) {
//                p = Game.random.nextInt(0, 35);
//            }
//            Game.Ais[k] = new AI(18 + p % 3 * 10, 5 + (k / 2) * Game.maxHeight / (2 + Game.level) + Game.maxHeight / ((2 + Game.level) * (3 + Game.level)), true, Game.player1Textures, 20 + rx, ry, k % 2, p % 4);
//
//
//        }
        AI.reInit();

        gllevel = glAutoDrawable.getGL();
        gllevel.glClearColor(1.5f, 0.5f, 0.5f, 0.0f); // the color of the canvas ;
        gllevel.glMatrixMode(GL.GL_PROJECTION);
        gllevel.glLoadIdentity(); // resets the identity of the matrix ;
        gllevel.glOrtho(0.0, 100.0, 0.0, 100, -1, 1); // is not need to easy
        gllevel.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gllevel.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gllevel.glGenTextures(textureNamesLevel.length, textureslevels, 0);
        for (int i = 0; i < textureNamesLevel.length; i++) {
            try {
                texturelevel[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNamesLevel[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textureslevels[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texturelevel[i].getWidth(), texturelevel[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texturelevel[i].getPixels() // Image_data
                );
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        for (Entity entity : entityManager.entities) {
            for (int i = 0; i < entity.texture.length; i++) {
                try {
                    entity.texture[i] = TextureReader.readTexture(assetsFolderName + "//" + entity.Textures.get(i), true);
                    gl.glBindTexture(GL.GL_TEXTURE_2D, entity.textures[i]);

                    //                mipmapsFromPNG(gl, new GLU(), texture[i]);
                    new GLU().gluBuild2DMipmaps(
                            GL.GL_TEXTURE_2D,
                            GL.GL_RGBA, // Internal Texel Format,
                            entity.texture[i].getWidth(), entity.texture[i].getHeight(),
                            GL.GL_RGBA, // External format from image,
                            GL.GL_UNSIGNED_BYTE,
                            entity.texture[i].getPixels() // Image_data
                    );
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        // end of philo code

    }

    public void mmmm() {


    }


    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        Game.fbs++; // per frame ;
        gllevel.glClear(GL.GL_COLOR_BUFFER_BIT);
        gllevel.glLoadIdentity();
        if (Game.displayChanged == -1) {
            DrawBackgroundlevel(gllevel, 10);
            DrawEPS(gllevel, 0, 0, scaleML, 3);
        } else if (Game.displayChanged == 0) {
            DrawBackgroundlevel(gllevel, textureNamesLevel.length - 2);
            DrawEPS(gllevel, -0.9, 0.3, 1.5, 9);
            DrawEPS(gllevel, -0.9, 0.7, 1.5, 8);
            DrawEPS(gllevel, -0.9, 1.1, 1.3, 6);
        } else if (Game.displayChanged == 1) {
            DrawBackgroundlevel(gllevel, textureNamesLevel.length - 2);
            DrawEPS(gllevel, -0.9, 0.3, 1.5, 11);
            DrawEPS(gllevel, -0.9, 0.7, 1.6, 12);
            DrawEPS(gllevel, -0.9, 1.1, 1.45, 3);
        } else if (Game.displayChanged == 2) {
            DrawBackgroundlevel(gllevel, textureNamesLevel.length - 1);
            Drawlevel1(gllevel, scaleML);
        } else if (Game.displayChanged == 3) {
            handleKeyPress();
            entityManager.update();
            entityManager.render(gllevel);
            gl.glPushMatrix();
            gl.glColor3f(0.9f, 0.0f, 0.0f);
            score1.drawScore(gl, -0.95f, -0.98f, Game.name1 + " RedScore: ");
            gl.glColor3f(0.0f, 0.0f, 0.8f);
            score2.drawScore(gl, 0.55f, -0.98f, Game.name2 + " BlueScore: ");
           gl.glPopMatrix();
            gl.glColor3f(1, 1, 1);
            if (Game.fbs == 24)
                Game.fbs = 0;
            DrawEPS(gllevel, 0, 0, .6, 7);
        } else if (Game.displayChanged == 4) {
            DrawBackgroundlevel(gllevel, textureNamesLevel.length - 1);
            DrawEPS(gllevel, -0.9, 0.3, 1.5, 14);
            DrawEPS(gllevel, -0.9, 0.7, 1.6, 15);
            DrawEPS(gllevel, -0.9, 1.1, 1.7, 16);
            DrawEPS(gllevel, -0.9, 1.5, 1.48, 3);
        }

        if (Game.sound && Game.displayChanged != 3) {
            DrawEPS(gllevel, -1.78, 0.03, .4, 4);
        } else if (Game.displayChanged != 3) {
            DrawEPS(gllevel, -1.78, 0.03, .5, 5);
        }
        System.out.println(Game.name1);
        System.out.println(Game.name2);
    }

    // Start abanoub code=================================================================================================
    public void DrawBackgroundlevel(GL gllevel, int index) {
        gllevel.glEnable(GL.GL_BLEND);
        gllevel.glBindTexture(GL.GL_TEXTURE_2D, textureslevels[index]);    // Turn Blending On
        gllevel.glPushMatrix();
        gllevel.glBegin(GL.GL_QUADS);
        //level Front Face
        gllevel.glTexCoord2f(0.0f, 0.0f);
        gllevel.glVertex3f(-1.0f, -1.0f, -1.0f);
        gllevel.glTexCoord2f(1.0f, 0.0f);
        gllevel.glVertex3f(1.0f, -1.0f, -1.0f);
        gllevel.glTexCoord2f(1.0f, 1.0f);
        gllevel.glVertex3f(1.0f, 1.0f, -1.0f);
        gllevel.glTexCoord2f(0.0f, 1.0f);
        gllevel.glVertex3f(-1.0f, 1.0f, -1.0f);
        gllevel.glEnd();
        gllevel.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void Drawlevel1(GL gllevel, Double scale) {
        for (int i = 0; i < 4; i++) {
            gllevel.glEnable(GL.GL_BLEND);
            gllevel.glBindTexture(GL.GL_TEXTURE_2D, textureslevels[i]);    // Turn Blending On;
            gllevel.glPushMatrix();
            gllevel.glTranslated(x / (maxWidth / 2.0) - 0.05, y / (maxHeight / 2.0) - 0.4 * i - 0.35, 0);
            if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 && Ychoose > 74 && i == 0)
                gllevel.glScaled(0.18 * scale, 0.18 * scale, 1);
            else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (i * 20) && Ychoose > 74 - (i * 20) && i == 1)
                gllevel.glScaled(0.18 * scale, 0.18 * scale, 1);
            else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (i * 20) && Ychoose > 74 - (i * 20) && i == 2)
                gllevel.glScaled(0.18 * scale, 0.18 * scale, 1);
            else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (i * 20) && Ychoose > 74 - (i * 20) && i == 3)
                gllevel.glScaled(0.18 * scale, 0.18 * scale, 1);
            else
                gllevel.glScaled(0.15 * scale, 0.15 * scale, 1);
            gllevel.glBegin(GL.GL_QUADS);
            gllevel.glTexCoord2f(0.0f, 0.0f);
            gllevel.glVertex3f(-1.0f, -1.0f, -1.0f);
            gllevel.glTexCoord2f(1.0f, 0.0f);
            gllevel.glVertex3f(1.0f, -1.0f, -1.0f);
            gllevel.glTexCoord2f(1.0f, 1.0f);
            gllevel.glVertex3f(1.0f, 1.0f, -1.0f);
            gllevel.glTexCoord2f(0.0f, 1.0f);
            gllevel.glVertex3f(-1.0f, 1.0f, -1.0f);
            gllevel.glEnd();
            gllevel.glPopMatrix();
        }
    }

    public void DrawEPS(GL gllevel, double x1, double y1, Double scale, int index) {
        gllevel.glEnable(GL.GL_BLEND);
        gllevel.glBindTexture(GL.GL_TEXTURE_2D, textureslevels[index]);    // Turn Blending On;
        gllevel.glPushMatrix();
        gllevel.glTranslated(x / (maxWidth / 2.0) + (-0.97 - x1), y / (maxHeight / 2.0) - (0.02 + y1), 0);
        gllevel.glScaled(0.15 * scale, 0.1 * scale, 1);
        gllevel.glBegin(GL.GL_QUADS);
        gllevel.glTexCoord2f(0.0f, 0.0f);
        gllevel.glVertex3f(-1.0f, -1.0f, -1.0f);
        gllevel.glTexCoord2f(1.0f, 0.0f);
        gllevel.glVertex3f(1.0f, -1.0f, -1.0f);
        gllevel.glTexCoord2f(1.0f, 1.0f);
        gllevel.glVertex3f(1.0f, 1.0f, -1.0f);
        gllevel.glTexCoord2f(0.0f, 1.0f);
        gllevel.glVertex3f(-1.0f, 1.0f, -1.0f);
        gllevel.glEnd();
        gllevel.glPopMatrix();
    }

    // End abanoub code=================================================================================================
    public void DrawStartWindow(GL gl, int index, int scaleML) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureslevels[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.8, y / (maxHeight / 2.0) - 0.7, 0);
        gl.glScaled(scaleML * 0.18, scaleML * 0.18, 1);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }
    //to handel key  for now not work we need to determine object we need to moved it

    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            Game.players[1].x--;
//            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            Game.players[1].x++;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            Game.players[1].y--;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            Game.players[1].y++;
        }
        if (isKeyPressed(KeyEvent.VK_A)) {
            Game.players[0].x--;
//            }
        }
        if (isKeyPressed(KeyEvent.VK_D)) {
            Game.players[0].x++;
        }
        if (isKeyPressed(KeyEvent.VK_S)) {
            Game.players[0].y--;
        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            Game.players[0].y++;
        }

    }


    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(Xchoose + " " + Ychoose);
        if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 && Ychoose > 74 && Game.displayChanged == 2) {
            Game.displayChanged = 3;
            Game.level = 1;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (1 * 20) && Ychoose > 74 - (1 * 20) && Game.displayChanged == 2) {
            Game.displayChanged = 3;
            Game.level = 2;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (2 * 20) && Ychoose > 74 - (2 * 20) && Game.displayChanged == 2) {
            Game.displayChanged = 3;
            Game.level = 3;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (3 * 20) && Ychoose > 74 - (3 * 20) && Game.displayChanged == 2) { // exit inside the level screen
            Game.displayChanged = 1;
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
        } else if (Xchoose > 0 && Xchoose < 9 && Ychoose < 100 && Ychoose > 90 && Game.displayChanged == 3) { // the exit inside the level
            Game.displayChanged = 4;
            if (Game.sound) {

                Game.Eclick.playMusic();
            }
        } else if (Xchoose > 0 && Xchoose < 9 && Ychoose < 100 && Ychoose > 90 && Game.displayChanged == -1) {
            Game.displayChanged = 0;
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 86 && Ychoose > 74 && Game.displayChanged == 0) {
            Game.displayChanged = 1;
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 67 && Ychoose > 55 && Game.displayChanged == 0) {
            Game.displayChanged = -1;
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 47 && Ychoose > 35 && Game.displayChanged == 0) {
            System.exit(0);
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 86 && Ychoose > 74 && Game.displayChanged == 1) {
            Game.displayChanged = 2;

            Game.name1 = JOptionPane.showInputDialog(null, "Enter the name player: ", "player");
            Game.name2 = "Computer";
            Game.numofplayer = 1;
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 67 && Ychoose > 55 && Game.displayChanged == 1) {
            Game.displayChanged = 2;
            Game.numofplayer = 2;
            Game.name1 = JOptionPane.showInputDialog(null, "Enter the name player1: ", "player1");
            Game.name2 = JOptionPane.showInputDialog(null, "Enter the name player2: ", "player2");
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 47 && Ychoose > 35 && Game.displayChanged == 1) {
            Game.displayChanged = 0;
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
        } else if (Xchoose > 40 && Xchoose < 56 && Ychoose < 86 && Ychoose > 76 && Game.displayChanged == 4) {
            Game.displayChanged = 0;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 40 && Xchoose < 56 && Ychoose < 86 - (1 * 20) && Ychoose > 76 - (1 * 20) && Game.displayChanged == 4) {
            Game.displayChanged = 3;
            Game.numofplayer = Game.displayChanged;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 40 && Xchoose < 56 && Ychoose < 86 - (2 * 20) && Ychoose > 76 - (2 * 20) && Game.displayChanged == 4) {
            Game.displayChanged = 2;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 40 && Xchoose < 56 && Ychoose < 86 - (3 * 20) && Ychoose > 76 - (3 * 20) && Game.displayChanged == 4) { // exit inside the level screen
            Game.displayChanged = 3;
            entityManager.reinitializeEntities();
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        }


        //On-Off Game.sound
        if (Xchoose > 89 && Xchoose < 99 && Ychoose < 99 && Ychoose > 89 && Game.displayChanged != 3) {
            if (Game.sound) {
                Game.sound = false;
                Game.mainMusic.stopMusic();
            } else {
                Game.sound = true;
                Game.mainMusic.playMusic();
            }
        }

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
//        System.out.println(x+"    "+y);
        Component c = e.getComponent();
        double wid = c.getWidth();
        double hi = c.getHeight();

        Xchoose = ((x / wid) * 100);
        Ychoose = 100 - ((y / hi) * 100);
//        System.out.println(Xchoose+"      "+Ychoose);

    }
}