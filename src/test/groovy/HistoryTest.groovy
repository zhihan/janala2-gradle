package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull

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

	@Test
	void testRemoveLastBranch() {
		def branchElement = new BranchElement(false, false, -1, 0)
		
		history.getHistory().add(branchElement)
		history.setIndex(1)

		assertNull(history.removeLastBranch())
	}

}