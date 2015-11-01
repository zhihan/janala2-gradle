package janala.interpreters;

import janala.utils.MyLogger;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClassDepot implements Serializable {
  private static final long serialVersionUID = 1;

  private final Map<String, ClassTemplate> templates;

  public static ClassDepot instance = new ClassDepot();
  
  public static void setInstance(ClassDepot newInstance) {
    instance = newInstance;
  }
  
  public static ClassDepot getInstance() {
    return instance;
  }

  private static final Logger logger = MyLogger.getLogger(ClassDepot.class.getName());

  // VisibleForTesting
  public ClassDepot() {
    templates = new TreeMap<String, ClassTemplate>();
  }

  private ClassTemplate getOrCreateTemplate(String className, Class<?> clazz) {
    ClassTemplate ct = templates.get(className);
    if (ct != null) {
      return ct;
    }
    ct = new ClassTemplate(clazz);
    templates.put(className, ct);
    Class<?> parent = clazz.getSuperclass();
    if (parent != null) {
      ClassTemplate pt = getOrCreateTemplate(parent.getName(), parent);
      ct.addFields(pt);
    }
    return ct;
  }

  public int getFieldIndex(String className, String field) {
    try {
      Class<?> clazz = Class.forName(className);
      ClassTemplate ct = getOrCreateTemplate(className, clazz);
      return ct.getFieldIndex(field);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "", e);
      return -1;
    }
  }

  public int getStaticFieldIndex(String className, String field) {
    try {
      Class<?> clazz = Class.forName(className);
      ClassTemplate ct = getOrCreateTemplate(className, clazz);
      return ct.getStaticFieldIndex(field);
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "", e);
      return -1;
    }
  }

  public int numFields(String className) {
    try {
      Class<?> clazz = Class.forName(className);
      ClassTemplate ct = getOrCreateTemplate(className, clazz);
      return ct.nFields();
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "Class not found", e);
      return -1;
    }
  }

  public int numStaticFields(String className) {
    try {
      Class<?> clazz = Class.forName(className);
      ClassTemplate ct = getOrCreateTemplate(className, clazz);
      return ct.nStaticFields();
    } catch (ClassNotFoundException e) {
      logger.log(Level.SEVERE, "", e);
      return -1;
    }
  }
}
