package janala.logger;

import janala.interpreters.ClassDepot;

import java.io.Serializable;

public class FieldInfo implements Serializable {
  private final String className;
  private final String fieldName;
  private final boolean isStatic;

  private int fieldId;

  public FieldInfo(String className, String fieldName, boolean aStatic) {
    this.className = className.replace('/', '.');
    this.fieldName = fieldName;
    isStatic = aStatic;
    fieldId = -1;
  }

  public FieldInfo init(ClassDepot classDepot) {
    if (fieldId == -1) {
      if (isStatic) {
        fieldId = classDepot.getStaticFieldIndex(className, fieldName);
      } else {
        fieldId = classDepot.getFieldIndex(className, fieldName);
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
        fieldId = ClassDepot.instance.getStaticFieldIndex(className,fieldName);
      } else {
        fieldId = ClassDepot.instance.getFieldIndex(className,fieldName);
      }
    }
    return fieldId;    
  }
}
