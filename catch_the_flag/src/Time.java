import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;

public class Time {
    GLUT glut = new GLUT();
    int time = 0;

    public void drawTime(GL gl) {
        gl.glRasterPos2f(0f, 0.95f);
        String timer =  time % 60  + ":" + time %(60*60) / 60 ;
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, timer);
    }
}