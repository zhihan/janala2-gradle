package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull

import janala.interpreters.IntValue
import janala.interpreters.Constraint
import janala.interpreters.SymbolicInt
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

  @Test
  void testSetBranch() {
    // Unconditionally set to true branch
    history.checkAndSetBranch(true, null, 0)
    assertEquals(1, history.getHistory().size())
    assertEquals(0, history.getPathConstraint().size())
  }

  @Test
  void testSetBranchWithConstraint() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0)
    assertEquals(1, history.getHistory().size())
    assertEquals(1, history.getPathConstraint().size())

    history.setIndex(0) 
    history.checkAndSetBranch(true, c, 0)
    assertEquals(1, history.getHistory().size())
    assertEquals(2, history.getPathConstraint().size())

    history.setIndex(0) 
    history.checkAndSetBranch(false, c, 0)
    println(history.getPathConstraint())
    assertEquals(1, history.getHistory().size())
    assertEquals(3, history.getPathConstraint().size())

    history.setIndex(0)
    history.ignore() // Insert into history
    history.checkAndSetBranch(false, c, 0)
    println(history.getPathConstraint())
    assertEquals(2, history.getHistory().size())
    assertEquals(4, history.getPathConstraint().size())
  }


}