package com.dcdl.spear;

import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.CollisionCallback;
import com.dcdl.spear.collision.Arena.CollisionDirection;

public class Entity implements CollisionCallback {
  private static final int MAX_FALL_SPEED = 1000;
  private int gravity = Constants.SCALE / 10;
  private final Point velocity = new Point(0, 0);
  private final Rectangle rect;
  private boolean isOnFloor;

  public Entity(Rectangle rect) {
    this.rect = rect;
  }

  public void setGravity(int gravity) {
    this.gravity = gravity;
  }

  public void setXVelocity(int dx) {
    velocity.x = dx;
  }

  public void setYVelocity(int dy) {
    velocity.y = dy;
  }

  public void moveHorizontal() {
    rect.x += velocity.x;
  }

  public void moveVertical() {
    velocity.y += gravity;
    velocity.y = Math.min(velocity.y, MAX_FALL_SPEED);
    rect.y += velocity.y;
    isOnFloor = false;
  }

  public boolean isOnFloor() {
    return isOnFloor;
  }

  @Override
  public void onBounced(CollisionDirection collisionDirection) {
    if (collisionDirection == CollisionDirection.UP) {
      hitFloor();
    }
  }

  public CollisionDirection getHorizontalDirection() {
    return velocity.x < 0 ? CollisionDirection.LEFT : CollisionDirection.RIGHT;
  }

  public CollisionDirection getVerticalDirection() {
    return velocity.y < 0 ? CollisionDirection.UP : CollisionDirection.DOWN;
  }

  private void hitFloor() {
    isOnFloor = true;
    velocity.y = 0;
  }

  public Rectangle getRect() {
    return rect;
  }
}
