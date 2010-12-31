package com.dcdl.spear.collision;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import com.dcdl.spear.Entity;

public class LinearArena implements Arena {
  private final Map<Integer, Entity> entities = new HashMap<Integer, Entity>();

  public void addEntity(int id, Entity entity) {
    entities.put(id, entity);
  }

  @Override
  public void collide(Entity entity, Direction direction) {
    if (direction == Direction.NONE) {
      return;
    }
    for (Map.Entry<Integer, Entity> entry : entities.entrySet()) {
      Entity otherEntity = entry.getValue();
      Rectangle intersection = otherEntity.intersection(entity);
      if (!intersection.isEmpty()) {
        Direction bounceDirection = direction.opposite();
        moveRectOut(entity.getRect(), otherEntity.getRect(), bounceDirection);
        entity.onBounced(bounceDirection, otherEntity);
        otherEntity.onGotBouncedIntoBy(bounceDirection, entity);
      }
    }
  }

  private int getDisplacement(Rectangle moving, Rectangle stationary, Direction direction) {
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

  private void moveRectOut(Rectangle moving, Rectangle stationary, Direction direction) {
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
