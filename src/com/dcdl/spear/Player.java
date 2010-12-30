package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import com.dcdl.spear.collision.Arena.CollisionCallback;
import com.dcdl.spear.collision.Arena.CollisionDirection;

public class Player implements CollisionCallback {
  private final Rectangle rect;
  private Point lastMove;
  private final Point acceleration = new Point(0, 1);
  private Point velocity = new Point(0, 0);
  private boolean leftPressed = false;
  private boolean rightPressed = false;

  public Player(int x, int y, int w, int h) {
    rect = new Rectangle(x, y, w, h);
  }

  private int clamp(int x, int i) {
    return (int) (Math.signum(x) * Math.min(Math.abs(x), i));
  }

  public void move() {
    velocity.x = 0;
    if (leftPressed && !rightPressed) {
      velocity.x = -1;
    }
    if (rightPressed && !leftPressed) {
      velocity.x = 1;
    }
    velocity.x += acceleration.x;
    velocity.y += acceleration.y;
    velocity.y = Math.min(velocity.y, 10);
    rect.x += velocity.x;
    rect.y += velocity.y;
    lastMove = (Point) velocity.clone();
  }

  public Point getLastMove() {
    return lastMove;
  }

  public Rectangle getRect() {
    return rect;
  }

  public void render(Graphics g) {
//    System.out.println("render!");
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void hitFloor() {
    velocity.y = 0;
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
    velocity = new Point(0, -20);
    System.out.println("Jump!");
  }
}
