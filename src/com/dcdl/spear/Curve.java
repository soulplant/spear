package com.dcdl.spear;

public class Curve {
  private int pos;
  private double timeElapsed; // Measured in seconds.
  private int vel;
  private int acc;
  private int pos2;
  private int vel2;
  private final int acc2;
  private final int fps;

  /**
   * @param pos pixels.
   * @param vel pixels per second.
   * @param acc pixels per second per second.
   */
  public Curve(int pos, int vel, int acc, int fps) {
    this.pos = pos;
    this.vel = vel;
    this.acc = acc;

    this.pos2 = pos;
    this.vel2 = vel;
    this.acc2 = acc;

    this.fps = fps;
  }

  public void adjust(int vel, int acc) {
    pos = getPos();
    this.vel = vel;
    this.acc = acc;
    timeElapsed = 0;
  }

  public int getPos() {
    return (int) Math.round(pos + (vel * timeElapsed) + (acc * timeElapsed * timeElapsed / 2.0));
  }

  public int getPos2() {
    return pos2;
  }

  public void tick() {
    timeElapsed += 1.0 / fps;
  }

  public void tick(int n) {
    timeElapsed += ((double) n) / fps;
  }

  public void tick1() {
    timeElapsed += 1;
    vel2 += acc2;
    pos2 += vel2;
  }
}
