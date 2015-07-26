package janala.solvers;

public class BranchElement extends Element {
  boolean branch;
  boolean done;
  int pathConstraintIndex; // -1 for no index
  boolean isForceTruth;

  public BranchElement(boolean branch, boolean done, int pathConstraintIndex, int iid) {
    this.branch = branch;
    this.done = done;
    this.pathConstraintIndex = pathConstraintIndex;
    this.iid = iid;
    this.isForceTruth = false;
  }

  @Override
  public String toString() {
    return "BranchElement{"
        + "branch="
        + branch
        + ", done="
        + done
        + ", pathConstraintIndex="
        + pathConstraintIndex
        + ", iid="
        + iid
        + ", isForceTruth="
        + isForceTruth
        + '}';
  }
}
