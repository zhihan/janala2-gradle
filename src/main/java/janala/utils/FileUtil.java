package janala.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
  public static void moveFile(String src, String dst) {
    File sf1 = new File(src);
    File df1 = new File(dst);
    df1.delete();
    df1 = new File(dst);
    sf1.renameTo(df1);
  }

  public static void touch(String src) {
    File file = new File(src);
    if (!file.exists())
      try {
        new FileOutputStream(file).close();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
  }

  public static boolean exists(String src) {
    File file = new File(src);
    return file.exists();
  }

  public static void remove(String src) {
    File file = new File(src);
    file.delete();
  }
}
