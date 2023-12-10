package cs304;

import Textures.AnimListener;
import Textures.TextureReader;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import javax.media.opengl.*;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import java.awt.Font;

public class AnimGLEventListener extends AnimListener implements MouseListener {

	int w = 900; // maxWidth
	int h = 500; // maxHeight
	int r = 30;

	Set<Integer> pressed = new HashSet<Integer>();

	private GLCanvas glc;
	static Anim anim;

	TextRenderer t_blue = new TextRenderer(new Font("Comic Sans MS", Font.BOLD, 14));
	TextRenderer t_red = new TextRenderer(new Font("Comic Sans MS", Font.BOLD, 14));
	Color color = new Color(0, 0, 0);

	int blueFlagX = w - 20, blueFlagY = h / 2, redFlagX = 20, redFlagY = h / 2;
	int red_score = 0, blue_score = 0;
	boolean isRedFlag = true, isBlueFlag = true;
	int m = 1;

	boolean pauseGame = false, finishGame = false;

	boolean soundOn = true;
	Clip clip;

	boolean f_r4 = false, f_r5 = false;
	boolean f_b4 = false, f_b5 = false;

	String textureNames[] = { "redbulll.png", "blueball.png", "redflagbb.png", "blueflagbb.png", "redballflag.png",
			"wall.png", "paused.jpg", "sound.png", "mute.png", "redVSblue.png", "Back.png" };
	TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
	int textures[] = new int[textureNames.length];
	Rectangle bound[] = { new Rectangle(w, 0, w + 100, h + 100), new Rectangle(0, h, w, h + 100),
			new Rectangle(-100, 0, 0, h), new Rectangle(0, -100, w, 0), new Rectangle(100, 100, 200, 200),
			new Rectangle(100, 350, 200, 450), new Rectangle(700, 100, 800, 200), new Rectangle(700, 350, 800, 450) };

	Ball blue[] = { new Ball(0, 0), new Ball(0, 0), new Ball(515, 370), new Ball(515, 120), new Ball(570, 480),
			new Ball(600, 15) };
	Ball red[] = { new Ball(0, 0), new Ball(0, 0), new Ball(380, 370), new Ball(380, 120), new Ball(330, 480),
			new Ball(300, 15) };

	public void init(GLAutoDrawable gld) {
		red[0].setOrbital(bound[4], 0);
		red[1].setOrbital(bound[5], 2);

		blue[0].setOrbital(bound[6], 0);
		blue[1].setOrbital(bound[7], 2);

		GL gl = gld.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // This Will Clear The Background Color To Black

		gl.glEnable(GL.GL_TEXTURE_2D); // Enable Texture Mapping
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glGenTextures(textureNames.length, textures, 0);

		for (int i = 0; i < textureNames.length; i++) {
			try {
				texture[i] = TextureReader.readTexture( textureNames[i], true);
				gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

				new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA, // Internal Texel Format,
						texture[i].getWidth(), texture[i].getHeight(), GL.GL_RGBA, // External format from image,
						GL.GL_UNSIGNED_BYTE, texture[i].getPixels() // Imagedata
				);
			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		}

		// music
		music("Assets//catchtheflag.wav");

		gl.glLoadIdentity();
		gl.glOrtho(0, w, 0, h, -1, 1);
	}

	public void display(GLAutoDrawable gld) {

		GL gl = gld.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		if (pauseGame) {
			Draw(gl, 450, 250, textures.length - 5, 900, 500);
			clip.stop();
		}

		if (!finishGame && !pauseGame) {
			// background
			Draw(gl, 450, 250, textures.length - 1, 900, 500);
			// scores
			redScore(this.red_score);
			blueScore(this.blue_score);
			// redVSblue
			Draw(gl, 440, 470, textures.length - 2, 200, 50);
			// sound
			if (soundOn) {
				Draw(gl, 40, 30, textures.length - 4, 50, 20);
				clip.start();
			} else {
				Draw(gl, 40, 30, textures.length - 3, 50, 20);
				clip.stop();
			}

			// 2_redBalls, 2_blueBalls
			Draw(gl, red[4].x, red[4].y, 0, r, r);
			Draw(gl, red[5].x, red[5].y, 0, r, r);
			Draw(gl, blue[4].x, blue[4].y, 1, r, r);
			Draw(gl, blue[5].x, blue[5].y, 1, r, r);
			// vertical direction reverse
			vdr();

			for (int i = 0; i < red.length - 2; i++)
				Draw(gl, red[i].x, red[i].y, 0, r, r);

			for (int i = 0; i < blue.length - 2; i++)
				Draw(gl, blue[i].x, blue[i].y, 1, r, r);

			for (int i = 4; i < bound.length; i++)
				Draw(gl, (bound[i].topX + bound[i].bottomX) / 2, (bound[i].topY + bound[i].bottomY) / 2, 5, 100,
						100);

			if (isRedFlag)
				Draw(gl, redFlagX, redFlagY, 2, 50, 50);
			else
				Draw(gl, blue[3].x, blue[3].y, 2, 30, 30);

			if (isBlueFlag)
				Draw(gl, blueFlagX, blueFlagY, 3, 50, 50);
			else
				Draw(gl, red[3].x, red[3].y, 3, 30, 30);

			textures_handle();

			red[0].moveBall(bound, 5);
			red[1].moveBall(bound, 5);

			blue[0].moveBall(bound, 5);
			blue[1].moveBall(bound, 5);

			red[2].moveTo(blue[3], bound);
			blue[2].moveTo(red[3], bound);

			// if Ball intersects with enemy Ball, flag's place restore
			for (int i = 0; i < blue.length; i++)
				if (Ball.isIntersectBall(red[3], blue[i]) && !isBlueFlag)
					isBlueFlag = true;

			for (int i = 0; i < red.length; i++)
				if (Ball.isIntersectBall(blue[3], red[i]) && !isRedFlag)
					isRedFlag = true;

		}

		if (blue_score == 2) {
			finishGame = true;
			while (m-- > 0)
				new teamBlueWon();
			blue_score = 0;
			anim.dispose();
			clip.stop();
		}
		if (red_score == 2) {
			finishGame = true;
			while (m-- > 0)
				new teamRedWon();
			red_score = 0;
			anim.dispose();
			clip.stop();
		}

	}

