import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

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
    String[] textureNamesLevel = {"old//Level1.png", "old//Level2.png", "old//Level3.png", "old//R.png", "old//sound.png", "old//mute.png",
            "old//Exit0.png", "old//pause.png", "old//information.png", "old//start.png", "old//Background.png", "old//Back100.png"};


    TextureReader.Texture[] texturelevel = new TextureReader.Texture[textureNamesLevel.length];
    int[] textureslevels = new int[textureNamesLevel.length];
    int MXL = 0, MYL = 0;
    double scaleML = 1;
    double Xchoose = 0, Ychoose = 0;

    //End abanoub code=======================================================================

    Player[] players = new Player[2];
    AI[] BlueBalls = new AI[4];
    static GL gl, gllevel;
    //    int xPosition = 50, yPosition = 60;
    int x = 5, y = 70;
//    int x_Update = 0, y_Update = 0;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL();
        gl.glClearColor(1.5f, 0.5f, 0.5f, 0.0f);  // the color of the canvas ;
        // init players

        for (int i = 0; i < 2; i++) {
            players[i] = new Player(Game.playersX[i], Game.playersY[i], true, Game.player1Textures, 1);
            entityManager.addEntity(players[i]);
        }

        // init blue balls
        for (int i = 0; i < 2 + Game.level; i++) {
            BlueBalls[i] = new AI(5 + i * 50, 2, true, Game.player1Textures, Game.maxWidth / ((3 + Game.level) * 2), Game.maxHeight / ((3 + Game.level) * 2));
            entityManager.addEntity(BlueBalls[i]);
        }


        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity(); // resets the identity of the matrix ;
        gl.glOrtho(0.0, Game.maxWidth, 0.0, Game.maxHeight, -1, 1); // is not need to easy
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Image_data
                );
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        //Start abanoub code=======================================================================================
        gllevel = glAutoDrawable.getGL();
        gllevel.glClearColor(1.5f, 0.5f, 0.5f, 0.0f); // the color of the canvas ;
        gllevel.glMatrixMode(GL.GL_PROJECTION);
        gllevel.glLoadIdentity(); // resets the identity of the matrix ;
        gllevel.glOrtho(0.0, 100.0, 0.0, 100.0, -1, 1); // is not need to easy
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


    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        Game.fbs++;
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        if (Game.displayChanged == 0) {
            DrawBackgroundlevel(gllevel, textureNamesLevel.length - 2);
            DrawEPS(gl, -0.9, 0.3, 1.5, 9);
            DrawEPS(gl, -0.9, 0.7, 1.5, 8);
            DrawEPS(gl, -0.9, 1.1, 1.5, 6);
        }
        if (Game.displayChanged == 2) {
            DrawBackgroundlevel(gllevel, textureNamesLevel.length - 1);
            Drawlevel1(gllevel, scaleML);
        } else if (Game.displayChanged == 3) {
            DrawBackground(gl);
            handleKeyPress();
            entityManager.update();
            score1.drawScore(gl, -0.95f, -0.95f, "RedScore: ");
            score2.drawScore(gl, 0.7f, -0.95f, "BlueScore: ");
            DrawEPS(gllevel, 0, 0, scaleML, 3);
            DrawEPS(gllevel, -1.5, 0, scaleML, 7);
            entityManager.render(gl);
            DrawGoal(gl, 1);
            if (Game.fbs == 24)
                Game.fbs = 0;
        }
        if (Game.sound) {
            DrawEPS(gllevel, -1.78, 0.03, .8, 4);
        } else {
            DrawEPS(gllevel, -1.78, 0.03, .9, 5);
        }

    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textureNames.length - 1]);    // Turn Blending On
        gl.glPushMatrix();
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


    public void DrawGoal(GL gl, double scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);
        gl.glPushMatrix();
        gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
        gl.glScaled(0.07 * scale, 0.07 * scale * 1000 / 700, 1);
        //System.out.println(x +" " + y);
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
            players[0].x--;
//            }
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            players[0].x++;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            players[0].y--;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            players[0].y++;
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
            Game.level = 0;
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (1 * 20) && Ychoose > 74 - (1 * 20) && Game.displayChanged == 2) {
            Game.displayChanged = 3;
            Game.level = 1;
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (2 * 20) && Ychoose > 74 - (2 * 20) && Game.displayChanged == 2) {
            Game.displayChanged = 3;
            Game.level = 2;
            if (Game.sound) {
                Game.Mclick.playMusic();
            }
        } else if (Xchoose > 44 && Xchoose < 58 && Ychoose < 85 - (3 * 20) && Ychoose > 74 - (3 * 20) && Game.displayChanged == 2) {
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
            Game.displayChanged = 0;
        } else if (Xchoose > 0 && Xchoose < 9 && Ychoose < 100 && Ychoose > 90 && Game.displayChanged == 3) {
            Game.displayChanged = 2;
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
        }
        if (Xchoose > 41 && Xchoose < 59 && Ychoose < 86 && Ychoose > 74 && Game.displayChanged == 0) {
            Game.displayChanged = 2;
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 67 && Ychoose > 55 && Game.displayChanged == 0) {
            Game.displayChanged = 0;
            if (Game.sound) {
                Game.Eclick.playMusic();
            }
        } else if (Xchoose > 41 && Xchoose < 59 && Ychoose < 47 && Ychoose > 35 && Game.displayChanged == 0) {
            System.exit(0);
        }

        //On-Off Game.sound
        if (Xchoose > 89 && Xchoose < 99 && Ychoose < 99 && Ychoose > 89) {
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