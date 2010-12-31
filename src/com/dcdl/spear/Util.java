package com.dcdl.spear;

import java.awt.Rectangle;

public class Util {
  public static Rectangle scaleRect(Rectangle rect, double scale) {
    return new Rectangle((int) (rect.x * scale), (int) (rect.y * scale),
        (int) (rect.width * scale), (int) (rect.height * scale));
  }
}
