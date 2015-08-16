package janala.interpreters;

import janala.solvers.History;

public class ObjectValue extends Value {
  final public static ObjectValue NULL = new ObjectValue(0, 0);
  
  private Value[] fields;
  public Value[] getFields() {
    return fields;
  }

  SymbolicObject symbolic;
  int address; // address 0 is null, address -1 is uninitialized address

  @Override
  public Object getConcrete() { // (TODO): confusing
    return address;
  }

  public ObjectValue(int nFields) {
    fields = new Value[nFields];
    address = -1;
  }

  // Initialize 
  // TODO: why is the first arg there?
  public ObjectValue(int unused, int v) {
    fields = null;
    address = v;
  }

  public ObjectValue(ObjectValue ov, SymbolicObject sym) {
    fields = ov.getFields();
    address = ov.address;
    symbolic = sym;
  }

  public IntValue IF_ACMPEQ(ObjectValue o2) {
    boolean result = this == o2;
    return new IntValue(result ? 1 : 0);
  }

  public IntValue IF_ACMPNE(ObjectValue o2) {
    boolean result = this != o2;
    return new IntValue(result ? 1 : 0);
  }

  public IntValue IFNULL() {
    boolean result = this.address == 0;
    return new IntValue(result ? 1 : 0);
  }

  public IntValue IFNONNULL() {
    boolean result = this.address != 0;
    return new IntValue(result ? 1 : 0);
  }

  public Value getField(int fieldId) {
    if (address == 0) {
      throw new NullPointerException("User NullPointerException");
    }
    if (fields == null) {
      return PlaceHolder.instance;
    } else {
      Value v = fields[fieldId];
      if (v == null) {
        return PlaceHolder.instance;
      }
      return v;
    }
  }

  public void setField(int fieldId, Value value) {
    if (address == 0) {
      throw new NullPointerException("User NullPointerException");
    }
    if (fields != null) {
      fields[fieldId] = value;
    }
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public Value invokeMethod(String name, Value[] args, History history) {
    return PlaceHolder.instance;
  }
}
