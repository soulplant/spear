package com.dcdl.spear;

import java.awt.Rectangle;

public class Util {
  public static Rectangle scaleRect(Rectangle rect, int scale) {
    return new Rectangle(rect.x * scale, rect.y * scale, rect.width * scale, rect.height * scale);
  }
}
