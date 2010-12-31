package com.dcdl.spear;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.dcdl.spear.collision.Arena;
import com.dcdl.spear.collision.LinearArena;

public class Stage implements Arena {
  private class Block {
    private final int id;
    private final int x;
    private final int y;
    private final Rectangle rect;

    public Block(int id, int x, int y, Rectangle rect) {
      this.id = id;
      this.x = x;
      this.y = y;
      this.rect = rect;
    }
  }

  private final LinearArena arena = new LinearArena();
  private final List<Block> blocks = new ArrayList<Block>();
  private int blockId = 0;

  public Stage() {
    addBlock(1, 0);
    addBlock(3, 0);
    addBlock(5, 0);
    addBlock(5, 1);
    addBlock(6, 0);
    addBlock(8, 0);
    addBlock(8, 1);
    addBlock(14, 0);
    for (int x = 0; x < 5; x++) {
      for (int y = 0; y < 2; y++) {
        addBlock(15 + x, y);
      }
    }
  }

  public void render(Graphics g) {
    g.setColor(Color.RED);
    for (Block block : blocks) {
      g.fillRect(block.rect.x / Constants.SCALE,
          block.rect.y / Constants.SCALE, block.rect.width / Constants.SCALE,
          block.rect.height / Constants.SCALE);
    }
  }

  private void addBlock(int x, int y) {
    Rectangle rect = new Rectangle(x * 16 * Constants.SCALE,
        (240 - (y + 1) * 16) * Constants.SCALE, 16 * Constants.SCALE,
        16 * Constants.SCALE);
    Block block = new Block(blockId++, x, y, rect);
    blocks.add(block);
    arena.addEntity(block.id, rect);
  }

  public void collide(Rectangle rect, Direction collisionDirection, CollisionCallback callback) {
    arena.collide(rect, collisionDirection, callback);
  }
}
