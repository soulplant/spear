package com.dcdl.spear.collision;

import java.awt.Point;
import java.awt.Rectangle;

public interface Arena {
  public interface CollisionCallback {
    void onBounced(CollisionDirection collisionDirection);
  }

  public class EmptyCollisionCallback implements CollisionCallback {
    @Override
    public void onBounced(CollisionDirection collisionDirection) { }
  }

  public enum CollisionDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    NONE
    ;
    public CollisionDirection opposite() {
      switch (this) {
      case UP: return DOWN;
      case DOWN: return UP;
      case LEFT: return RIGHT;
      case RIGHT: return LEFT;
      default: return this;
      }
    }
  }

  void addEntity(int id, Rectangle rect);
  void move(Rectangle rect, Point vector, CollisionCallback callback);
  void collide(Rectangle rect, Point vector, CollisionCallback callback);
  void collide(Rectangle rect, CollisionDirection direction, CollisionCallback callback);
}
