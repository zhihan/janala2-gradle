package janala.interpreters;

import janala.solvers.History;

public class LongValue extends Value {
  private SymbolicInt symbolic; // mutable in make_symbolic
  public SymbolicInt getSymbolic() {
    return symbolic;
  }

  private final long concrete;

  @Override
  public Object getConcrete() {
    return concrete;
  }

  public long getConcreteLong() {
    return concrete;
  }

  public LongValue(long concrete) {
    this.concrete = concrete;
    symbolic = null;
  }

  public LongValue(long concrete, SymbolicInt symbolic) {
    this.concrete = concrete;
    this.symbolic = symbolic;
  }

  public int MAKE_SYMBOLIC(History history) {
    symbol = symbol + inc;
    symbolic = new SymbolicInt(symbol - inc);
    return symbol - inc;
  }

  public LongValue LADD(LongValue i) {
    if (symbolic == null && i.symbolic == null) {
      return new LongValue(concrete + i.concrete);
    } else if (symbolic != null && i.symbolic != null) {
      LongValue ret = new LongValue(concrete + i.concrete);
      ret.symbolic = symbolic.add(i.symbolic);
      return ret;
    } else if (symbolic != null) {
      LongValue ret = new LongValue(concrete + i.concrete);
      ret.symbolic = symbolic.add(i.concrete);
      return ret;
    } else {
      LongValue ret = new LongValue(concrete + i.concrete);
      ret.symbolic = i.symbolic.add(concrete);
      return ret;
    }
  }

  public LongValue LSUB(LongValue i) {
    if (symbolic == null && i.symbolic == null) {
      return new LongValue(concrete - i.concrete);
    } else if (symbolic != null && i.symbolic != null) {
      LongValue ret = new LongValue(concrete - i.concrete);
      ret.symbolic = symbolic.subtract(i.symbolic);
      return ret;
    } else if (symbolic != null) {
      LongValue ret = new LongValue(concrete - i.concrete);
      ret.symbolic = symbolic.subtract(i.concrete);
      return ret;
    } else {
      LongValue ret = new LongValue(concrete - i.concrete);
      ret.symbolic = i.symbolic.subtractFrom(concrete);
      return ret;
    }
  }

  public LongValue LMUL(LongValue i) {
    long concreteVal = concrete * i.concrete;
    if (symbolic == null && i.symbolic == null) {
      return new LongValue(concreteVal);
    } else if (symbolic != null && i.symbolic != null) {
      SymbolicInt s = symbolic.multiply(i.concrete);
      return new LongValue(concreteVal, s);
    } else if (symbolic != null) {
      SymbolicInt s = symbolic.multiply(i.concrete);
      return new LongValue(concreteVal, s);
    } else {
      SymbolicInt s = i.symbolic.multiply(concrete);
      return new LongValue(concreteVal, s);
    }
  }

  public LongValue LDIV(LongValue i) {
    return new LongValue(concrete / i.concrete);
  }

  public LongValue LREM(LongValue i) {
    return new LongValue(concrete % i.concrete);
  }

  public LongValue LNEG() {
    if (symbolic == null) return new LongValue(-concrete);
    else {
      LongValue ret = new LongValue(-concrete);
      ret.symbolic = symbolic.subtractFrom(0);
      return ret;
    }
  }

  public LongValue LSHL(LongValue i) {
    return new LongValue(concrete << i.concrete);
  }

  public LongValue LSHR(LongValue i) {
    return new LongValue(concrete >> i.concrete);
  }

  public LongValue LUSHR(LongValue i) {
    return new LongValue(concrete >>> i.concrete);
  }

  public LongValue LAND(LongValue i) {
    return new LongValue(concrete & i.concrete);
  }

  public LongValue LOR(LongValue i) {
    return new LongValue(concrete | i.concrete);
  }

  public LongValue LXOR(LongValue i) {
    return new LongValue(concrete ^ i.concrete);
  }

  public IntValue L2I() {
    return new IntValue((int) concrete, symbolic);
  }

  public FloatValue L2F() {
    return new FloatValue((float) concrete);
  }

  public DoubleValue L2D() {
    return new DoubleValue((double) concrete);
  }

  public IntValue LCMP(LongValue i2) {
    IntValue ret;
    if (concrete == i2.concrete) {
      ret = new IntValue(0);
    } else if (concrete > i2.concrete) {
      ret = new IntValue(1);
    } else {
      ret = new IntValue(-1);
    }
    if (symbolic == null && i2.symbolic == null) {
      return ret;
    } else if (symbolic != null && i2.symbolic != null) {
      ret.symbolic = symbolic.subtract(i2.symbolic);
      return ret;
    } else if (symbolic != null) {
      if (ret.concrete == 0)
        ret.symbolic = symbolic.subtract(i2.concrete); //.setop(SymbolicInt.COMPARISON_OPS.EQ);
      if (ret.concrete == 1)
        ret.symbolic = symbolic.subtract(i2.concrete); //.setop(SymbolicInt.COMPARISON_OPS.GT);
      if (ret.concrete == -1)
        ret.symbolic = symbolic.subtract(i2.concrete); //.setop(SymbolicInt.COMPARISON_OPS.LT);
      return ret;
    } else {
      if (ret.concrete == 0)
        ret.symbolic = i2.symbolic.subtractFrom(concrete); //.setop(SymbolicInt.COMPARISON_OPS.EQ);
      if (ret.concrete == 1)
        ret.symbolic = i2.symbolic.subtractFrom(concrete); //.setop(SymbolicInt.COMPARISON_OPS.GT);
      if (ret.concrete == -1)
        ret.symbolic = i2.symbolic.subtractFrom(concrete); //.setop(SymbolicInt.COMPARISON_OPS.LT);
      return ret;
    }
  }

  @Override
  public String toString() {
    return "LongValue{" + "symbolic=" + symbolic + ", concrete=" + concrete + '}';
  }
}
