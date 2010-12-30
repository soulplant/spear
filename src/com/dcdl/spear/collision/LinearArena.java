package com.dcdl.spear.collision;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class LinearArena implements Arena {
  private enum CollisionAxis {
    VERTICAL,
    HORIZONTAL
  }
  private final Map<Integer, Rectangle> entities = new HashMap<Integer, Rectangle>();
  @Override
  public void addEntity(int id, Rectangle rect) {
    entities.put(id, rect);
  }

  @Override
  public void move(Rectangle rect, Point vector, CollisionCallback callback) {
    moveRect(rect, vector);
    collide(rect, vector, callback);
  }

  public void collide(Rectangle rect, Point vector, CollisionCallback callback) {
    if (callback == null) {
      callback = new EmptyCollisionCallback();
    }
    for (Map.Entry<Integer, Rectangle> entry : entities.entrySet()) {
      Rectangle intersection = entry.getValue().intersection(rect);
      if (!intersection.isEmpty()) {
        // There is an overlap.
        if (intersection.contains(rect)) {
          // TODO The moving thing is completely inside the thing it has
          // collided with, so we need to move it out of there.
          System.out.println("HERE");
          continue;
        }

        CollisionAxis axis = CollisionAxis.HORIZONTAL;
        if (intersection.width > intersection.height) {
          axis = CollisionAxis.VERTICAL;
        }
        if (axis == CollisionAxis.VERTICAL) {
          int sign = (int) Math.signum(vector.y) * -1;
          int verticalBounce = sign * intersection.height;

          moveRect(rect, new Point(0, verticalBounce));
          callback.onBounced(sign < 0 ? CollisionDirection.UP : CollisionDirection.DOWN);
        } else {
          int sign = (int) Math.signum(vector.x) * -1;
          int horizontalBounce = sign * intersection.width;

          moveRect(rect, new Point(horizontalBounce, 0));
          callback.onBounced(sign < 0 ? CollisionDirection.LEFT : CollisionDirection.RIGHT);
        }
      }
    }
  }

  private void moveRect(Rectangle rect, Point vector) {
    rect.x += vector.x;
    rect.y += vector.y;
  }
}
