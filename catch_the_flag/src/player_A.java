import javax.media.opengl.GL;
import java.util.ArrayList;

public class player_A extends eventListener {
    double x, y;
    player_A(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void DrawPlayer(GL gl, int[] textures, double scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]);
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
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
