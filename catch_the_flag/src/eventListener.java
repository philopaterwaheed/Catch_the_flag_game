import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import Texture.TextureReader;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.BitSet;

public class eventListener extends AnimListener {
    int frame = 0;
    static int maxWidth = 150;//to use int class player
    static int maxHeight = 150;
    String[] textureNames = {"flag//flag animation1.png", "flag//flag animation2.png", "flag//flag animation3.png",
            "old//redflagbb.png", "old//Balloon1.png", "flag//flag animation4.png", "flag//flag animation5.png", "old//Back.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];
    Player players [] = new Player[2] ;
    static GL gl;
    int xPosition = 50, yPosition = 60;
    int x = 5, y = 70;
    int x_Update = 0, y_Update = 0;

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        gl = glAutoDrawable.getGL();
        gl.glClearColor(1.5f, 0.5f, 0.5f, 0.0f); // the color of the canvas ;

        // init players

        for (int i =0 ; i < 2 ;  i ++){
            players[i] = new Player(Game.playersX[i] , Game.playersY[i] , true ,Game.player1Textures ,  1 );
            entityManager.addEntity(players[i]);
        }


        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity(); // resets the identity of the matrix ;
        gl.glOrtho(0.0, 100.0, 0.0, 100.0, -1, 1); // is not need to easy
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
        // philo code don't touch now
//        player1 = new Player(1, 1, true, new String[]{"flag//flag animation1.png", "flag//flag animation2.png", "flag//flag animation3.png", "flag//flag animation4.png", "flag//flag animation5.png"});
//        entityManager.addEntity(player1);
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
        Game.fbs ++ ;
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        DrawBackground(gl);
        handleKeyPress();
        entityManager.update();
        entityManager.render(gl);
        DrawGoal(gl, 1);
        if (Game.fbs == 24)
            Game.fbs = 0 ;
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
        gl.glTranslated((x-8) / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.07 * scale, 0.07 * scale*1000/700, 1);
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
}