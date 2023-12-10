import javax.media.opengl.GL;

public class Player extends Entity {
    double scale  ;

    Player(int x, int y, boolean render, String[] texturesStrings , double scale) {
        super(x, y, render, texturesStrings);
        this.scale = scale ;
    }

    @Override
    public void render(GL gl) {

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[0]);
        gl.glPushMatrix();
        gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.9, y / (Game.maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.05 * scale, 0.05*scale*1000/700, 1);
        //System.out.println(x +" " + y;
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
    public void update() {

    }


    @Override
    public void destroy() {

    }

//    @Override
//    public void update() {
//
//    }
//
//    @Override
//    public void render(GL gl) {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
}
