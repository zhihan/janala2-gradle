package example;

import janala.logger.DJVM;
import janala.logger.DirectConcolicExecution;
import janala.instrument.Coverage;

public class IntExample1 {
    
    private static int greaterThanZero(int x) {
      DJVM.ILOAD(0, 0, 0);
      DJVM.IFLE(1, 0, 6);
      if (x > 0) {
        DJVM.SPECIAL(1); // True branch
        DJVM.ICONST_1(2, 0);
        DJVM.IRETURN(4, 0);
        return 1;
      }
      DJVM.ICONST_M1(5, 0);
      DJVM.IRETURN(6, 0);
      return -1;
    }

    public static void main(String[] args) {
      DJVM.setInterpreter(new DirectConcolicExecution());
      DJVM.ICONST_1(1, 0);
      DJVM.INVOKESTATIC(2, 0, "example/IntExample1", "greaterThanZero", "(I)I");
      int x = greaterThanZero(1);
      if (x == 1) {
        System.out.println("Greater");
      }
    }

}