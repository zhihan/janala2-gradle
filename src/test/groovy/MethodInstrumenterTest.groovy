package janala.instrument

import static org.junit.Assert.assertEquals

import org.objectweb.asm.Opcodes
import org.objectweb.asm.Label
import org.objectweb.asm.ClassReader
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.TryCatchBlockNode

import java.io.InputStream
import janala.config.Config
import janala.testing.MethodRecorder

import org.junit.Before
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class MethodInstrumenterTest {
  private MethodRecorder recorder
  private SnoopInstructionMethodAdapter ma

  @Before
  void setup() {
    Coverage cov = Coverage.get()
    GlobalStateForInstrumentation.instance.reset()
    Config.instance.analysisClass = "MyAnalysisClass"

    recorder = new MethodRecorder()
    ma = new SnoopInstructionMethodAdapter(recorder.getVisitor(), false)
  }

  private void testInsn(int opcode, String name) {
    ma.visitInsn(opcode)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(II)V")
    ev.visitInsn(opcode)

    assertEquals(expected, recorder)
  }

  @Test
  void testNOP() {
    testInsn(Opcodes.NOP, "NOP")
  }

  @Test
  void testACONST_NULL() {
    testInsn(Opcodes.ACONST_NULL, "ACONST_NULL")
  }

  @Test
  void testICONST_M1() {
    testInsn(Opcodes.ICONST_M1, "ICONST_M1")
  }

  @Test
  void testICONST_0() {
    testInsn(Opcodes.ICONST_0, "ICONST_0")
  }

  @Test
  void testICONST_1() {
    testInsn(Opcodes.ICONST_1, "ICONST_1")
  }

  @Test
  void testICONST_2() {
    testInsn(Opcodes.ICONST_2, "ICONST_2")
  }

  @Test
  void testICONST_3() {
    testInsn(Opcodes.ICONST_3, "ICONST_3")
  }

  @Test
  void testICONST_4() {
    testInsn(Opcodes.ICONST_4, "ICONST_4")
  }

  @Test
  void testICONST_5() {
    testInsn(Opcodes.ICONST_5, "ICONST_5")
  }

  @Test
  void testLCONST_0() {
    testInsn(Opcodes.LCONST_0, "LCONST_0")
  }

  @Test
  void testLCONST_1() {
    testInsn(Opcodes.LCONST_1, "LCONST_1")
  }

  @Test
  void testFCONST_0() {
    testInsn(Opcodes.FCONST_0, "FCONST_0")
  }

  @Test
  void testFCONST_1() {
    testInsn(Opcodes.FCONST_1, "FCONST_1")
  }

  @Test
  void testFCONST_2() {
    testInsn(Opcodes.FCONST_2, "FCONST_2")
  }

  @Test
  void testDCONST_0() {
    testInsn(Opcodes.DCONST_0, "DCONST_0")
  }

  @Test
  void testDCONST_1() {
    testInsn(Opcodes.DCONST_1, "DCONST_1")
  }

  @Test
  void testPOP() {
    testInsn(Opcodes.POP, "POP")
  }

  @Test
  void testPOP2() {
    testInsn(Opcodes.POP2, "POP2")
  }

  @Test
  void testDUP() {
    testInsn(Opcodes.DUP, "DUP")
  }

  @Test
  void testDUP_X1() {
    testInsn(Opcodes.DUP_X1, "DUP_X1")
  }

  @Test
  void testDUP_X2() {
    testInsn(Opcodes.DUP_X2, "DUP_X2")
  }

  @Test
  void testDUP2() {
    testInsn(Opcodes.DUP2, "DUP2")
  }

  @Test
  void testDUP2_X1() {
    testInsn(Opcodes.DUP2_X1, "DUP2_X1")
  }

  @Test
  void testDUP2_X2() {
    testInsn(Opcodes.DUP2_X2, "DUP2_X2")
  }

  @Test
  void testSWAP() {
    testInsn(Opcodes.SWAP, "SWAP")
  }

  @Test
  void testIADD() {
    testInsn(Opcodes.IADD, "IADD")
  }

  @Test
  void testLADD() {
    testInsn(Opcodes.LADD, "LADD")
  }

  @Test
  void testFADD() {
    testInsn(Opcodes.FADD, "FADD")
  }

  @Test
  void testDADD() {
    testInsn(Opcodes.DADD, "DADD")
  }

  @Test
  void testISUB() {
    testInsn(Opcodes.ISUB, "ISUB")
  }

  @Test
  void testLSUB() {
    testInsn(Opcodes.LSUB, "LSUB")
  }

  @Test
  void testFSUB() {
    testInsn(Opcodes.FSUB, "FSUB")
  }

  @Test
  void testDSUB() {
    testInsn(Opcodes.DSUB, "DSUB")
  }

  @Test
  void testIMUL() {
    testInsn(Opcodes.IMUL, "IMUL")
  }

  @Test
  void testLMUL() {
    testInsn(Opcodes.LMUL, "LMUL")
  }

  @Test
  void testFMUL() {
    testInsn(Opcodes.FMUL, "FMUL")
  }

  @Test
  void testDMUL() {
    testInsn(Opcodes.DMUL, "DMUL")
  }

  @Test
  void testFDIV() {
    testInsn(Opcodes.FDIV, "FDIV")
  }

  @Test
  void testDDIV() {
    testInsn(Opcodes.DDIV, "DDIV")
  }

  @Test
  void testFREM() {
    testInsn(Opcodes.FREM, "FREM")
  }

  @Test
  void testDREM() {
    testInsn(Opcodes.DREM, "DREM")
  }

  @Test
  void testINEG() {
    testInsn(Opcodes.INEG, "INEG")
  }

  @Test
  void testLNEG() {
    testInsn(Opcodes.LNEG, "LNEG")
  }

  @Test
  void testFNEG() {
    testInsn(Opcodes.FNEG, "FNEG")
  }

  @Test
  void testDNEG() {
    testInsn(Opcodes.DNEG, "DNEG")
  }

  @Test
  void testISHL() {
    testInsn(Opcodes.ISHL, "ISHL")
  }

  @Test
  void testISHR() {
    testInsn(Opcodes.ISHR, "ISHR")
  }

  @Test
  void testLSHL() {
    testInsn(Opcodes.LSHL, "LSHL")
  }

  @Test
  void testLSHR() {
    testInsn(Opcodes.LSHR, "LSHR")
  }

  @Test
  void testIUSHR() {
    testInsn(Opcodes.IUSHR, "IUSHR")
  }

  @Test
  void testLUSHR() {
    testInsn(Opcodes.LUSHR, "LUSHR")
  }

  @Test
  void testIAND() {
    testInsn(Opcodes.IAND, "IAND")
  }

  @Test
  void testLAND() {
    testInsn(Opcodes.LAND, "LAND")
  }

  @Test
  void testIOR() {
    testInsn(Opcodes.IOR, "IOR")
  }

  @Test
  void testLOR() {
    testInsn(Opcodes.LOR, "LOR")
  }

  @Test
  void testIXOR() {
    testInsn(Opcodes.IXOR, "IXOR")
  }

  @Test
  void testLXOR() {
    testInsn(Opcodes.LXOR, "LXOR")
  }

  @Test
  void testI2L() {
    testInsn(Opcodes.I2L, "I2L")
  }

  @Test
  void testI2F() {
    testInsn(Opcodes.I2F, "I2F")
  }

  @Test
  void testI2D() {
    testInsn(Opcodes.I2D, "I2D")
  }

  @Test
  void testL2I() {
    testInsn(Opcodes.L2I, "L2I")
  }

  @Test
  void testL2F() {
    testInsn(Opcodes.L2F, "L2F")
  }

  @Test
  void testL2D() {
    testInsn(Opcodes.L2D, "L2D")
  }

  @Test
  void testF2I() {
    testInsn(Opcodes.F2I, "F2I")
  }

  @Test
  void testF2L() {
    testInsn(Opcodes.F2L, "F2L")
  }

  @Test
  void testF2D() {
    testInsn(Opcodes.F2D, "F2D")
  }

  @Test
  void testD2I() {
    testInsn(Opcodes.D2I, "D2I")
  }

  @Test
  void testD2L() {
    testInsn(Opcodes.D2L, "D2L")
  }

  @Test
  void testD2F() {
    testInsn(Opcodes.D2F, "D2F")
  }

  @Test
  void testI2B() {
    testInsn(Opcodes.I2B, "I2B")
  }

  @Test
  void testI2C() {
    testInsn(Opcodes.I2C, "I2C")
  }

  @Test
  void testI2S() {
    testInsn(Opcodes.I2S, "I2S")
  }

  @Test
  void testLCMP() {
    testInsn(Opcodes.LCMP, "LCMP")
  }

  @Test
  void testFCMPL() {
    testInsn(Opcodes.FCMPL, "FCMPL")
  } 

  @Test
  void testFCMPG() {
    testInsn(Opcodes.FCMPG, "FCMPG")
  }  

  @Test
  void testDCMPL() {
    testInsn(Opcodes.DCMPL, "DCMPL")
  } 

  @Test
  void testDCMPG() {
    testInsn(Opcodes.DCMPG, "DCMPG")
  }  

  @Test
  void testIRETURN() {
    testInsn(Opcodes.IRETURN, "IRETURN")
  }

  @Test
  void testFRETURN() {
    testInsn(Opcodes.FRETURN, "FRETURN")
  }

  @Test
  void testLRETURN() {
    testInsn(Opcodes.LRETURN, "LRETURN")
  }

  @Test
  void testDRETURN() {
    testInsn(Opcodes.DRETURN, "DRETURN")
  }

  @Test
  void testRETURN() {
    testInsn(Opcodes.RETURN, "RETURN")
  }

  @Test
  void testARETURN() {
    testInsn(Opcodes.ARETURN, "ARETURN")
  }

  @Test
  void testATHROW() {
    testInsn(Opcodes.ATHROW, "ATHROW")
  }

  private void testInsnWithExceptionAndValue(int opcode, 
      String name, String type, String prefix) {
    ma.visitInsn(opcode)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(II)V")
    ev.visitInsn(opcode)

    Utils.addBipushInsn(ev, 0) // exception-free path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");
    Utils.addValueReadInsn(ev, type, prefix)

    assertEquals(expected, recorder)
  }

  @Test
  void testIALOAD() {
    testInsnWithExceptionAndValue(Opcodes.IALOAD, "IALOAD", "I", "GETVALUE_")
  }

  @Test
  void testLALOAD() {
    testInsnWithExceptionAndValue(Opcodes.LALOAD, "LALOAD", "J", "GETVALUE_")
  }

  @Test
  void testFALOAD() {
    testInsnWithExceptionAndValue(Opcodes.FALOAD, "FALOAD", "F", "GETVALUE_")
  }

  @Test
  void testDALOAD() {
    testInsnWithExceptionAndValue(Opcodes.DALOAD, "DALOAD", "D", "GETVALUE_")
  }

  @Test
  void testBALOAD() {
    testInsnWithExceptionAndValue(Opcodes.BALOAD, "BALOAD", "B", "GETVALUE_")
  }

  @Test
  void testCALOAD() {
    testInsnWithExceptionAndValue(Opcodes.CALOAD, "CALOAD", "C", "GETVALUE_")
  }

  @Test
  void testSALOAD() {
    testInsnWithExceptionAndValue(Opcodes.SALOAD, "SALOAD", "S", "GETVALUE_")
  }

  @Test
  void testAALOAD() {
    testInsnWithExceptionAndValue(Opcodes.AALOAD, "AALOAD", "Ljava/lang/Object;", "GETVALUE_")
  }

  @Test
  void testARRAYLENGTH() {
    testInsnWithExceptionAndValue(Opcodes.ARRAYLENGTH, 
      "ARRAYLENGTH", "I", "GETVALUE_")
  }

  private void testInsnWithException(int opcode, String name) {
    ma.visitInsn(opcode)
    
    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, name, "(II)V")
    ev.visitInsn(opcode)

    Utils.addBipushInsn(ev, 0) // exception-free path
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "SPECIAL", "(I)V");

    assertEquals(expected, recorder)
  }

  @Test
  void testIASTORE() {
    testInsnWithException(Opcodes.IASTORE, "IASTORE")
  }

  @Test
  void testLASTORE() {
    testInsnWithException(Opcodes.LASTORE, "LASTORE")
  }

  @Test
  void testFASTORE() {
    testInsnWithException(Opcodes.FASTORE, "FASTORE")
  }

  @Test
  void testDASTORE() {
    testInsnWithException(Opcodes.DASTORE, "DASTORE")
  }

  @Test
  void testBASTORE() {
    testInsnWithException(Opcodes.BASTORE, "BASTORE")
  }

  @Test
  void testCASTORE() {
    testInsnWithException(Opcodes.CASTORE, "CASTORE")
  }

  @Test
  void testSASTORE() {
    testInsnWithException(Opcodes.SASTORE, "SASTORE")
  }

  @Test
  void testAASTORE() {
    testInsnWithException(Opcodes.AASTORE, "AASTORE")
  }

  @Test
  void testIDIV() {
    testInsnWithException(Opcodes.IDIV, "IDIV")
  }

  @Test
  void testLDIV() {
    testInsnWithException(Opcodes.LDIV, "LDIV")
  }

  @Test
  void testIREM() {
    testInsnWithException(Opcodes.IREM, "IREM")
  }

  @Test
  void testLREM() {
    testInsnWithException(Opcodes.LREM, "LREM")
  }

  @Test
  void testMONITORENTER() {
    testInsnWithException(Opcodes.MONITORENTER, "MONITORENTER")
  }

  @Test
  void testMONITOREXIT() {
    testInsnWithException(Opcodes.MONITOREXIT, "MONITOREXIT")
  }

  @Test
  void testILOAD() {
    ma.visitVarInsn(Opcodes.ILOAD, 1)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, "ILOAD", "(III)V")
    ev.visitVarInsn(Opcodes.ILOAD, 1)
    ev.visitInsn(Opcodes.DUP)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, "GETVALUE_int", "(I)V")

    assertEquals(expected, recorder)
  }

  @Test
  void testISTORE() {
    ma.visitVarInsn(Opcodes.ISTORE, 1)

    MethodRecorder expected = new MethodRecorder()
    MethodVisitor ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC,
      Config.instance.analysisClass, "ISTORE", "(III)V")
    ev.visitVarInsn(Opcodes.ISTORE, 1)

    assertEquals(expected, recorder)
  }
}
