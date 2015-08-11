package janala.solvers;

import janala.interpreters.Constraint;
import janala.interpreters.ConstraintVisitor;

import java.util.List;
import java.util.LinkedList;

public interface Solver extends ConstraintVisitor {
  public boolean solve();

  public void setInputs(LinkedList<InputElement> inputs);

  public void setPathConstraint(List<Constraint> pathConstraint);

  public void setPathConstraintIndex(int pathConstraintIndex);
}
