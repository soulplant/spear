package com.dcdl.spear.collision;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class LinearArena implements Arena {
  public enum CollisionDirection {
    VERTICAL,
    HORIZONTAL,
    NONE
  }
  private final Map<Integer, Rectangle> entities = new HashMap<Integer, Rectangle>();
  @Override
  public void addEntity(int id, Rectangle rect) {
    entities.put(id, rect);
  }

  @Override
  public CollisionDirection move(Rectangle rect, Point vector) {
    moveRect(rect, vector);
    return collide(rect, vector);
  }

  public CollisionDirection collide(Rectangle rect, Point vector) {
    for (Map.Entry<Integer, Rectangle> entry : entities.entrySet()) {
      Rectangle intersection = entry.getValue().intersection(rect);
      if (!intersection.isEmpty()) {
        if (intersection.width > intersection.height) {
          int sign = (int) Math.signum(vector.y) * -1;
          int verticalBounce = sign * intersection.height;

          moveRect(rect, new Point(0, verticalBounce));
          return CollisionDirection.VERTICAL;
        } else {
          int sign = (int) Math.signum(vector.x) * -1;
          int horizontalBounce = sign * intersection.width;

          moveRect(rect, new Point(horizontalBounce, 0));
          return CollisionDirection.HORIZONTAL;
        }
      }
    }
    return CollisionDirection.NONE;
  }

  private void moveRect(Rectangle rect, Point vector) {
    rect.x += vector.x;
    rect.y += vector.y;
  }
}
