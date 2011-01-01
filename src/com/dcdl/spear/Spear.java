package com.dcdl.spear;

import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.Stage.Block;
import com.dcdl.spear.collision.Arena.Direction;

public class Spear extends Entity {
  private static final int WIDTH_PX = 14;
  private static final int HEIGHT_PX = 2;
  private static final int SPEED_PPS = 48;

  public Spear(Point position, Direction direction) {
    super(new Rectangle(position.x, position.y, WIDTH_PX * Constants.SCALE,
        HEIGHT_PX * Constants.SCALE));
    setGravityEnabled(false);

    setXVelocity(SPEED_PPS * (direction == Direction.LEFT ? -1 : 1));
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
