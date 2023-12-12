//<<<<<<< HEAD

//=======
import javax.media.opengl.GL;

public class Player extends Entity {
    public boolean hasFlag = false ;
    double scale  ;
    int score = 0 ;
    boolean team ;
    int ogx, ogy , frame = 0;

    Player(int x, int y, boolean render, String[] texturesStrings , double scale , boolean team ){
        super(x, y, render, texturesStrings);
        this.scale = scale ;
        this.hitbox = new hitBox(x,y,0.04 * scale *150);
        this.team = team ;
        this.ogx = x ;
        this.ogy =y ;
        if (team)
            frame =1 ;
    }

    @Override
    public void render(GL gl) {
//        System.out.println(x+ " " + y);
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, this.textures[frame]);
        gl.glPushMatrix();
        gl.glTranslated(x / (Game.maxWidth / 2.0) - 0.96, y / (Game.maxHeight / 2.0) - 0.96, 0);
        gl.glScaled(0.04 * scale, 0.04*scale*1000/700, 1);
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
    public void update() {
        System.out.println(score);
        if (x < Game.rightXBound)
            x = Game.rightXBound;
        if (x > Game.leftXBound)
            x = Game.leftXBound;
        if (y > Game.upYBound)
            y = Game.upYBound;
        if (y < Game.downYBound)
            y = Game.downYBound;
        this.hitbox.x = x;
        hitbox.y =y ;
        if ( hasFlag)
        {
         if (!team )
            frame = 2 ;
        if (team )
                frame = 3 ;
            if (!team && x < 75) {
                score+=10;
                Game.flags[1].render = true;
                Game.flags[1].collide = true;
                hasFlag = false ;
            }
            else if (team && x >75)
            {

                score+=10;
                Game.flags[0].render = true;
                hasFlag = false ;
                Game.flags[0].collide = true;

            }

        }
        else  {
            if (!team)
                Game.flags[1].render = true;
            else
                Game.flags[0].render = true ;
            if (!team )
                frame = 0 ;
            else
                frame = 1 ;
        }




    }

    @Override
    public void destroy() {

    }
    static public void reInit() {
        for (int i = 0; i < 2; i++) {
//            Game.players[i] = new Player(Game.playersX[i], Game.playersY[i], true, Game.player1Textures, 1, i != 0);
            Game.players[i].x = Game.playersX[i];
            Game.players[i].y = Game.playersY[i];
            Game.players[i].team = i !=0;
            Game.players[i].score = 0;
        }

    }

}

