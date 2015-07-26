package janala.interpreters;

import janala.solvers.History;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/19/12
 * Time: 8:58 AM
 */
public class ObjectValue extends Value {
  final public static ObjectValue NULL = new ObjectValue(0, 0);
  Value[] concrete;
  SymbolicObject symbolic;
  int address; // address 0 is null, address -1 is uninitialized address

  @Override
  public Object getConcrete() {
    return address;
  }

  public ObjectValue(int nFields) {
    concrete = new Value[nFields];
    address = -1;
  }

  public ObjectValue(int i, int v) {
    concrete = null;
    address = v;
    //        symbolic = (address==0?0:-1);
  }

  public ObjectValue(ObjectValue ov, SymbolicObject sym) {
    concrete = ov.concrete;
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
    if (address == 0) throw new NullPointerException("User NullPointerException");
    if (concrete == null) return PlaceHolder.instance;
    Value v = concrete[fieldId];
    if (v == null) return PlaceHolder.instance;
    else return v;
  }

  public void setField(int fieldId, Value value) {
    if (address == 0) throw new NullPointerException("User NullPointerException");
    if (concrete != null) concrete[fieldId] = value;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public Value invokeMethod(String name, Value[] args, History history) {
    return PlaceHolder.instance;
  }
}
