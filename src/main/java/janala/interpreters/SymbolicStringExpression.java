package janala.interpreters;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;

public class SymbolicStringExpression {
  private final LinkedList list; // either String or SymbolicStringVar

  public SymbolicStringExpression(int sym, IntValue length) {
    this.list = new LinkedList();
    this.list.addLast(new SymbolicStringVar(sym, length));
  }

  public SymbolicStringExpression(SymbolicStringExpression sym) {
    this.list = new LinkedList();
    for (Object elem : sym.list) {
      this.list.addLast(elem);
    }
  }

  public SymbolicStringExpression concatStr(String str) {
    SymbolicStringExpression ret = new SymbolicStringExpression(this);
    Object last = ret.list.getLast();
    if (last instanceof String) {
      ret.list.removeLast();
      ret.list.addLast(last + str);
    } else {
      ret.list.addLast(str);
    }
    return ret;
  }

  public SymbolicStringExpression concat(SymbolicStringExpression expr) {
    SymbolicStringExpression ret = new SymbolicStringExpression(this);
    Object last = ret.list.getLast();
    Object first = expr.list.getFirst();
    if (last instanceof String && first instanceof String) {
      ret.list.removeLast();
      ret.list.addLast(last + first.toString());
    } else {
      ret.list.addLast(first);
    }

    boolean isFirst = true;
    for (Object elem : expr.list) {
      if (isFirst) {
        isFirst = false;
      } else {
        ret.list.addLast(elem);
      }
    }

    return ret;
  }

  public SymbolicStringExpression concatToStr(String str) {
    SymbolicStringExpression ret = new SymbolicStringExpression(this);
    Object first = ret.list.getFirst();
    if (first instanceof String) {
      ret.list.removeFirst();
      ret.list.addFirst(str + first);
    } else {
      ret.list.addFirst(str);
    }
    return ret;
  }

  public boolean isCompound() {
    return this.list.size() > 1;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    boolean flag = true;
    for (Object elem : this.list) {
      if (flag) {
        flag = false;
      } else {
        sb.append('+');
      }
      if (elem instanceof SymbolicStringVar) {
        sb.append(elem.toString());
      } else { 
        sb.append('"');
        sb.append((String)elem);
        sb.append('"');
      }
    }
    return sb.toString();
  }

  public SymbolicStringExpression substitute(ArrayList<Value> assignments) {
    return this;
  }

  public IntValue getField(String offset) {
    if (offset.equals("length")) {
      IntValue ret = null, len;
      for (Object val : this.list) {
        if (val instanceof String) {
          len = new IntValue(((String) val).length());
        } else if (val instanceof SymbolicStringVar) {
          len = (IntValue) ((SymbolicStringVar) val).getField("length");
        } else {
          throw new RuntimeException("Unsupported string type.");
        }
        if (ret == null) {
          ret = len;
        } else {
          ret = ret.IADD(len);
        }
      }
      return ret;
    }
    return null;
  }

  public SymOrInt getExprAt(int i, Set<String> freeVars, Map<String, Long> assignments) {
    int len = list.size();
    for (Object s : list) {
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
    return null;
  }
}
