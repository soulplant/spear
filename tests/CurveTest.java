import junit.framework.TestCase;

import com.dcdl.spear.Curve;


public class CurveTest extends TestCase {
  public void testStuff() {
    Curve curve60 = new Curve(0, -50, 10, 60);
    Curve curve25 = new Curve(0, -50, 10, 25);

    for (int i = 0; i < 10; i++) {
      assertEquals(curve60.getPos(), curve25.getPos());
//      System.out.println(curve60.getPos() + " " + curve25.getPos());

      for (int j = 0; j < 60; j++) {
        curve60.tick();
      }
      for (int j = 0; j < 25; j++) {
        curve25.tick();
      }
    }
  }
}
