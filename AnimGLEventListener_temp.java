package cs304;

import Textures.AnimListener;
import Textures.TextureReader;

//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.media.opengl.*;
import java.util.BitSet;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AnimGLEventListener_temp extends AnimListener {

	int animationIndex_blue = 3;
	int maxHeight = 500;
	int maxWidth = 900;
	int ball_index = 0;
	int w = 900;
	int h = 500;
	int r = 30;

	Set<Integer> pressed = new HashSet<Integer>();

	FileInputStream music;
	AudioStream audios;

	static Anim anim;

	// int line = 700;
	// boolean hasCrossedBlue = false;

	GLAutoDrawable gldddd;

	TextRenderer t_blue = new TextRenderer(new Font("Comic Sans MS", Font.BOLD, 14));
	TextRenderer renderer = new TextRenderer(new Font("SanasSerif", Font.BOLD, 20));
	TextRenderer t_red = new TextRenderer(new Font("Comic Sans MS", Font.BOLD, 14));
	Color color = new Color(0, 0, 0);

	String time = java.time.LocalTime.now() + "";
	int timer = 0, m = 1;

	int x1 = 380, y1 = 370, x2 = 515, y2 = 240;
	// 450, 250
	int blueFlagX = w - 20, blueFlagY = h / 2, redFlagX = 20, redFlagY = h / 2;
	int red_score = 0, blue_score = 0, redf = 0, bluef = 0;
	boolean isRedFlag = true, finishGame = false;
	boolean withflag = false, n = false;
	boolean isBlueFlag = true;

	String textureNames[] = { "redbulll.png", "blueball.png", "redflagbb.png", "blueflagbb.png", "redballflag.png",
			"wall.png", "redVSblue.png", "Back.png" };
	TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
	int textures[] = new int[textureNames.length];
	Rectangle bound[] = { new Rectangle(w, 0, w + 100, h + 100), new Rectangle(0, h, w, h + 100),
			new Rectangle(-100, 0, 0, h), new Rectangle(0, -100, w, 0), new Rectangle(100, 100, 200, 200),
			new Rectangle(100, 350, 200, 450), new Rectangle(700, 100, 800, 200), new Rectangle(700, 350, 800, 450) };

	Ball blue[] = { new Ball(0, 0), new Ball(0, 0), new Ball(515, 370), new Ball(515, 120) };
	Ball red[] = { new Ball(0, 0), new Ball(0, 0), new Ball(380, 370), new Ball(380, 120) };

	private GLCanvas glc;

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
				texture[i] = TextureReader.readTexture("Assets" + "//" + textureNames[i], true);
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
		try {
			music = new FileInputStream(new File("catchtheflag.wav"));
			audios = new AudioStream(music);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
		AudioPlayer.player.start(audios);

		gl.glLoadIdentity();
		gl.glOrtho(0, maxWidth, 0, maxHeight, -1, 1);
	}

	public void display(GLAutoDrawable gld) {

		GL gl = gld.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gldddd = gld;

		if (!finishGame) {
			// try {
			//// DrawTime();
			// } catch (ParseException ex) {
			// System.err.println(ex.getMessage());
			// }
			// background
			DrawSprite(gl, 450, 250, textures.length - 1, 900, 500);
			// scores
			redScore(this.red_score);
			blueScore(this.blue_score);
			// redVSblue
			DrawSprite(gl, 440, 470, textures.length - 2, 200, 50);

			for (int i = 0; i < red.length; i++) {
				DrawSprite(gl, red[i].x, red[i].y, 0, r, r);
			}
			for (int i = 0; i < blue.length; i++) {
				DrawSprite(gl, blue[i].x, blue[i].y, 1, r, r);
			}
			for (int i = 4; i < bound.length; i++) {
				DrawSprite(gl, (bound[i].topX + bound[i].bottomX) / 2, (bound[i].topY + bound[i].bottomY) / 2, 5, 100,
						100);
			}

			if (isRedFlag) {
				DrawSprite(gl, redFlagX, redFlagY, 2, 50, 50);
			} else {
				DrawSprite(gl, blue[3].x, blue[3].y, 2, 30, 30);
			}
			if (isBlueFlag) {
				DrawSprite(gl, blueFlagX, blueFlagY, 3, 50, 50);
			} else {
				DrawSprite(gl, red[3].x, red[3].y, 3, 30, 30);
			}

			doo();

			// handleKeyPress();

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

			System.out.println(red[3].x);

		}

		if (blue_score == 2) {
			finishGame = true;
			while (m-- > 0)
				new teamBlueWon();
			blue_score = 0;
			anim.dispose();
		}
		if (red_score == 2) {
			finishGame = true;
			while (m-- > 0)
				new teamRedWon();
			red_score = 0;
			anim.dispose();

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

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}

	public void DrawSprite(GL gl, int x, int y, int index, float width, float height) {
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

	void setCanvas(GLCanvas glcanvas) {
		this.glc = glcanvas;
	}

	// public void handleKeyPress() {
	//// if(Math.sqrt(Math.pow(x1-x2,2)+ Math.pow(y1-y2,2))‏){
	//// if(Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2))>=100){
	// if (isKeyPressed(KeyEvent.VK_LEFT))
	// red[3].moveBallX(-1, bound);
	// else if (isKeyPressed(KeyEvent.VK_RIGHT))
	// red[3].moveBallX(1, bound);
	// else if (isKeyPressed(KeyEvent.VK_DOWN))
	// red[3].moveBallY(-1, bound);
	// else if (isKeyPressed(KeyEvent.VK_UP))
	// red[3].moveBallY(1, bound);
	//
	// else if (isKeyPressed(KeyEvent.VK_A)) {
	// blue[3].moveBallX(-1, bound);
	// }
	// if (isKeyPressed(KeyEvent.VK_D)) {
	// blue[3].moveBallX(1, bound);
	// }
	// if (isKeyPressed(KeyEvent.VK_S)) {
	// blue[3].moveBallY(-1, bound);
	// }
	// if (isKeyPressed(KeyEvent.VK_W)) {
	// blue[3].moveBallY(1, bound);
	// }
	// }

	// public void handleKeyPress(KeyEvent e) {
	// if (e.getKeyCode() == KeyEvent.VK_UP) {
	// red[3].y_dir = 1;
	// red[3].x_dir = 0;
	// red[3].moveBallY(1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	// red[3].y_dir = -1;
	// red[3].x_dir = 0;
	// red[3].moveBallY(-1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	// red[3].y_dir = 0;
	// red[3].x_dir = 1;
	// red[3].moveBallX(1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	// red[3].y_dir = 0;
	// red[3].x_dir = -1;
	// red[3].moveBallX(-1, bound);
	// }
	// }

	public void doo() {
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

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		pressed.add(e.getKeyCode());

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

		// if ((blue[3].x >= redFlagX - 15 && blue[3].x <= redFlagX + 15)
		// && (blue[3].y >= redFlagY - 15 && blue[3].y <= redFlagY + 15)) {
		// isRedFlag = false;
		// }
		// if ((red[3].x >= blueFlagX - 15 && red[3].x <= blueFlagX + 15)
		// && (red[3].y >= blueFlagY - 15 && red[3].y <= blueFlagY + 15)) {
		// isBlueFlag = false;
		// }
		// if (isRedFlag == false) {
		// if (blue[3].x >= w / 2) {
		// blue_score++;
		// isRedFlag = true;
		// }
		// }
		// if (isBlueFlag == false) {
		// if (red[3].x <= w / 2) {
		// red_score++;
		// isBlueFlag = true;
		// }
		// }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressed.remove(e.getKeyCode());
	}

	// @Override
	// public void keyPressed(KeyEvent e) {
	// if (e.getKeyCode() == KeyEvent.VK_UP) {
	// red[3].y_dir = 1;
	// red[3].x_dir = 0;
	// red[3].moveBallY(1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_DOWN) {
	// red[3].y_dir = -1;
	// red[3].x_dir = 0;
	// red[3].moveBallY(-1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	// red[3].y_dir = 0;
	// red[3].x_dir = 1;
	// red[3].moveBallX(1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	// red[3].y_dir = 0;
	// red[3].x_dir = -1;
	// red[3].moveBallX(-1, bound);
	// }
	//
	// if (e.getKeyCode() == KeyEvent.VK_W) {
	// blue[3].y_dir = 1;
	// blue[3].x_dir = 0;
	// blue[3].moveBallY(1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_S) {
	// blue[3].y_dir = -1;
	// blue[3].x_dir = 0;
	// blue[3].moveBallY(-1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_D) {
	// blue[3].y_dir = 0;
	// blue[3].x_dir = 1;
	// blue[3].moveBallX(1, bound);
	// }
	// if (e.getKeyCode() == KeyEvent.VK_A) {
	// blue[3].y_dir = 0;
	// blue[3].x_dir = -1;
	// blue[3].moveBallX(-1, bound);
	// }
	// if ((blue[3].x >= redFlagX - 15 && blue[3].x <= redFlagX + 15)
	// && (blue[3].y >= redFlagY - 15 && blue[3].y <= redFlagY + 15)) {
	// isRedFlag = false;
	// }
	// if ((red[3].x >= blueFlagX - 15 && red[3].x <= blueFlagX + 15)
	// && (red[3].y >= blueFlagY - 15 && red[3].y <= blueFlagY + 15)) {
	// isBlueFlag = false;
	// }
	// if (isRedFlag == false) {
	// if (blue[3].x >= w / 2) {
	// blue_score++;
	// isRedFlag = true;
	// }
	// }
	// if (isBlueFlag == false) {
	// if (red[3].x <= w / 2) {
	// red_score++;
	// isBlueFlag = true;
	// }
	//
	// }
	// }

	public void DrawTime() throws ParseException {
		String time1 = time;
		String time2 = java.time.LocalTime.now() + "";

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date date1 = format.parse(time1);
		Date date2 = format.parse(time2);
		long difference = date2.getTime() - date1.getTime();

		String fi = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(difference),
				TimeUnit.MILLISECONDS.toSeconds(difference)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference)));

		renderer.beginRendering(gldddd.getWidth(), gldddd.getHeight());
		renderer.draw(fi, 500, 500);
		renderer.endRendering();
	}

	// @Override
	// public void keyReleased(final KeyEvent event) {
	// int keyCode = event.getKeyCode();
	// }

	@Override
	public void keyTyped(final KeyEvent event) {
		// don't care
	}

	public boolean isKeyPressed(final int keyCode) {
		return false;
	}

	public static void main(String[] args) {
		anim = new Anim(new AnimGLEventListener_temp());
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
//
// if(red[3].x >= line)
// hasCrossedBlue = true;
// if(hasCrossed)
// for(int i=0; i<2; i++)
// {
// if(i % 2 == 0)
// blue[i].moveTo(new Ball(red[3].x + (int)(Math.random() * 50), red[3].y),
// bound);
// else
// blue[i].moveTo(new Ball(red[3].x, red[3].y + (int)(Math.random() * 50)),
// bound);
// }
// else
// for(int i=0; i<2; i++)
// blue[i].moveBall(bound, 5);
