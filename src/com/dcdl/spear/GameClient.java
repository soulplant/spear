package com.dcdl.spear;

import java.awt.Point;

import com.dcdl.spear.collision.Arena.Direction;

public interface GameClient {
  void onBounceOnWalker(Walker walker);
  void spawnWalkerAt(Point spawnAt, Direction direction);
}
