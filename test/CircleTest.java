import cs3500.animator.model.Circle;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * Testing the behavior of the methods in the cs3500.animator.model.Circle class.
 */
public class CircleTest {

  IShape circle;

  // This sets up the variables to be used in the unit tests
  @Before
  public void setUp() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(50, 50), 50);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THROWN BY THE CONSTRUCTOR

  // Testing that the Circle's constructor throws an exception when the given position is null
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullPosition() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(-20, -30, -40)), null, 50);
  }

  // Testing that the Circle's constructor throws an exception when it's list of rgb values
  // consist of negative integers
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeRGB() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(-20, -30, -40)),
        new Position(50, 50), 50);
  }

  // Testing that the Circle's constructor throws an exception when it's list of rgb values
  // consist of integers greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorRGB() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(256, 260, 300)),
        new Position(50, 50), 50);
  }

  // Testing that the Circle's constructor throws an exception when a negative width is given
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeWidth() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(100, 100), -50, 100);
  }


  // Testing that the Circle's constructor throws an exception when a zero width is given
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorZeroWidth() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(100, 100), 0, 100);
  }


  // Testing that the Circle's constructor throws an exception when a negative height is given
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeHeight() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(100, 100), 50, -100);
  }


  // Testing that the Circle's constructor throws an exception when a zero height is given
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorZeroHeight() {
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(100, 100), 50, 0);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getColor(), getPosition(), AND getSize()

  // Testing getColor() returns the correct list of integers
  @Test
  public void testGetColor() {
    List<Integer> expectedRGB = new ArrayList<Integer>(Arrays.asList(0, 0, 0));
    assertEquals(expectedRGB, circle.getColor());
  }

  // Testing getColor() doesn't allow the circle's color to be altered
  @Test
  public void testGetColorModify() {
    List<Integer> expectedRGB = circle.getColor();
    expectedRGB.set(0, 20);
    expectedRGB.set(1, 200);

    assertNotEquals(expectedRGB, circle.getColor());
  }

  // Testing getPosition()
  @Test
  public void testGetPosition() {
    Position expectedPosition = new Position(50, 50);
    assertEquals(expectedPosition, circle.getPosition());
  }


  // Testing getSize()
  @Test
  public void testGetSize() {
    List<Integer> expectedSize = new ArrayList<Integer>(Arrays.asList(50, 50));
    assertEquals(expectedSize, circle.getSize());
  }

  // Testing getSize() doesn't allow the circle's position to be altered
  @Test
  public void testGetSizeModify() {
    List<Integer> expectedSize = circle.getSize();
    expectedSize.set(0, 500);
    expectedSize.set(1, 100);

    assertNotEquals(expectedSize, circle.getSize());
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS setColor(), setPosition(), AND setSize()


  // Testing setColor()
  @Test
  public void testSetColor() {
    circle.setColor(100, 40, 30);

    IShape expectedCircle = new Circle(new ArrayList<Integer>(Arrays.asList(100, 40, 30)),
        new Position(50, 50), 50);

    assertEquals(expectedCircle, circle);
  }

  // Testing setColor() throws an exception when the rbg values are negative
  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidColorNegativeValues() {
    circle.setColor(-10, -10, -10);
  }

  // Testing setColor() throws an exception when one of the rbg values is greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidColorPositiveValues() {
    circle.setColor(30, 250, 300);
  }

  // Testing setPosition()
  @Test
  public void testSetPosition() {
    circle.setPosition(new Position(100, 70));

    IShape expectedCircle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(100, 70), 50);

    assertEquals(expectedCircle, circle);
  }

  // Testing setPosition() throws an exception when the position values are negative
  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidPosition() {
    circle.setPosition(new Position(-10, 10));
  }

  // Testing setPosition() throws an exception when cs3500.animator.model.Position is null
  @Test(expected = IllegalArgumentException.class)
  public void testSetNullPosition() {
    circle.setPosition(null);
  }

  // Testing setSize()
  @Test
  public void testSetSize() {
    circle.setSize(30, 40);

    IShape expectedCircle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(50, 50), 30, 40);

    assertEquals(expectedCircle, circle);
  }

  // Testing setPosition() throws an exception when the width and height values are negative
  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidSize() {
    circle.setSize(-10, -10);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THAT copyShape() WILL THROW

  // Testing copyShape() throws an exception when the size of the given rgb list is not 3
  @Test(expected = IllegalArgumentException.class)
  public void testCopyShapeListSize() {
    circle.copyShape(new ArrayList<Integer>(Arrays.asList(1)), new Position(0, 0),
        2, 2);
  }

  // Testing copyShape() throws an exception the given rgb values are negative
  @Test(expected = IllegalArgumentException.class)
  public void testCopyShapeNegativeRGB() {
    circle.copyShape(new ArrayList<Integer>(Arrays.asList(-1, -10, -10)), new Position(0, 0),
        2, 2);
  }

  // Testing copyShape() throws an exception the given rgb values are greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testCopyPositiveRGB() {
    circle.copyShape(new ArrayList<Integer>(Arrays.asList(260, 300, 300)), new Position(0, 0),
        2, 2);
  }

  // Testing copyShape() throws an exception the given width and height are negative
  @Test(expected = IllegalArgumentException.class)
  public void testCopyNegativeDimensions() {
    circle.copyShape(new ArrayList<Integer>(Arrays.asList(255, 30, 30)), new Position(0, 0),
        -2, -2);
  }

  // Testing copyShape() throws an exception the given width and height are zero
  @Test(expected = IllegalArgumentException.class)
  public void testCopyZeroDimensions() {
    circle.copyShape(new ArrayList<Integer>(Arrays.asList(255, 30, 30)), new Position(0, 0),
        0, 0);
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE VALID CASES FOR copyShape()

  // Testing copyShape()
  @Test
  public void testCopyShape() {
    IShape expectedCircle = new Circle(new ArrayList<Integer>(Arrays.asList(10, 10, 10)),
        new Position(0, 0), 50);

    assertEquals(expectedCircle, circle.copyShape(new ArrayList<Integer>(Arrays.asList(10, 10, 10)),
        new Position(0, 0), 50, 50));
  }

  // ----------------------------------------------------------------------------------------------
  // THIS SECTION TESTS equals() AND hashCode()

  // Testing equals() for both same and different circles
  @Test
  public void testEquals() {
    IShape circleSame = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(50, 50), 50);
    IShape circleDif = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(50, 50), 30);

    assertEquals(circle, circleSame);
    assertNotEquals(circle, circleDif);
    assertNotEquals(circleSame, circleDif);
  }

  // Testing hashCode() for both same and different circles
  @Test
  public void testHashCode() {
    IShape circleSame = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(50, 50), 50);
    IShape circleDif = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(50, 50), 30);

    assertEquals(circle.hashCode(), circleSame.hashCode());
    assertNotEquals(circle.hashCode(), circleDif.hashCode());
    assertNotEquals(circleSame.hashCode(), circleDif.hashCode());
  }

}