package janala.interpreters

import static org.junit.Assert.assertEquals

import org.junit.Test

class ClassDepotTest {
    @Test
    void testInit() {
        ClassDepot a = new ClassDepot()
        assertEquals(0, a.getClassId("a"))
    }
}