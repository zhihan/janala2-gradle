package janala.interpreters;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

import janala.solvers.CVC4Solver.CONSTRAINT_TYPE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;

public class SymbolicStringPredicate extends Constraint {

  public static enum COMPARISON_OPS {
    EQ,
    NE,
    IN,
    NOTIN
  };

  private final COMPARISON_OPS op;
  public COMPARISON_OPS getOp() { return op; }

  private final Object left;
  private final Object right;

  public SymbolicStringPredicate(COMPARISON_OPS op, Object left, Object right) {
    this.op = op;
    this.left = left;
    this.right = right;
  }

  public SymbolicStringPredicate(SymbolicStringPredicate other) {
    this.op = other.op;
    this.left = other.left;
    this.right = other.right;
  }

  @Override
  public void accept(ConstraintVisitor v) {
    v.visitSymbolicStringPredicate(this);
  }

  @Override
  public Constraint not() {
    SymbolicStringPredicate ret = new SymbolicStringPredicate(this);
    COMPARISON_OPS retOp = COMPARISON_OPS.NE;
    switch (this.op) {
      case EQ:
        retOp = COMPARISON_OPS.NE;
        break;
      case NE:
        retOp = COMPARISON_OPS.EQ;
        break;
      case IN:
        retOp = COMPARISON_OPS.NOTIN;
        break;
      case NOTIN:
        retOp = COMPARISON_OPS.IN;
        break;
    }
    return new SymbolicStringPredicate(retOp, left, right);
  }

  @Override
  public Constraint substitute(Map<String, Long> assignments) {
    return this;
  }

  public Constraint substitute(ArrayList<Value> assignments) {
    return this;
  }

  private String stringfy(Object s) {
    if (s instanceof String) {
      return "\"" + s + "\"";
    } else if (s == null) {
      return "null";
    } else {
      return s.toString();
    } 
  }

  public String toString() {
    switch (this.op) {
      case EQ:
        return stringfy(this.left) + " == " + stringfy(this.right);
      case NE:
        return stringfy(this.left) + " != " + stringfy(this.right);
      case IN:
        return stringfy(this.left) + " regexin " + stringfy(this.right);
      case NOTIN:
        return stringfy(this.left) + " regexnotin " + stringfy(this.right);
    }
    throw new RuntimeException("Not implemented");
  }

  private static class ExprAt {
    final boolean isSymbolic;
    final String prefix;
    final int symOrVal;

    ExprAt(boolean symbolic, String prefix, int symOrVal) {
      isSymbolic = symbolic;
      this.prefix = prefix;
      this.symOrVal = symOrVal;
    }
  }

  private SymOrInt exprAt(Object sExpr, int i, Set<String> freeVars, 
    Map<String, Long> assignments) {
    if (sExpr instanceof String) {
      return new SymOrInt(((String) sExpr).charAt(i));
    } else {
      SymbolicStringExpression tmp = (SymbolicStringExpression) sExpr;
      int len = tmp.list.size();
      for (int j = 0; j < len; j++) {
        Object s = tmp.list.get(j);
        if (s instanceof String) {
          if (i < ((String) s).length()) {
            return new SymOrInt(((String) s).charAt(i));
          } else {
            i = i - ((String) s).length();
          }
        } else {
          String idx = s.toString();
          int length =
              (int) ((SymbolicStringVar) s).getField("length").substituteInLinear(assignments);
          if (i < length) {
            freeVars.add("x" + idx + "__" + i);
            return new SymOrInt("x" + idx + "__" + i);
          } else {
            i = i - length;
          }
        }
      }
    }
    return null;
  }

  private Constraint getStringEqualityFormula(
      Object left,
      Object right,
      long length,
      Set<String> freeVars,
      Map<String, Long> assignments) {
    SymbolicAndConstraint and = null;

    if (length <= 0) {
      return SymbolicTrueConstraint.instance;
    }
    for (int i = 0; i < length; i++) {
      SymOrInt e1 = exprAt(left, i, freeVars, assignments);
      SymOrInt e2 = exprAt(right, i, freeVars, assignments);
      Constraint c;
      c = new SymbolicIntCompareConstraint(e1, e2, 
        SymbolicIntCompareConstraint.COMPARISON_OPS.EQ);

      if (i != 0) {
        and = and.AND(c);
      } else {
        and = new SymbolicAndConstraint(c);
      }
    }
    return and;
  }

