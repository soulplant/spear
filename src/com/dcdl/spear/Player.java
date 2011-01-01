package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.Direction;

public class Player extends Entity {
  private static final int WIDTH_PX = 16;
  private static final int HEIGHT_PX = 16;
  private static final int WALK_SPEED_PPS = 60;
  private static final int WALK_SPEED = Util.pps2cppf(WALK_SPEED_PPS);
  private static final int JUMP_SPEED_PPS = 140;
  private static final int JUMP_SPEED = Util.pps2cppf(JUMP_SPEED_PPS);
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private final GameClient client;
  private Direction facing = Direction.RIGHT;

  private static final int FRICTION_PPS = 3;
  private static final int FRICTION = Util.pps2cppf(FRICTION_PPS);
  private static final int WALKING_ACC_PPS = 3;
  private static final int WALKING_ACC = Util.pps2cppf(WALKING_ACC_PPS);

  public Player(GameClient client, int x, int y) {
    super(Util.scaleRect(new Rectangle(x, y, WIDTH_PX, HEIGHT_PX), Constants.SCALE));
    this.client = client;
  }

  @Override
  public void render(Graphics g) {
    Rectangle rect = Util.scaleRect(getRect(), 1.0 / Constants.SCALE);
    g.setColor(Color.ORANGE);
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void left(boolean pressed) {
    this.leftPressed = pressed;
  }

  public void right(boolean pressed) {
    this.rightPressed = pressed;
  }

  public void jump(boolean pressed) {
    if (isOnFloor() && pressed) {
      setYVelocity(-JUMP_SPEED);
    }
  }

  @Override
  public void onBounced(Direction direction, Entity otherEntity) {
    if (otherEntity instanceof Spear) {
      if (direction == Direction.UP && !isOnFloor() && getDisplacement(otherEntity, direction) < 2 * Constants.SCALE) {
        bounceOut(direction, otherEntity);
      }
      return;
    }
    super.onBounced(direction, otherEntity);
    if (otherEntity instanceof Walker) {
      if (direction == Direction.UP) {
        Walker walker = (Walker) otherEntity;
        setYVelocity(-JUMP_SPEED);
        walker.die();
      } else {
        client.killPlayer(this);
      }
    }
  }

  @Override
  public void tick() {
    updateXVelocity();
  }

  private void updateXVelocity() {
    int dx = getXVelocity();
    if (leftPressed && !rightPressed) {
      dx -= WALKING_ACC * (dx > 0 ? 2 : 1);
      facing = Direction.LEFT;
    }
    if (rightPressed && !leftPressed) {
      dx += WALKING_ACC * (dx < 0 ? 2 : 1);
      facing = Direction.RIGHT;
    }
    if (!leftPressed && !rightPressed) {
      dx = Util.shrink(dx, Util.pps2cppf(FRICTION));
    }
    dx = Util.clampAbs(dx, WALK_SPEED);
    setXVelocity(dx);
  }

  public void shoot(boolean pressed) {
    if (pressed) {
      int x = facing.isLeft() ? getLeftSide() - 2 * Constants.SCALE : getRightSide() + 2 * Constants.SCALE;
      Point spearPos = new Point(x, getY() + 2 * Constants.SCALE);
      client.shootSpear(spearPos, facing);
    }
  }
}
