package com.dcdl.spear.collision;

import java.awt.Rectangle;

import junit.framework.TestCase;

import com.dcdl.spear.Entity;
import com.dcdl.spear.collision.Arena.Direction;

public class ArenaTest extends TestCase {
  private LinearArena arena;

  @Override
  protected void setUp() throws Exception {
    arena = new LinearArena();
  }

  public void testBouncedUpwardsFromGround() {
    Entity player = makeEntity(0, -10, 10, 10);
    Entity ground = makeEntity(0, 0, 100, 100);

    arena.addEntity(0, ground);

    player.displace(0, 2);
    arena.collide(player, Direction.DOWN);

    assertEquals(-10, player.getY());
  }

  public void testMultipleObstacles() {
    Entity player = makeEntity(5, 5, 5, 5);
    Entity ground = makeEntity(0, 10, 100, 100);
    Entity wall = makeEntity(10, 0, 10, 10);

    arena.addEntity(0, ground);
    arena.addEntity(1, wall);

    player.displace(2, 0);
    arena.collide(player, Direction.RIGHT);

    player.displace(0, 2);
    arena.collide(player, Direction.DOWN);

    assertEquals(5, player.getX());
    assertEquals(5, player.getY());
  }

  private Entity makeEntity(int x, int y, int width, int height) {
    return new Entity(new Rectangle(x, y ,width, height));
  }
}
