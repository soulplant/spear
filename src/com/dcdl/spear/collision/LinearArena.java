package com.dcdl.spear.collision;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.dcdl.spear.Entity;

public class LinearArena implements Arena {
  private final List<Entity> entities = new ArrayList<Entity>();
  private final List<Arena> arenas = new ArrayList<Arena>();

  public void addEntity(Entity entity) {
    entities.add(entity);
  }

  public void addArena(Arena arena) {
    arenas.add(arena);
  }

  public void tick() {
    for (Entity entity : entities) {
      entity.moveHorizontal();
      for (Arena arena : arenas) {
        arena.collide(entity, entity.getHorizontalDirection());
      }

      entity.moveVertical();
      for (Arena arena : arenas) {
        arena.collide(entity, entity.getVerticalDirection());
      }
    }
  }

  @Override
  public void collide(Entity entity, Direction direction) {
    if (direction == Direction.NONE) {
      return;
    }
    for (Entity otherEntity : entities) {
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
