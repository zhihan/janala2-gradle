package janala.solvers;

import janala.interpreters.Value;

public class InputElement extends Element {
  Integer symbol;
  Value value;

  public InputElement(Integer symbol, Value value) {
    this.symbol = symbol;
    this.value = value;
  }
}