  public Constraint getFormula(
      Set<String> freeVars,
      CONSTRAINT_TYPE mode,
      Map<String, Long> assignments) {
    StringBuilder sb = new StringBuilder();
    long length1, length2;
    int j;
    
    IntValue s1 =
        (this.left instanceof String)
            ? new IntValue(((String) this.left).length())
            : ((SymbolicStringExpression) this.left).getField("length");
    IntValue s2 =
        (this.right instanceof String)
            ? new IntValue(((String) this.right).length())
            : ((SymbolicStringExpression) this.right).getField("length");
    IntValue formula;

    if (mode == CONSTRAINT_TYPE.INT) {
      switch (this.op) {
        case EQ:
          formula = s1.ISUB(s2);
          return formula.symbolic.setop(SymbolicInt.COMPARISON_OPS.EQ);
        case NE:
          SymbolicInt int1 =
              s1.symbolic != null ? s1.symbolic.setop(SymbolicInt.COMPARISON_OPS.GT) : null;
          SymbolicInt int2 =
              s2.symbolic != null ? s2.symbolic.setop(SymbolicInt.COMPARISON_OPS.GT) : null;
          if (int1 != null && int2 != null) {
            SymbolicAndConstraint ret = new SymbolicAndConstraint(int1);
            ret = ret.AND(int2);
            return ret;
          } else if (int1 != null) {
            return int1;
          } else if (int2 != null) {
            return int2;
          } else {
            return SymbolicTrueConstraint.instance;
          }
        case IN:
          // @todo regex_escape
          return RegexpEncoder.getLengthFormulaString(
              (String) this.right, "x", s1.getSymbol(), true);
        case NOTIN:
          // @todo regex_escape
          return RegexpEncoder.getLengthFormulaString(
              (String) this.right, "x", s1.getSymbol(), false);
      }
    } else if (mode == CONSTRAINT_TYPE.STR) {
      switch (this.op) {
        case EQ:
          if (s1.symbolic != null) {
            length1 = s1.substituteInLinear(assignments);
          } else {
            length1 = s1.concrete;
          }
          if (s2.symbolic != null) {
            length2 = s2.substituteInLinear(assignments);
          } else {
            length2 = s2.concrete;
          }
          if (length1 != length2) {
            return SymbolicFalseConstraint.instance;
          } else {
            return getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments);
          }
        case NE:
          if (s1.symbolic != null) {
            length1 = s1.substituteInLinear(assignments);
          } else {
            length1 = s1.concrete;
          }
          if (s2.symbolic != null) {
            length2 = s2.substituteInLinear(assignments);
          } else {
            length2 = s2.concrete;
          }
          if (length1 != length2) {
            return SymbolicTrueConstraint.instance;
          } else {
            return new SymbolicNotConstraint(
                getStringEqualityFormula(this.left, this.right, length1, freeVars, assignments));
          }
          //                    return (length1 !== length2)?"TRUE":"FALSE";
        case IN:
          length1 = s1.substituteInLinear(assignments);
          for (j = 0; j < length1; j++) {
            freeVars.add("x" + this.left + "__" + j);
          }
          // @todo regex_escape
          return RegexpEncoder.getRegexpFormulaString(
              (String) this.right, "x" + this.left + "__", (int) length1);
        case NOTIN:
          length1 = s1.substituteInLinear(assignments);
          for (j = 0; j < length1; j++) {
            freeVars.add("x" + this.left + "__" + j);
          }
          // @todo regex_escape
          return RegexpEncoder.getRegexpFormulaString(
              "~(" + (String) this.right + ")", "x" + this.left + "__", (int) length1);
      }
    }
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof SymbolicStringPredicate)) {
      return false;
    }

    SymbolicStringPredicate tmp = (SymbolicStringPredicate) o;
    if (this.op != tmp.op) {
      return false;
    }
    String s1 = stringfy(left);
    String s2 = stringfy(right);

    String s3 = stringfy(tmp.left);
    String s4 = stringfy(tmp.right);

    return (s1.equals(s3) && s2.equals(s4));
  }
}
