package janala.solvers;

public class MethodElement extends Element {
  boolean isBegin;
  boolean isAbstracted;
  boolean isValidExpansion;

  public MethodElement(boolean isBegin, int iid) {
    this.isBegin = isBegin;
    this.isAbstracted = true;
    this.isValidExpansion = true;
    this.iid = iid;
  }

  @Override
  public boolean isInvalidScopeBegin() {
    return isBegin && !isValidExpansion;
  }

  @Override
  public String toString() {
    return "MethodElement{"
        + "isBegin="
        + isBegin
        + ", isAbstracted="
        + isAbstracted
        + ", isValidExpansion="
        + isValidExpansion
        + ", iid="
        + iid
        + '}';
  }
}
