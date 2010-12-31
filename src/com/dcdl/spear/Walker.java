package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.Direction;

public class Walker extends Entity {
  private static final int WALKING_SPEED = 50;
  private Direction facing;

  public Walker(Rectangle rect, Direction facing) {
    super(Util.scaleRect(rect, Constants.SCALE));
    this.facing = facing;
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
  public void onBounced(Direction direction) {
    super.onBounced(direction);
    if (direction.isHorizontal()) {
      facing = direction;
      setXVelocity(-getXVelocity());
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.BLACK);
    Rectangle rect = getRect();
    g.fillRect(rect.x / Constants.SCALE, rect.y / Constants.SCALE, rect.width / Constants.SCALE, rect.height / Constants.SCALE);
  }
}
