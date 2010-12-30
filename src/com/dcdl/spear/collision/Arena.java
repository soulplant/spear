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
  }

  void addEntity(int id, Rectangle rect);
  void move(Rectangle rect, Point vector,
      CollisionCallback callback);
}
