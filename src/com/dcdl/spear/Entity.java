package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.CollisionCallback;
import com.dcdl.spear.collision.Arena.Direction;

public class Entity implements CollisionCallback {
  private static final int MAX_FALL_SPEED_PPS = 120;
  private static final int MAX_FALL_SPEED = Util.pps2cppf(MAX_FALL_SPEED_PPS);
  private static final int GRAVITY_PPS = 6;
  private static final int GRAVITY = Util.pps2cppf(GRAVITY_PPS);
  private final Point velocity = new Point(0, 0);
  private final Rectangle rect;
  private boolean isOnFloor;
  private boolean wasOnFloor = false;

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
      if (!wasOnFloor) {
        onHitFloor();
      }
    }
  }

  protected void onHitFloor() { }

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

  public void onGotBouncedIntoBy(Entity entity, Direction bounceDirection) { }

  public void moveOutOf(Entity otherEntity, Direction direction) {
    switch (direction) {
    case UP:
    case DOWN:
      displace(0, -getDisplacement(otherEntity, direction));
      break;
    case LEFT:
    case RIGHT:
      displace(-getDisplacement(otherEntity, direction), 0);
      break;
    case NONE:
      // Do nothing.
      break;
    }
  }

  /**
   * @returns how many units we need to move in the specified direction to no
   *          longer be overlapping with the given {@link Entity}.
   */
  private int getDisplacement(Entity stationary, Direction direction) {
    switch (direction) {
    case UP:
      return (this.getY() + this.getHeight()) - stationary.getY();
    case DOWN:
      return - ((stationary.getY() + stationary.getHeight()) - this.getY());
    case LEFT:
      return (this.getX() + this.getWidth()) - stationary.getX();
    case RIGHT:
      return - ((stationary.getX() + stationary.getWidth()) - this.getX());
    }
    return 0;
  }

  public void render(Graphics g) {
    g.setColor(Color.WHITE);
    Rectangle rect = Util.scaleRect(getRect(), 1.0 / Constants.SCALE);
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void tick() {
    wasOnFloor = isOnFloor();
  }
}
