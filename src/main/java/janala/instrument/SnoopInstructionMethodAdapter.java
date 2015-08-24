package janala.instrument;

import janala.config.Config;
import janala.logger.ClassNames;
import janala.logger.ObjectInfo;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Label;
import java.util.LinkedList;

public class SnoopInstructionMethodAdapter extends MethodVisitor implements Opcodes {
  boolean isInit;
  boolean isSuperInitCalled;
  LinkedList<TryCatchBlock> tryCatchBlocks;
  private int line;
  boolean calledNew = false;
  private Coverage coverage;

  public SnoopInstructionMethodAdapter(MethodVisitor mv, boolean isInit, Coverage coverage) {
    super(ASM5, mv);
    this.isInit = isInit;
    this.isSuperInitCalled = false;
    tryCatchBlocks = new LinkedList<TryCatchBlock>();
    this.coverage = coverage;
  }

  @Override
  public void visitCode() {
    GlobalStateForInstrumentation.instance.incMid();
    coverage.setCidmidToName(GlobalStateForInstrumentation.instance.getMid());
    mv.visitCode();
  }

  /** Push a value onto the stack. */
  private static void addBipushInsn(MethodVisitor mv, int val) {
    Utils.addBipushInsn(mv, val);
  }

  /** Add a GETVALUE call to synchronize the top stack with that of the symbolic stack. */
  private void addValueReadInsn(MethodVisitor mv, String desc, String methodNamePrefix) {
    Utils.addValueReadInsn(mv, desc, methodNamePrefix);
  }

  /** Add a special probe instruction. */
  private void addSpecialInsn(MethodVisitor mv, int val) {
    addBipushInsn(mv, val);
    mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SPECIAL", "(I)V", false);
  }

  @Override
  public void visitLineNumber(int i, Label label) {
    line = i;
    mv.visitLineNumber(i, label);
  }

