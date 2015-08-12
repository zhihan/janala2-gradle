package janala.solvers;

import janala.interpreters.Value;

public class InputElement extends Element {
  final int symbol;
  final Value value;

  public InputElement(int symbol, Value value) {
    this.symbol = symbol;
    this.value = value;
  }
}
