package com.dcdl.spear;

import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.CollisionCallback;
import com.dcdl.spear.collision.Arena.Direction;

public class Entity implements CollisionCallback {
  private static final int MAX_FALL_SPEED = 1000;
  private static final int GRAVITY = Constants.SCALE / 10;
  private final Point velocity = new Point(0, 0);
  private final Rectangle rect;
  private boolean isOnFloor;

  public Entity(Rectangle rect) {
    this.rect = rect;
  }

  public int getXVelocity() {
    return velocity.x;
  }

  public void setXVelocity(int dx) {
    velocity.x = dx;
  }

  public int getYVelocity() {
    return velocity.y;
  }

  public void setYVelocity(int dy) {
    velocity.y = dy;
  }

  public void moveHorizontal() {
    rect.x += velocity.x;
  }

  public void moveVertical() {
    velocity.y += GRAVITY;
    velocity.y = Math.min(velocity.y, MAX_FALL_SPEED);
    rect.y += velocity.y;
    isOnFloor = false;
  }

  public boolean isOnFloor() {
    return isOnFloor;
  }

  @Override
  public void onBounced(Direction direction, Entity otherEntity) {
    if (direction == Direction.UP) {
      hitFloor();
    }
  }

  public Direction getHorizontalDirection() {
    return velocity.x < 0 ? Direction.LEFT : Direction.RIGHT;
  }

  public Direction getVerticalDirection() {
    return velocity.y < 0 ? Direction.UP : Direction.DOWN;
  }

  private void hitFloor() {
    isOnFloor = true;
    velocity.y = 0;
  }

  public Rectangle getRect() {
    return rect;
  }

  public Rectangle intersection(Entity entity) {
    return getRect().intersection(entity.getRect());
  }

  public void displace(int x, int y) {
    rect.x += x;
    rect.y += y;
  }

  public int getX() {
    return rect.x;
  }

  public int getY() {
    return rect.y;
  }

  public int getWidth() {
    return rect.width;
  }

  public int getHeight() {
    return rect.height;
  }

  public void onGotBouncedIntoBy(Direction bounceDirection, Entity entity) { }
}
