package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Player {
  private enum State {
    NEUTRAL,
    FALLING,
    JUMPING
  }
  private final Rectangle rect;
  private Point lastMove;
  private final State state = State.NEUTRAL;
  private final Point acceleration = new Point(0, 1);
  private Point velocity = new Point(0, 0);

  public Player(int x, int y, int w, int h) {
    rect = new Rectangle(x, y, w, h);
  }

  private int clamp(int x, int i) {
    return (int) (Math.signum(x) * Math.min(x, i));
  }

  public void move() {
    System.out.println("velocity = " + velocity);
    velocity.x += acceleration.x;
    velocity.y += acceleration.y;
//    velocity.y = clamp(velocity.y, 10);
//    velocity.x = clamp(velocity.x, 10);
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
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }

  public void jump() {
    velocity = new Point(0, -20);
    System.out.println("Jump!");
  }

  public void hitFloor() {
    velocity.y = 0;
  }
}
