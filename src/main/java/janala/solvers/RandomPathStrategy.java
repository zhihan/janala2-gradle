package janala.solvers;

import janala.utils.MyLogger;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomPathStrategy extends Strategy {
  Random rand = new Random();
  public final static int BOUND = 2;
  private final static Logger logger = MyLogger.getLogger(RandomPathStrategy.class.getName());

  @Override
  public int solve(ArrayList<Element> history, int historySize, History solver) {
    int begin = -1;
    for (int j = historySize - 1; j >= 0; j--) {
      Element tmp = history.get(j);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (current.done) {
          begin = j;
          break;
        }
      }
    }
    begin = (begin + 1) % historySize;

    int repeat = 0;
    for (int i = begin; true; i = ((i + 1) % historySize)) {
      if (i == 0) {
        if (repeat > BOUND) {
          logger.log(Level.INFO, "Ending random search");
          return -1;
        }
        repeat++;
      }
      if (rand.nextBoolean()) {
        Element tmp = history.get(i);
        if (tmp instanceof BranchElement) {
          BranchElement current = (BranchElement) tmp;
          if (current.pathConstraintIndex != -1) {
            if (solver.solveAt(current.pathConstraintIndex)) {
              return i;
            }
          }
        }
      }
    }
    //return -1;
  }
}
