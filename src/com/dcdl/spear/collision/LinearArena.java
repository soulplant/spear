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
          throw new RuntimeException("Moving thing got lodged into something.");
        }

        CollisionDirection direction = CollisionDirection.NONE;

        CollisionAxis axis = CollisionAxis.HORIZONTAL;
        if (Math.abs(vector.x) == Math.abs(vector.y)) {
          // We can't use the vector to determine the direction of the
          // collision, so we use the size of the intersection.
          if (intersection.width == intersection.height) {
            // We have hit a corner, so we move out horizontally.
            axis = CollisionAxis.HORIZONTAL;
          } else if (intersection.height < intersection.width) {
            axis = CollisionAxis.VERTICAL;
          }
        } else {
          if (vector.x == 0 && vector.y == 0) {
            // No movement?
            axis = CollisionAxis.VERTICAL; // TODO Do something smarter here.
          } else {
            if (vector.x == 0) {
              axis = CollisionAxis.VERTICAL;
            } else if (vector.y == 0) {
              axis = CollisionAxis.HORIZONTAL;
            } else if (Math.abs(vector.x) <= Math.abs(vector.y)) {
              axis = CollisionAxis.HORIZONTAL;
            } else {
              axis = CollisionAxis.VERTICAL;
            }
          }
        }

        if (axis == CollisionAxis.VERTICAL) {
          direction = vector.y < 0 ? CollisionDirection.DOWN : CollisionDirection.UP;
        } else {
          direction = vector.x < 0 ? CollisionDirection.RIGHT : CollisionDirection.LEFT;
        }

        if (direction != CollisionDirection.NONE) {
          moveRectOut(rect, entry.getValue(), direction);
          callback.onBounced(direction);
        }
      }
    }
  }

  private void moveRectOut(Rectangle moving, Rectangle stationary, CollisionDirection direction) {
    switch (direction) {
    case UP:
      moving.y -= (moving.y + moving.height) - stationary.y;
      break;
    case DOWN:
      moving.y += (stationary.y + stationary.height) - moving.y;
      break;
    case LEFT:
      moving.x -= (moving.x + moving.width) - stationary.x;
      break;
    case RIGHT:
      moving.x += (stationary.x + stationary.width) - moving.x;
      break;
    case NONE:
      // Do nothing.
      break;
    }
  }

  private void moveRect(Rectangle rect, Point vector) {
    rect.x += vector.x;
    rect.y += vector.y;
  }
}
