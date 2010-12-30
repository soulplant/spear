package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.dcdl.spear.collision.LinearArena;

public class Game implements KeyListener {
  private final Player player;
  private final LinearArena floor;

  public Game() {
    player = new Player(0, 240 - 16, 16, 16);
    floor = new LinearArena();
    floor.addEntity(0, new Rectangle(0, 240, 320, 10)); // The ground.
  }

  public void tick() {
    player.move();
    floor.collide(player.getRect(), player.getLastMove(), player);
//    System.out.println("Now player is at: " + player.getRect());
  }

  public void render(Graphics g) {
    g.clearRect(0, 0, 320, 240);  // TODO Remove magic numbers.
    player.render(g);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    player.jump();
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub

  }
}
