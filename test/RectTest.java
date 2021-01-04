import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rect;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Defines tests for the functionality and exceptions of the cs3500.animator.model.Rect class.
 */

public class RectTest {

  //Define some Rectangles variables for testing
  IShape rect1 =
      new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 3,4);
  IShape rect2 =
      new Rect(Arrays.asList(100, 200, 50), new Position(20, 0), 2,2);

  //Initializes the variables for testing
  @Before
  public void initTest() {
    rect1 = new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 3, 4);
    rect2 = new Rect(Arrays.asList(100, 200, 50), new Position(20, 0), 2, 2);
  }

  //The following tests test the exception behavior of the Rect constructor.

  //Null position
  @Test(expected = IllegalArgumentException.class)
  public void nullPosition() {
    new Rect(Arrays.asList(255, 255, 255), null, 3, 4);
  }

  //Too-small width
  @Test(expected = IllegalArgumentException.class)
  public void smallWidth() {
    new Rect(Arrays.asList(), new Position(7, 8), -1, 5);
  }

  //Too-small height
  @Test(expected = IllegalArgumentException.class)
  public void smallHeight() {
    new Rect(Arrays.asList(), new Position(5, 4), 6, -2);
  }

  //Negative RGB values
  @Test(expected = IllegalArgumentException.class)
  public void negativeRgb() {
    new Rect(Arrays.asList(-8, 9, -155), new Position(3, 6), 10, 5);
  }

  //Too-large RGB values
  @Test(expected = IllegalArgumentException.class)
  public void largeRgb() {
    new Rect(Arrays.asList(256, 256, 256), new Position(3, 4), 11, 12);
  }

  //Tests that the getColor method returns the color of the Rect as a list
  @Test
  public void getColor() {
    assertEquals(new ArrayList<>(Arrays.asList(255, 255, 255)), rect1.getColor());
    assertEquals(new ArrayList<>(Arrays.asList(100, 200, 50)), rect2.getColor());
  }

  //Tests that the getPosition method returns the Position of the Rect
  @Test
  public void getPosition() {
    assertEquals(new Position(7, 8), rect1.getPosition());
    assertEquals(new Position(20, 0), rect2.getPosition());
  }

  //Tests that the getSize method returns the size of the Rect
  @Test
  public void getSize() {
    assertEquals(new ArrayList<>(Arrays.asList(3, 4)), rect1.getSize());
    assertEquals(new ArrayList<>(Arrays.asList(2, 2)), rect2.getSize());
  }

  //Tests that the setColor correctly mutates the Rect
  @Test
  public void setColor() {
    rect1.setColor(23, 45, 230);
    assertEquals(new ArrayList<>(Arrays.asList(23, 45, 230)), rect1.getColor());
    rect2.setColor(0, 0, 255);
    assertEquals(new ArrayList<>(Arrays.asList(0, 0, 255)), rect2.getColor());
  }

  //Tests the exception behaviour of setColor when given invalid RGB values
  @Test(expected = IllegalArgumentException.class)
  public void setColorIllegalRGB() {
    rect1.setColor(-30, 256, 4);
  }

  //Tests that setPosition correctly mutates the Rect
  @Test
  public void setPosition() {
    rect1.setPosition(new Position(5, 5));
    assertEquals(new Position(5, 5), rect1.getPosition());
    rect2.setPosition(new Position(4, 10));
    assertEquals(new Position(4, 10), rect2.getPosition());
  }

  //Tests the exception behavior of setPosition when given a negative position
  @Test(expected = IllegalArgumentException.class)
  public void setPositionIllegal() {
    rect1.setPosition(new Position(-7, -8));
  }

  //Tests that setSize correctly mutates the Rect
  @Test
  public void setSize() {
    rect1.setSize(20, 40);
    assertEquals(new ArrayList<>(Arrays.asList(20, 40)), rect1.getSize());
    rect2.setSize(13, 12);
    assertEquals(new ArrayList<>(Arrays.asList(13, 12)), rect2.getSize());
  }

  //Tests the exception behavior of setSize when given negative dimensions
  @Test(expected = IllegalArgumentException.class)
  public void setSizeInvalid() {
    rect1.setSize(-10, -3);
  }

  //Tests the overridden equals() method
  @Test
  public void testEquals() {
    IShape rect1Copy =
        new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 3, 4);
    assertEquals(rect1, rect1);
    assertEquals(rect1, rect1Copy);
    assertNotEquals(rect1, rect2);
  }

  //Tests the overridden hashCode() method
  @Test
  public void testHashCode() {
    IShape rect1Copy =
        new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 3, 4);
    assertEquals(rect1.hashCode(), rect1.hashCode());
    assertEquals(rect1.hashCode(), rect1Copy.hashCode());
    assertNotEquals(rect1.hashCode(), rect2.hashCode());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THAT copyShape() WILL THROW

  // Testing copyShape() throws an exception when the size of the given rgb list is not 3
  @Test(expected = IllegalArgumentException.class)
  public void testCopyShapeListSize() {
    rect1.copyShape(new ArrayList<Integer>(Arrays.asList(1)), new Position(0, 0),
        2, 2);
  }

  // Testing copyShape() throws an exception the given rgb values are negative
  @Test(expected = IllegalArgumentException.class)
  public void testCopyShapeNegativeRGB() {
    rect1.copyShape(new ArrayList<Integer>(Arrays.asList(-1, -10, -10)), new Position(0, 0),
        2, 2);
  }

  // Testing copyShape() throws an exception the given rgb values are greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testCopyPositiveRGB() {
    rect1.copyShape(new ArrayList<Integer>(Arrays.asList(260, 300, 300)), new Position(0, 0),
        2, 2);
  }

  /*
  // Testing copyShape() throws an exception the given position values are negative
  @Test(expected = IllegalArgumentException.class)
  public void testCopyNegativePosition() {
    rect1.copyShape(new ArrayList<Integer>(Arrays.asList(255, 30, 30)),
        new Position(-10, -10), 2, 2);
  }
   */

  // Testing copyShape() throws an exception the given width and height are negative
  @Test(expected = IllegalArgumentException.class)
  public void testCopyNegativeDimensions() {
    rect1.copyShape(new ArrayList<Integer>(Arrays.asList(255, 30, 30)), new Position(0, 0),
        -2, -2);
  }

  // Testing copyShape() throws an exception the given width and height are zero
  @Test(expected = IllegalArgumentException.class)
  public void testCopyZeroDimensions() {
    rect1.copyShape(new ArrayList<Integer>(Arrays.asList(255, 30, 30)), new Position(0, 0),
        0, 0);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE VALID CASES FOR copyShape()

  // Testing copyShape()
  @Test
  public void testCopyShape() {
    IShape expectedCircle = new Rect(new ArrayList<Integer>(Arrays.asList(10, 10, 10)),
        new Position(0, 0), 50);

    assertEquals(expectedCircle, rect1.copyShape(new ArrayList<Integer>(Arrays.asList(10, 10, 10)),
        new Position(0, 0), 50, 50));
  }
}