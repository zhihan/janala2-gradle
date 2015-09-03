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
class ConcolicInterpreterTest {
  private ClassDepot classDepot
  private ClassNames classNames
  private ConcolicInterpreter interpreter
  private Solver solver
  private History history
  private Coverage coverage
 
  @Before
  void setup() {
    classDepot = mock(ClassDepot.class)
    classNames = new ClassNames(classDepot)
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
  void testISUB() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(2))
    frame.push(new IntValue(1))
    interpreter.visitISUB(new ISUB(0, 0))
    assertEquals(new IntValue(1), frame.peek())
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

  @Test
  void testIFGE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFGE(new IFGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFGE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(-1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFGE(new IFGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFGT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFGT(new IFGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFGT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFGT(new IFGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFLE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFLE(new IFLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFLE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(-1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFLE(new IFLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }       
  
  @Test
  void testIFLT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(-1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFLT(new IFLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFLT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIFLT(new IFLT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  } 

  @Test
  void testIFICMEQ_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPEQ(new IF_ICMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPEQ_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPEQ(new IF_ICMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  } 

  @Test
  void testIFICMNE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPNE(new IF_ICMPNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPNE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPNE(new IF_ICMPNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFICMLE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPLE(new IF_ICMPLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPLE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPLE(new IF_ICMPLE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFICMGT_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPGT(new IF_ICMPGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPGT_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPGT(new IF_ICMPGT(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFICMGE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    frame.push(new IntValue(0))
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIF_ICMPGE(new IF_ICMPGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFICMPGE_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(0))
    frame.push(new IntValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ICMPGE(new IF_ICMPGE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFACMPEQ_true() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    frame.push(v)
    frame.push(v)
    interpreter.setNext(new SPECIAL(1)) 
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPEQ(new IF_ACMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFACMPEQ_false() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new ObjectValue(1))
    frame.push(new ObjectValue(1))
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPEQ(new IF_ACMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFACMPNE_true() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new ObjectValue(1))
    frame.push(new ObjectValue(1))
    interpreter.setNext(new SPECIAL(1)) 
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPNE(new IF_ACMPNE(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFACMPNE_false() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    frame.push(v)
    frame.push(v)
    // If evaluate to false, the next should not be SPECIAL
    interpreter.visitIF_ACMPEQ(new IF_ACMPEQ(0, 0, 1))
    // For the true branch, see SnoopInstructionMethodAdapter
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testGetValueInt_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_int(new GETVALUE_int(1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueInt_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_int(new GETVALUE_int(1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueLong_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(new LongValue(1L))
    interpreter.visitGETVALUE_long(new GETVALUE_long(1L))
    assertEquals(new LongValue(1L), frame.peek2())
  }

  @Test
  void testGetValueLong_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push2(PlaceHolder.instance)
    interpreter.visitGETVALUE_long(new GETVALUE_long(1L))
    assertEquals(new LongValue(1L), frame.peek2())
  }


  @Test
  void testGetValueShort_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_short(new GETVALUE_short((short)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueShort_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_short(new GETVALUE_short((short)1))
    assertEquals(new IntValue(1), frame.peek())
  }  

  @Test
  void testGetValueChar_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_char(new GETVALUE_char((char)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueChar_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_char(new GETVALUE_char((char)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueBoolean_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_boolean(new GETVALUE_boolean(true))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueBoolean_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_boolean(new GETVALUE_boolean(false))
    assertEquals(new IntValue(0), frame.peek())
  }

  @Test
  void testGetValueByte_fail() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(PlaceHolder.instance)
    interpreter.visitGETVALUE_byte(new GETVALUE_byte((byte)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testGetValueByte_pass() {
    Frame frame = interpreter.getCurrentFrame()
    frame.push(new IntValue(1))
    interpreter.visitGETVALUE_byte(new GETVALUE_byte((byte)1))
    assertEquals(new IntValue(1), frame.peek())
  }

  @Test
  void testIFNULL_pass() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    frame.push(v)
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFNULL( new IFNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFNULL_fail() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    v.setAddress(10)
    frame.push(v)
    interpreter.visitIFNULL( new IFNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testIFNONNULL_pass() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    v.setAddress(10)
    frame.push(v)
    interpreter.setNext(new SPECIAL(1)) 
    interpreter.visitIFNONNULL( new IFNONNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
    verify(coverage).visitBranch(0, true)
  }

  @Test
  void testIFNONNULL_fail() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1, 0)
    frame.push(v)
    interpreter.visitIFNONNULL( new IFNONNULL(0, 0, 1))
    assertEquals(1, history.getHistory().size())
    def branch = (BranchElement) history.getHistory().get(0)
    assertFalse(branch.branch)
    verify(coverage).visitBranch(0, false)
  }

  @Test
  void testCASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitCASTORE(new CASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testCALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitCALOAD(new CALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testBASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitBASTORE(new BASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testBALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitBALOAD(new BALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testCALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitCALOAD(new CALOAD(0, 0))

    IntValue result = (IntValue) frame.peek()
    assertEquals(2, result.concrete)
    println(result.getSymbolic()) // TODO: how to test this?

    assertEquals(1, history.getHistory().size())
    BranchElement branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
  }

  @Test
  void testLALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new LongValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitLALOAD(new LALOAD(0, 0))
    assertEquals(new LongValue(2), frame.peek2())
  }

  @Test
  void testLASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push2(new LongValue(2)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitLASTORE(new LASTORE(0, 0))
    assertEquals(new LongValue(2), v.getFields()[0])
  }

  @Test
  void testLALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new LongValue(2))
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitLALOAD(new LALOAD(0, 0))

    LongValue result = (LongValue) frame.peek2()
    assertEquals(2L, result.concrete)
    println(result.getSymbolic()) // TODO: how to test this?

    assertEquals(1, history.getHistory().size())
    BranchElement branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
  }
  @Test
  void testIASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitIASTORE(new IASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testIALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitIALOAD(new IALOAD(0, 0))
    assertEquals(new IntValue(2), frame.peek())
  }

  @Test
  void testIALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    v.setField(0, new IntValue(2))
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitIALOAD(new IALOAD(0, 0))

    IntValue result = (IntValue) frame.peek()
    assertEquals(2, result.concrete)
    println(result.getSymbolic()) // TODO: how to test this?

    assertEquals(1, history.getHistory().size())
    BranchElement branch = (BranchElement) history.getHistory().get(0)
    assertTrue(branch.branch)
  }

  @Test
  void testAASTORE() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    frame.push(v)
    frame.push(new IntValue(0)) // index
    frame.push(new IntValue(1)) // value
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitAASTORE(new AASTORE(0, 0))
    assertEquals(new IntValue(1), v.getFields()[0])
  }

  @Test
  void testAALOAD() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    ObjectValue val = new ObjectValue(0, 1)
    v.setField(0, val)
    frame.push(v)
    frame.push(new IntValue(0)) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitAALOAD(new AALOAD(0, 0))
    assertEquals(val, frame.peek())
  }

  @Test
  void testAALOAD_Symbol() {
    Frame frame = interpreter.getCurrentFrame()
    def v = new ObjectValue(1)
    v.setAddress(1) // Arbitrary address
    ObjectValue val = new ObjectValue(0, 1)
    v.setField(0, val)
    frame.push(v)

    SymbolicInt x = new SymbolicInt(1)
    IntValue idx = new IntValue(0, x)
    frame.push(idx) // index
    interpreter.setNext(new SPECIAL(0)) // exception handling
    interpreter.visitAALOAD(new AALOAD(0, 0))

    ObjectValue result = (ObjectValue) frame.peek()
    assertEquals(val.address, result.address)
    println(result.getSymbolic()) // TODO: how to test this?
  }  

  @Test
  void testLDC_int() {
    IntValue x = new IntValue(1)
    interpreter.visitLDC_int(new LDC_int(0, 0, 1))
    assertEquals(x, interpreter.getCurrentFrame().peek())
  }

  @Test
  void testLDC_long() {
    LongValue x = new LongValue(1L)
    interpreter.visitLDC_long(new LDC_long(0, 0, 1L))
    assertEquals(x, interpreter.getCurrentFrame().peek2())
  }

  @Test
  void testILOAD() {
    IntValue e = new IntValue(1)
    Frame frame = interpreter.getCurrentFrame()
    frame.setLocal(1, e)
    interpreter.visitILOAD(new ILOAD(0, 0, 1))
    assertEquals(e, frame.peek())
  }

  @Test
  void testISTORE() {
    IntValue e = new IntValue(1)
    Frame frame = interpreter.getCurrentFrame()
    frame.push(e)
    interpreter.visitISTORE(new ISTORE(0, 0, 1))
    assertEquals(e, frame.getLocal(1))
  }
}