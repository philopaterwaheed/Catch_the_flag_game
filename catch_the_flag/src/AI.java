import javax.media.opengl.GL;

public class AI extends Entity {
    int fps = 0;
    boolean team;
    String type;
    int lx, ly;

    AI(int x, int y, boolean render, String[] texturesStrings, int lx, int ly) {
        super(x, y, render, texturesStrings);
        this.lx = lx;
        this.ly = ly;

    }

    @Override
    public void update() {
        fps++;
        Zigzag();
    }

    @Override
    public void render(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[0]);
        gl.glPushMatrix();
        gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
        gl.glScaled(0.04, 0.04 * 10 / 7, 1);
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
    public void destroy() {

    }

    int h = 0;
    int v = 0;
    int m = 1;
    int d = 1;

    public void Zigzag() {
        h++;
        v++;
        if (h % lx == 0) {
            d = -d;
            h = 0;
        }
        if (v % ly == 0) {
            m = -m;
            v = 0;
        }
        x += d;
        y += m;
    }
}
