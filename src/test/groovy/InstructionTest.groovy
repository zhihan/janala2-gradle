package janala.logger.inst

import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.junit.Before
import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class InstrutionTest {
  private IVisitor visitor

  private testToString(Object o) {
    String s = o.toString()
    assertTrue(s.contains(o.getClass().getSimpleName()))
  }

  @Before
  void setup() {
    visitor = mock(IVisitor.class)
  }

  @Test
  void testAALOAD() {
    def insn = new AALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitAALOAD(insn)
  }

  @Test
  void testAASTORE() {
    def insn = new AASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitAASTORE(insn)
  }

  @Test
  void testACONST_NULL() {
    def insn = new ACONST_NULL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitACONST_NULL(insn)
  }

  @Test
  void testALOAD() {
    def insn = new ALOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitALOAD(insn)
  }

  @Test
  void testANEWARRAY() {
    def insn = new ANEWARRAY(0, 0, "java/lang/String")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitANEWARRAY(insn)
  }

  @Test
  void testARETURN() {
    def insn = new ARETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitARETURN(insn)
  }

  @Test
  void testARRAYLENGTH() {
    def insn = new ARRAYLENGTH(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitARRAYLENGTH(insn)
  }

  @Test
  void testASTORE() {
    def insn = new ASTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitASTORE(insn)
  }

  @Test
  void testATHROW() {
    def insn = new ATHROW(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitATHROW(insn)
  }

  @Test
  void testBALOAD() {
    def insn = new BALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitBALOAD(insn)
  }

  @Test
  void testBASTORE() {
    def insn = new BASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitBASTORE(insn)
  }

  @Test
  void testBIPUSH() {
    def insn = new BIPUSH(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitBIPUSH(insn)
  }

  @Test
  void testCALOAD() {
    def insn = new CALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitCALOAD(insn)
  }

  @Test
  void testCASTORE() {
    def insn = new CASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitCASTORE(insn)
  }

  @Test
  void testCHECKCAST() {
    def insn = new CHECKCAST(0, 0, "java/lang/String")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitCHECKCAST(insn)
  }
  
  @Test
  void testD2F() {
    def insn = new D2F(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitD2F(insn)
  }

  @Test
  void testD2I() {
    def insn = new D2I(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitD2I(insn)
  }

  @Test
  void testD2L() {
    def insn = new D2L(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitD2L(insn)
  }
   
  @Test
  void testDADD() {
    def insn = new DADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDADD(insn)
  }
   
  @Test
  void testDALOAD() {
    def insn = new DALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDALOAD(insn)
  }

  @Test
  void testDASTORE() {
    def insn = new DASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDASTORE(insn)
  }

  @Test
  void testDCMPG() {
    def insn = new DCMPG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCMPG(insn)
  }

  @Test
  void testDCMPL() {
    def insn = new DCMPL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCMPL(insn)
  }

  @Test
  void testDCONST_0() {
    def insn = new DCONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCONST_0(insn)
  }
      
  @Test
  void testDCONST_1() {
    def insn = new DCONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDCONST_1(insn)
  }

  @Test
  void testDDIV() {
    def insn = new DDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDDIV(insn)
  }
  
  @Test
  void testDLOAD() {
    def insn = new DLOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDLOAD(insn)
  }

  @Test
  void testDMUL() {
    def insn = new DMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDMUL(insn)
  }

  @Test
  void testDNEG() {
    def insn = new DNEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDNEG(insn)
  }

  @Test
  void testDREM() {
    def insn = new DREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDREM(insn)
  }

  @Test
  void testDRETURN() {
    def insn = new DRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDRETURN(insn)
  }

  @Test
  void testDSTORE() {
    def insn = new DSTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDSTORE(insn)
  }

  @Test
  void testDSUB() {
    def insn = new DSUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDSUB(insn)
  }

  @Test
  void testDUP() {
    def insn = new DUP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP(insn)
  }

  @Test
  void testDUP2() {
    def insn = new DUP2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP2(insn)
  }

  @Test
  void testDUP2_X1() {
    def insn = new DUP2_X1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP2_X1(insn)
  }

  @Test
  void testDUP2_X2() {
    def insn = new DUP2_X2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP2_X2(insn)
  }

  @Test
  void testDUP_X1() {
    def insn = new DUP_X1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP_X1(insn)
  }

  @Test
  void testDUP_X2() {
    def insn = new DUP_X2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitDUP_X2(insn)
  }

  @Test
  void testF2D() {
    def insn = new F2D(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitF2D(insn)
  }

  @Test
  void testF2I() {
    def insn = new F2I(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitF2I(insn)
  }

  @Test
  void testF2L() {
    def insn = new F2L(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitF2L(insn)
  }

  @Test
  void testFADD() {
    def insn = new FADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFADD(insn)
  }

  @Test
  void testFALOAD() {
    def insn = new FALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFALOAD(insn)
  }

  @Test
  void testFASTORE() {
    def insn = new FASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFASTORE(insn)
  }

  @Test
  void testFCMPG() {
    def insn = new FCMPG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCMPG(insn)
  }

  @Test
  void testFCMPL() {
    def insn = new FCMPL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCMPL(insn)
  }

  @Test
  void testFCONST_0() {
    def insn = new FCONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCONST_0(insn)
  }

  @Test
  void testFCONST_1() {
    def insn = new FCONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCONST_1(insn)
  }

  @Test
  void testFCONST_2() {
    def insn = new FCONST_2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFCONST_2(insn)
  }

  @Test
  void testFDIV() {
    def insn = new FDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFDIV(insn)
  }

  @Test
  void testFLOAD() {
    def insn = new FLOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFLOAD(insn)
  }

  @Test
  void testFSTORE() {
    def insn = new FSTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFSTORE(insn)
  }

  @Test
  void testFMUL() {
    def insn = new FMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFMUL(insn)
  }

  @Test
  void testFNEG() {
    def insn = new FNEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFNEG(insn)
  }

  @Test
  void testFREM() {
    def insn = new FREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFREM(insn)
  }

  @Test
  void testFRETURN() {
    def insn = new FRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFRETURN(insn)
  }

  @Test
  void testFSUB() {
    def insn = new FSUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitFSUB(insn)
  }

  @Test
  void testGETFIELD() {
    def insn = new GETFIELD(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETFIELD(insn)
  }

  @Test
  void testGETSTATIC() {
    def insn = new GETSTATIC(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETSTATIC(insn)
  }

  @Test
  void testGETVALUE_Object() {
    def insn = new GETVALUE_Object(0, "a", true)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_Object(insn)
  }

  @Test
  void testGETVALUE_boolean() {
    def insn = new GETVALUE_boolean(false)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_boolean(insn)
  }

  @Test
  void testGETVALUE_byte() {
    def insn = new GETVALUE_byte((byte)1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_byte(insn)
  }

  @Test
  void testGETVALUE_char() {
    def insn = new GETVALUE_char((char)1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_char(insn)
  }

  @Test
  void testGETVALUE_double() {
    def insn = new GETVALUE_double(1.0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_double(insn)
  }

  @Test
  void testGETVALUE_float() {
    def insn = new GETVALUE_float(1.0F)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_float(insn)
  }

  @Test
  void testGETVALUE_int() {
    def insn = new GETVALUE_int(1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_int(insn)
  }

  @Test
  void testGETVALUE_long() {
    def insn = new GETVALUE_long(1L)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_long(insn)
  }

  @Test
  void testGETVALUE_short() {
    def insn = new GETVALUE_short((short)1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_short(insn)
  }

  @Test
  void testGETVALUE_void() {
    def insn = new GETVALUE_void()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGETVALUE_void(insn)
  }

  @Test
  void testGOTO() {
    def insn = new GOTO(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitGOTO(insn)
  }

  @Test
  void testI2B() {
    def insn = new I2B(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2B(insn)
  }

  @Test
  void testI2C() {
    def insn = new I2C(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2C(insn)
  }

  @Test
  void testI2D() {
    def insn = new I2D(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2D(insn)
  }

  @Test
  void testI2F() {
    def insn = new I2F(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2F(insn)
  }

  @Test
  void testI2L() {
    def insn = new I2L(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2L(insn)
  }

  @Test
  void testI2S() {
    def insn = new I2S(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitI2S(insn)
  }

  @Test
  void testIADD() {
    def insn = new IADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIADD(insn)
  } 

  @Test
  void testIALOAD() {
    def insn = new IALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIALOAD(insn)
  }

  @Test
  void testIAND() {
    def insn = new IAND(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIAND(insn)
  }

  @Test
  void testIASTORE() {
    def insn = new IASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIASTORE(insn)
  }

  @Test
  void testICONST_0() {
    def insn = new ICONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_0(insn)
  }

  @Test
  void testICONST_1() {
    def insn = new ICONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_1(insn)
  }

  @Test
  void testICONST_2() {
    def insn = new ICONST_2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_2(insn)
  }

  @Test
  void testICONST_3() {
    def insn = new ICONST_3(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_3(insn)
  }

  @Test
  void testICONST_4() {
    def insn = new ICONST_4(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_4(insn)
  }

  @Test
  void testICONST_5() {
    def insn = new ICONST_5(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_5(insn)
  }

  @Test
  void testICONST_M1() {
    def insn = new ICONST_M1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitICONST_M1(insn)
  }

  @Test
  void testIDIV() {
    def insn = new IDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIDIV(insn)
  }

  @Test
  void testIFEQ() {
    def insn = new IFEQ(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFEQ(insn)
  }

  @Test
  void testIFGE() {
    def insn = new IFGE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFGE(insn)
  }

  @Test
  void testIFGT() {
    def insn = new IFGT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFGT(insn)
  }

  @Test
  void testIFLE() {
    def insn = new IFLE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFLE(insn)
  }

  @Test
  void testIFLT() {
    def insn = new IFLT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFLT(insn)
  }

  @Test
  void testIFNE() {
    def insn = new IFNE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFNE(insn)
  }

  @Test
  void testIFNONNULL() {
    def insn = new IFNONNULL(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFNONNULL(insn)
  }

  @Test
  void testIFNULL() {
    def insn = new IFNULL(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIFNULL(insn)
  }

  @Test
  void testIF_ACMPEQ() {
    def insn = new IF_ACMPEQ(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ACMPEQ(insn)
  }

  @Test
  void testIF_ACMPNE() {
    def insn = new IF_ACMPNE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ACMPNE(insn)
  }

  @Test
  void testIF_ICMPEQ() {
    def insn = new IF_ICMPEQ(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPEQ(insn)
  }

  @Test
  void testIF_ICMPGE() {
    def insn = new IF_ICMPGE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPGE(insn)
  }

  @Test
  void testIF_ICMPGT() {
    def insn = new IF_ICMPGT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPGT(insn)
  }

  @Test
  void testIF_ICMPLE() {
    def insn = new IF_ICMPLE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPLE(insn)
  }

  @Test
  void testIF_ICMPLT() {
    def insn = new IF_ICMPLT(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPLT(insn)
  }

  @Test
  void testIF_ICMPNE() {
    def insn = new IF_ICMPNE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIF_ICMPNE(insn)
  }

  @Test
  void testIINC() {
    def insn = new IINC(0, 0, 1, 2)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIINC(insn)
  }

  @Test
  void testILOAD() {
    def insn = new ILOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitILOAD(insn)
  }

  @Test
  void testIMUL() {
    def insn = new IMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIMUL(insn)
  }

  @Test
  void testINEG() {
    def insn = new INEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINEG(insn)
  }

  @Test
  void testINSTANCEOF() {
    def insn = new INSTANCEOF(0, 0, "java/lang/String")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINSTANCEOF(insn)
  }

  @Test
  void testINVOKEINTERFACE() {
    def insn = new INVOKEINTERFACE(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEINTERFACE(insn)
  }

  @Test
  void testINVOKEMETHOD_END() {
    def insn = new INVOKEMETHOD_END()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEMETHOD_END(insn)
  }

  @Test
  void testINVOKEMETHOD_EXCEPTION() {
    def insn = new INVOKEMETHOD_EXCEPTION()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEMETHOD_EXCEPTION(insn)
  }

  @Test
  void testINVOKESPECIAL() {
    def insn = new INVOKESPECIAL(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKESPECIAL(insn)
  }

  @Test
  void testINVOKESTATIC() {
    def insn = new INVOKESTATIC(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKESTATIC(insn)
  }

  @Test
  void testINVOKEVIRTUAL() {
    def insn = new INVOKEVIRTUAL(0, 0, "owner", "method", "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitINVOKEVIRTUAL(insn)
  }

  @Test
  void testIOR() {
    def insn = new IOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIOR(insn)
  }

  @Test
  void testIREM() {
    def insn = new IREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIREM(insn)
  }

  @Test
  void testIRETURN() {
    def insn = new IRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIRETURN(insn)
  }

  @Test
  void testISHL() {
    def insn = new ISHL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISHL(insn)
  }

  @Test
  void testISHR() {
    def insn = new ISHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISHR(insn)
  }

  @Test
  void testISTORE() {
    def insn = new ISTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISTORE(insn)
  }

  @Test
  void testISUB() {
    def insn = new ISUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitISUB(insn)
  }

  @Test
  void testIUSHR() {
    def insn = new IUSHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIUSHR(insn)
  }

  @Test
  void testIXOR() {
    def insn = new IXOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitIXOR(insn)
  }

  @Test
  void testJSR() {
    def insn = new JSR(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitJSR(insn)
  }

  @Test
  void testL2D() {
    def insn = new L2D(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitL2D(insn)
  }

  @Test
  void testL2F() {
    def insn = new L2F(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitL2F(insn)
  }

  @Test
  void testL2I() {
    def insn = new L2I(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitL2I(insn)
  }

  @Test
  void testLADD() {
    def insn = new LADD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLADD(insn)
  }

  @Test
  void testLALOAD() {
    def insn = new LALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLALOAD(insn)
  }

  @Test
  void testLAND() {
    def insn = new LAND(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLAND(insn)
  }

  @Test
  void testLASTORE() {
    def insn = new LASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLASTORE(insn)
  }

  @Test
  void testLCMP() {
    def insn = new LCMP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLCMP(insn)
  }

  @Test
  void testLCONST_0() {
    def insn = new LCONST_0(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLCONST_0(insn)
  }

  @Test
  void testLCONST_1() {
    def insn = new LCONST_1(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLCONST_1(insn)
  }

  @Test
  void testLDC_Object() {
    def insn = new LDC_Object(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_Object(insn)
  }

  @Test
  void testLDC_String() {
    def insn = new LDC_String(0, 0, "a", 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_String(insn)
  }

  @Test
  void testLDC_double() {
    def insn = new LDC_double(0, 0, 1.0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_double(insn)
  }

  @Test
  void testLDC_float() {
    def insn = new LDC_float(0, 0, 1.0F)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_float(insn)
  }

  @Test
  void testLDC_int() {
    def insn = new LDC_int(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_int(insn)
  }

  @Test
  void testLDC_long() {
    def insn = new LDC_long(0, 0, 1L)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDC_long(insn)
  }

  @Test
  void testLDIV() {
    def insn = new LDIV(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLDIV(insn)
  }

  @Test
  void testLLOAD() {
    def insn = new LLOAD(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLLOAD(insn)
  }

  @Test
  void testLMUL() {
    def insn = new LMUL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLMUL(insn)
  }

  @Test
  void testLNEG() {
    def insn = new LNEG(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLNEG(insn)
  }

  @Test
  void testLOOKUPSWITCH() {
    int[] x = new int[1]
    x[0] = 1
    int[] y = new int[1]
    y[0] = 2
    def insn = new LOOKUPSWITCH(0, 0, 1, x, y)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLOOKUPSWITCH(insn)
  }

  @Test
  void testLOR() {
    def insn = new LOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLOR(insn)
  }

  @Test
  void testLREM() {
    def insn = new LREM(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLREM(insn)
  }

  @Test
  void testLRETURN() {
    def insn = new LRETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLRETURN(insn)
  }

  @Test
  void testLSHL() {
    def insn = new LSHL(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSHL(insn)
  }

  @Test
  void testLSHR() {
    def insn = new LSHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSHR(insn)
  }

  @Test
  void testLSTORE() {
    def insn = new LSTORE(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSTORE(insn)
  }

  @Test
  void testLSUB() {
    def insn = new LSUB(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLSUB(insn)
  }

  @Test
  void testLUSHR() {
    def insn = new LUSHR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLUSHR(insn)
  }

  @Test
  void testLXOR() {
    def insn = new LXOR(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitLXOR(insn)
  }

  @Test
  void testMAKE_SYMBOLIC() {
    def insn = new MAKE_SYMBOLIC()
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMAKE_SYMBOLIC(insn)
  }

  @Test
  void testMONITORENTER() {
    def insn = new MONITORENTER(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMONITORENTER(insn)
  }

  @Test
  void testMONITOREXIT() {
    def insn = new MONITOREXIT(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMONITOREXIT(insn)
  }

  @Test
  void testMULTIANEWARRAY() {
    def insn = new MULTIANEWARRAY(0, 0, "I", 2)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitMULTIANEWARRAY(insn)
  }

  @Test
  void testNEW() {
    def insn = new NEW(0, 0, "java/lang/Integer", 2)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitNEW(insn)
  }

  @Test
  void testNEWARRAY() {
    def insn = new NEWARRAY(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitNEWARRAY(insn)
  }

  @Test
  void testNOP() {
    def insn = new NOP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitNOP(insn)
  }

  @Test
  void testPOP() {
    def insn = new POP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPOP(insn)
  }

  @Test
  void testPOP2() {
    def insn = new POP2(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPOP2(insn)
  }

  @Test
  void testPUTFIELD() {
    def insn = new PUTFIELD(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPUTFIELD(insn)
  }

  @Test void testPUTSTATIC() {
    def insn = new PUTSTATIC(0, 0, 1, 1, "I")
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitPUTSTATIC(insn)
  }

  @Test
  void testRET() {
    def insn = new RET(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitRET(insn)
  }

  @Test
  void testRETURN() {
    def insn = new RETURN(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitRETURN(insn)
  }

  @Test
  void testSALOAD() {
    def insn = new SALOAD(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSALOAD(insn)
  }

  @Test
  void testSASTORE() {
    def insn = new SASTORE(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSASTORE(insn)
  }

  @Test
  void testSIPUSH() {
    def insn = new SIPUSH(0, 0, 1)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSIPUSH(insn)
  }

  @Test
  void testSPECIAL() {
    def insn = new SPECIAL(0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSPECIAL(insn)
  }

  @Test
  void testSWAP() {
    def insn = new SWAP(0, 0)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitSWAP(insn)
  }

  @Test
  void testTABLESWITCH() {
    int[] tab = new int[1]
    tab[0] = 1
    def insn = new TABLESWITCH(0, 0, 0, 1, 1, tab)
    testToString(insn)
    insn.visit(visitor)
    verify(visitor).visitTABLESWITCH(insn)
  }
}