package janala.interpreters;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

import org.junit.Test
import java.util.HashMap

class IntValueTest {
    @Test
    void testNewId() {
        Value.reset()
        IntValue i = new IntValue(0)
        assertEquals(0, i.concrete)
        def b = i.MAKE_SYMBOLIC(null)
        assertEquals(1, b)
        assertEquals(1, i.getSymbolicInt().linear.size())
        assertEquals(b, i.getSymbol())
    }

    @Test
    void testConstructor() {
        Value.reset()

        IntValue a = new IntValue(1, SymbolicFalseConstraint.instance)
        assertEquals(a.concrete, 1)
        assertEquals(a.symbolic, SymbolicFalseConstraint.instance)

        SymbolicInt b = new SymbolicInt(1)
        b.setOp(SymbolicInt.COMPARISON_OPS.EQ)
        IntValue c = new IntValue(1, b)
        assertEquals(1, c.concrete)
        assertEquals(b, c.symbolic)
        assertEquals(b, c.symbolicInt)
    }

    @Test
    void testSubstitute() {
        Value.reset()
        IntValue i = new IntValue(0)
        def b = i.MAKE_SYMBOLIC(null)
        def m = ["x1": 1L]
        assertEquals(1, i.substituteInLinear(m))
    }

    @Test
    void testSubstitueNone() {
        Value.reset()
        IntValue i = new IntValue(0)
        assertEquals(0, i.substituteInLinear(["x2": 1L]))
        IntValue j = new IntValue(0)
        def b = j.MAKE_SYMBOLIC(null)
        assertEquals(0, j.substituteInLinear(["x2": 1L]))
    }

    @Test
    public void testIINC() {
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)

        IntValue j = i.IINC(1)
        assertEquals(1, j.concrete)
        def m = new HashMap<Integer, Long>()
        m.put(b, 1L)
        assertEquals(new SymbolicInt(m, 1), j.symbolicInt)
    }

    @Test
    public void testIFEQNoConstraint() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.TRUE, i.IFEQ())
        IntValue j = new IntValue(1)
        assertEquals(IntValue.FALSE, j.IFEQ())
    }

    @Test
    public void testIFEQSymbolicInt() {
        // (0, x) == 0 
        // -> (1, x == 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFEQ()
        assertEquals(1, r.concrete)
        assertEquals(SymbolicInt.COMPARISON_OPS.EQ, r.symbolicInt.op)

        // (0, x == 0) == 0 
        // -> (1, x != 0)
        SymbolicInt x = new SymbolicInt(1)
        x.setOp(SymbolicInt.COMPARISON_OPS.EQ)
        IntValue j = new IntValue(0, x)
        r = j.IFEQ()
        assertEquals(1, r.concrete)
        assertEquals(SymbolicInt.COMPARISON_OPS.NE, r.symbolicInt.op)
    }

    @Test
    public void testIFEQConstraint() {
        // (0, FALSE) == 0
        // -> (1, TRUE)
        IntValue i = new IntValue(0, SymbolicFalseConstraint.instance)
        def r = i.IFEQ()
        assertEquals(1, r.concrete)
        assertEquals(SymbolicTrueConstraint.instance, r.symbolic)
    }

    @Test
    public void testIFNENoConstraint() {
        IntValue i = new IntValue(0)
        assertEquals(IntValue.FALSE, i.IFNE())
        IntValue j = new IntValue(1)
        assertEquals(IntValue.TRUE, j.IFNE())
    }

    @Test
    public void testIFNESymbolicInt() {
        // (0, x) != 0 
        // -> (0, x == 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFNE()
        assertEquals(0, r.concrete)
        assertEquals(SymbolicInt.COMPARISON_OPS.EQ, r.symbolicInt.op)

        // (0, x == 0) != 0 
        // -> (0, x == 0)
        SymbolicInt x = new SymbolicInt(1)
        x.setOp(SymbolicInt.COMPARISON_OPS.EQ)
        IntValue j = new IntValue(0, x)
        r = j.IFNE()
        assertEquals(0, r.concrete)
        assertEquals(SymbolicInt.COMPARISON_OPS.NE, r.symbolicInt.op)
    }

    @Test
    public void testIFNEConstraint() {
        // (0, FALSE) != 0
        // -> (0, TRUE)
        IntValue i = new IntValue(0, SymbolicFalseConstraint.instance)
        def r = i.IFNE()
        assertEquals(0, r.concrete)
        assertEquals(SymbolicTrueConstraint.instance, r.symbolic)
    }
    @Test
    public void testIFLTNoConstraint() {
        IntValue i = new IntValue(-1)
        assertEquals(IntValue.TRUE, i.IFLT())
        IntValue j = new IntValue(0)
        assertEquals(IntValue.FALSE, j.IFLT())
    }

    @Test
    public void testIFLTSymbolicInt() {
        // (0, x) < 0 
        // -> (false, x >= 0)
        IntValue i = new IntValue(0)
        int b = i.MAKE_SYMBOLIC(null)
        IntValue r = i.IFLT()
        assertEquals(0, r.concrete)
        assertEquals(SymbolicInt.COMPARISON_OPS.GE, r.symbolicInt.op)

        // (-1, x) < 0 
        // -> (true, x < 0)
        SymbolicInt x = new SymbolicInt(1)
        IntValue j = new IntValue(-1, x)
        r = j.IFLT()
        assertEquals(1, r.concrete)
        assertEquals(SymbolicInt.COMPARISON_OPS.LT, r.symbolicInt.op)
    }

}