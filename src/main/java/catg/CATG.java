package catg;

import janala.Main;
import janala.config.Config;
import janala.interpreters.OrValue;

import java.io.Serializable;

public class CATG {
  /** */
  public static int abstractInt(int value) {
    int inputValue = readInt(value);
    Main.abstractEqualsConcrete(Main.compare(inputValue, value));
    return inputValue;
  }

  public static boolean abstractBool(String test, boolean x) {
      boolean y = readBool(x);
      Main.abstractEqualsConcrete(Main.compare(y, x));
      return y;
  }

  public static long abstractLong(String test, long x) {
      long y = readLong(x);
      Main.abstractEqualsConcrete(Main.compare(y, x));
      return y;
  }

  public static char abstractChar(String test, char x) {
      char y = readChar(x);
      Main.abstractEqualsConcrete(Main.compare(y, x));
      return y;
  }

  public static byte abstractByte(String test, byte x) {
      byte y = readByte(x);
      Main.abstractEqualsConcrete(Main.compare(y, x));
      return y;
  }

  public static short abstractShort(String test, short x) {
      short y = readShort(x);
      Main.abstractEqualsConcrete(Main.compare(y, x));
      return y;
  }

  public static String abstractString(String test, String x) {
      String y = readString(x);
      Main.abstractEqualsConcrete(Main.compare(y, x));
      return y;
  }

  public static int[] readIntArray(int length, int x) {
    int[] ret = new int[length];
    for (int i = 0; i < length; i++) {
      ret[i] = readInt(x);
    }
    return ret;
  }

  public static int readInt(int x) {
    int y = Main.readInt(x);
    Main.MakeSymbolic(y);
    Main.assume(y <= Integer.MAX_VALUE ? 1 : 0);
    Main.assume(y >= Integer.MIN_VALUE ? 1 : 0);
    return y;
  }

  public static long readLong(long x) {
    long y = Main.readLong(x);
    Main.MakeSymbolic(y);
    Main.assume(y <= Long.MAX_VALUE ? 1 : 0);
    Main.assume(y >= Long.MIN_VALUE + 1 ? 1 : 0);
    return y;
  }

  public static char readChar(char x) {
    char y = Main.readChar(x);
    Main.MakeSymbolic(y);
    Main.assume(y <= Character.MAX_VALUE ? 1 : 0);
    Main.assume(y >= Character.MIN_VALUE ? 1 : 0);
    return y;
  }

  public static byte readByte(byte x) {
    byte y = Main.readByte(x);
    Main.MakeSymbolic(y);
    Main.assume(y <= Byte.MAX_VALUE ? 1 : 0);
    Main.assume(y >= Byte.MIN_VALUE ? 1 : 0);
    return y;
  }

  public static short readShort(short x) {
    short y = Main.readShort(x);
    Main.MakeSymbolic(y);
    Main.assume(y <= Short.MAX_VALUE ? 1 : 0);
    Main.assume(y >= Short.MIN_VALUE ? 1 : 0);
    return y;
  }

  public static boolean readBool(boolean x) {
    boolean y = Main.readBool(x);
    Main.MakeSymbolic(y);
    OrValue tmp;
    Main.ignore();
    tmp = Main.assumeOrBegin(y == true ? 1 : 0);
    Main.ignore();
    tmp = Main.assumeOr(!y ? 1 : 0, tmp);
    if (tmp != null) {
      Main.assumeOrEnd(tmp);
    }
    return y;
  }

  public static String readString(String x) {
    String y = Main.readString(x);
    Main.MakeSymbolic(y);
    return y;
  }

  public static boolean assertIfPossible(int pathId, boolean predicate) {
    if (pathId == Config.instance.pathId) {
      Main.forceTruth(predicate ? 1 : 0);
    }
    return predicate;
  }

  public static void BeginScope() {
    Main.beginScope();
  }

  public static void EndScope() {
    Main.endScope();
  }

  public static void event(String eventName) {
    Main.event(eventName);
  }

  public static void pathRegex(String test, String regex) {
    Main.pathRegex(regex);
  }

  public static void equivalent(String test, String location, Serializable value) {
    Main.equivalent(location, value);
  }

  public static void skipPath() {
    Main.skipPath();
  }
}
