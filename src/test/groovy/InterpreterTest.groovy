package janala.interpreters

import org.junit.Test
import static org.junit.Assert.assertEquals
import java.util.Map
import groovy.transform.CompileStatic

@CompileStatic
public class InterpreterTest {
    @Test
    void testClassTemplate() {
        def ct = new ClassTemplate(TestClass.class)
        assertEquals(2, ct.nFields())
    }

    @Test
    void testNewId() {
        Value.reset()
        IntValue i = new IntValue(0)
        def b = i.MAKE_SYMBOLIC(null)
        assertEquals(1, b)
        assertEquals(1, i.getSymbolicInt().linear.size())
        assertEquals(b, i.getSymbol())
    }

    @Test
    void testSubstitute() {
        Value.reset()
        IntValue i = new IntValue(0)
        def b = i.MAKE_SYMBOLIC(null)
        def m = ["x1": 1L]
        assertEquals(1, i.substituteInLinear(m))
    }

}