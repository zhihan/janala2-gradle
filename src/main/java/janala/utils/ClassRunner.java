package janala.utils;

import janala.utils.Annotations.Test;
import java.lang.Class;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

public final class ClassRunner {
  private final Class<?> clazz;

  public ClassRunner(String className) throws ClassNotFoundException {
    clazz = Class.forName(className);
  }

  public void run() throws IllegalAccessException, IllegalArgumentException,
  InvocationTargetException, InstantiationException {
    Method[] methods = clazz.getMethods();
    for (Method method: methods) {
      System.out.println(method.getName());
      Test annotation = method.getAnnotation(Test.class);
      if (annotation != null) {
        new Runner(clazz, method).run();
      }
    }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("Usage java janala.utils.ClassRunner <test-class>");
    }

    ClassRunner runner = new ClassRunner(args[0]);
    runner.run();
  }
}
