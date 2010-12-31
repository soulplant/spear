package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Entity {
  private boolean leftPressed = false;
  private boolean rightPressed = false;

  public Player(int x, int y, int w, int h) {
    super(new Rectangle(x * Constants.SCALE, y * Constants.SCALE, w * Constants.SCALE, h * Constants.SCALE));
  }

  public void render(Graphics g) {
    Rectangle rect = getRect();
    g.setColor(Color.ORANGE);
    g.fillRect(rect.x / Constants.SCALE, rect.y / Constants.SCALE, rect.width / Constants.SCALE, rect.height / Constants.SCALE);
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
      setYVelocity(-2 * Constants.SCALE);
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
    setXVelocity(dx);
  }
}
