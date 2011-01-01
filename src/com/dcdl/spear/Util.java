package com.dcdl.spear;

import java.awt.Point;
import java.awt.Rectangle;

public class Util {
  public static Rectangle scaleRect(Rectangle rect, double scale) {
    return new Rectangle((int) (rect.x * scale), (int) (rect.y * scale),
        (int) (rect.width * scale), (int) (rect.height * scale));
  }

  public static Point scalePoint(Point point, double scale) {
    return new Point((int) (point.x * scale), (int) (point.y * scale));
  }

  public static int scaleUp(int x) {
    return x * Constants.SCALE;
  }

  public static int scaleDown(int x) {
    return x / Constants.SCALE;
  }

  public static int ticksInMs(int ticks) {
    return (int) (ticks * 1000.0 / Constants.FPS);
  }

  public static Rectangle scaledRect(int x, int y, int width, int height) {
    return scaleRect(new Rectangle(x, y, width, height), Constants.SCALE);
  }

  /**
   * Pixels per second to centi-pixels per frame.
   */
  public static int pps2cppf(int pps) {
    return (int) (100.0 / Constants.FPS * pps);
  }
}
