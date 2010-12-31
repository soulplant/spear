package com.dcdl.spear;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import com.dcdl.swingutils.App;
import com.dcdl.swingutils.AppRunner;

public class Main {
  public static void main(String[] args) {
    App myApp = new App() {
      private final Game game = new Game();

      @Override
      public KeyListener getKeyListener() {
        return game;
      }

      @Override
      public Dimension getPreferredSize() {
        return new Dimension(320, 240);
      }

      @Override
      public String getTitle() {
        return "Spear";
      }

      @Override
      public void render(Graphics g) {
        game.render(g);
      }

      @Override
      public void setAppListener(Listener listener) {

      }

      @Override
      public void tick() {
        game.tick();
      }

      @Override
      public MouseListener getMouseListener() {
        return new MouseAdapter() {};
      }

      @Override
      public MouseMotionListener getMouseMotionListener() {
        return new MouseMotionAdapter() {};
      }
    };
    AppRunner.runApp(myApp, Constants.FPS);
  }
}
