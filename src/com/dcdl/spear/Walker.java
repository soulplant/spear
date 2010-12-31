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

  private static final int WALKING_SPEED = 50;
  private static final int WIDTH_PX = 16;
  private static final int HEIGHT_PX = 16;

  private Direction facing;
  private final Listener listener;

  public Walker(int x, int y, Direction facing, Listener listener) {
    super(Util.scaledRect(x, y, WIDTH_PX, HEIGHT_PX));
    this.facing = facing;
    this.listener = listener != null ? listener : new EmptyListener();
    assert(facing.isHorizontal());
  }

  @Override
  public void moveHorizontal() {
    if (isOnFloor()) {
      setXVelocity(WALKING_SPEED * (facing == Direction.LEFT ? -1 : 1));
    }
    super.moveHorizontal();
  }

  @Override
  public void onBounced(Direction direction, Entity otherEntity) {
    super.onBounced(direction, otherEntity);
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
    listener.onDied();
  }
}
