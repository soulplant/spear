package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.Direction;

public class Player extends Entity {
  private static final int WIDTH_PX = 16;
  private static final int HEIGHT_PX = 16;
  private static final int WALK_SPEED = 50;
  private static final int JUMP_SPEED = 200;
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private final GameClient client;

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
    updateXVelocity();
  }

  public void right(boolean pressed) {
    this.rightPressed = pressed;
    updateXVelocity();
  }

  public void jump(boolean pressed) {
    if (isOnFloor() && pressed) {
      setYVelocity(-JUMP_SPEED);
    }
  }

  @Override
  public void onBounced(Direction direction, Entity otherEntity) {
    super.onBounced(direction, otherEntity);
    if (otherEntity instanceof Walker) {
      if (direction == Direction.UP) {
        Walker walker = (Walker) otherEntity;
        setYVelocity(-JUMP_SPEED);
        walker.die();
        client.killWalker(walker);
      } else {
        client.killPlayer(this);
      }
    }
  }

  private void updateXVelocity() {
    int dx = 0;
    if (leftPressed && !rightPressed) {
      dx = -WALK_SPEED;
    }
    if (rightPressed && !leftPressed) {
      dx = WALK_SPEED;
    }
    setXVelocity(dx);
  }
}
