package janala.logger

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

import janala.interpreters.ClassDepot
import groovy.transform.CompileStatic
import org.junit.Test

@CompileStatic
class FakeClassDepot extends ClassDepot {
    @Override
    public int getFieldIndex(String cNam, String field) {
        1
    }

    @Override
    public int getStaticFieldIndex(String cname, String field) {
        1
    }
}

class FieldInfoTest {
    @Test
    void testField() {
        ClassDepot cd = new FakeClassDepot()
        FieldInfo fi = new FieldInfo("class", "field", true)
        fi.init(cd)
        assertEquals(1, fi.getFieldId())


        FieldInfo f = new FieldInfo("class", "field", false)
        f.init(cd)
        assertEquals(1, f.getFieldId())

        assertTrue(f.toString().contains("FieldInfo"))
    }   
}