  @Override
  public void visitInsn(int opcode) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    switch (opcode) {
      case NOP:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "NOP", "(II)V", false);
        break;
      case ACONST_NULL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ACONST_NULL", "(II)V", false);
        break;
      case ICONST_M1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_M1", "(II)V", false);
        break;
      case ICONST_0:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_0", "(II)V", false);
        break;
      case ICONST_1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_1", "(II)V", false);
        break;
      case ICONST_2:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_2", "(II)V", false);
        break;
      case ICONST_3:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_3", "(II)V", false);
        break;
      case ICONST_4:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_4", "(II)V", false);
        break;
      case ICONST_5:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ICONST_5", "(II)V", false);
        break;
      case LCONST_0:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LCONST_0", "(II)V", false);
        break;
      case LCONST_1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LCONST_1", "(II)V", false);
        break;
      case FCONST_0:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCONST_0", "(II)V", false);
        break;
      case FCONST_1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCONST_1", "(II)V", false);
        break;
      case FCONST_2:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCONST_2", "(II)V", false);
        break;
      case DCONST_0:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCONST_0", "(II)V", false);
        break;
      case DCONST_1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCONST_1", "(II)V", false);
        break;
      case IALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "I", "GETVALUE_");
        return;
      case LALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "J", "GETVALUE_");
        return;
      case FALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "F", "GETVALUE_");
        return;
      case DALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "D", "GETVALUE_");
        return;
      case AALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "AALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "Ljava/lang/Object;", "GETVALUE_");
        return;
      case BALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "BALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "B", "GETVALUE_");
        return;
      case CALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "CALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "C", "GETVALUE_");
        return;
      case SALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SALOAD", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "S", "GETVALUE_");
        return;
      case IASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case LASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case FASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case DASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case AASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "AASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case BASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "BASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case CASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "CASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case SASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SASTORE", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case POP:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "POP", "(II)V", false);
        break;
      case POP2:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "POP2", "(II)V", false);
        break;
      case DUP:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP", "(II)V", false);
        break;
      case DUP_X1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP_X1", "(II)V", false);
        break;
      case DUP_X2:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP_X2", "(II)V", false);
        break;
      case DUP2:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP2", "(II)V", false);
        break;
      case DUP2_X1:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP2_X1", "(II)V", false);
        break;
      case DUP2_X2:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP2_X2", "(II)V", false);
        break;
      case SWAP:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SWAP", "(II)V", false);
        break;
      case IADD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IADD", "(II)V", false);
        break;
      case LADD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LADD", "(II)V", false);
        break;
      case FADD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FADD", "(II)V", false);
        break;
      case DADD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DADD", "(II)V", false);
        break;
      case ISUB:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISUB", "(II)V", false);
        break;
      case LSUB:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSUB", "(II)V", false);
        break;
      case FSUB:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FSUB", "(II)V", false);
        break;
      case DSUB:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DSUB", "(II)V", false);
        break;
      case IMUL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IMUL", "(II)V", false);
        break;
      case LMUL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LMUL", "(II)V", false);
        break;
      case FMUL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FMUL", "(II)V", false);
        break;
      case DMUL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DMUL", "(II)V", false);
        break;
      case IDIV:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IDIV", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case LDIV:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDIV", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case FDIV:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FDIV", "(II)V", false);
        break;
      case DDIV:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DDIV", "(II)V", false);
        break;
      case IREM:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IREM", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case LREM:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LREM", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case FREM:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FREM", "(II)V", false);
        break;
      case DREM:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DREM", "(II)V", false);
        break;
      case INEG:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INEG", "(II)V", false);
        break;
      case LNEG:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LNEG", "(II)V", false);
        break;
      case FNEG:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FNEG", "(II)V", false);
        break;
      case DNEG:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DNEG", "(II)V", false);
        break;
      case ISHL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISHL", "(II)V", false);
        break;
      case LSHL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSHL", "(II)V", false);
        break;
      case ISHR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISHR", "(II)V", false);
        break;
      case LSHR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSHR", "(II)V", false);
        break;
      case IUSHR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IUSHR", "(II)V", false);
        break;
      case LUSHR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LUSHR", "(II)V", false);
        break;
      case IAND:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IAND", "(II)V", false);
        break;
      case LAND:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LAND", "(II)V", false);
        break;
      case IOR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IOR", "(II)V", false);
        break;
      case LOR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LOR", "(II)V", false);
        break;
      case IXOR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IXOR", "(II)V", false);
        break;
      case LXOR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LXOR", "(II)V", false);
        break;
      case I2L:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2L", "(II)V", false);
        break;
      case I2F:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2F", "(II)V", false);
        break;
      case I2D:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2D", "(II)V", false);
        break;
      case L2I:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "L2I", "(II)V", false);
        break;
      case L2F:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "L2F", "(II)V", false);
        break;
      case L2D:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "L2D", "(II)V", false);
        break;
      case F2I:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "F2I", "(II)V", false);
        break;
      case F2L:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "F2L", "(II)V", false);
        break;
      case F2D:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "F2D", "(II)V", false);
        break;
      case D2I:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "D2I", "(II)V", false);
        break;
      case D2L:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "D2L", "(II)V", false);
        break;
      case D2F:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "D2F", "(II)V", false);
        break;
      case I2B:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2B", "(II)V", false);
        break;
      case I2C:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2C", "(II)V", false);
        break;
      case I2S:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "I2S", "(II)V", false);
        break;
      case LCMP:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LCMP", "(II)V", false);
        break;
      case FCMPL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCMPL", "(II)V", false);
        break;
      case FCMPG:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FCMPG", "(II)V", false);
        break;
      case DCMPL:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCMPL", "(II)V", false);
        break;
      case DCMPG:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DCMPG", "(II)V", false);
        break;
      case IRETURN:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IRETURN", "(II)V", false);
        break;
      case LRETURN:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LRETURN", "(II)V", false);
        break;
      case FRETURN:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FRETURN", "(II)V", false);
        break;
      case DRETURN:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DRETURN", "(II)V", false);
        break;
      case ARETURN:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ARETURN", "(II)V", false);
        break;
      case RETURN:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "RETURN", "(II)V", false);
        break;
      case ARRAYLENGTH:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ARRAYLENGTH", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "I", "GETVALUE_");
        return;
      case ATHROW:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ATHROW", "(II)V", false);
        break;
      case MONITORENTER:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "MONITORENTER", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      case MONITOREXIT:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "MONITOREXIT", "(II)V", false);
        mv.visitInsn(opcode);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      default:
        throw new RuntimeException("Unknown instruction opcode " + opcode);

    }
    mv.visitInsn(opcode);
  }

  @Override
  public void visitVarInsn(int opcode, int var) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    addBipushInsn(mv, var);
    switch (opcode) {
      case ILOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ILOAD", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        addValueReadInsn(mv, "I", "GETVALUE_");
        break;
      case LLOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LLOAD", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        addValueReadInsn(mv, "J", "GETVALUE_");
        break;
      case FLOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FLOAD", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        addValueReadInsn(mv, "F", "GETVALUE_");
        break;
      case DLOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DLOAD", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        addValueReadInsn(mv, "D", "GETVALUE_");
        break;
      case ALOAD:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ALOAD", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        if (!(var == 0 && isInit && !isSuperInitCalled)) {
          addValueReadInsn(mv, "Ljava/lang/Object;", "GETVALUE_");
        }
        break;
      case ISTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ISTORE", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        break;
      case LSTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LSTORE", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        break;
      case FSTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "FSTORE", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        break;
      case DSTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DSTORE", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        break;
      case ASTORE:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "ASTORE", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        break;
      case RET:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "RET", "(III)V", false);
        mv.visitVarInsn(opcode, var);
        break;
      default:
        System.err.println("Unknown var instruction opcode " + opcode);
        System.exit(1);
    }
  }

  @Override
  public void visitIntInsn(int opcode, int operand) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    switch (opcode) {
      case BIPUSH:
        addBipushInsn(mv, operand);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "BIPUSH", "(III)V", false);
        break;
      case SIPUSH:
        addBipushInsn(mv, operand);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "SIPUSH", "(III)V", false);
        break;
      case NEWARRAY:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "NEWARRAY", "(II)V", false);
        mv.visitIntInsn(opcode, operand);
        addSpecialInsn(mv, 0); // for non-exceptional path
        return;
      default:
        System.err.println("Unknown int instruction opcode " + opcode);
        System.exit(1);
    }
    mv.visitIntInsn(opcode, operand);
  }

  @Override
  public void visitTypeInsn(int opcode, String type) {
    switch (opcode) {
      case NEW:
        //mv.visitTypeInsn(opcode, type);
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(type);
        int cIdx = ClassNames.getInstance().get(type);
        addBipushInsn(mv, cIdx);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "NEW", "(IILjava/lang/String;I)V", false);
        mv.visitTypeInsn(opcode, type);
        calledNew = true;
        addSpecialInsn(mv, 0); // for non-exceptional path
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "DUP", "(II)V", false);
        mv.visitInsn(DUP);
        break;
      case ANEWARRAY:
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(type);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "ANEWARRAY", "(IILjava/lang/String;)V", false);
        mv.visitTypeInsn(opcode, type);
        addSpecialInsn(mv, 0); // for non-exceptional path
        break;
      case CHECKCAST:
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(type);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "CHECKCAST", "(IILjava/lang/String;)V", false);
        mv.visitTypeInsn(opcode, type);
        addSpecialInsn(mv, 0); // for non-exceptional path
        break;
      case INSTANCEOF:
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
        mv.visitLdcInsn(type);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "INSTANCEOF", "(IILjava/lang/String;)V", false);
        mv.visitTypeInsn(opcode, type);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, "I", "GETVALUE_");
        break;
      default:
        throw new RuntimeException("Unknown type instruction opcode " + opcode);
    }
  }

  @Override
  public void visitFieldInsn(int opcode, String owner, String name, String desc) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    int cIdx = ClassNames.getInstance().get(owner);
    ObjectInfo tmp = ClassNames.getInstance().get(cIdx);
    addBipushInsn(mv, cIdx);
    switch (opcode) {
      case GETSTATIC:
        int fIdx = tmp.getIdx(name, true);
        addBipushInsn(mv, fIdx);
        mv.visitLdcInsn(desc);

        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "GETSTATIC", "(IIIILjava/lang/String;)V", false);

        mv.visitFieldInsn(opcode, owner, name, desc);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, desc, "GETVALUE_");
        break;
      case PUTSTATIC:
        fIdx = tmp.getIdx(name, true);
        addBipushInsn(mv, fIdx);
        mv.visitLdcInsn(desc);

        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "PUTSTATIC", "(IIIILjava/lang/String;)V", false);
        mv.visitFieldInsn(opcode, owner, name, desc);
        addSpecialInsn(mv, 0); // for non-exceptional path
        break;
      case GETFIELD:
        fIdx = tmp.getIdx(name, false);
        addBipushInsn(mv, fIdx);
        mv.visitLdcInsn(desc);

        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "GETFIELD", "(IIIILjava/lang/String;)V", false);
        mv.visitFieldInsn(opcode, owner, name, desc);
        addSpecialInsn(mv, 0); // for non-exceptional path
        addValueReadInsn(mv, desc, "GETVALUE_");
        break;
      case PUTFIELD:
        fIdx = tmp.getIdx(name, false);
        addBipushInsn(mv, fIdx);
        mv.visitLdcInsn(desc);

        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, "PUTFIELD", "(IIIILjava/lang/String;)V", false);
        mv.visitFieldInsn(opcode, owner, name, desc);
        addSpecialInsn(mv, 0); // for non-exceptional path
        break;
      default:
        System.err.println("Unknown field access opcode " + opcode);
        System.exit(1);
    }
  }

  @Override
  public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    if (opcode == INVOKESPECIAL && name.equals("<init>")) {
      if (owner.equals("java/lang/Object")) {
        // Constructor calls to <init> method of the Object class. If this is the
        // case, there is no need to wrap the method call in try catch block as
        // it uses uninitialized this object.
        mv.visitMethodInsn(opcode, owner, name, desc);
        return;
      }

      addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
      addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());

      mv.visitLdcInsn(owner);
      mv.visitLdcInsn(name);
      mv.visitLdcInsn(desc);
      mv.visitMethodInsn(
                         INVOKESTATIC,
                         Config.instance.analysisClass,
                         "INVOKESPECIAL",
                         "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
      // Wrap the method call in a try-catch block
      Label begin = new Label();
      Label handler = new Label();
      Label end = new Label();

      tryCatchBlocks.addFirst(new TryCatchBlock(begin, handler, handler, null));

      mv.visitLabel(begin);
      mv.visitMethodInsn(opcode, owner, name, desc);
      mv.visitJumpInsn(GOTO, end);

      mv.visitLabel(handler);
      mv.visitMethodInsn(
                         INVOKESTATIC, Config.instance.analysisClass, "INVOKEMETHOD_EXCEPTION", "()V", false);
      mv.visitInsn(ATHROW);

      mv.visitLabel(end);
      mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKEMETHOD_END", "()V", false);

      isSuperInitCalled = true;
      addValueReadInsn(mv, desc, "GETVALUE_");
      if (calledNew) {
        calledNew = false;
        addValueReadInsn(mv, "Ljava/lang/Object;", "GETVALUE_");
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
        addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "POP", "(II)V", false);
        mv.visitInsn(POP);
      }
      return;
    }

    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());

    mv.visitLdcInsn(owner);
    mv.visitLdcInsn(name);
    mv.visitLdcInsn(desc);

    switch (opcode) {
      case INVOKEVIRTUAL:
        mv.visitMethodInsn(
            INVOKESTATIC,
            Config.instance.analysisClass,
            "INVOKEVIRTUAL",
            "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
        break;
      case INVOKESPECIAL:
        mv.visitMethodInsn(
            INVOKESTATIC,
            Config.instance.analysisClass,
            "INVOKESPECIAL",
            "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
        break;
      case INVOKESTATIC:
        mv.visitMethodInsn(
            INVOKESTATIC,
            Config.instance.analysisClass,
            "INVOKESTATIC",
            "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
        break;
      case INVOKEINTERFACE:
        mv.visitMethodInsn(
            INVOKESTATIC,
            Config.instance.analysisClass,
            "INVOKEINTERFACE",
            "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
        break;
      default:
        throw new RuntimeException("Error instrumenting invokeMethod");
    }

    // Wrap the method call in a try-catch block
    Label begin = new Label();
    Label handler = new Label();
    Label end = new Label();

    tryCatchBlocks.addFirst(new TryCatchBlock(begin, handler, handler, null));

    mv.visitLabel(begin);
    mv.visitMethodInsn(opcode, owner, name, desc);
    mv.visitJumpInsn(GOTO, end);

    mv.visitLabel(handler);
    mv.visitMethodInsn(
        INVOKESTATIC, Config.instance.analysisClass, "INVOKEMETHOD_EXCEPTION", "()V", false);
    mv.visitInsn(ATHROW);

    mv.visitLabel(end);
    mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "INVOKEMETHOD_END", "()V", false);
    addValueReadInsn(mv, desc, "GETVALUE_");
  }

  @Override
  public void visitJumpInsn(int opcode, Label label) {
    int iid3;
    addBipushInsn(mv, iid3 = GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    addBipushInsn(mv, System.identityHashCode(label)); // label.getOffset()
    switch (opcode) {
      case IFEQ:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFEQ", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IFNE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFNE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IFLT:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFLT", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IFGE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFGE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IFGT:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFGT", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IFLE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFLE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ICMPEQ:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPEQ", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ICMPNE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPNE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ICMPLT:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPLT", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ICMPGE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPGE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ICMPGT:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPGT", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ICMPLE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ICMPLE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ACMPEQ:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ACMPEQ", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IF_ACMPNE:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IF_ACMPNE", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case GOTO:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "GOTO", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        break;
      case JSR:
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "JSR", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        break;
      case IFNULL:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFNULL", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      case IFNONNULL:
        coverage.addBranchCount(iid3);
        mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IFNONNULL", "(III)V", false);
        mv.visitJumpInsn(opcode, label);
        addSpecialInsn(mv, 1); // for true path
        break;
      default:
        throw new RuntimeException("Unknown jump opcode " + opcode);

    }
  }

  @Override
  public void visitLdcInsn(Object cst) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    mv.visitLdcInsn(cst);
    if (cst instanceof Integer) {
      mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(III)V", false);
    } else if (cst instanceof Long) {
      mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IIJ)V", false);
    } else if (cst instanceof Float) {
      mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IIF)V", false);
    } else if (cst instanceof Double) {
      mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IID)V", false);
    } else if (cst instanceof String) {
      mv.visitMethodInsn(
          INVOKESTATIC, Config.instance.analysisClass, "LDC", "(IILjava/lang/String;)V", false);
    } else {
      mv.visitMethodInsn(
          INVOKESTATIC, Config.instance.instance.analysisClass, "LDC", "(IILjava/lang/Object;)V", false);
    }
    mv.visitLdcInsn(cst);
  }

  @Override
  public void visitIincInsn(int var, int increment) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    addBipushInsn(mv, var);
    addBipushInsn(mv, increment);
    mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "IINC", "(IIII)V", false);
    mv.visitIincInsn(var, increment);
  }

  @Override
  public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
    int iid3;
    addBipushInsn(mv, iid3 = GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    addBipushInsn(mv, min);
    addBipushInsn(mv, max);
    addBipushInsn(mv, System.identityHashCode(dflt)); // label.getOffset()

    addBipushInsn(mv, labels.length);
    mv.visitIntInsn(NEWARRAY, T_INT);
    for (int i = 0; i < labels.length; i++) {
      if (i != 0) {
        iid3 = GlobalStateForInstrumentation.instance.incAndGetId();
      }
      coverage.addBranchCount(iid3);
      mv.visitInsn(DUP);
      addBipushInsn(mv, i);
      addBipushInsn(mv, System.identityHashCode(labels[i])); // label.getOffset()
      mv.visitInsn(IASTORE);
    }

    mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "TABLESWITCH", "(IIIII[I)V", false);
    mv.visitTableSwitchInsn(min, max, dflt, labels);
  }

  @Override
  public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
    int iid3;
    addBipushInsn(mv, iid3 = GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    addBipushInsn(mv, System.identityHashCode(dflt)); // label.getOffset()

    addBipushInsn(mv, keys.length);
    mv.visitIntInsn(NEWARRAY, T_INT);
    for (int i = 0; i < keys.length; i++) {
      mv.visitInsn(DUP);
      addBipushInsn(mv, i);
      addBipushInsn(mv, keys[i]);
      mv.visitInsn(IASTORE);
    }

    addBipushInsn(mv, labels.length);
    mv.visitIntInsn(NEWARRAY, T_INT);
    for (int i = 0; i < labels.length; i++) {
      if (i != 0) {
        iid3 = GlobalStateForInstrumentation.instance.incAndGetId();
      }
      coverage.addBranchCount(iid3);
      mv.visitInsn(DUP);
      addBipushInsn(mv, i);
      addBipushInsn(mv, System.identityHashCode(labels[i])); // label.getOffset()
      mv.visitInsn(IASTORE);
    }

    mv.visitMethodInsn(INVOKESTATIC, Config.instance.analysisClass, "LOOKUPSWITCH", "(III[I[I)V", false);
    mv.visitLookupSwitchInsn(dflt, keys, labels);
  }

  @Override
  public void visitMultiANewArrayInsn(String desc, int dims) {
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.incAndGetId());
    addBipushInsn(mv, GlobalStateForInstrumentation.instance.getMid());
    mv.visitLdcInsn(desc);
    addBipushInsn(mv, dims);
    mv.visitMethodInsn(
        INVOKESTATIC, Config.instance.analysisClass, "MULTIANEWARRAY", "(IILjava/lang/String;I)V", false);
    mv.visitMultiANewArrayInsn(desc, dims);
    addSpecialInsn(mv, 0); // for non-exceptional path
  }

  @Override
  public void visitMaxs(int maxStack, int maxLocals) {
    for (TryCatchBlock b : tryCatchBlocks) {
      b.visit(mv);
    }
    mv.visitMaxs(
        maxStack + 8,
        maxLocals); //To change body of overridden methods use File | Settings | File Templates.
  }

  @Override
  public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
    tryCatchBlocks.addLast(new TryCatchBlock(label, label1, label2, s));
  }
}
