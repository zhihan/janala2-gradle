package janala.config;

import janala.logger.Logger;
import janala.solvers.Solver;
import janala.solvers.Strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
  // System properties
  public static final String mainClass = System.getProperty("janala.mainClass", null);
  public static final int iteration = Integer.getInteger("janala.iteration", 0);
  public static final String propFile = System.getProperty("janala.conf", "catg.conf");
  
  
  public static final Config instance = new Config();

  public boolean isTest;
  public boolean verbose;
  public boolean printTrace;
  public boolean printConstraints;
  public String analysisClass;
  public String traceFileName;
  public String traceAuxFileName;
  public String history;
  public String coverage;
  public String inputs;
  public String yicesCommand;
  public String formulaFile;
  public String testLog;
  public String cvc3Command;
  public String cvc4Command;
  public String[] excludeList;
  public String[] includeList;
  private String loggerClass;
  private String solver;
  private String strategy;
  public int maxStringLength;
  public int pathId;
  public boolean printFormulaAndSolutions;
  public String scopeBeginMarker;
  public String scopeEndMarker;
  public int scopeBeginSymbol = -1;
  public int scopeEndSymbol = -2;
  public String test;
  public TestChecker testChecker;
  public String oldStates;
  public boolean printHistory;

  public Config() {
    try {
      Properties properties = new Properties();
      properties.load(new FileInputStream(propFile));

      isTest = properties.getProperty("catg.isInternalTestMode", "false").equals("true");
      verbose = properties.getProperty("catg.isVerbose", "false").equals("true");
      printHistory = properties.getProperty("catg.isPrintHistory", "false").equals("true");
      printTrace = properties.getProperty("catg.isPrintTrace", "false").equals("true");
      printConstraints = properties.getProperty("catg.isPrintConstraints", "false").equals("true");
      printFormulaAndSolutions =
          properties.getProperty("catg.isPrintFormulaAndSolutions", "false").equals("true");
      traceFileName = properties.getProperty("catg.traceFile", "trace");
      traceAuxFileName = properties.getProperty("catg.auxTraceFile", "trace.aux");
      history = properties.getProperty("catg.historyFile", "history");
      coverage = properties.getProperty("catg.coverageFile", "coverage.catg");
      inputs = properties.getProperty("catg.inputsFile", "inputs");
      yicesCommand = properties.getProperty("catg.yicesCommand", "yices");
      formulaFile = properties.getProperty("catg.formulaFile", "formula");
      testLog = properties.getProperty("catg.testLogFile", "test.log");
      cvc3Command = properties.getProperty("catg.cvc3Command", "cvc3");
      cvc4Command = properties.getProperty("catg.cvc4Command", "cvc4");
      loggerClass = System.getProperty("janala.loggerClass", "janala.logger.FileLogger");
      analysisClass =
          properties.getProperty("catg.analysisClass", "janala.logger.DJVM").replace('.', '/');
      solver = properties.getProperty("catg.solverClass", "janala.solvers.YicesSolver2");
      strategy = properties.getProperty("catg.strategyClass", "janala.solvers.DFSStrategy");
      excludeList = properties.getProperty("catg.excludeList", "").split(",");
      includeList = properties.getProperty("catg.includeList", "catg.CATG").split(",");
      maxStringLength = Integer.parseInt(properties.getProperty("catg.maxStringLength", "30"));
      pathId = Integer.parseInt(properties.getProperty("catg.pathId", "1"));
      scopeBeginMarker = properties.getProperty("catg.scopeBeginMarker", "begin$$$$");
      scopeEndMarker = properties.getProperty("catg.scopeEndMarker", "end$$$$");

      oldStates = properties.getProperty("catg.oldStatesFile", "oldStates");
      test = System.getProperty("catg.test", properties.getProperty("catg.test", "test"));
      String testCheckingClass =
          System.getProperty(
              "catg.testCheckingClass",
              properties.getProperty(
                  "catg.testCheckingClass", "janala.config.DefaultTestCheckerImpl"));
      testChecker = (TestChecker) loadClass(testCheckingClass);

    } catch (IOException ex) {
      //ex.printStackTrace();
      // If no property file is given, set up the bare minimum
      analysisClass = "janala/logger/DJVM";
      loggerClass = "janala.logger.StringPrintLogger";
      traceFileName = "trace.dat";
      traceAuxFileName = "trace_aux.dat";
    }
  }

  public Object loadClass(String cName) {
    try {
      Class clazz = Class.forName(cName);
      Object ret = clazz.newInstance();
      return ret;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (InstantiationException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  public Logger getLogger() {
    try {
      Class solverClass = Class.forName(loggerClass);
      Logger ret = (Logger) solverClass.newInstance();
      return ret;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (InstantiationException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  public Solver getSolver() {
    try {
      Class solverClass = Class.forName(solver);
      Solver ret = (Solver) solverClass.newInstance();
      return ret;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (InstantiationException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  public Strategy getStrategy() {
    if (strategy == null || strategy.isEmpty()) {
      return null;
    }
    try {
      Class solverClass = Class.forName(strategy);
      Strategy ret = (Strategy) solverClass.newInstance();
      return ret;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (InstantiationException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }
}
