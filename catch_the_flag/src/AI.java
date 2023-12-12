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
        System.out.println(team);
        if (type == 0) {
            ly = 0;
            Zigzag();
        } else if (type == 1)
            Zigzag();
        else if (type == 2) {
            lx = 0;
            Zigzag();
        } else if (type == 3) {
            Zigzag();
        }
        System.out.println( type+ "UWU" );
    }

    @Override
    public void render(GL gl) {
        if (render) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[team]);
            gl.glPushMatrix();
            if (team == 0) {
                if (type != -1)
                    gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
                else
                    gl.glTranslated(xd / (Game.maxWidth / 2.0) - 0.96, yd / (Game.maxHeight / 2.0) - 0.96, 0);
            } else {
                if (type != -1)
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
    }
    @Override
    public void destroy() {

    }

    static public void reInit() {

        int p = Game.random.nextInt(0, 35);
        // init blue balls
        for (int k = 0; k < 4 + 2 * Game.level; k++) {

            int rxx = 10 + (int) Math.random() * Game.maxWidth / (3 + Game.level);// 140
            int ryx = 10 + (int) Math.random() * Game.maxHeight / (3 + Game.level);// 94

            if (k % 2 == 0) {
                p = Game.random.nextInt(0, 35);
            }

//            Game.Ais[k] = new AI(18 + p % 3 * 10, 5 + (k / 2) * Game.maxHeight / (2 + Game.level) + Game.maxHeight / ((2 + Game.level) * (3 + Game.level)), true, Game.player1Textures, 20 + rx, ry, k % 2, p % 4);
            Game.Ais[k].x = 18 + p % 3 * 10;
            Game.Ais[k].y = 5 + (k / 2) * Game.maxHeight / (2 + Game.level) + Game.maxHeight / ((2 + Game.level) * (3 + Game.level));
            Game.Ais[k].render = true;
            Game.Ais[k].lx=20 + rxx;
            Game.Ais[k].ly= ryx;
            Game.Ais[k].team = k%2;
            Game.Ais[k].type=p % 4;

        }
        for (int i =  4 + 2 * Game.level ; i < 12 ; i++ )
        {
            Game.Ais[i].render = false;
        }

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

    double origin = 0;

//    public void ellips() {
//        double t = Math.PI / 180;
//        origin += t;
//        xd = ((lx / 2) * (Math.cos(origin)) + (lx + xd) / 2);
//        yd = ((ly / 2) * (Math.sin(origin)) + (ly + yd) / 2);
////        if (origin == 2 * Math.PI) {
////            origin = 0;
////        }
//
//    }

}
