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

  @Test
  void testICONST_0() {
    ma.visitInsn(Opcodes.ICONST_0)

    MethodRecorder expected = new MethodRecorder()
    def ev = expected.getVisitor()
    ev.visitInsn(Opcodes.ICONST_1)
    ev.visitInsn(Opcodes.ICONST_0)
    ev.visitMethodInsn(Opcodes.INVOKESTATIC, 
      Config.instance.analysisClass, "ICONST_0", "(II)V")
    ev.visitInsn(Opcodes.ICONST_0)
    
    assertEquals(expected, recorder)
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