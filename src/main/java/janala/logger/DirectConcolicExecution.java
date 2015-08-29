package janala.logger;

import janala.config.Config;
import janala.interpreters.ConcolicInterpreter;
import janala.logger.inst.*;
import janala.utils.MyLogger;

public class DirectConcolicExecution extends AbstractLogger {
  private Instruction inst, next;

  java.util.logging.Logger tester =
  MyLogger.getTestLogger(Config.mainClass + "." + Config.iteration);
  
  private final ConcolicInterpreter intp;

  public DirectConcolicExecution() {
    this(new ConcolicInterpreter(ClassNames.getInstance()));
  }

  public DirectConcolicExecution(ConcolicInterpreter interpreter) {
    intp = interpreter;
    Runtime.getRuntime().addShutdownHook(new Finisher(this));
  }
  
  private static class Finisher extends Thread {
    private final DirectConcolicExecution parent;

    public Finisher(DirectConcolicExecution parent) {
      this.parent = parent;
    }

    @Override
    public void run() {
      parent.finish();
    }
  }

  public void finish() {
    log(null);
    intp.endExecution();
    MyLogger.checkLog(tester);
  }

  @Override
  protected void log(Instruction insn) {
    if (inst == null) {
      inst = insn;
    } else {
      next = insn;
      intp.setNext(next);
      inst.visit(intp);
      inst = next;
    }
  }
}