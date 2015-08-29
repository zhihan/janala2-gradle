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

    DJVM.LDC(0, 0, 0.2D)
    verify(intp).LDC(0, 0, 0.2D)

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

    DJVM.GOTO(0, 0, 1)
    verify(intp).GOTO(0, 0, 1)

    DJVM.JSR(0, 0, 1)
    verify(intp).JSR(0, 0, 1)
  }

  @Test
  void testInvoke() {
    DJVM.INVOKEVIRTUAL(0, 0, "owner", "method", "()I")
    verify(intp).INVOKEVIRTUAL(0, 0, "owner", "method", "()I")

    DJVM.INVOKESTATIC(0, 0, "owner", "method", "()I")
    verify(intp).INVOKESTATIC(0, 0, "owner", "method", "()I")
    
    DJVM.INVOKESPECIAL(0, 0, "owner", "method", "()I")
    verify(intp).INVOKESPECIAL(0, 0, "owner", "method", "()I")
    
    DJVM.INVOKEINTERFACE(0, 0, "owner", "method", "()I")
    verify(intp).INVOKEINTERFACE(0, 0, "owner", "method", "()I")
  }

  @Test
  void testField() {
    DJVM.GETFIELD(0, 0, 1, 1, "I")
    verify(intp).GETFIELD(0, 0, 1, 1, "I")

    DJVM.PUTFIELD(0, 0, 1, 1, "I")
    verify(intp).PUTFIELD(0, 0, 1, 1, "I")

    DJVM.GETSTATIC(0, 0, 1, 1, "I")
    verify(intp).GETSTATIC(0, 0, 1, 1, "I")

    DJVM.PUTSTATIC(0, 0, 1, 1, "I")
    verify(intp).PUTSTATIC(0, 0, 1, 1, "I")
  }

  @Test
  void testNew() {
    DJVM.NEW(0, 0, "MyClass", 0)
    verify(intp).NEW(0, 0, "MyClass", 0)
  }

  @Test
  void testTypeInsn() {
    DJVM.ANEWARRAY(0, 0, "MyClass")
    verify(intp).ANEWARRAY(0, 0, "MyClass")

    DJVM.CHECKCAST(0, 0, "MyClass")
    verify(intp).CHECKCAST(0, 0, "MyClass")

    DJVM.INSTANCEOF(0, 0, "MyClass")
    verify(intp).INSTANCEOF(0, 0, "MyClass")
  }

  @Test
  void testVarInsn(){
    DJVM.BIPUSH(0, 0, 1)
    verify(intp).BIPUSH(0, 0, 1)

    DJVM.SIPUSH(0, 0, 1)
    verify(intp).SIPUSH(0, 0, 1)

    DJVM.ILOAD(0, 0, 1)
    verify(intp).ILOAD(0, 0, 1)

    DJVM.ISTORE(0, 0, 1)
    verify(intp).ISTORE(0, 0, 1)

    DJVM.LLOAD(0, 0, 1)
    verify(intp).LLOAD(0, 0, 1)

    DJVM.LSTORE(0, 0, 1)
    verify(intp).LSTORE(0, 0, 1)

    DJVM.FLOAD(0, 0, 1)
    verify(intp).FLOAD(0, 0, 1)

    DJVM.FSTORE(0, 0, 1)
    verify(intp).FSTORE(0, 0, 1)

    DJVM.DLOAD(0, 0, 1)
    verify(intp).DLOAD(0, 0, 1)

    DJVM.DSTORE(0, 0, 1)
    verify(intp).DSTORE(0, 0, 1)

    DJVM.ALOAD(0, 0, 1)
    verify(intp).ALOAD(0, 0, 1)

    DJVM.ASTORE(0, 0, 1)
    verify(intp).ASTORE(0, 0, 1)

    DJVM.RET(0, 0, 1)
    verify(intp).RET(0, 0, 1)
  }


  @Test
  void testAllInsn() {
    DJVM.NOP(0, 0)
    verify(intp).NOP(0, 0)

    DJVM.NEWARRAY(0, 0)
    verify(intp).NEWARRAY(0, 0)

    DJVM.ACONST_NULL(0, 0)
    verify(intp).ACONST_NULL(0, 0)

  }
}