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
}