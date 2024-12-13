//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Templates;

import javax.media.opengl.GL;

public class Shapes {
    public Shapes() {
    }

    public static void drawPolygonCircle(int xCenter, int yCenter, int radius, GL gl, float c1, float c2, float c3) {
        gl.glColor3f(c1, c2, c3);
        gl.glBegin(9);
        gl.glColor3f(c1, c2, c3);

        for(double a = 0.0; a < Math.toRadians(360.0); a += Math.toRadians(1.0)) {
            int x = (int)((double)radius * Math.cos(a)) + xCenter;
            int y = (int)((double)radius * Math.sin(a)) + yCenter;
            gl.glVertex2d((double)x, (double)y);
        }

        gl.glEnd();
    }

    public static void drawCircle(int xCenter, int yCenter, int radius, GL gl, float c1, float c2, float c3) {
        gl.glColor3f(c1, c2, c3);
        gl.glBegin(2);

        for(double a = 0.0; a < Math.toRadians(360.0); a += Math.toRadians(1.0)) {
            int x = (int)((double)radius * Math.cos(a)) + xCenter;
            int y = (int)((double)radius * Math.sin(a)) + yCenter;
            gl.glVertex2d((double)x, (double)y);
        }

        gl.glEnd();
    }

    public static void drawEllipse(int xCenter, int yCenter, double a, double b, GL gl) {
        gl.glBegin(2);

        for(int i = 0; i < 100; ++i) {
            double angle = 6.283185307179586 * (double)i / 100.0;
            double x = a * Math.cos(angle) + (double)xCenter;
            double y = b * Math.sin(angle) + (double)yCenter;
            gl.glVertex2d(x, y);
        }

        gl.glEnd();
    }

    public static void drawPolygonTriangle(int xp1, int yp1, int xp2, int yp2, int xp3, int yp3, GL gl) {
        gl.glBegin(9);
        gl.glVertex2i(xp1, yp1);
        gl.glVertex2i(xp2, yp2);
        gl.glVertex2i(xp3, yp3);
        gl.glEnd();
    }

    public static void drawSquare(int x1Pos, int x2Pos, int y1Pos, int y2Pos, GL gl) {
        gl.glBegin(1);
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos, y2Pos);
        gl.glVertex2i(x2Pos, y2Pos);
        gl.glVertex2i(x1Pos, y2Pos);
        gl.glVertex2i(x1Pos, y2Pos);
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glEnd();
    }

    public static void drawBackGround(int x1Pos, int x2Pos, int y1Pos, int y2Pos, GL gl, float c1, float c2, float c3) {
        gl.glBegin(9);
        gl.glColor3f(c1, c2, c3);
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos, y2Pos);
        gl.glVertex2i(x1Pos, y2Pos);
        gl.glEnd();
    }

    public static void drawTriangle(int x1Pos, int x2Pos, int y1Pos, int y2Pos, GL gl) {
        gl.glBegin(1);
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos - x1Pos, y1Pos + y2Pos);
        gl.glVertex2i(x2Pos - x1Pos, y1Pos + (y2Pos - y1Pos));
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glEnd();
    }
}
