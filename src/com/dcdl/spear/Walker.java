package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.Direction;

public class Walker extends Entity {
  public interface Listener {
    void onDied();
  }
  public class EmptyListener implements Listener {
    @Override public void onDied() { }
  }

  private static final int WALKING_SPEED_PPS = 30;
  private static final int WALKING_SPEED = Util.pps2cppf(WALKING_SPEED_PPS);
  private static final int WIDTH_PX = 16;
  private static final int HEIGHT_PX = 16;
  private static final int FALL_RECOVERY_MS = 500;

  private Direction facing;
  private final Listener listener;
  private int ticksSinceHitFloor = -1;
  private boolean canMove = true;
  private final GameClient client;

  public Walker(GameClient client, int x, int y, Direction facing, Listener listener) {
    super(Util.scaledRect(x, y, WIDTH_PX, HEIGHT_PX));
    this.client = client;
    this.facing = facing;
    this.listener = listener != null ? listener : new EmptyListener();
    assert(facing.isHorizontal());
  }

  @Override
  public boolean moveHorizontal() {
    if (isOnFloor() && canMove) {
      setXVelocity(WALKING_SPEED * (facing == Direction.LEFT ? -1 : 1));
    } else {
      setXVelocity(0);
    }
    return super.moveHorizontal();
  }

  @Override
  public void onBounced(Direction direction, Entity otherEntity) {
    if (otherEntity instanceof Spear) {
      if (direction == Direction.UP && isOnFloor()) {
        return;
      }
    }
    bounceOut(direction, otherEntity);
    if (direction.isHorizontal()) {
      facing = direction;
      setXVelocity(-getXVelocity());
    }
  }

  @Override
  public void render(Graphics g) {
    g.setColor(Color.BLACK);
    Rectangle rect = Util.scaleRect(getRect(), 1.0 / Constants.SCALE);
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void die() {
    client.killWalker(this);
    listener.onDied();
  }

  @Override
  protected void onHitFloor() {
    ticksSinceHitFloor = 0;
  }

  @Override
  public void tick() {
    super.tick();
    canMove = ticksSinceHitFloor == -1 || Util.ticksInMs(ticksSinceHitFloor) > FALL_RECOVERY_MS;
    if (ticksSinceHitFloor != -1) {
      ticksSinceHitFloor += 1;
    }
  }
}
