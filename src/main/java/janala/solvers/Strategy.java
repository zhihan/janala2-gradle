package janala.solvers;

import java.util.List;

public abstract class Strategy {
  abstract public int solve(List<Element> history, int historySize, History solver);
}
