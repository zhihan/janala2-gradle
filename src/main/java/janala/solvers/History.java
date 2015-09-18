package janala.solvers;

import janala.Main;
import janala.config.Config;
import janala.interpreters.*;
import janala.utils.FileUtil;
import janala.utils.MyLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A collection of import check points (element) and the corresponding constraints */
public class History {
  private final static Logger logger = MyLogger.getLogger(History.class.getName());
  private final static Logger tester =
      MyLogger.getTestLogger(Config.mainClass + "." + Config.iteration);

  private List<Element> history; // A list of branches or scope begin/end
  public List<Element> getHistory() {
    return history;
  }

  private List<Constraint> pathConstraint; // A list of nonempty constraints.
  public List<Constraint> getPathConstraint() {
    return pathConstraint;
  }

  private int index;  // Always point to the next entry in the current path.
  public void setIndex(int index) { this.index = index; }
  public int getIndex() { return index; }
  
  private final Solver solver;
  private boolean ignore;
  private boolean predictionFailed = false;

  private LinkedList<InputElement> inputs;
  private Strategy strategy = Config.instance.getStrategy();
  private final FileUtil fileUtil;
  
  public History(Solver solver, FileUtil fileUtil) {
    history = new ArrayList<Element>(1024);
    pathConstraint = new ArrayList<Constraint>(1024);
    inputs = new LinkedList<InputElement>();
    index = 0;
    this.solver = solver;
    this.ignore = false;
    this.fileUtil = fileUtil;
  }

  public SymbolicOrValue assumeOrBegin(IntValue arg) {
    Constraint last = this.removeLastBranch();
    boolean res = arg.concrete != 0;
    if (!res && last != null) {
      last = last.not();
    }
    return new SymbolicOrValue(res, new SymbolicOrConstraint(last));
  }

  public SymbolicOrValue assumeOr(IntValue first, SymbolicOrValue second) {
    Constraint last = this.removeLastBranch();
    SymbolicOrValue b2 = second;
    SymbolicOrConstraint tmp;
    boolean res = first.concrete != 0;
    if (!res && last != null) last = last.not();
    tmp = b2.symbolic.OR(last);
    return new SymbolicOrValue(res || b2.concrete, tmp);
  }

  public Value assumeOrEnd(int iid, SymbolicOrValue b) {
    boolean res = b.concrete;
    Constraint c;
    if (!res) c = b.symbolic.not();
    else c = b.symbolic;
    this.checkAndSetBranch(res, c, iid);
    if (b.concrete) {
      this.setLastBranchDone();
    }
    return PlaceHolder.instance;
  }

