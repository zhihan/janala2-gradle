package janala.interpreters

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import janala.solvers.History
import janala.solvers.Solver
import janala.solvers.BranchElement
import janala.logger.ClassNames
import janala.logger.inst.*
import janala.instrument.Coverage

import org.junit.Before
import org.junit.Test
import groovy.transform.CompileStatic

@CompileStatic
class ConcoliInterpreterTest {
  private ClassNames classNames
  private ConcolicInterpreter interpreter
  private Solver solver
  private History history
  private Coverage coverage
 
  @Before
  void setup() {
    classNames = new ClassNames()
    solver = mock(Solver.class)
    history = new History(solver)
    coverage = mock(Coverage.class)
    interpreter = new ConcolicInterpreter(classNames, history, coverage)
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
  void testICONST2() {
    interpreter.visitICONST_2(new ICONST_2(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testICONST3() {
    interpreter.visitICONST_3(new ICONST_3(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(3), frame.peek())
  }

  @Test
  void testICONST4() {
    interpreter.visitICONST_4(new ICONST_4(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(4), frame.peek())
  }

  @Test
  void testICONST5() {
    interpreter.visitICONST_5(new ICONST_5(0, 0))
    Frame frame = interpreter.getCurrentFrame()
    assertEquals(new IntValue(5), frame.peek())
  }

  @Test
  void testIADD() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.visitIADD(new IADD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testIFICMPLT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPLT(new IF_ICMPLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPLT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPLT(new IF_ICMPLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFEQ_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFEQ(new IFEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFEQ_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFEQ(new IFEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }  

  @Test
  void testIFNE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFNE(new IFNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFNE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFNE(new IFNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }    
}