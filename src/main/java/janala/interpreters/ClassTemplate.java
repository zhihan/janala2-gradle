package janala.interpreters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClassTemplate {
  private final LinkedList<String> fields;
  private final LinkedList<String> staticFields;

  private void populateAllFields(Class c) {
    for (Field field: c.getDeclaredFields()) {
      if (!Modifier.isStatic(field.getModifiers())) {
        addField(field.getName());
      } else {
        addStaticField(field.getName());
      }
    }
  }

  public ClassTemplate(Class c) {
    fields = new LinkedList<String>();
    staticFields = new LinkedList<String>();
    populateAllFields(c);
  }

  private void addField(String name) {
    fields.add(name);
   }

  public void addFields(ClassTemplate pt) {
    fields.addAll(pt.fields);
    staticFields.addAll(pt.staticFields);
  }

  private void addStaticField(String name) {
    staticFields.add(name);
  }

  public int getFieldIndex(String name) {
    return fields.indexOf(name);
  }

  public int getStaticFieldIndex(String name) {
    return staticFields.indexOf(name);
  }

  public int nFields() {
    return fields.size();
  }

  public int nStaticFields() {
    return staticFields.size();
  }
}
