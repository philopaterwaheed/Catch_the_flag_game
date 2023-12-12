import javax.media.opengl.GL;

public class AI extends Entity {
    int fps = 0;
    int team;
    int xog = 2;
    int yog = 3;
    int type;
    int lx, ly;
    double xd, yd;

    AI(int x, int y, boolean render, String[] texturesStrings, int lx, int ly, int t, int type) {
        super(x, y, render, texturesStrings);
        this.xog = x;
        this.lx = lx;
        this.yog = y;
        this.ly = ly;
        this.type = type;
        this.team = t;
        this.xd = x;
        this.yd = y;
    }

    @Override
    public void update() {

        if (type == 0) {
            ly = 0;
            Zigzag();
        } else if (type == 1) {
            ellips();
        } else if (type == 2) {
            lx = 0;
            Zigzag();
        } else if (type == 3) {
            Zigzag();
        }
    }

    @Override
    public void render(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[team]);
        gl.glPushMatrix();
        if (team == 0) {
            if (type != 1)
                gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
            else
                gl.glTranslated(xd / (Game.maxWidth / 2.0) - 0.96, yd / (Game.maxHeight / 2.0) - 0.96, 0);
        } else {
            if (type != 1)
                gl.glTranslated((Game.maxWidth - x - 7) / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
            else
                gl.glTranslated((Game.maxWidth - xd - 7) / (Game.maxWidth / 2.0) - 0.96, yd / (Game.maxHeight / 2.0) - 0.96, 0);
        }
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
        if (lx <= 1) d = 0;
        else if (h % lx == 0 || (x % 140 == 0)) {
            d = -d;
            h = 0;
//            x-=d;
        }
        if (ly <= 1) m = 0;
        else if (v % ly == 0 || (y % 94 == 0)) {
            m = -m;
            v = 0;
//            y-=d;
        }
        x += d;
        y += m;
    }

    double origin = 45;

    public void ellips() {
        double t = Math.PI / 180;
        origin += 2*t;
        xd = (((lx - 3) / 2) * (Math.cos(origin)) + (lx + x) / 2);
        yd = (((ly - 3) / 2) * (Math.sin(origin)) + (ly + y) / 2);
        if (origin == 2 * Math.PI) {
            origin = 0;
        }

    }

}
