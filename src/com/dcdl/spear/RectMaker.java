package com.dcdl.spear;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class RectMaker extends MouseAdapter {
  private final List<Rectangle> rects = new ArrayList<Rectangle>();
  private Rectangle currentRect = null;

  public RectMaker() {
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("mouse pressed");
    currentRect = new Rectangle(e.getX(), e.getY(), 0, 0);
    rects.add(currentRect);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    System.out.println("mouse dragged");
    updateCurrentRect(e.getX(), e.getY());
  }

  private void updateCurrentRect(int x, int y) {
    if (currentRect == null) {
      return;
    }

    currentRect.width = Math.abs(x - currentRect.x);
    currentRect.height = Math.abs(y - currentRect.y);

    if (x < currentRect.x) {
      currentRect.x = x;
    }
    if (y < currentRect.y) {
      currentRect.y = y;
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    System.out.println("mouse released");
    updateCurrentRect(e.getX(), e.getY());
    currentRect = null;
  }

  public List<Rectangle> getRects() {
    return rects;
  }
}
