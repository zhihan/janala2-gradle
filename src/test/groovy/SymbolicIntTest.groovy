package janala.interpreters;

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

import org.junit.Test

import groovy.transform.CompileStatic

@CompileStatic
class SymbolicIntTest {
    @Test
    void testSymbolicIntAdd() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt y = x1.add(1)
        assertEquals(1L, y.constant)

        SymbolicInt a = x1.subtract(1L)
        assertEquals(-1L, a.constant)

        SymbolicInt x2 = new SymbolicInt(2)

        SymbolicInt z = y.add(x2)
        assertEquals(1L, z.constant)
        assertEquals(2, z.linear.size())

        SymbolicInt z2 = x2.subtract(y)
        assertEquals(-1L, z2.constant)
    }

    @Test
    void testSymbolicIntNegate() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        SymbolicInt y = x1.add(1)
        SymbolicInt z = y.add(x2)
        SymbolicInt nz = z.negate()

        assertEquals(-1L, nz.constant)
        assertEquals(2, nz.linear.size())
        assertEquals(-1L, nz.linear.get(1))
        assertEquals(-1L, nz.linear.get(2))

        SymbolicInt w = z.add(nz)
        assertEquals(null, w)
    }

    @Test
    void testSymbolicIntSubtractFrom() {
        SymbolicInt x1 = new SymbolicInt(1)
        
        SymbolicInt y = x1.negate().add(1)
        SymbolicInt z = x1.subtractFrom(1)
        assertEquals(y, z)
    }

    @Test
    void testSymbolicIntMultiply() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt y = x1.add(1)
        
        SymbolicInt z = y.multiply(2)
        assertEquals(2L, z.linear.get(1))
        assertEquals(2L, z.constant)
    }

    @Test
    void testToString() {
        SymbolicInt x1 = new SymbolicInt(1)
        System.out.println(x1.toString())
        String s = x1.toString()
        assertTrue(s.contains("x1"))
    }

    @Test
    void testEquals() {
        SymbolicInt x1 = new SymbolicInt(1)
        SymbolicInt x2 = new SymbolicInt(2)
        assertFalse(x1.equals(null))
        assertFalse(x1.equals("a"))
        assertTrue(x1.equals(x1))

        assertFalse(x1.equals(x2))
        assertFalse(x1.hashCode() == x2.hashCode())
    }

}