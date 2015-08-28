package janala.logger;
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.junit.Test
import org.junit.Before
import groovy.transform.CompileStatic

@CompileStatic
class DJVMTest {
  private Logger intp

  @Before
  void setup() {
    intp = mock(Logger.class)
    DJVM.setInterpreter(intp)
  }

  @Test
  void testLDC() {
    DJVM.LDC(0, 0, 1)
    verify(intp).LDC(0, 0, 1)

    DJVM.LDC(0, 0, 2L)
    verify(intp).LDC(0, 0, 2L)

    DJVM.LDC(0, 0, 0.1F)
    verify(intp).LDC(0, 0, 0.1F)

    DJVM.LDC(0, 0, 0.2)
    verify(intp).LDC(0, 0, 0.2)

    DJVM.LDC(0, 0, "a")
    verify(intp).LDC(0, 0, "a")

    DJVM.LDC(0, 1, new Integer(1))
    verify(intp).LDC(0, 1, new Integer(1))
  }

  @Test
  void testIINC() {
    DJVM.IINC(0, 0, 1, 1)
    verify(intp).IINC(0, 0, 1, 1)
  }

  @Test
  void testMULTIANEWARRAY() {
    DJVM.MULTIANEWARRAY(0, 0, "I", 2)
    verify(intp).MULTIANEWARRAY(0, 0, "I", 2)
  }

  @Test
  void testLOOKUPSWITCH() {
    int[] keys = new int[1]
    keys[0] = 1
    int[] labels = new int[1]
    labels[0] = 2
    DJVM.LOOKUPSWITCH(0, 0, 2, keys, labels)
    verify(intp).LOOKUPSWITCH(0, 0, 2, keys, labels)
  }

  @Test
  void testTABLESWITCH() {
    int[] labels = new int[1]
    labels[0] = 2
    DJVM.TABLESWITCH(0, 0, 0, 2, 1, labels)
    verify(intp).TABLESWITCH(0, 0, 0, 2, 1, labels)
  }

  @Test
  void testIF() {
    DJVM.IFEQ(0, 0, 1)
    verify(intp).IFEQ(0, 0, 1)

    DJVM.IFNE(0, 0, 1)
    verify(intp).IFNE(0, 0, 1)

    DJVM.IFLT(0, 0, 1)
    verify(intp).IFLT(0, 0, 1)

    DJVM.IFGE(0, 0, 1)
    verify(intp).IFGE(0, 0, 1)

    DJVM.IFGT(0, 0, 1)
    verify(intp).IFGT(0, 0, 1)

    DJVM.IFLE(0, 0, 1)
    verify(intp).IFLE(0, 0, 1)

    DJVM.IF_ICMPEQ(0, 0, 1)
    verify(intp).IF_ICMPEQ(0, 0, 1)

    DJVM.IF_ICMPNE(0, 0, 1)
    verify(intp).IF_ICMPNE(0, 0, 1)

    DJVM.IF_ICMPLT(0, 0, 1)
    verify(intp).IF_ICMPLT(0, 0, 1)

    DJVM.IF_ICMPGE(0, 0, 1)
    verify(intp).IF_ICMPGE(0, 0, 1)

    DJVM.IF_ICMPGT(0, 0, 1)
    verify(intp).IF_ICMPGT(0, 0, 1)

    DJVM.IF_ICMPLE(0, 0, 1)
    verify(intp).IF_ICMPLE(0, 0, 1)

    DJVM.IF_ACMPEQ(0, 0, 1)
    verify(intp).IF_ACMPEQ(0, 0, 1)

    DJVM.IF_ACMPNE(0, 0, 1)
    verify(intp).IF_ACMPNE(0, 0, 1)

    DJVM.IFNULL(0, 0, 1)
    verify(intp).IFNULL(0, 0, 1)

    DJVM.IFNONNULL(0, 0, 1)
    verify(intp).IFNONNULL(0, 0, 1)
  }
}