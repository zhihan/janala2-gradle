package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.anyInt
import static org.mockito.Mockito.eq

import janala.config.Config
import janala.solvers.History

import org.junit.Before
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class StaticInvocationTest {
  StaticInvocation context
  Config config
  History history

  @Before
  void setup() {
    config = new Config()
    context = new StaticInvocation(config)
    history = mock(History.class)
  }

  @Test
  void testIntegerValueOf() {
    Value[] x = new Value[1]
    x[0] = new IntValue(1)
    Value v = context.invokeMethod(0, "java/lang/Integer", "valueOf", x, null)
    assertEquals(new IntegerObjectValue(new IntValue(1), 10), v)
    assertTrue(context.knownMethod)
  }

  @Test
  void testLongValueOf() {
    Value[] x = new Value[1]
    x[0] = new LongValue(1L)
    Value v = context.invokeMethod(0, "java/lang/Long", "valueOf", x, null)
    assertEquals(new LongObjectValue(new LongValue(1L), 10), v)
    assertTrue(context.knownMethod)
  }

  @Test
  void testAssume() {
    Value[] x = new Value[1]
    x[0] = new IntValue(1)
    Value v = context.invokeMethod(0, "janala/Main", "assume", x, history)
    verify(history).setLastBranchDone()
    assertTrue(context.knownMethod)
  }
  
  @Test
  void testIgnore() {
    Value[] x = new Value[0]
    Value v = context.invokeMethod(0, "janala/Main", "ignore", x, history)
    verify(history).ignore()
    assertTrue(context.knownMethod)
  }

  @Test
  void testForceTruth() {
    Value[] x = new Value[1]
    x[0] = new IntValue(1)
    Value v = context.invokeMethod(0, "janala/Main", "forceTruth", x, history)
    verify(history).setLastForceTruth()
    assertTrue(context.knownMethod)
  }

  @Test
  void testMakeSymbolic() {
    Value[] x = new Value[1]
    IntValue a = new IntValue(1)
    x[0] = a
    Value v = context.invokeMethod(0, "janala/Main", "MakeSymbolic", x, history)
    assertNotNull(a.getSymbolic())
    verify(history).addInput(anyInt(), eq(a))
    assertTrue(context.knownMethod)
  }

  @Test
  void testBeginScope() {
    Value[] x = new Value[0]
    Value v = context.invokeMethod(0, "janala/Main", "beginScope", x, history)
    verify(history).addInput(config.scopeBeginSymbol, null)
    verify(history).beginScope(0)
    assertTrue(context.knownMethod)
  }

  @Test
  void testEndScope() {
    Value[] x = new Value[0]
    Value v = context.invokeMethod(0, "janala/Main", "endScope", x, history)
    verify(history).addInput(config.scopeEndSymbol, null)
    verify(history).endScope(0)
    assertTrue(context.knownMethod)
  }
  
  @Test
  void testAssumeOrBegin() {
    Value[] x = new Value[1]
    IntValue a = new IntValue(1)
    x[0] = a
    Value v = context.invokeMethod(0, "janala/Main", "assumeOrBegin", x, history)
    verify(history).assumeOrBegin(a)
    assertTrue(context.knownMethod)
  }
  
  @Test
  void testAssumeOr() {
    Value[] x = new Value[2]
    IntValue a = new IntValue(1)
    SymbolicOrConstraint con = new SymbolicOrConstraint(new SymbolicInt(1))
    SymbolicOrValue b = new SymbolicOrValue(true, con)
    x[0] = a
    x[1] = b
    Value v = context.invokeMethod(0, "janala/Main", "assumeOr", x, history)
    verify(history).assumeOr(a, b)
    assertTrue(context.knownMethod)
  }
  
  @Test
  void testAssumeOrEnd() {
    Value[] x = new Value[1]
    SymbolicOrConstraint con = new SymbolicOrConstraint(new SymbolicInt(1))
    SymbolicOrValue b = new SymbolicOrValue(true, con)
    x[0] = b
    Value v = context.invokeMethod(0, "janala/Main", "assumeOrEnd", x, history)
    verify(history).assumeOrEnd(0, b)
    assertTrue(context.knownMethod)
  }
  
}