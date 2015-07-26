package janala.interpreters;

import gnu.trove.iterator.TIntLongIterator;
import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.TIntLongMap;
import java.util.Map;

public final class SymbolicInt extends Constraint {
  public enum COMPARISON_OPS {
    EQ,
    NE,
    GT,
    GE,
    LT,
    LE,
    UN
  };

  public COMPARISON_OPS op;
  public TIntLongMap linear; // coefficients
  public long constant; // nominal value

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicInt(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o == null) {
      return false;
    } else if (o instanceof SymbolicInt) {
      SymbolicInt e = (SymbolicInt) o;
      return (linear.equals(e.linear) && (constant == e.constant) && (op == e.op));
    } else {
      return false;
    }
  }

  public int hashCode() {
    int ret = 37;
    ret = 71 * ret + linear.hashCode();
    ret = 71 * ret + (int) constant;
    ret = 71 * ret + op.hashCode();
    return ret;
  }

  // Construct a symbolic int i := x
  public SymbolicInt(int i) {
    linear = new TIntLongHashMap();
    linear.put(i, 1L);
    constant = 0;
    op = COMPARISON_OPS.UN;
  }

  private SymbolicInt() {
    linear = new TIntLongHashMap();
    constant = 0;
    op = COMPARISON_OPS.UN;
  }

  private SymbolicInt(SymbolicInt e) {
    this.linear = new TIntLongHashMap(e.linear);
    constant = e.constant;
    op = e.op;
  }

  public SymbolicInt negate() {
    SymbolicInt tmp = new SymbolicInt(this);
    for (TIntLongIterator it = tmp.linear.iterator(); it.hasNext(); ) {
      it.advance();
      it.setValue(-it.value());
    }
    tmp.constant = -constant;
    return tmp;
  }

  public SymbolicInt add(long l) {
    return add(l, true);
  }

  private SymbolicInt add(long l, boolean add) {
    SymbolicInt tmp = new SymbolicInt(this);
    if (add) {
      tmp.constant = constant + l;
    } else {  
      tmp.constant = constant - l;
    } 
    return tmp;
  }

  public SymbolicInt add(SymbolicInt l) {
    return add(l, true);
  }

  private SymbolicInt add(SymbolicInt l, boolean add) {
    SymbolicInt tmp = new SymbolicInt(this);
    SymbolicInt e = (SymbolicInt) l;
    for (TIntLongIterator it = e.linear.iterator(); it.hasNext(); ) {
      it.advance();

      int integer = it.key();
      long coeff = linear.get(integer); // 0 is default value
      long toadd;
      if (add) {
        toadd = coeff + it.value();
      } else {
        toadd = coeff - it.value();
      }
      if (toadd == 0) {
        tmp.linear.remove(integer);
      } else {
        tmp.linear.put(integer, toadd);
      }
    }
    if (tmp.linear.isEmpty()) {
      return null; // Shouldn't this returns the constant value?
    }

    if (add) {
      tmp.constant = this.constant + e.constant;
    } else {
      tmp.constant = this.constant - e.constant;
    }
    return tmp;
  }

  public SymbolicInt subtractFrom(long l) {
    SymbolicInt e = (SymbolicInt) negate();
    e.constant = l + e.constant;
    return e;
  }

  public SymbolicInt subtract(long l) {
    return add(l, false);
  }

  public SymbolicInt subtract(SymbolicInt l) {
    return add(l, false);
  }

  public SymbolicInt multiply(long l) {
    if (l == 0) return null;
    if (l == 1) return this;
    SymbolicInt tmp = new SymbolicInt();
    for (TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
      it.advance();

      int integer = it.key();
      tmp.linear.put(integer, l * it.value());
    }
    tmp.constant = l * constant;
    return tmp;
  }

  public SymbolicInt setop(COMPARISON_OPS op) {
    SymbolicInt ret = new SymbolicInt(this);
    if (ret.op != COMPARISON_OPS.UN) {
      if (op == COMPARISON_OPS.EQ) { // (x op 0)==0 is same as !(x op 0)
        ret = (SymbolicInt) ret.not();
      }
    } else {
      ret.op = op;
    }
    return ret;
  }

  public Constraint not() {
    SymbolicInt ret = new SymbolicInt(this);
    if (ret.op == COMPARISON_OPS.EQ) ret.op = COMPARISON_OPS.NE;
    else if (ret.op == COMPARISON_OPS.NE) ret.op = COMPARISON_OPS.EQ;
    else if (ret.op == COMPARISON_OPS.GT) ret.op = COMPARISON_OPS.LE;
    else if (ret.op == COMPARISON_OPS.GE) ret.op = COMPARISON_OPS.LT;
    else if (ret.op == COMPARISON_OPS.LT) ret.op = COMPARISON_OPS.GE;
    else if (ret.op == COMPARISON_OPS.LE) ret.op = COMPARISON_OPS.GT;
    return ret;
  }

  public Constraint substitute(Map<String, Long> assignments) {
    long val = 0;
    SymbolicInt ret = null;
    boolean isSymbolic = false;
    Constraint ret2 = null;

    for (TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
      it.advance();

      int key = it.key();
      long l = it.value();
      if (assignments.containsKey("x" + key)) {
        val += assignments.get("x" + key) * l;
      } else {
        isSymbolic = true;
        if (ret == null) {
          ret = new SymbolicInt();
        }
        ret.linear.put(key, l);
      }
    }
    //val += this.constant;
    if (ret != null) {
      ret.constant = val + this.constant;
    }
    if (!isSymbolic) {
      if (this.op == COMPARISON_OPS.EQ) {
        ret2 =
            (val == -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.NE) {
        ret2 =
            (val != -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.LE) {
        ret2 =
            (val <= -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.LT) {
        ret2 =
            (val < -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.GE) {
        ret2 =
            (val >= -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else if (this.op == COMPARISON_OPS.GT) {
        ret2 =
            (val > -this.constant)
                ? SymbolicTrueConstraint.instance
                : SymbolicFalseConstraint.instance;
      } else {
        return null;
      }
      return ret2;
    } else {
      ret.op = this.op;
      return ret;
    }
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    for (TIntLongIterator it = linear.iterator(); it.hasNext(); ) {
      it.advance();

      int integer = it.key(); // Index of variable
      long l = it.value();
      if (first) {
        first = false;
      } else {
        sb.append('+');
      }
      if (l < 0) {
        sb.append('(');
        sb.append(l);
        sb.append(")*x");
        sb.append(integer);
      } else if (l == 1) {
        sb.append("x");
        sb.append(integer);
      } else if (l > 0) {
        sb.append(l);
        sb.append("*x");
        sb.append(integer);
      }
    }
    if (constant != 0) {
      if (constant > 0) sb.append('+');
      sb.append(constant);
    }
    if (op == COMPARISON_OPS.EQ) {
      sb.append("==");
      sb.append('0');
    } else if (op == COMPARISON_OPS.NE) {
      sb.append("!=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.LE) {
      sb.append("<=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.LT) {
      sb.append("<");
      sb.append('0');
    } else if (op == COMPARISON_OPS.GE) {
      sb.append(">=");
      sb.append('0');
    } else if (op == COMPARISON_OPS.GT) {
      sb.append(">");
      sb.append('0');
    }
    sb.append(" at iid ");
    sb.append(iid);
    sb.append(" and index ");
    sb.append(index);
    return sb.toString();
  }

}
