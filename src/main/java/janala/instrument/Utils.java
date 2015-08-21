package janala.instrument;

import janala.config.Config;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.Opcodes;

public class Utils implements Opcodes {
  public static void addBipushInsn(MethodVisitor mv, int val) {
    switch (val) {
      case 0:
        mv.visitInsn(ICONST_0);
        break;
      case 1:
        mv.visitInsn(ICONST_1);
        break;
      case 2:
        mv.visitInsn(ICONST_2);
        break;
      case 3:
        mv.visitInsn(ICONST_3);
        break;
      case 4:
        mv.visitInsn(ICONST_4);
        break;
      case 5:
        mv.visitInsn(ICONST_5);
        break;
      default:
        mv.visitLdcInsn(new Integer(val));
        break;
    }
  }

  public static void addValueReadInsn(MethodVisitor mv, String desc, String methodNamePrefix) {
    Type t;

    if (desc.startsWith("(")) {
      t = Type.getReturnType(desc);
    } else {
      t = Type.getType(desc);
    }
    switch (t.getSort()) {
      case Type.DOUBLE:
        mv.visitInsn(DUP2);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "double", "(D)V");
        break;
      case Type.LONG:
        mv.visitInsn(DUP2);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "long", "(J)V");
        break;
      case Type.ARRAY:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC,
            Config.instance.analysisClass,
            methodNamePrefix + "Object",
            "(Ljava/lang/Object;)V");
        break;
      case Type.BOOLEAN:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "boolean", "(Z)V");
        break;
      case Type.BYTE:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "byte", "(B)V");
        break;
      case Type.CHAR:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "char", "(C)V");
        break;
      case Type.FLOAT:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "float", "(F)V");
        break;
      case Type.INT:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "int", "(I)V");
        break;
      case Type.OBJECT:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC,
            Config.instance.analysisClass,
            methodNamePrefix + "Object",
            "(Ljava/lang/Object;)V");
        break;
      case Type.SHORT:
        mv.visitInsn(DUP);
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "short", "(S)V");
        break;
      case Type.VOID:
        mv.visitMethodInsn(
            INVOKESTATIC, Config.instance.analysisClass, methodNamePrefix + "void", "()V");
        break;
      default:
        System.err.println("Unknown field or method descriptor " + desc);
        System.exit(1);
    }
  }
}