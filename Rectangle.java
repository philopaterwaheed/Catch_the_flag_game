/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs304;

/**
 *
 * @author Ahmed hany
 */
public class Rectangle {
    int bottomX, bottomY, topX, topY;

    Rectangle(int bottomX, int bottomY, int topX, int topY) {
        this.bottomX = bottomX;
        this.bottomY = bottomY;
        this.topX = topX;
        this.topY = topY;
    }

    public static boolean isIntersect2Line(int a, int b, int c, int d) {
        return !(b < c || d < a);
    }

    public static boolean isIntersectRectRect(Rectangle r1, Rectangle r2) {
        // collision x-axis?
        boolean collisionX = isIntersect2Line(r1.bottomX, r1.topX, r2.bottomX, r2.topX);
        // collision y-axis?
        boolean collisionY = isIntersect2Line(r1.bottomY, r1.topY, r2.bottomY, r2.topY);
        // collision only if on both axes
        return collisionX && collisionY;
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static boolean isIntersectRectBall(Rectangle r, Ball b) {
        // collision x-axis?

        float closestX = clamp(b.x, r.bottomX, r.topX);
        float closestY = clamp(b.y, r.bottomY, r.topY);

        // Calculate the distance between the circle's center and this closest point
        float distanceX = b.x - closestX;
        float distanceY = b.y - closestY;

        // If the distance is less than the circle's radius, an intersection occurs
        float distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);
        return distanceSquared < (b.r * b.r);
    }
}
