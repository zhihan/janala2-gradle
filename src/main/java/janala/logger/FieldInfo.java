package janala.logger;

import janala.interpreters.ClassDepot;

import java.io.Serializable;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/3/12
 * Time: 5:46 PM
 */
public class FieldInfo implements Serializable {
  String className;
  String fieldName;
  boolean isStatic;

  private int fieldId;

  public FieldInfo(String className, String fieldName, boolean aStatic) {
    this.className = className.replace('/', '.');
    this.fieldName = fieldName;
    isStatic = aStatic;
    fieldId = -1;
  }

  public FieldInfo init() {
    if (fieldId == -1) {
      if (isStatic) {
        fieldId = ClassDepot.instance.getStaticFieldIndex(className, fieldName);
      } else {
        fieldId = ClassDepot.instance.getFieldIndex(className, fieldName);
      }
    }
    return this;
  }

  @Override
  public String toString() {
    return "FieldInfo{"
        + "className='"
        + className
        + '\''
        + ", fieldName='"
        + fieldName
        + '\''
        + ", isStatic="
        + isStatic
        + ", fieldId="
        + fieldId
        + '}';
  }

  public int getFieldId() {
    if (fieldId == -1) {
      if (isStatic) {
        fieldId = ClassDepot.instance.getStaticFieldIndex(className, fieldName);
      } else {
        fieldId = ClassDepot.instance.getFieldIndex(className, fieldName);
      }
    }
    return fieldId;
  }
}
