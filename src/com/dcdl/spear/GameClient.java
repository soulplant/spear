package com.dcdl.spear;

import java.awt.Point;

import com.dcdl.spear.collision.Arena.Direction;

public interface GameClient {
  void spawnWalkerAt(Point spawnAt, Direction direction);
  void killWalker(Walker walker);
  void killPlayer(Player player);
}
