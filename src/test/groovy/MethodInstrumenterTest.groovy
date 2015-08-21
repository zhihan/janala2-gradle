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

  private void testInsnWithException(int opcode, String name, String type) {
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
    testInsnWithException(Opcodes.IASTORE, "IASTORE", "I")
  }

  @Test
  void testLASTORE() {
    testInsnWithException(Opcodes.LASTORE, "LASTORE", "J")
  }

  @Test
  void testFASTORE() {
    testInsnWithException(Opcodes.FASTORE, "FASTORE", "F")
  }

  @Test
  void testDASTORE() {
    testInsnWithException(Opcodes.DASTORE, "DASTORE", "D")
  }

  @Test
  void testBASTORE() {
    testInsnWithException(Opcodes.BASTORE, "BASTORE", "B")
  }

  @Test
  void testCASTORE() {
    testInsnWithException(Opcodes.CASTORE, "CASTORE", "C")
  }

  @Test
  void testSASTORE() {
    testInsnWithException(Opcodes.SASTORE, "SASTORE", "S")
  }

  @Test
  void testAASTORE() {
    testInsnWithException(Opcodes.AASTORE, "AASTORE", "Ljava/lang/Object;")
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
