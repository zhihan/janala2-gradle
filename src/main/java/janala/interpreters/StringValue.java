package janala.interpreters;

import janala.config.Config;
import janala.solvers.History;

public final class StringValue extends ObjectValue {
  private final String string;
  private SymbolicStringExpression symbolicExp;

  public StringValue(String string, int address) {
    super(100, address);
    this.string = string;
  }

  public StringValue(String string, SymbolicStringExpression symbolicExp) {
    super(100, -1);
    this.string = string;
    this.symbolicExp = symbolicExp;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o == this) {
      return true;
    } else if (o instanceof StringValue) {
      StringValue other = (StringValue)o;
      return (this.string == other.string ||
        this.string.equals(other.string));
    } else {
      return false;
    }
  }

  @Override
  public String getConcrete() {
    return string;
  }

  public SymbolicStringExpression getSymbolicExp() {
    return symbolicExp;
  }

  private String escapeRE(String str) {
    return str.replaceAll("([^a-zA-z0-9])", "\\\\$1");
  }

  private Value invokeEquals(Value arg) {
    if (arg instanceof StringValue) {
      StringValue other = (StringValue) arg;
      boolean result = string.equals(other.string);
      if (symbolicExp != null && other.symbolicExp != null) {
        return new IntValue(
          result ? 1 : 0,
          new SymbolicStringPredicate(
            SymbolicStringPredicate.COMPARISON_OPS.EQ, symbolicExp, other.symbolicExp));
      } else if (symbolicExp != null) {
        return new IntValue(
          result ? 1 : 0,
          new SymbolicStringPredicate(
            SymbolicStringPredicate.COMPARISON_OPS.EQ, symbolicExp, other.string));
      } else if (other.symbolicExp != null) {
        return new IntValue(
          result ? 1 : 0,
          new SymbolicStringPredicate(
            SymbolicStringPredicate.COMPARISON_OPS.EQ, string, other.symbolicExp));
      } else {
        return new IntValue(result ? 1 : 0);
      }
    } else {
      // arg is not StringValue type.
      return new IntValue(0);
    }
  }

  @Override
  public Value invokeMethod(String name, Value[] args, History history) {
    if (name.equals("equals") && args.length == 1) {
      return invokeEquals(args[0]);
    } else if (name.equals("startsWith") && args.length == 1) {
      if (args[0] instanceof StringValue) {
        StringValue other = (StringValue) args[0];
        boolean result = string.startsWith(other.string);
        if (symbolicExp != null) {
          return new IntValue(
              result ? 1 : 0,
              new SymbolicStringPredicate(
                  SymbolicStringPredicate.COMPARISON_OPS.IN,
                  symbolicExp,
                  escapeRE(other.string) + ".*"));
        } else {
          return new IntValue(result ? 1 : 0);
        }
      }
    } else if (name.equals("startsWith") && args.length == 2) {
      if (args[0] instanceof StringValue) {
        StringValue other = (StringValue) args[0];
        IntValue offset = (IntValue) args[1];
        boolean result = string.startsWith(other.string, offset.concrete);
        if (symbolicExp != null) {
          return new IntValue(
              result ? 1 : 0,
              new SymbolicStringPredicate(
                  SymbolicStringPredicate.COMPARISON_OPS.IN,
                  symbolicExp,
                  ".{" + offset.concrete + "}" + escapeRE(other.string) + ".*"));
        } else {
          return new IntValue(result ? 1 : 0);
        }
      }
    } else if (name.equals("endsWith") && args.length == 1) {
      StringValue other = (StringValue) args[0];
      boolean result = string.endsWith(other.string);
      if (symbolicExp != null) {
        return new IntValue(
            result ? 1 : 0,
            new SymbolicStringPredicate(
                SymbolicStringPredicate.COMPARISON_OPS.IN,
                symbolicExp,
                ".*" + escapeRE(other.string)));
      } else {
        return new IntValue(result ? 1 : 0);
      }
    } else if (name.equals("contains") && args.length == 1) {
      StringValue other = (StringValue) args[0];
      boolean result = string.contains(other.string);
      if (symbolicExp != null) {
        return new IntValue(
            result ? 1 : 0,
            new SymbolicStringPredicate(
                SymbolicStringPredicate.COMPARISON_OPS.IN,
                symbolicExp,
                ".*" + escapeRE(other.string) + ".*"));
      } else {
        return new IntValue(result ? 1 : 0);
      }
    } else if (name.equals("concat") && args.length == 1) {
      if (args[0] instanceof StringValue) {
        StringValue other = (StringValue) args[0];
        String result = string.concat(other.string);
        if (symbolicExp != null && other.symbolicExp  != null) {
          return new StringValue(result, symbolicExp.concat(other.symbolicExp));
        } else if (symbolicExp != null) {
          return new StringValue(result, symbolicExp.concatStr(other.string));
        } else if (other.symbolicExp  != null) {
          return new StringValue(result, other.symbolicExp.concatToStr(string));
        } else {
          return new StringValue(result, null);
        }
      }
    } else if (name.equals("length") && args.length == 0) {
      int result = string.length();
      if (symbolicExp != null) {
        return symbolicExp.getField("length");
      } else {
        return new IntValue(result);
      }
    } else if (name.equals("matches") && args.length == 1) {
      if (args[0] instanceof StringValue) {
        StringValue other = (StringValue) args[0];
        boolean result = string.matches(other.string);
        if (symbolicExp != null) {
          return new IntValue(
              result ? 1 : 0,
              new SymbolicStringPredicate(
                  SymbolicStringPredicate.COMPARISON_OPS.IN, symbolicExp, other.string));
        } else {
          return new IntValue(result ? 1 : 0);
        }
      }
    }
    return super.invokeMethod(name, args, history);
  }

  public int MAKE_SYMBOLIC(History history) {
    IntValue length = new IntValue(string.length());
    int ret = symbol;
    symbol = symbol + inc;
    symbolicExp = new SymbolicStringExpression(ret, length);
    length.MAKE_SYMBOLIC(history);

    Constraint results = length.symbolic.setop(SymbolicInt.COMPARISON_OPS.GE);
    boolean resultc = length.concrete >= 0;
    history.checkAndSetBranch(resultc, results, 0);
    if (resultc) {
      history.setLastBranchDone();
    }

    results =
        length
            .ISUB(new IntValue(Config.instance.maxStringLength))
            .symbolic
            .setop(SymbolicInt.COMPARISON_OPS.LE);
    resultc = length.concrete <= Config.instance.maxStringLength;
    history.checkAndSetBranch(resultc, results, 0);
    if (resultc) {
      history.setLastBranchDone();
    }

    //System.out.println("String symbol x"+ret+" = \""+string+"\"");
    return ret;
  }
}
