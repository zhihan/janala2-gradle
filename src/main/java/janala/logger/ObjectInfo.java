package janala.logger;

import janala.interpreters.ClassDepot;
import janala.interpreters.PlaceHolder;
import janala.interpreters.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/** 
 * Containing the static info of a class and dynamic values of the
 * static fields. 
 */
public class ObjectInfo implements Serializable {
  Map<String, Integer> fieldNameToIndex;
  ArrayList<FieldInfo> fieldList;

  Map<String, Integer> staticFieldNameToIndex;
  ArrayList<FieldInfo> staticFieldList;

  int nStaticFields;
  private int nFields;
  public Value[] statics;

  private String className;
  public String getClassName() {
    return className;
  }

  public ObjectInfo(String className) {
    this.className = className.replace('/', '.');
    nFields = -1;
  }

  private int get(
      String fieldName,
      boolean isStatic,
      Map<String, Integer> fieldNameToIndex,
      ArrayList<FieldInfo> fieldList) {
     if (fieldNameToIndex == null) {
      fieldNameToIndex = new TreeMap<String, Integer>();
      if (isStatic) {
        this.staticFieldNameToIndex = fieldNameToIndex;
      } else {
        this.fieldNameToIndex = fieldNameToIndex;
      }
    }
    if (fieldList == null) {
      fieldList = new ArrayList<FieldInfo>();
      if (isStatic) {
        this.staticFieldList = fieldList;
      } else {
        this.fieldList = fieldList;
      }
    }
    Integer i = fieldNameToIndex.get(fieldName);
    if (i == null) {
      i = fieldList.size();
      fieldNameToIndex.put(fieldName, i);
      fieldList.add(new FieldInfo(className, fieldName, isStatic));
    }
    return i;
  }

  public int getIdx(String fieldName, boolean isStatic) {
    if (isStatic) {
      return get(fieldName, isStatic, staticFieldNameToIndex, staticFieldList);
    }
    return get(fieldName, isStatic, fieldNameToIndex, fieldList);
  }

  public FieldInfo get(int i, boolean isStatic) {
    if (isStatic) {
      return staticFieldList.get(i);
    }
    return fieldList.get(i);
  }

  public ObjectInfo init() {
    return init(ClassDepot.getInstance());
  }

  public ObjectInfo init(ClassDepot classDepot) {
    initialize(classDepot);
    if (fieldList != null) {
      for (FieldInfo fieldInfo : fieldList) {
        fieldInfo.init(classDepot);
      }
    }
    if (staticFieldList != null) {
      for (FieldInfo fieldInfo : staticFieldList) {
        fieldInfo.init(classDepot);
      }
    }
    return this;
  }

  public Value getStaticField(int fieldId) {
    initialize(ClassDepot.getInstance());
    Value v = statics[fieldId];
    if (v == null) {
      return PlaceHolder.instance;
    }
    return v;
  }

  public void setStaticField(int fieldId, Value value) {
    initialize(ClassDepot.getInstance());
    statics[fieldId] = value;
  }

  @Override
  public String toString() {
    return "ObjectInfo{"
        + "fieldNameToIndex="
        + fieldNameToIndex
        + ", fieldList="
        + fieldList
        + ", staticFieldNameToIndex="
        + staticFieldNameToIndex
        + ", staticFieldList="
        + staticFieldList
        + ", nStaticFields="
        + nStaticFields
        + ", nFields="
        + nFields
        + ", statics="
        + (statics == null ? null : Arrays.asList(statics))
        + ", className='"
        + className
        + '\''
        + '}';
  }

  private void initialize(ClassDepot classDepot) {
    if (nFields == -1) {
      nFields = classDepot.nFields(className);
      nStaticFields = classDepot.nStaticFields(className);
      statics = new Value[nStaticFields];
    }
  }

  public int getNFields() {
    return getNFields(ClassDepot.getInstance());
  }

  public int getNFields(ClassDepot classDepot) {
    initialize(classDepot);
    return nFields;
  }
}
