package com.dcdl.spear.collision;

import java.awt.Point;
import java.awt.Rectangle;

import junit.framework.TestCase;

public class ArenaTest extends TestCase {
  private LinearArena arena;

  @Override
  protected void setUp() throws Exception {
    arena = new LinearArena();
  }

  public void testBouncedUpwardsFromGround() {
    Rectangle player = new Rectangle(0, -10, 10, 10);
    Rectangle ground = new Rectangle(0, 0, 100, 100);
    Point falling = new Point(0, 2);

    arena.addEntity(0, ground);
    arena.move(player, falling);

    assertEquals(-10, player.y);
  }

  public void testFallToGround() {
    Rectangle player = new Rectangle(0, 0, 5, 5);
    Rectangle ground = new Rectangle(0, 10, 100, 100);
    Point falling = new Point(0, 10);

    arena.addEntity(0, ground);
    arena.move(player, falling);

    assertEquals(5, player.y);
  }

  public void testMultipleObstacles() {
    Rectangle player = new Rectangle(5, 5, 5, 5);
    Rectangle ground = new Rectangle(0, 10, 100, 100);
    Rectangle wall = new Rectangle(10, 0, 10, 10);
    Point falling = new Point(2, 2);

    arena.addEntity(0, ground);
    arena.addEntity(1, wall);
    arena.move(player, falling);

    assertEquals(5, player.x);
    assertEquals(5, player.y);
  }
}
