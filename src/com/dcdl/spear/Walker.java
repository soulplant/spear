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
  private Direction facing;
  private final Listener listener;

  public Walker(Rectangle rect, Direction facing, Listener listener) {
    super(Util.scaleRect(rect, Constants.SCALE));
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
    Rectangle rect = getRect();
    g.fillRect(rect.x / Constants.SCALE, rect.y / Constants.SCALE, rect.width / Constants.SCALE, rect.height / Constants.SCALE);
  }

  public void die() {
    listener.onDied();
  }
}
