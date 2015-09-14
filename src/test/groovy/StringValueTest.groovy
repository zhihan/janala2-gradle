package janala.interpreters

import static org.junit.Assert.assertEquals

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class StringValueTest {
  
  @Test
  void testConstructor() {
    StringValue s = new StringValue("x", 10)
    assertEquals("x", s.concrete)

    def exp = new SymbolicStringExpression(1, new IntValue(1))
    s = new StringValue("x", exp)
    assertEquals("x", s.concrete)
    assertEquals(exp, s.symbolicExp)
  }

  @Test
  void testStringEquals() {
    StringValue s = new StringValue("x", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    assertEquals(new IntValue(1), s.invokeMethod("equals", b, null))

    b[0] = new StringValue("y", 20)
    assertEquals(new IntValue(0), s.invokeMethod("equals", b, null))
     
    b[0] = new ObjectValue(1)
    assertEquals(new IntValue(0), s.invokeMethod("equals", b, null))
  }

  @Test
  void testStringEquals_Symbol() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("x", exp)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.COMPARISON_OPS.EQ,
      exp, "x")

    assertEquals(new IntValue(1, con), s.invokeMethod("equals", b, null))
  }

  @Test
  void testStringEquals_Symbol2() {
    StringValue s = new StringValue("x", 10)
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    Value[] b = new Value[1];
    b[0] = new StringValue("y", exp)
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.COMPARISON_OPS.EQ,
      "x", exp)
    assertEquals(new IntValue(0, con), s.invokeMethod("equals", b, null))
  }

  @Test
  void testStringEquals_Symbol3() {
    def exp = new SymbolicStringExpression(1, new IntValue(1))
    StringValue s = new StringValue("x", exp)
    
    Value[] b = new Value[1];
    def exp2 = new SymbolicStringExpression(2, new IntValue(1)) 
    b[0] = new StringValue("x", exp2)
    
    def con = new SymbolicStringPredicate(SymbolicStringPredicate.COMPARISON_OPS.EQ,
      exp, exp2)
    assertEquals(new IntValue(1, con), s.invokeMethod("equals", b, null))
  }


  @Test
  void testStringStartsWith() {
    StringValue s = new StringValue("xy", 10)
    Value[] b = new Value[1];
    b[0] = new StringValue("x", 20)
    assertEquals(new IntValue(1), s.invokeMethod("startsWith", b, null))

    b[0] = new StringValue("y", 20)
    assertEquals(new IntValue(0), s.invokeMethod("startsWith", b, null))

    Value[] c = new Value[2];
    c[0] = new StringValue("y", 20)
    c[1] = new IntValue(0)
    assertEquals(new IntValue(0), s.invokeMethod("startsWith", c, null))

    c[1] = new IntValue(1)
    assertEquals(new IntValue(1), s.invokeMethod("startsWith", c, null))
  }
}