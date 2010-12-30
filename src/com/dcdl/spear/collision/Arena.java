package com.dcdl.spear.collision;

import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.LinearArena.CollisionDirection;

public interface Arena {
  public class Collision {
    public int id;
    public Point vector;

    public Collision(int id, Point vector) {
      this.id = id;
      this.vector = vector;
    }
  }
  CollisionDirection move(Rectangle rect, Point vector);
  void addEntity(int id, Rectangle rect);
}
