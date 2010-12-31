package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.CollisionCallback;
import com.dcdl.spear.collision.Arena.CollisionDirection;

public class Player implements CollisionCallback {
  private static final int MAX_FALL_SPEED = 1000;
  private static final int GRAVITY = Constants.SCALE / 10;
  private final Rectangle rect;
  private Point lastMove;
  private Point velocity = new Point(0, 0);
  private boolean leftPressed = false;
  private boolean rightPressed = false;
  private boolean onFloor = false;

  public Player(int x, int y, int w, int h) {
    rect = new Rectangle(x * Constants.SCALE, y * Constants.SCALE, w * Constants.SCALE, h * Constants.SCALE);
  }

  public void moveHorizontal() {
    velocity.x = 0;
    if (leftPressed && !rightPressed) {
      velocity.x = -1 * Constants.SCALE;
    }
    if (rightPressed && !leftPressed) {
      velocity.x = 1 * Constants.SCALE;
    }
    rect.x += velocity.x;
    lastMove = new Point(velocity.x, 0);
  }

  public CollisionDirection getHorizontalDirection() {
    return velocity.x < 0 ? CollisionDirection.LEFT : CollisionDirection.RIGHT;
  }

  public CollisionDirection getVerticalDirection() {
    return velocity.y < 0 ? CollisionDirection.UP : CollisionDirection.DOWN;
  }

  public void moveVertical() {
    velocity.y += GRAVITY;
    velocity.y = Math.min(velocity.y, MAX_FALL_SPEED);
    lastMove = (Point) velocity.clone();
    rect.y += velocity.y;
    lastMove = new Point(0, velocity.y);
    onFloor = false;
  }

  public Point getLastMove() {
    return lastMove;
  }

  public Rectangle getRect() {
    return rect;
  }

  public void render(Graphics g) {
//    System.out.println("render!");
    g.fillRect(rect.x / Constants.SCALE, rect.y / Constants.SCALE, rect.width / Constants.SCALE, rect.height / Constants.SCALE);
  }

  public void hitFloor() {
    velocity.y = 0;
    onFloor = true;
  }

  @Override
  public void onBounced(CollisionDirection collisionDirection) {
    if (collisionDirection == CollisionDirection.UP) {
      hitFloor();
    }
  }

  public void left(boolean pressed) {
    this.leftPressed = pressed;
  }

  public void right(boolean pressed) {
    this.rightPressed = pressed;
  }

  public void jump(boolean pressed) {
    if (onFloor && pressed) {
      velocity = new Point(0, -2 * Constants.SCALE);
      System.out.println("Jump!");
    }
  }
}
