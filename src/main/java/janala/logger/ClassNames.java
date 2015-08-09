package janala.logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ClassNames implements Serializable {
  Map<String, Integer> nameToIndex;
  List<ObjectInfo> classList;

  private static ClassNames instance = new ClassNames();
  public static ClassNames getInstance() {
    return instance;
  }

  //VisibleForTesting
  public static void setInstance(ClassNames c) {
    instance = c;
  }

  public int get(String className) {
    if (nameToIndex == null) {
      nameToIndex = new TreeMap<String, Integer>();
    }
    if (classList == null) {
      classList = new ArrayList<ObjectInfo>();
    }
    Integer i = nameToIndex.get(className);
    if (i == null) {
      i = classList.size();
      nameToIndex.put(className, i);
      classList.add(new ObjectInfo(className));
    }
    return i;
  }

  public ObjectInfo get(int i) {
    return classList.get(i);
  }

  public void init() {
    if (classList != null)
      for (ObjectInfo objectInfo : classList) {
        objectInfo.init();
      }
  }

  @Override
  public String toString() {
    return "ClassNames{\n" + "nameToIndex=" + nameToIndex + "\n, classList=" + classList + "\n}";
  }
}
