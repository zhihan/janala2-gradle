package janala.interpreters

import static org.junit.Assert.assertEquals

import janala.logger.ClassNames
import janala.logger.inst.*

import org.junit.Before
import org.junit.Test


class ConcoliInterpreterTest {
  ClassNames classNames
  ConcolicInterpreter interpreter


  @Before
  void setup() {
    classNames = new ClassNames()
    interpreter = new ConcolicInterpreter(classNames, null)
  }

  @Test
  void testICONST0() {
    interpreter.visitICONST_0(new ICONST_0(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(0), frame.peek())
  }

  @Test
  void testICONST1() {
    interpreter.visitICONST_1(new ICONST_1(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIADD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.visitIADD(new IADD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }
}