	public void redScore(int score) {
		t_red.beginRendering(500, 500);
		t_red.setColor(color.RED);
		t_red.draw("SCORE : " + score, 10, 480);
		t_red.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		t_red.endRendering();
	}

	public void blueScore(int score) {
		t_blue.beginRendering(500, 500);
		t_blue.setColor(color.BLUE);
		t_blue.draw("SCORE : " + score, 410, 480);
		t_blue.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		t_blue.endRendering();
	}

	public void Draw(GL gl, int x, int y, int index, float width, float height) {
		gl.glEnable(GL.GL_BLEND);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]); // Turn Blending On
		gl.glPushMatrix();
		gl.glTranslated(x, y, 0);
		// gl.glScaled(0.1*scale,0.1*scale,1);
		gl.glScaled(width / 2, height / 2, 1);
		gl.glBegin(GL.GL_QUADS);
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

	public void textures_handle() {
		if ((blue[3].x >= redFlagX - 15 && blue[3].x <= redFlagX + 15)
				&& (blue[3].y >= redFlagY - 15 && blue[3].y <= redFlagY + 15)) {
			isRedFlag = false;
		}
		if ((red[3].x >= blueFlagX - 15 && red[3].x <= blueFlagX + 15)
				&& (red[3].y >= blueFlagY - 15 && red[3].y <= blueFlagY + 15)) {
			isBlueFlag = false;
		}
		if (isRedFlag == false) {
			if (blue[3].x >= w / 2) {
				blue_score++;
				isRedFlag = true;
			}
		}
		if (isBlueFlag == false) {
			if (red[3].x <= w / 2) {
				red_score++;
				isBlueFlag = true;
			}
		}
	}

	public void vdr() {
		// red
		red[4].y = (f_r4) ? red[4].y - 3 : red[4].y + 3;
		if (red[4].y >= 480)
			f_r4 = true;
		if (red[4].y <= 15)
			f_r4 = false;

		red[5].y = (f_r5) ? red[5].y + 3 : red[5].y - 3;
		if (red[5].y >= 480)
			f_r5 = false;
		if (red[5].y <= 15)
			f_r5 = true;

		// blue
		blue[4].y = (f_b4) ? blue[4].y - 3 : blue[4].y + 3;
		if (blue[4].y >= 480)
			f_b4 = true;
		if (blue[4].y <= 15)
			f_b4 = false;

		blue[5].y = (f_b5) ? blue[5].y + 3 : blue[5].y - 3;
		if (blue[5].y >= 480)
			f_b5 = false;
		if (blue[5].y <= 15)
			f_b5 = true;
	}

	public void music(String filepath) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filepath));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		pressed.add(e.getKeyCode());

		if (pressed.contains(KeyEvent.VK_ESCAPE)) {
			pauseGame = (pauseGame) ? false : true;
		}

		if (pressed.contains(KeyEvent.VK_UP)) {
			red[3].moveBallY(1, bound);
		} else if (pressed.contains(KeyEvent.VK_DOWN)) {
			red[3].moveBallY(-1, bound);
		} else if (pressed.contains(KeyEvent.VK_RIGHT)) {
			red[3].moveBallX(1, bound);
		} else if (pressed.contains(KeyEvent.VK_LEFT)) {
			red[3].moveBallX(-1, bound);
		}

		if (pressed.contains(KeyEvent.VK_A)) {
			blue[3].moveBallX(-1, bound);
		} else if (pressed.contains(KeyEvent.VK_D)) {
			blue[3].moveBallX(1, bound);
		} else if (pressed.contains(KeyEvent.VK_S)) {
			blue[3].moveBallY(-1, bound);
		} else if (pressed.contains(KeyEvent.VK_W)) {
			blue[3].moveBallY(1, bound);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(final KeyEvent event) {
		// don't care
	}

	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		if (x >= 20 && x <= 60 && y >= 430 && y <= 445)
			soundOn = (soundOn) ? false : true;
	}

	void setCanvas(GLCanvas glcanvas) {
		this.glc = glcanvas;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	public static void main(String[] args) {
		anim = new Anim(new AnimGLEventListener());
	}

}