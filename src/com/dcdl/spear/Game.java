package com.dcdl.spear;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.dcdl.spear.collision.Arena.Direction;
import com.dcdl.spear.collision.LinearArena;

public class Game implements KeyListener, GameClient {
  private final Player player;
  private final LinearArena floor;
  private final Stage stage;
  private final LinearArena playerArena;
  private final LinearArena enemyArena;
  private final WalkerSpawner walkerSpawner;
  private final LinearArena spearArena;

  public Game() {
    player = new Player(this, 0, 240 - 16);
    stage = new Stage();
    floor = new LinearArena();
    floor.addEntity(new Entity(Util.scaledRect(0, 240, 320, 10)));
    walkerSpawner = new WalkerSpawner(this, 1, new Point(200, 10), Direction.LEFT);

    playerArena = new LinearArena();
    enemyArena = new LinearArena();
    spearArena = new LinearArena();

    playerArena.addEntity(player);
    playerArena.addArena(stage);
    playerArena.addArena(floor);
    playerArena.addArena(spearArena);
    playerArena.addArena(enemyArena);

    enemyArena.addArena(stage);
    enemyArena.addArena(floor);
    enemyArena.addArena(spearArena);

    spearArena.addArena(enemyArena);
    spearArena.addArena(stage);
  }

  public void tick() {
    walkerSpawner.tick();
    enemyArena.tick();
    spearArena.tick();
    playerArena.tick();
  }

  public void render(Graphics g) {
    g.clearRect(0, 0, 320, 240);  // TODO Remove magic numbers.
    stage.render(g);
    enemyArena.render(g);
    spearArena.render(g);
    playerArena.render(g);
  }

  private void onKey(KeyEvent e, boolean pressed) {
    switch (e.getKeyCode()) {
    case KeyEvent.VK_X:
      player.jump(pressed);
      break;
    case KeyEvent.VK_C:
      player.shoot(pressed);
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

  @Override
  public void killWalker(Walker walker) {
    enemyArena.removeEntity(walker);
  }

  @Override
  public void spawnWalkerAt(Point spawnAt, Direction direction) {
    Walker walker = new Walker(this, spawnAt.x, spawnAt.y, direction, walkerSpawner);
    enemyArena.addEntity(walker);
  }

  @Override
  public void killPlayer(Player player) {
    playerArena.removeEntity(player);
  }

  @Override
  public void shootSpear(Point pos, Direction direction) {
    Spear spear = new Spear(pos, direction);
    spearArena.addEntity(spear);
  }
}
