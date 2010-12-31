package com.dcdl.spear.collision;

import java.awt.Rectangle;

import junit.framework.TestCase;

import com.dcdl.spear.collision.Arena.CollisionDirection;

public class ArenaTest extends TestCase {
  private LinearArena arena;

  @Override
  protected void setUp() throws Exception {
    arena = new LinearArena();
  }

  public void testBouncedUpwardsFromGround() {
    Rectangle player = new Rectangle(0, -10, 10, 10);
    Rectangle ground = new Rectangle(0, 0, 100, 100);

    arena.addEntity(0, ground);

    player.y += 2;
    arena.collide(player, CollisionDirection.DOWN, null);

    assertEquals(-10, player.y);
  }

  public void testMultipleObstacles() {
    Rectangle player = new Rectangle(5, 5, 5, 5);
    Rectangle ground = new Rectangle(0, 10, 100, 100);
    Rectangle wall = new Rectangle(10, 0, 10, 10);

    arena.addEntity(0, ground);
    arena.addEntity(1, wall);

    player.x += 2;
    arena.collide(player, CollisionDirection.RIGHT, null);

    player.y += 2;
    arena.collide(player, CollisionDirection.DOWN, null);

    assertEquals(5, player.x);
    assertEquals(5, player.y);
  }
}
