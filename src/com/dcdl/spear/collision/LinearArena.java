package com.dcdl.spear.collision;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinearArena implements Arena {
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

  @Override
  public void collide(Rectangle rect, CollisionDirection direction,
      CollisionCallback callback) {
    if (callback == null) {
      callback = new EmptyCollisionCallback();
    }
    for (Map.Entry<Integer, Rectangle> entry : entities.entrySet()) {
      Rectangle intersection = entry.getValue().intersection(rect);
      if (!intersection.isEmpty() && direction != CollisionDirection.NONE) {
        moveRectOut(rect, entry.getValue(), direction.opposite());
      }
    }
  }

  @Override
  public void collide(Rectangle rect, Point vector, CollisionCallback callback) {
    if (callback == null) {
      callback = new EmptyCollisionCallback();
    }
    for (Map.Entry<Integer, Rectangle> entry : entities.entrySet()) {
      Rectangle intersection = entry.getValue().intersection(rect);
      if (!intersection.isEmpty()) {
        // There is an overlap.
        List<CollisionDirection> directions = Arrays.asList(
            CollisionDirection.UP, CollisionDirection.DOWN,
            CollisionDirection.LEFT, CollisionDirection.RIGHT);
        int smallest = 0;
        CollisionDirection smallestDirection = CollisionDirection.NONE;
        for (CollisionDirection dir : directions) {
          int displacement = Math.abs(getDisplacement(rect, entry.getValue(), dir));
          if (smallest == 0 || displacement < smallest) {
            smallest = displacement;
            smallestDirection = dir;
          }
        }

        if (smallestDirection != CollisionDirection.NONE) {
          moveRectOut(rect, entry.getValue(), smallestDirection);
          callback.onBounced(smallestDirection);
        }
      }
    }
  }

  private int getDisplacement(Rectangle moving, Rectangle stationary, CollisionDirection direction) {
    switch (direction) {
    case UP:
      return (moving.y + moving.height) - stationary.y;
    case DOWN:
      return - ((stationary.y + stationary.height) - moving.y);
    case LEFT:
      return (moving.x + moving.width) - stationary.x;
    case RIGHT:
      return - ((stationary.x + stationary.width) - moving.x);
    }
    return 0;
  }

  private void moveRectOut(Rectangle moving, Rectangle stationary, CollisionDirection direction) {
    switch (direction) {
    case UP:
    case DOWN:
      moving.y -= getDisplacement(moving, stationary, direction);
      break;
    case LEFT:
    case RIGHT:
      moving.x -= getDisplacement(moving, stationary, direction);
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
