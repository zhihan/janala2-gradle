package janala.solvers

import static org.junit.Assert.assertEquals

import janala.interpreters.IntValue
import org.junit.Test
import org.junit.Before

import groovy.transform.CompileStatic

@CompileStatic
class HistoryTest {
	History history

	@Before
	void setup() {
		history = new History(new NoopSolver())
	}


	@Test
	void testInit() {
		history.beginScope(0)
		history.endScope(0)
	}

}