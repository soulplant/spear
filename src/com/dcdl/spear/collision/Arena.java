package com.dcdl.spear.collision;

import com.dcdl.spear.Entity;

public interface Arena {
  public interface CollisionCallback {
    void onBounced(Direction direction);
  }

  public class EmptyCollisionCallback implements CollisionCallback {
    @Override
    public void onBounced(Direction direction) { }
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

  void collide(Entity entity, Direction direction);
}
