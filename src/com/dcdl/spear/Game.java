package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.dcdl.spear.collision.LinearArena;
import com.dcdl.spear.collision.Arena.Direction;

public class Game implements KeyListener {
  private final Player player;
  private final LinearArena floor;
  private final Stage stage;
  private final Walker walker;

  public Game() {
    player = new Player(0, 240 - 16, 16, 16);
    stage = new Stage();
    floor = new LinearArena();
    floor.addEntity(0, new Entity(new Rectangle(0, 240 * Constants.SCALE,
        320 * Constants.SCALE, 10 * Constants.SCALE))); // The ground.
    walker = new Walker(new Rectangle(200, 10, 16, 16), Direction.LEFT);
  }

  public void tick() {
    player.moveHorizontal();
    floor.collide(player, player.getHorizontalDirection());
    stage.collide(player, player.getHorizontalDirection());
    player.moveVertical();
    floor.collide(player, player.getVerticalDirection());
    stage.collide(player, player.getVerticalDirection());

    walker.moveHorizontal();
    floor.collide(walker, walker.getHorizontalDirection());
    stage.collide(walker, walker.getHorizontalDirection());
    walker.moveVertical();
    floor.collide(walker, walker.getVerticalDirection());
    stage.collide(walker, walker.getVerticalDirection());
  }

  public void render(Graphics g) {
    g.clearRect(0, 0, 320, 240);  // TODO Remove magic numbers.
    stage.render(g);
    walker.render(g);
    player.render(g);
  }

  private void onKey(KeyEvent e, boolean pressed) {
    switch (e.getKeyCode()) {
    case KeyEvent.VK_X:
      player.jump(pressed);
      break;
    case KeyEvent.VK_LEFT:
      player.left(pressed);
      break;
    case KeyEvent.VK_RIGHT:
      player.right(pressed);
      break;
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    onKey(e, true);
  }

  @Override
  public void keyReleased(KeyEvent e) {
    onKey(e, false);
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
