package com.dcdl.spear;

import java.awt.Point;

import com.dcdl.spear.collision.Arena.Direction;

public class WalkerSpawner implements Walker.Listener {
  private static final int COOLDOWN_MS = 2000;
  private final Point spawnAt;
  private final GameClient client;
  private int walkersAlive = 0;
  private final int walkerLimit;
  private final Direction direction;
  private int ticksSinceLastSpawn = -1;

  public WalkerSpawner(GameClient client, int walkerLimit, Point spawnAt,
      Direction direction) {
    this.client = client;
    this.walkerLimit = walkerLimit;
    this.spawnAt = spawnAt;
    this.direction = direction;
  }

  public void tick() {
    if (walkersAlive < walkerLimit && isReadyToSpawn()) {
      spawn();
    }
    ticksSinceLastSpawn += 1;
  }

  private void spawn() {
    ticksSinceLastSpawn = 0;
    walkersAlive += 1;
    client.spawnWalkerAt(spawnAt, direction);
  }

  private boolean isReadyToSpawn() {
    boolean result = ticksSinceLastSpawn == -1
        || Util.ticksInMs(ticksSinceLastSpawn) >= COOLDOWN_MS;
    return result;
  }

  @Override
  public void onDied() {
    walkersAlive -= 1;
  }
}
