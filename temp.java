package cs304;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class temp {

}

/*
 * // b5.moveTo(b1, bound);
 *
 * // handleKeyPress();
 * // (x1 >= x2-5 && x1 <= x2+5) && (y1 >= y2-7 && y1 <= y2+7)
 * // if (!n) {
 * // DrawSprite(gl, x_blueflag, y_blyeflag, animationIndex_blue, 900, 500);
 * // }
 * // if ((x1 >= x_blueflag - 7 && x1<=x_blueflag+7) && ( y1 >= y_blyeflag - 5
 * && y1 <= y_blyeflag+5) && !withflag) {
 * // withflag = true;
 * // n = true;
 * // ball_index = 4;
 * // DrawSprite(gl, x1, y1, ball_index, 900, 500); // player 1
 * // }
 * // else{
 * // DrawSprite(gl, x1, y1, ball_index, 900, 500);
 * // }
 * //
 * // if((x1 >= x_redflag - 7 && x1<=x_redflag+7) && ( y1 >= y_redflag - 5 && y1
 * <= y_redflag +5))
 * // reset();
 * // try {
 * //// DrawTime();
 * // } catch (ParseException ex) {
 * // System.err.println(ex.getMessage());
 * // }
 *
 *
 *
 *
 *
 *
 *
  public void handleKeyPress(KeyEvent e) {

  // if(Math.sqrt(Math.pow(x1-x2,2)+ Math.pow(y1-y2,2))â€�){
  // if(Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2))>=100){
  // if(e.getKeyCode()==KeyEvent.VK_SPACE ){
  // b1.knockBack=20;
 b1.x_dir=-1;
  // b1.y_dir=0;
   }
 }
 *
 *
 *
 *
 *
 *
 * public void keyPressed(KeyEvent e) {
 * // if(Rectangle.isIntersectRectBall(new Rectangle(x_redflag-25,x_redflag+25,
 * // y_redflag-25,y_redflag+25), blue[3]) )isRedFlag=false;
 *
 * System.out.println(red[3].x + " " + red[3].y);
 * }
 */

// public class Anim extends JFrame {
// public static void main(String[] args) {
// new Anim(new AnimGLEventListener4());
// }
// }

// for(int i = 0; i< blue.length; i++)
// if( ( (blue[i].x == red[3].x -1 && blue[i].y == red[3].y - 2) ||
// (blue[i].x == red[3].x + 1 && blue[i].y == red[3].y - 1) ||
// (blue[i].x == red[3].x && blue[i].y == red[3].y - 2) ||
// (blue[i].x == red[3].x -2 && blue[i].y == red[3].y - 2) ) &&
// !isBlueFlag)
// isBlueFlag = true;
//
// for(int i = 0; i< red.length; i++)
// if( ( (red[i].x == blue[3].x -1 && red[i].y == blue[3].y - 2) ||
// (red[i].x == blue[3].x + 1 && red[i].y == blue[3].y - 1) ||
// (red[i].x == blue[3].x && red[i].y == blue[3].y - 2) ||
// (red[i].x == blue[3].x -2 && red[i].y == blue[3].y - 2) ) &&
// !isRedFlag)
// isRedFlag = true;