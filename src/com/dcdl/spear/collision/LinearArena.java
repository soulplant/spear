package com.dcdl.spear.collision;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class LinearArena implements Arena {
  private final Map<Integer, Rectangle> entities = new HashMap<Integer, Rectangle>();
  @Override
  public void addEntity(int id, Rectangle rect) {
    entities.put(id, rect);
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
}
