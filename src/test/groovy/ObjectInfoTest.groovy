package janala.logger

import static org.junit.Assert.assertEquals

import org.junit.Test

class ObjectInfoTest {
    @Test
    void testInit() {
        ObjectInfo info = new ObjectInfo("testClass")
        assertEquals(info.className, "testClass")
    }
}

