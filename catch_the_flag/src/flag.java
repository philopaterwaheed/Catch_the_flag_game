import javax.media.opengl.GL;

public class flag extends Entity {
    double scale ;
    int frame = 0;
    double hight;
    boolean team , collide = true;

    flag(int x, int y, boolean render, String[] texturesStrings , double scale  , boolean team ) {

        super(x, y, render, texturesStrings);
        this.scale = scale;
        this.team = team  ;
        this.hitbox.x = x ;
        this.hitbox.y =y ;
        this.hitbox.width = 0 ;
        hight = 0.04 * scale *150 ;
        render =true;
    }

    @Override
    public void update() {

        frame ++ ;
        if (frame >4 )
            frame = 0 ;

    }

    @Override
    public void render(GL gl) {
//        System.out.println(render);
        if (render) {
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[frame]);
            gl.glPushMatrix();
            gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
            gl.glScaled(0.04 * scale * ((team) ? -1 : 1), 0.04 * scale * 1000 / 700, 1);
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
}
