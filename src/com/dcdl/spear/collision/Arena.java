package com.dcdl.spear.collision;

import com.dcdl.spear.Entity;

public interface Arena {
  public interface CollisionCallback {
    void onBounced(Direction direction, Entity otherEntity);
  }

  public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE
    ;
    public Direction opposite() {
      switch (this) {
      case UP: return DOWN;
      case DOWN: return UP;
      case LEFT: return RIGHT;
      case RIGHT: return LEFT;
      default: return this;
      }
    }

    public boolean isHorizontal() {
      return this == LEFT || this == RIGHT;
    }
  }

  /**
   * Collides the given entity with the entities in this {@link Arena}.
   */
  void collide(Entity entity, Direction direction);
}
