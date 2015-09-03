package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import org.junit.Test
import org.junit.Before
import groovy.transform.CompileStatic

@CompileStatic
class LongValueTest {
  @Before
  void setup() {
    Value.reset()
  }

  @Test
  void testNewId() {
    LongValue i = new LongValue(0)
    assertEquals(0L, i.concrete)
    def b = i.MAKE_SYMBOLIC(null)
    assertEquals(1, b)
    assertEquals(1, i.getSymbolic().linear.size())
  }

}