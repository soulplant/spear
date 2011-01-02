package com.dcdl.spear;

import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.Stage.Block;
import com.dcdl.spear.collision.Arena.Direction;

public class Spear extends Entity {
  private static final int WIDTH_PX = 14;
  private static final int HEIGHT_PX = 2;
  private static final int SPEED_PPS = 96;

  public Spear(Point position, Direction direction) {
    super(startingRectFromPos(position, direction));
    setGravityEnabled(false);

    setXVelocity(SPEED_PPS * (direction == Direction.LEFT ? -1 : 1));
  }

  private static Rectangle startingRectFromPos(Point position, Direction direction) {
    int x = position.x;
    int y = position.y;
    if (direction.isLeft()) {
      // 'position' refers to the handle end of the spear.
      x -= WIDTH_PX * Constants.SCALE;
    }
    return new Rectangle(x, y, WIDTH_PX * Constants.SCALE, HEIGHT_PX * Constants.SCALE);
  }

  @Override
  public void onBounced(Direction direction, Entity otherEntity) {
    assert(direction.isHorizontal());
    if (otherEntity instanceof Block) {
      bounceOut(direction, otherEntity);
      freeze();
    }
    if (otherEntity instanceof Walker) {
      Walker walker = (Walker) otherEntity;
      walker.die();
    }
  }

  private void freeze() {
    setXVelocity(0);
  }
}
