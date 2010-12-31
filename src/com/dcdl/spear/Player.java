package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.CollisionCallback;
import com.dcdl.spear.collision.Arena.CollisionDirection;

public class Player implements CollisionCallback {
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private final Entity entity;

  public Player(int x, int y, int w, int h) {
    entity = new Entity(new Rectangle(x * Constants.SCALE, y * Constants.SCALE, w * Constants.SCALE, h * Constants.SCALE));
  }

  public void moveHorizontal() {
    entity.moveHorizontal();
  }

  public void moveVertical() {
    entity.moveVertical();
  }

  public CollisionDirection getHorizontalDirection() {
    return entity.getHorizontalDirection();
  }

  public CollisionDirection getVerticalDirection() {
    return entity.getVerticalDirection();
  }

  public Rectangle getRect() {
    return entity.getRect();
  }

  public void render(Graphics g) {
    Rectangle rect = entity.getRect();
    g.fillRect(rect.x / Constants.SCALE, rect.y / Constants.SCALE, rect.width / Constants.SCALE, rect.height / Constants.SCALE);
  }

  @Override
  public void onBounced(CollisionDirection collisionDirection) {
    entity.onBounced(collisionDirection);
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
    if (entity.isOnFloor() && pressed) {
      entity.setYVelocity(-2 * Constants.SCALE);
    }
  }

  private void updateXVelocity() {
    int dx = 0;
    if (leftPressed && !rightPressed) {
      dx = -1 * Constants.SCALE;
    }
    if (rightPressed && !leftPressed) {
      dx = 1 * Constants.SCALE;
    }
    entity.setXVelocity(dx);
  }
}