  public static void createBackTrackHistory(int skipIndex) {
    ObjectInputStream inputStream = null;
    ArrayList<Element> history;
    System.out.println("back up history begin");
    try {
      inputStream = new ObjectInputStream(new FileInputStream(Config.instance.history));
      Object tmp = inputStream.readObject();
      System.out.println("back up history 2");
      if (tmp instanceof ArrayList) {
        history = (ArrayList) tmp;
        ((BranchElement) history.get(skipIndex)).setDone(true);
        System.out.println("back up history 3");
        ObjectOutputStream outputStream;
        try {
          outputStream =
              new ObjectOutputStream(new FileOutputStream(Config.instance.history + ".bak"));
          outputStream.writeObject(history);
          outputStream.close();
          System.out.println("backed up history");

        } catch (IOException e) {
          logger.log(Level.SEVERE, "", e);
          System.out.println("back up history 5" + e);
          e.printStackTrace();
          System.exit(1);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("back up history 4" + e);

    } finally {
      try {
        if (inputStream != null) {
          inputStream.close();
        }
      } catch (IOException ex) {
        logger.log(Level.WARNING, "", ex);
      }
    }
  }

  public static History readHistory(Solver solver) {
    try {
      return readHistory(solver, new FileInputStream(Config.instance.history));
    } catch (Exception ex) {
      logger.log(Level.WARNING, "", ex);
      return new History(solver, new FileUtil());
    }
  }

  public static History readHistory(Solver solver, InputStream is) {
    History ret = new History(solver, new FileUtil());
    
    try {
      ObjectInputStream inputStream = new ObjectInputStream(is);
      try {
        Object tmp = inputStream.readObject();
        if (tmp instanceof ArrayList) {
          ret.history = (ArrayList) tmp;
        }
      } catch (Exception ex) {
        logger.log(Level.WARNING, "", ex);
        inputStream.close();
      }
    } catch (IOException ex) {
        logger.log(Level.WARNING, "", ex);
    }
    ret.print();
    return ret;
  }

  public void print() {
    int i = 0;
    if (Config.instance.printHistory) {
      System.out.println("History");
      System.out.println("-------");
      for (Element e : history) {
        System.out.println(i + ":" + e);
        i++;
      }
    }
  }

  private static boolean isEnd(Element tmp) {
    return tmp instanceof MethodElement && !((MethodElement) tmp).isBegin;
  }

  Stack<MethodElement> scopeStack = new Stack<MethodElement>();
  MethodElement lastScope;
  private int skip = 0;
  public int getSkip() { return skip; }

  private void setInPrefix() {
    if (index < history.size()) {
      Element tmp = history.get(index);
      if (isEnd(tmp)) {
        Main.isInPrefix = false;
      } else {
        Main.isInPrefix = true;
      }
    } else {
      Main.isInPrefix = false;
    }
  }

  public void beginScope(int iid) {
    MethodElement current;
    if (index < history.size()) {
      Element tmp = history.get(index);
      if (isEnd(tmp)) {
        current = new MethodElement(true, iid);
        history.add(index, current);
        skip++;
      } else if (!ignore && (!(tmp instanceof MethodElement) || !((MethodElement) tmp).isBegin)) {
        predictionFailed = true;
        tester.log(Level.INFO, "Prediction failed");
        logger.log(
            Level.WARNING,
            "!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "
                + index
                + " history.size() "
                + history.size());
        logger.log(Level.WARNING, "At old iid " + tmp.getIid() + " at iid " + iid + " beginScope");
        int len = history.size();
        for (int j = len - 1; j >= index; j--) {
          history.remove(j);
        }
        current = new MethodElement(true, iid);
        history.add(current);
      } else {
        current = (MethodElement) tmp;
        current.isValidExpansion = true;
      }
    } else {
      current = new MethodElement(true, iid);
      history.add(current);
    }
    scopeStack.push(current);
    index++;
    setInPrefix();
  }

  public void endScope(int iid) {
    MethodElement current;
    if (index < history.size()) {
      Element tmp = history.get(index);
      if (isEnd(tmp) && skip > 0) {
        current = new MethodElement(false, iid);
        history.add(index, current);
        skip--;
      } else if (!ignore && (!(tmp instanceof MethodElement) || ((MethodElement) tmp).isBegin)) {
        predictionFailed = true;
        tester.log(Level.INFO, "Prediction failed");
        logger.log(
            Level.WARNING,
            "!!!!!!!!!!!!!!!!! Prediction failed !!!!!!!!!!!!!!!!! index "
                + index
                + " history.size() "
                + history.size());
        logger.log(Level.WARNING, "At old iid " + tmp.getIid() + " at iid " + iid + " endScope");
        int len = history.size();
        for (int j = len - 1; j >= index; j--) {
          history.remove(j);
        }
        current = new MethodElement(false, iid);
        history.add(current);
      } else {
        current = (MethodElement) tmp;
      }
    } else {
      current = new MethodElement(false, iid);
      history.add(current);
    }
    lastScope = scopeStack.pop();
    index++;
    setInPrefix();
  }

  public void abstractData(boolean isEqual, int iid) {
    lastScope.isValidExpansion = lastScope.isValidExpansion && isEqual;
  }

  public void checkAndSetBranch(boolean result, Constraint constraint, int iid) {
    BranchElement current;
    if (index < history.size()) {
      Element tmp = history.get(index);
      if (isEnd(tmp) || ignore) {
        current = new BranchElement(result, false, -1, iid);
        history.add(index, current);
      } else if (!ignore
          && (!(tmp instanceof BranchElement) || ((BranchElement) tmp).branch != result)) {
        predictionFailed = true;
        tester.log(Level.INFO, "Prediction failed " + ignore);
        logger.log(
            Level.WARNING,
            "!!!!!!!!!!!!!!!!! Prediction failed (checkAndSetBranch) !!!!!!!!!!!!!!!!! index "
                + index
                + " history.size() "
                + history.size());
        logger.log(
            Level.WARNING,
            "At old iid " + tmp.getIid() + " at iid " + iid + " constraint " + constraint);
        int len = history.size();
        for (int j = len - 1; j >= index; j--) {
          history.remove(j);
        }
        current = new BranchElement(result, false, -1, iid);
        history.add(current);
      } else {
        current = (BranchElement) tmp;
      }
    } else {
      current = new BranchElement(result, false, -1, iid);
      history.add(current);
    }
    if (constraint != null) {
      constraint.iid = iid;
      constraint.index = index;
      pathConstraint.add(constraint);
      current.pathConstraintIndex = pathConstraint.size() - 1;
    } else {
      current.pathConstraintIndex = -1;
    }
    if (ignore) {
      ignore = false;
    }
    index++;
    setInPrefix();
  }

  public void solveAndSave() {
    int i = 0;
    String file = "backtrackFlag";
    if (Config.instance.printConstraints) {
      for (Constraint c : pathConstraint) {
        System.out.println(i + ":" + c);
        i++;
      }
    }
    print();
    if (predictionFailed) {
      System.out.println("***********");
      // backtrack
      fileUtil.moveFile(Config.instance.inputs + ".bak", Config.instance.inputs);
      fileUtil.moveFile(Config.instance.history + ".bak", Config.instance.history);
      fileUtil.touch(file);
    } else {
      if (strategy != null) {
        if ((i = strategy.solve(history, index, this)) >= 0) {
          if (fileUtil.exists(file)) {
            if ((i = strategy.solve(history, history.size(), this)) >= 0) {
              writeHistory(i);
            } else {
              removeHistory();
            }
            fileUtil.remove(file);
          } else {
            writeHistory(i);
          }
        } else {
          removeHistory();
        }
      }
    }
  }

  /** Solve the path constraint using index in pathConstraints. */
  public boolean solveAt(int pathConstraintIndex) {
    solver.setInputs(inputs);
    solver.setPathConstraint(pathConstraint);
    solver.setPathConstraintIndex(pathConstraintIndex);
    for (int i = pathConstraintIndex; i >= 0; i--) {
      pathConstraint.get(i).accept(solver);
    }
    return solver.solve();
  }


  /**
   * Solve the constraints between two indices in history.
   *
   * NOTE, the index is confusing. head is exclusive, n is inclusive
   */
  public boolean solveAt(int head, int n) {
    ArrayList<Constraint> pathConstraint = collectPathConstraints(head, n);
    solver.setInputs(inputs);
    solver.setPathConstraint(pathConstraint);
    solver.setPathConstraintIndex(pathConstraint.size() - 1);
    for (int i = pathConstraint.size() - 1; i >= 0; i--) {
      pathConstraint.get(i).accept(solver);
    }
    return solver.solve();
  }

  private ArrayList<Constraint> collectPathConstraints(int head, int n) {
    ArrayList<Constraint> ret = new ArrayList<Constraint>();
    for (int i = 0; i <= head; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (current.pathConstraintIndex != -1) {
          ret.add(pathConstraint.get(current.pathConstraintIndex));
        }
      }
    }
    int scopeLevel = 0;  // scope level
    for (int i = head + 1; i <= n; i++) {
      Element tmp = history.get(i);
      if (tmp instanceof BranchElement) {
        BranchElement current = (BranchElement) tmp;
        if (scopeLevel == 0 && current.pathConstraintIndex != -1) {
          ret.add(pathConstraint.get(current.pathConstraintIndex));
        }
      } else if (tmp instanceof MethodElement) {
        MethodElement melem = (MethodElement) tmp;
        if (melem.isBegin) {
          scopeLevel++;
        } else {
          scopeLevel--;
        }
      }
    }
    return ret;
  }

  private void removeHistory() {
    File f = new File(Config.instance.history);
    f.delete();
    logger.log(Level.INFO, "Done with search.");
  }

  public void cleanup(int i) {
    if (i != Integer.MAX_VALUE) {
      BranchElement current = (BranchElement) history.get(i);
      // Set the last branch to done.
      current.setDone (true);
      current.branch = !current.branch;

      int len = history.size();
      for (int j = len - 1; j > i; j--) {
        history.remove(j);
      }
    }
  }

  private void writeHistory(int i) {
    cleanup(i);
    fileUtil.moveFile(Config.instance.history, Config.instance.history + ".bak");
    try {
      OutputStream ostream = new FileOutputStream(Config.instance.history);
      writeHistory(ostream);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "", ex);
    }
  }

