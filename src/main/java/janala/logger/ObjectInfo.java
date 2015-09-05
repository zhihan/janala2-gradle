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

  private final ClassDepot classDepot;
  public ObjectInfo(String className, ClassDepot classDepot) {
    this.className = className.replace('/', '.');
    nFields = -1;
    this.classDepot = classDepot;
  }

  private int get(String fieldName,
      boolean isStatic,
      Map<String, Integer> fieldNameToIndex,
      ArrayList<FieldInfo> fieldList) {
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
      fieldList.add(new FieldInfo(className, fieldName, isStatic, classDepot));
    }
    return i;
  }

  private Map<String, Integer> createMap(Map<String, Integer> fieldNameToIndex) {
     if (fieldNameToIndex == null) {
       return new TreeMap<String, Integer>();
     } 
     return fieldNameToIndex;
  }

  public int getIdx(String fieldName, boolean isStatic) {
    if (isStatic) {
      staticFieldNameToIndex = createMap(staticFieldNameToIndex);
      return get(fieldName, isStatic, staticFieldNameToIndex, staticFieldList);
    }
    fieldNameToIndex = createMap(fieldNameToIndex);
    return get(fieldName, isStatic, fieldNameToIndex, fieldList);
  }

  public FieldInfo get(int i, boolean isStatic) {
    if (isStatic) {
      return staticFieldList.get(i);
    }
    return fieldList.get(i);
  }

  public Value getStaticField(int fieldId) {
    initialize();
    Value v = statics[fieldId];
    if (v == null) {
      return PlaceHolder.instance;
    }
    return v;
  }

  public void setStaticField(int fieldId, Value value) {
    initialize();
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

  private void initialize() {
    if (nFields == -1) {
      nFields = classDepot.nFields(className);
      nStaticFields = classDepot.nStaticFields(className);
      statics = new Value[nStaticFields];
    }
  }

  public int getNFields() {
    initialize();
    return nFields;
  }
}
