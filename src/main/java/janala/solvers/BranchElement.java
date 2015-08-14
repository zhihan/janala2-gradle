package janala.solvers;

public class BranchElement extends Element {
  int iid;
  @Override
  public int getIid() { return iid; }
  
  boolean branch;
  
  private boolean done;
  public void setDone(boolean val) { done = val; }
  public boolean getDone() { return done; }

  int pathConstraintIndex; // -1 for no index
  boolean isForceTruth;
  public void setIsForceTruth(boolean val) { isForceTruth = val; }
  public boolean getIsForceTruth() { return isForceTruth; }

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
