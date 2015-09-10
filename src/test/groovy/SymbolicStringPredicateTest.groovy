package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import janala.interpreters.SymbolicStringPredicate.COMPARISON_OPS

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class SymbolicStringPredicateTest {

  @Test
  void testNot() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      COMPARISON_OPS.EQ, "a", "b")
    SymbolicStringPredicate p2 = new SymbolicStringPredicate(
      COMPARISON_OPS.NE, "a", "b")
    
    assertEquals(p2, p1.not())
    assertEquals(p1, p2.not())
  }

  @Test
  void testStringfy() {
    SymbolicStringPredicate p1 = new SymbolicStringPredicate(
      COMPARISON_OPS.EQ, "a", "b")
    assertEquals('"a" == "b"', p1.toString())

    p1 = new SymbolicStringPredicate(
      COMPARISON_OPS.NE, "a", new Integer(1))
    assertEquals('"a" != 1', p1.toString())

    p1 = new SymbolicStringPredicate(
      COMPARISON_OPS.IN, "a", null)
    assertEquals('"a" regexin null', p1.toString())
  }
}