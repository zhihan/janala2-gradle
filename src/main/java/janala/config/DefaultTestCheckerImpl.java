package janala.config;

public class DefaultTestCheckerImpl implements TestChecker {

  public boolean check(String test) {
    return Config.instance.test.equals(test);
  }
}