  public void writeHistory(OutputStream os) {
    ObjectOutputStream outputStream;
    try {
      outputStream = new ObjectOutputStream(os);
      outputStream.writeObject(history);
      outputStream.close();

    } catch (IOException e) {
      logger.log(Level.SEVERE, "", e);
      throw new RuntimeException("fail");
    }
  }

 
  public Constraint removeLastBranch() {
    index--;
    BranchElement current = (BranchElement) history.get(index);
    Constraint ret = null;
    if (current.pathConstraintIndex != -1) {
      ret = pathConstraint.remove(pathConstraint.size() - 1);
    }
    if (index <= history.size() - 1) {
      history.remove(index);
    }
    return ret;
  }

  public Value ignore() {
    ignore = true;
    return PlaceHolder.instance;
  }

  public void addInput(int symbol, Value value) {
    inputs.addLast(new InputElement(symbol, value));
  }

  public void setLastBranchDone() {
    if (index >= 1 && index - 1 < history.size()) {
      ((BranchElement) history.get(index - 1)).setDone(true);
    }
  }

  public void setLastForceTruth() {
    System.out.println("Set Last Force True in");
    if (index >= 1 && index - 1 < history.size()) {
      System.out.println("Set Last Force True set");
      ((BranchElement) history.get(index - 1)).isForceTruth = true;
    }
  }
}
