package janala.solvers;

import janala.Main;
import janala.instrument.Coverage;

import java.util.List;
import java.util.LinkedList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class AbstractRefineStrategy extends Strategy {
  @Override
  public int solve(List<Element> history, int historySize, History solver) {
    int oldBeginIndex;
    int beginIndex = oldBeginIndex = findUnsatBeginScopeIndex(history, historySize);
    if (Main.skipPath) {
      beginIndex = -1;
      System.out.println(
          "************************ Skipping path ************************************");
    }
    int endIndex = findMatchingEndScopeIndex(history, historySize, beginIndex);
    int ret;
    if (oldBeginIndex == -1 && !Main.skipPath) {
      Coverage.instance.commitBranches();
      System.out.println("******************* Found a real input. *************************");
      Main.setRealInput(true);
    } else {
      System.out.println(
          "******************* Found an intermediate input.  It should not be used for testing. *************************");
      Main.setRealInput(false);
    }
    while ((ret = searchWithIfPossibleAssert(history, beginIndex, endIndex, historySize, solver))
        == -1) {
      if (beginIndex == -1) {
        return ret;
      }
      beginIndex = findPreviousBeginScopeIndex(history, historySize, beginIndex);
      endIndex = findMatchingEndScopeIndex(history, historySize, beginIndex);
    }
    return ret;
  }

  private int findPreviousBeginScopeIndex(
      List<Element> history, int historySize, int beginScopeIndex) {
    int ret = -1;
    for (int i = 0; i <= beginScopeIndex; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (i == beginScopeIndex) {
          return ret;
        }
        if (((MethodElement) tmp).isBegin) {
          ret = i;
        }
      }
    }
    throw new RuntimeException(
        "Should not reach here beginScopeIndex =" + beginScopeIndex + " history " + history);
  }

  private int findNextBeginScopeIndex(List<Element> history, int start, int end) {
    for (int i = start + 1; i < end; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (((MethodElement) tmp).isBegin) {
          return i;
        } else {
          return -1;
        }
      }
    }
    return -1;
  }

  class RemovalPair {
    int b, e;

    RemovalPair(int b, int e) {
      this.b = b;
      this.e = e;
    }
  }

  private void removeElements(
      List<Element> history, int low, int high, int i, int historySize) {
    int from = low;
    int bi, ei;

    LinkedList<RemovalPair> toBeRemovedRanges = new LinkedList<RemovalPair>();

    while ((bi = findNextBeginScopeIndex(history, from, i)) != -1) {
      ei = findMatchingEndScopeIndex(history, i, bi);
      toBeRemovedRanges.addFirst(new RemovalPair(bi, ei));
      from = ei;
    }
    toBeRemovedRanges.addFirst(new RemovalPair(i, high));

    from = high;
    while ((bi = findNextBeginScopeIndex(history, from, historySize)) != -1) {
      ei = findMatchingEndScopeIndex(history, historySize, bi);
      toBeRemovedRanges.addFirst(new RemovalPair(bi, ei));
      from = ei;
    }
    for (RemovalPair pair : toBeRemovedRanges) {
      for (int j = pair.e - 1; j > pair.b; j--) {
        history.remove(j);
      }
    }
    //        System.out.println(history);
  }

  private int findUnsatBeginScopeIndex(List<Element> history, int historySize) {
    for (int i = 0; i < historySize; i++) {
      Element tmp = history.get(i);
      if (tmp.isInvalidScopeBegin()) {
        return i;
      }
    }
    return -1;
  }

  private int findMatchingEndScopeIndex(
      List<Element> history, int historySize, int beginScopeIndex) {
    int skip = 0;
    for (int i = beginScopeIndex + 1; i < historySize; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (((MethodElement) tmp).isBegin) {
          skip++;
        } else {
          if (skip == 0) {
            return i;
          } else {
            skip--;
          }
        }
      }
    }
    return historySize;
  }

  public int searchWithIfPossibleAssert(
      List<Element> history, int low, int high, int historySize, History solver) {
    int to, from = low, ret;

    for (to = low + 1; to < high; to++) {
      Element tmp = history.get(to);
      BranchElement current;
      if (tmp instanceof BranchElement) {
        current = (BranchElement) tmp;
        if (current.isForceTruth && !current.branch) {
          if ((ret = dfs(history, from, to + 1, high, historySize, solver)) != -1) {
            return ret;
          }
          from = to;
        } else if (current.isForceTruth) {
          from = to;
        }
      }
    }

    return dfs(history, from, to, high, historySize, solver);
  }

  private int dfs(
      List<Element> history, int low, int start, int high, int historySize, History solver) {
    LinkedList<Integer> indices = new LinkedList<Integer>();
    int skip = 0;
    for (int i = start - 1; i > low; i--) {
      Element tmp = history.get(i);
      if (tmp instanceof MethodElement) {
        if (((MethodElement) tmp).isBegin) skip++;
        else skip--;
      }
      if (tmp instanceof BranchElement && skip == 0) {
        indices.addLast(i);
      }
    }
    for (int i : indices) {
      Element tmp = history.get(i);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (!current.getDone() && current.pathConstraintIndex != -1) {
          if (solver.solveAt(low, i)) {
            current.setDone(true);
            current.branch = !current.branch;
            removeElements(history, low, high, i, historySize);
            return Integer.MAX_VALUE;
          }
        }
      }
    }
    return -1;
  }
}
