package janala.solvers;

import janala.interpreters.Constraint;
import janala.interpreters.ConstraintVisitor;

import java.util.ArrayList;
import java.util.LinkedList;

public interface Solver extends ConstraintVisitor {
  public boolean solve();

  public void setInputs(LinkedList<InputElement> inputs);

  public void setPathConstraint(ArrayList<Constraint> pathConstraint);

  public void setPathConstraintIndex(int pathConstraintIndex);
}
