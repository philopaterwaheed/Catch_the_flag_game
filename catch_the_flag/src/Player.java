import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.util.ArrayList;

public class Player extends  Entity {
    int frame = 0 ;
    int movedx=0 , movedy=0 ;
    Player(int x, int y, boolean render, String[] textures) {
        super(x, y, render, textures);
    }

    @Override
    public void update() {
        frame++ ;
        if (frame > 4) frame =  0 ;
        System.out.println(movedx);
        movedx++ ;
        movedy++;
    }

    @Override
    public void addTextures(ArrayList<String> textures) {

    }

    @Override
    public void render(GL gl ) {

        eventListener.gl.glEnable(GL.GL_BLEND);
        eventListener.gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[frame]);	// Turn Blending On

        eventListener.gl.glPushMatrix();
        eventListener.gl.glScaled(.5,.5,1);
        eventListener.gl.glTranslated(50,50,1);


        eventListener.gl.glBegin(GL.GL_QUADS);
        // Front Face
        eventListener.gl.glTexCoord2f(0.0f, 0.0f);
        eventListener.gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        eventListener.gl.glTexCoord2f(1.0f, 0.0f);
        eventListener.gl.glVertex3f(1.0f, -1.0f, -1.0f);
        eventListener.gl.glTexCoord2f(1.0f, 1.0f);
        eventListener.gl.glVertex3f(1.0f, 1.0f, -1.0f);
        eventListener.gl.glTexCoord2f(0.0f, 1.0f);
        eventListener.gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        eventListener.gl.glEnd();
        eventListener.gl.glPopMatrix();
        eventListener.gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void destroy() {

    }
}
