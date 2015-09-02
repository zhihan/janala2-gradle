package janala.logger

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.junit.Assert.assertTrue

import org.junit.Before
import org.junit.Test
import groovy.transform.CompileStatic

@CompileStatic
class StringLoggerTest {
  private StringLogger logger

  @Before
  void setup() {
    logger = new StringLogger()
  }

  @Test
  void testLDC1() {
    logger.LDC(0, 0, 1)
    assertTrue(logger.log.contains("LDC"))
  }


  @Test
  void testLDC2() {
    logger.LDC(0, 0, 2L)
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC3(){
    logger.LDC(0, 0, 0.1F)
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC4() {
    logger.LDC(0, 0, 0.2D)
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC5() {
    logger.LDC(0, 0, "a")
    assertTrue(logger.log.contains("LDC"))
  }

  @Test
  void testLDC6() {
    logger.LDC(0, 1, new Integer(1))
    assertTrue(logger.log.contains("LDC"))  
  }

  @Test
  void testIINC() {
    logger.IINC(0, 0, 1, 1)
    assertTrue(logger.log.contains("IINC"))
  }

  @Test
  void testMULTIANEWARRAY() {
    logger.MULTIANEWARRAY(0, 0, "I", 2)
    assertTrue(logger.log.contains("MULTIANEWARRAY"))
  }

  @Test
  void testLOOKUPSWITCH() {
    int[] keys = new int[1]
    keys[0] = 1
    int[] labels = new int[1]
    labels[0] = 2
    logger.LOOKUPSWITCH(0, 0, 2, keys, labels)
    assertTrue(logger.log.contains("LOOKUPSWITCH"))
  }

  @Test
  void testTABLESWITCH() {
    int[] labels = new int[1]
    labels[0] = 2
    logger.TABLESWITCH(0, 0, 0, 2, 1, labels)
    assertTrue(logger.log.contains("TABLESWITCH"))
  }

  @Test
  void testIFEQ() {
    logger.IFEQ(0, 0, 1)
    assertTrue(logger.log.contains("IFEQ"))
  }

  @Test
  void testIFNE() {
    logger.IFNE(0, 0, 1)
    assertTrue(logger.log.contains("IFNE"))
  }

  @Test
  void testIFLT(){
    logger.IFLT(0, 0, 1)
    assertTrue(logger.log.contains("IFLT"))
  }

  @Test
  void testIFGE() {
    logger.IFGE(0, 0, 1)
    assertTrue(logger.log.contains("IFGE"))
  }

  @Test
  void testIFGT() {
    logger.IFGT(0, 0, 1)
    assertTrue(logger.log.contains("IFGT"))
  }

  @Test
  void testIFLE() {
    logger.IFLE(0, 0, 1)
    assertTrue(logger.log.contains("IFLE"))
  }

  @Test
  void testIF_ICMPEQ() {
    logger.IF_ICMPEQ(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPEQ"))
  }

  @Test
  void testIF_ICMPNE() {
    logger.IF_ICMPNE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPNE"))
  }

  @Test
  void testIF_ICMPLT() {
    logger.IF_ICMPLT(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPLT"))
  }

  @Test
  void testIF_ICMPGE() {
    logger.IF_ICMPGE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPGE"))
  }

  @Test
  void testIF_ICMPGT() {
    logger.IF_ICMPGT(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPGT"))
  }

  @Test
  void testIF_ICMPLE() {
    logger.IF_ICMPLE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ICMPLE"))
  }

  @Test
  void testIF_ACMPEQ() {
    logger.IF_ACMPEQ(0, 0, 1)
    assertTrue(logger.log.contains("IF_ACMPEQ"))
  }

  @Test
  void testIF_ACMPNE() {
    logger.IF_ACMPNE(0, 0, 1)
    assertTrue(logger.log.contains("IF_ACMPNE"))
  }

  @Test
  void testIFNULL() {
    logger.IFNULL(0, 0, 1)
    assertTrue(logger.log.contains("IFNULL"))
  }

  @Test
  void testIFNONNULL() {
    logger.IFNONNULL(0, 0, 1)
    assertTrue(logger.log.contains("IFNONNULL"))
  }

  @Test
  void testGOTO() {
    logger.GOTO(0, 0, 1)
    assertTrue(logger.log.contains("GOTO"))
  }

  @Test
  void testJSR() {
    logger.JSR(0, 0, 1)
    assertTrue(logger.log.contains("JSR"))
  }
}