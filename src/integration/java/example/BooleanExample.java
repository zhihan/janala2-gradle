package example;

import janala.utils.Annotations.Test;

public class BooleanExample {
  public int and(boolean x, boolean y) {
    if (x && y) {
      return 1;
    } else {
      return 0;
    }
  }

  @Test
  public void testAnd() {
    int x = and(true, true);
    if (x == 1) {
      System.out.println("Pass");
    } else {
      System.out.println("Fail");
    }

    x = and(false, true);
    if (x == 0) {
      System.out.println("Pass");
    } else {
      System.out.println("Fail");
    }
  }

  public int or(boolean x, boolean y) {
    if (x || y) {
      return 1;
    } else {
      return 0;
    }
  }

  @Test
  public void testOr() {
    int x = or(false, true);
    if (x == 1) {
      System.out.println("Pass");
    } else {
      System.out.println("Fail");
    }

    x = or(false, false);
    if (x == 0) {
      System.out.println("Pass");
    } else {
      System.out.println("Fail");
    }
  }
}
