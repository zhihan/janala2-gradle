package janala.solvers

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull
import static org.junit.Assert.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when
import static org.mockito.Matchers.eq

import janala.interpreters.IntValue
import janala.interpreters.Constraint
import janala.interpreters.SymbolicInt
import org.junit.Test
import org.junit.Before


import groovy.transform.CompileStatic

@CompileStatic
class HistoryTest {
  History history
  Solver solver // A mock solver

  @Before
  void setup() {
    solver = mock(Solver.class)
    history = new History(solver)
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

  @Test
  void testSolveAt() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0)

    when(solver.solve()).thenReturn(true)
    history.solveAt(0)
    
    verify(solver).setInputs(new LinkedList<InputElement>())
    verify(solver).setPathConstraint(history.getPathConstraint())
    verify(solver).setPathConstraintIndex(0)
    verify(solver).solve()
  }

  @Test
  void testSolveAtWithHead() {
    // The first constraint will be included since it has a path constraint.
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 
    history.checkAndSetBranch(true, c, 0)

    when(solver.solve()).thenReturn(true)
    history.solveAt(0, 1)

    verify(solver).setInputs(new LinkedList<InputElement>())
    verify(solver).setPathConstraint(history.getPathConstraint())
    verify(solver).setPathConstraintIndex(1)
    verify(solver).solve()
  }

  @Test
  void testSetLastBranchDone() {
    Constraint c = new SymbolicInt(1)
    history.checkAndSetBranch(true, c, 0) 
    history.setLastBranchDone()

    List<Element> h = history.getHistory()
    BranchElement last = (BranchElement) h.get(h.size() - 1)
    assertTrue(last.done)
  }

}