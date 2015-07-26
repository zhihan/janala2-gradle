package janala.config;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */
public class DefaultTestCheckerImpl implements TestChecker {

  public boolean check(String test) {
    return Config.instance.test.equals(test);
  }
}
