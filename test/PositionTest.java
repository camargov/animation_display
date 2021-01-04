import cs3500.animator.model.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Tests the methods of the cs3500.animator.model.Position class.
 */
public class PositionTest {

  Position pos1;
  Position pos2;
  Position pos3;

  @Before
  public void setUp() {
    pos1 = new Position(0, 0);
    pos2 = new Position(0, 0);
    pos3 = new Position(10, 10);
  }

  // Testing equals() for both same and different positions
  @Test
  public void testEquals() {
    assertEquals(pos1, pos2);
    assertNotEquals(pos1, pos3);
    assertNotEquals(pos2, pos3);
  }

  // Testing hashCode() for both same and different positions
  @Test
  public void testHashCode() {
    assertEquals(pos1.hashCode(), pos2.hashCode());
    assertNotEquals(pos1.hashCode(), pos3.hashCode());
    assertNotEquals(pos2.hashCode(), pos3.hashCode());
  }
}