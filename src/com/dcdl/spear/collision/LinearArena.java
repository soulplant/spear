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
        entity.moveOutOf(otherEntity, bounceDirection);
        entity.onBounced(bounceDirection, otherEntity);
        otherEntity.onGotBouncedIntoBy(entity, bounceDirection);
      }
    }
  }
}
