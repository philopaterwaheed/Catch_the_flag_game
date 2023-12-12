import com.sun.opengl.util.GLUT;

import javax.media.opengl.GL;


public class Score {
    GLUT glut = new GLUT();
    int score = 0;

    public void drawScore(GL gl, float x, float y, String s) {
        gl.glRasterPos2f(x, y);
        String scoreString = s + " " + score;
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, scoreString);

    }

    public void drawEndScore(GL gl, double x, double y) {
        gl.glRasterPos2f(-0.1f, -0.01f);
        String scoreString = "Score: " + score;
        glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, scoreString);

    }

}