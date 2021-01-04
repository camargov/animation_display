import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Circle;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;


/**
 * Testing the behavior of the methods in AnimationModel.
 */
public class AnimationModelTest {

  IAnimationModel model;
  Map<String, Map<Integer, IShape>> shapes;
  Map<Integer, IShape> circles;
  IShape circle;

  IShape rect1 =
      new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 6, 4);
  IShape rect2 =
      new Rect(Arrays.asList(100, 200, 50), new Position(20, 0), 2, 2);
  IShape rect3 = new Rect(Arrays.asList(0, 0, 0), new Position(10, 10), 3, 4);
  Map<Integer, IShape> shape1 = new TreeMap<>();
  Map<Integer, IShape> shape2 = new TreeMap<>();
  Map<String, Map<Integer, IShape>> shapes2 = new TreeMap<>();
  IAnimationModel model2 = new AnimationModel();

  @Before
  public void setUp() {
    circles = new TreeMap<Integer, IShape>();
    shapes = new TreeMap<String, Map<Integer, IShape>>();
    circle = new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)), new Position(0, 0),
        10);

    circles.put(1, circle); // circle at tick 1
    shapes.put("Circle", circles); // list of circles labeled "Circle"
    model = new AnimationModel(shapes); // model with one circle at tick 1
  }


  @Before
  public void initFullModel() {
    Map<Integer, IShape> shape1 = new TreeMap<>();
    shape1.put(10, rect1);
    shape1.put(20, rect2);

    Map<Integer, IShape> shape2 = new TreeMap<>();
    shape2.put(0, rect2);
    shape2.put(15, rect1);

    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Shape1", shape1);
    shapes.put("Shape2", shape2);
    model2 = new AnimationModel(shapes);
  }


  // Testing the constructor throws an exception when the map contains negative integer values
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeInt() {
    Map<Integer, IShape> invalidCircles = new TreeMap<Integer, IShape>(); // map for circles
    Map<String, Map<Integer, IShape>> invalidShapes = new TreeMap<String, Map<Integer, IShape>>();
    invalidCircles.put(-1, circle); // adding circle to map with negative frame
    invalidShapes.put("Circle", invalidCircles); // adding circle map to shapes map

    model = new AnimationModel(invalidShapes); // throws exception
  }

  // Testing the constructor throws an exception when passed a map with null values for shapes
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullShape() {
    Map<Integer, IShape> invalidCircles = new TreeMap<Integer, IShape>(); // map for circles
    Map<String, Map<Integer, IShape>> invalidShapes = new TreeMap<String, Map<Integer, IShape>>();
    invalidCircles.put(1, null); // adding null to map
    invalidShapes.put("Circle", invalidCircles); // adding circle map to shapes map

    model = new AnimationModel(invalidShapes); // throws exception
  }

  // Testing the constructor throws an exception when passed a null canvas
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullCanvas() {
    new AnimationModel(shapes, null);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THROWN BY move()

  // Testing move() throws an exception when given a negative integer for frame number
  @Test(expected = IllegalArgumentException.class)
  public void testMoveNegativeFrame() {
    model.move(-10, "cs3500.animator.model.Circle", 10, 10);
  }

  // Testing move() throws an exception when given string doesn't appear in map
  @Test(expected = IllegalArgumentException.class)
  public void testMoveInvalidString() {
    model.move(10, "cs3500.animator.model.Rect", 10, 10);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THROWN BY changeColor()

  // Testing changeColor() throws an exception when given a negative integer for frame number
  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNegativeFrame() {
    model.changeColor(-10, "Circle", 0, 0, 0);
  }

  // Testing changeColor() throws an exception when given string doesn't appear in map
  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorInvalidString() {
    model.changeColor(10, "Rect", 0, 0, 0);
  }

  // Testing changeColor() throws an exception when given negative RGB values
  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorNegativeRGB() {
    model.changeColor(10, "Circle", -10, -40, -50);
  }

  // Testing changeColor() throws an exception when given RGB values greater than 255
  @Test(expected = IllegalArgumentException.class)
  public void testChangeColorPositiveRGB() {
    model.changeColor(10, "Circle", 10, 400, 256);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THROWN BY changeSize()

  // Testing changeSize() throws an exception when given a negative integer for frame number
  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeNegativeFrame() {
    model.changeSize(-10, "Circle", 10, 10);
  }

  // Testing changeSize() throws an exception when given string doesn't appear in map
  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeInvalidString() {
    model.changeSize(10, "Rect", 10, 10);
  }

  // Testing changeSize() throws an exception when given negative width and height values
  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeNegativeSize() {
    model.changeSize(10, "Circle", -10, -10);
  }

  // Testing changeSize() throws an exception when given zero values for width and height
  @Test(expected = IllegalArgumentException.class)
  public void testChangeSizeZeroSize() {
    model.changeSize(10, "Circle", 0, 0);
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS VALID CASES FOR move(), changeColor(), AND changeSize()

  // Testing move() to ensure the model's map has a new entry for changing a shape
  @Test
  public void testMoveMapEntry() {
    this.setUp();

    model.move(10, "Circle", 100, 0);

    // Getting the frames for circle
    Map<Integer, IShape> circleFrames = model.getShapes().get("Circle");

    // testing that model has expected circle entry and expected frames for circle
    assertEquals(2, circleFrames.size());
    assertTrue(circleFrames.containsKey(10));
    assertTrue(circleFrames.containsValue(new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(100, 0), 10)));
  }

  // Testing move() to ensure that the model's map can't be modified
  @Test
  public void testMoveModifiedMap() {
    this.setUp();
    // Changes the current circle by adding a new entry to the map for circle frames
    model.move(10, "Circle", 100, 0);

    // Modifies the map by modifying global variable and using getShapes()
    this.modifyModel();

    // Getting the circle frames for the model after the modifications
    Map<Integer, IShape> circleFrames = model.getShapes().get("Circle");

    // testing that model doesn't have the added circles
    assertEquals(2, circleFrames.size());
    assertFalse(circleFrames.containsKey(30));
    assertFalse(circleFrames.containsValue(new Circle(new ArrayList<Integer>(Arrays.asList(0, 200,
        0)), new Position(0, 0), 10)));
  }


  // Testing changeColor() to ensure the model's map has a new entry for changing a shape
  @Test
  public void testChangeColorMapEntry() {
    this.setUp();

    model.changeColor(10, "Circle", 200, 0, 0);

    // Getting the frames for circle
    Map<Integer, IShape> circleFrames = model.getShapes().get("Circle");

    // testing that model has expected circle entry and expected frames for circle
    assertEquals(2, circleFrames.size());
    assertTrue(circleFrames.containsKey(10));
    assertTrue(circleFrames.get(10).equals(new Circle(new ArrayList<Integer>(Arrays.asList(200, 0,
        0)), new Position(0, 0), 10)));
  }


  // Testing changeColor() to ensure that the model's map can't be modified
  @Test
  public void testChangeColorModifiedMap() {
    this.setUp();

    // Changes the current circle by adding a new entry to the map for circle frames
    model.changeColor(10, "Circle", 0, 200, 0);

    // Modifies the map by modifying global variable and using getShapes()
    this.modifyModel();

    // Getting the circle frames for the model after the modifications
    Map<Integer, IShape> circleFrames = model.getShapes().get("Circle");

    // testing that model has expected shapes map
    assertEquals(2, circleFrames.size());
    assertFalse(circleFrames.containsKey(30));
    assertFalse(circleFrames.containsValue(new Circle(new ArrayList<Integer>(Arrays.asList(200, 200,
        200)), new Position(0, 0), 10)));
  }


  // Testing changeSize() to ensure the model's map has a new entry for changing a shape's color
  @Test
  public void testChangeSizeMapEntry() {
    this.setUp();

    // Changes the current circle by adding a new entry to the map for circle frames
    model.changeSize(10, "Circle", 30, 70);

    // Getting the frames for circle
    Map<Integer, IShape> circleFrames = model.getShapes().get("Circle");

    // testing that model has expected circle entry and expected frames for circle
    assertEquals(2, circleFrames.size());
    assertTrue(circleFrames.containsKey(10));
    assertTrue(circleFrames.containsValue(new Circle(new ArrayList<Integer>(Arrays.asList(0, 0, 0)),
        new Position(0, 0), 30, 70)));
  }


  // Testing changeSize() to ensure that the model's map can't be modified
  @Test
  public void testChangeSizeModifiedMap() {
    this.setUp();

    // Changes the current circle by adding a new entry to the map for circle frames
    model.changeSize(10, "Circle", 30, 70);

    // Modifies the map by modifying global variable and using getShapes()
    this.modifyModel();

    // Getting the circle frames for the model after the modifications
    Map<Integer, IShape> circleFrames = model.getShapes().get("Circle");

    assertEquals(2, circleFrames.size());
    assertFalse(circleFrames.containsKey(30));
    assertFalse(circleFrames.containsValue(new Circle(new ArrayList<Integer>(Arrays.asList(200, 200,
        200)), new Position(0, 0), 40)));
  }


  // Modifying the model by changing the contents of the map of shapes and the map of frames for
  // a circle
  private void modifyModel() {
    // adding a circle to the circle map by modifying the global variable
    IShape addedCircle = new Circle(new ArrayList<Integer>(Arrays.asList(200, 200, 200)),
        new Position(0, 0), 40);
    circles.put(30, addedCircle);

    // adding a circle to the circle map by modifying the model's map of circle frames
    Map<Integer, IShape> modifiedCircleFrames = model.getShapes().get("Circle");
    modifiedCircleFrames.put(50, new Circle(new ArrayList<Integer>(Arrays.asList(20, 20, 0)),
        new Position(0, 0), 40));
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS VALID CASES FOR toString()

  // Testing toString() for case of empty list of shapes
  @Test
  public void testToStringEmptyList() {
    model = new AnimationModel();
    assertEquals("canvas 0 0 360 360", model.toString());
  }

  // Testing toString() for case of list of same type of shapes
  @Test
  public void testTextRenderSameShapesList() {
    this.setUp();

    // new circle entry in map
    model.changeColor(10, "Circle", 100, 100, 100);
    // 1 space between numbers and 3 spaces between start/end entries
    assertEquals("canvas 0 0 360 360\nshape Circle ellipse\n"
        + "motion Circle 1 0 0 10 10 0 0 0 10 0 0 10 10 100 100 100",
        model.toString());
  }

  // Testing toString() for case of list of different types of shapes
  @Test
  public void testTextRenderDifferentShapesList() {
    // creating a new rectangle and a map of its frames
    IShape rect = new Rect(new ArrayList<Integer>(Arrays.asList(100, 0, 0)),
        new Position(10, 10), 50);
    Map<Integer, IShape> rectangles = new TreeMap<Integer, IShape>();
    rectangles.put(1, rect);

    // creating a new model with a map containing the frames for a rectangle and circle
    Map<String, Map<Integer, IShape>> tempAnimation = new TreeMap<String, Map<Integer, IShape>>();
    tempAnimation.put("Circle", circles);
    tempAnimation.put("Rect", rectangles);
    IAnimationModel tempModel = new AnimationModel(tempAnimation);

    // changing the size and position of circle
    tempModel.changeSize(10, "Circle", 20, 20);
    tempModel.move(20, "Circle", 100, 50);
    tempModel.move(30, "Circle", 100, 60);

    // adding the same rectangle to its frames
    tempModel.changeSize(10, "Rect", 50, 50);
    tempModel.changeSize(20, "Rect", 50, 50);
    // changing the size of the rectangle
    tempModel.changeSize(30, "Rect", 30, 80);

    // 1 space between numbers and 3 spaces between start/end entries
    assertEquals("canvas 0 0 360 360\nshape Circle ellipse\n"
            + "motion Circle 1 0 0 10 10 0 0 0 10 0 0 20 20 0 0 0\n"
            + "motion Circle 10 0 0 20 20 0 0 0 20 100 50 20 20 0 0 0\n"
            + "motion Circle 20 100 50 20 20 0 0 0 30 100 60 20 20 0 0 0\n"
            + "shape Rect rectangle\n"
            + "motion Rect 1 10 10 50 50 100 0 0 10 10 10 50 50 100 0 0\n"
            + "motion Rect 10 10 10 50 50 100 0 0 20 10 10 50 50 100 0 0\n"
            + "motion Rect 20 10 10 50 50 100 0 0 30 10 10 30 80 100 0 0",
        tempModel.toString());
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS getShapes()


  // Testing getShapes() for a model with an empty map
  @Test
  public void testGetShapesEmptyMap() {
    this.setUp();

    IAnimationModel emptyModel = new AnimationModel();
    Map<String, Map<Integer, IShape>> actualMap = emptyModel.getShapes();

    assertTrue(actualMap.isEmpty());
  }


  // Testing getShapes() for a model with a map with shapes
  @Test
  public void testGetShapes() {
    this.setUp();

    Map<String, Map<Integer, IShape>> actualMap = model.getShapes();
    Collection<Map<Integer, IShape>> actualFrames = actualMap.values();

    assertTrue(actualMap.containsKey("Circle"));
    assertTrue(actualMap.containsValue(circles));
  }


  // Testing getShapes() doesn't allow for map to be modified
  @Test
  public void testGetShapesEmptyMapModify() {
    this.setUp();

    IAnimationModel emptyModel = new AnimationModel();
    Map<String, Map<Integer, IShape>> emptyMap = emptyModel.getShapes();
    Collection<Map<Integer, IShape>> emptyFrames = emptyMap.values();

    // Adding a circle to map
    for (Map<Integer, IShape> frames : emptyFrames) {
      frames.put(40, circle);
    }

    // Adding an oval to map
    emptyMap.put("Oval", circles);

    //assertTrue(emptyMap.isEmpty());
    assertFalse(emptyModel.getShapes().containsKey("Oval"));
    assertTrue(emptyModel.getShapes().isEmpty());
  }

  //----------------------------------------------------------------------------------------------
  //THIS SECTION TESTS changeSpeed()

  //Tests that changeSpeed returns the correct map with changed speeds
  @Test
  public void changeSpeed() {
    Map<String, Map<Integer, IShape>> expected = new TreeMap<>();
    Map<Integer, IShape> e1 = new TreeMap<>();
    Map<Integer, IShape> e2 = new TreeMap<>();
    e1.put(20, rect1);
    e1.put(40, rect2);
    e2.put(0, rect2);
    e2.put(30, rect1);
    expected.put("Shape1", e1);
    expected.put("Shape2", e2);
    assertEquals(expected, model2.changeSpeed(2));

    model2.changeSpeed(0.5);
    expected = new TreeMap<>();
    e1 = new TreeMap<>();
    e2 = new TreeMap<>();
    e1.put(5, rect1);
    e1.put(10, rect2);
    e2.put(0, rect2);
    e2.put(7, rect1);
    expected.put("Shape1", e1);
    expected.put("Shape2", e2);
    assertEquals(expected, model2.changeSpeed(0.5));
  }

  //----------------------------------------------------------------------------------------------
  //THIS SECTION TESTS getState()

  //Tests that getState returns the correct state of the given shape at the given frame correctly
  @Test
  public void getState() {

    Map<Integer, IShape> shape1 = new TreeMap<>();
    shape1.put(10, rect1);
    shape1.put(20, rect2);

    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Shape1", shape1);
    model2 = new AnimationModel(shapes);

    IShape rect4 =
        new Rect(Arrays.asList(177, 227, 152), new Position(13, 4), 4, 3);
    IShape rect5 =
        new Rect(Arrays.asList(131, 211, 91), new Position(17, 1), 2, 2);

    assertNull(model2.getState(5, "Shape1"));
    assertEquals(rect1, model2.getState(10, "Shape1"));
    assertEquals(rect5, model2.getState(18, "Shape1"));
    assertEquals(rect4, model2.getState(15, "Shape1"));
    assertEquals(rect2, model2.getState(20, "Shape1"));
    assertEquals(rect2, model2.getState(25, "Shape1"));
  }

  //Tests that getState throws an exception when given a shape not in the model
  @Test(expected = IllegalArgumentException.class)
  public void testGetStateInvalidShape() {
    model2.getState(10, "Shape3");
  }

  //Tests that getState throws an exception for a negative frame
  @Test(expected = IllegalArgumentException.class)
  public void testGetStateInvalid() {
    model2.getState(-1, "Shape1");
  }

  //----------------------------------------------------------------------------------------------
  //THIS SECTION TESTS getFullState()

  //Tests that getFullState returns the correct map for specific instants in time
  @Test
  public void getFullState() {

    Map<Integer, IShape> shape1 = new TreeMap<>();
    shape1.put(10, rect1);
    shape1.put(20, rect2);

    Map<Integer, IShape> shape2 = new TreeMap<>();
    shape2.put(0, rect2);
    shape2.put(15, rect1);

    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Shape1", shape1);
    shapes.put("Shape2", shape2);
    model2 = new AnimationModel(shapes);

    IShape rect4 =
        new Rect(Arrays.asList(177, 227, 152), new Position(13, 4), 4, 3);
    IShape rect5 =
        new Rect(Arrays.asList(151, 218, 118), new Position(15, 2), 3, 2);

    Map<String, IShape> stateAt0 = new TreeMap<>();
    Map<String, IShape> stateAt10 = new TreeMap<>();
    Map<String, IShape> stateAt15 = new TreeMap<>();
    Map<String, IShape> stateAt20 = new TreeMap<>();

    stateAt0.put("Shape1", null);
    stateAt0.put("Shape2", rect2);

    stateAt10.put("Shape1", rect1);
    stateAt10.put("Shape2", rect5);

    stateAt15.put("Shape1", rect4);
    stateAt15.put("Shape2", rect1);

    stateAt20.put("Shape1", rect2);
    stateAt20.put("Shape2", rect1);

    assertEquals(stateAt0, model2.getFullState(0));
    //assertEquals(stateAt10, model2.getFullState(10));
    assertEquals(stateAt15, model2.getFullState(15));
    assertEquals(stateAt20, model2.getFullState(20));
    assertEquals(stateAt20, model2.getFullState(25));
  }

  //Tests that getFullState throws an exception when given a negative frame
  @Test(expected = IllegalArgumentException.class)
  public void getFullStateInvalid() {
    model2.getFullState(-3);
  }

  //----------------------------------------------------------------------------------------------
  //THIS SECTION TESTS add

  //Tests that the add method works correctly for valid inputs
  @Test
  public void add() {

    model2 = new AnimationModel();

    //Add at frame 0 and it should be there from the start of the animation
    model2.add(0, "Shape1", rect1);
    Map<String, Map<Integer, IShape>> expected1 = new TreeMap<>();
    Map<Integer, IShape> e = new TreeMap<>();
    e.put(0, rect1);
    expected1.put("Shape1", e);
    assertEquals(expected1, model2.getShapes());

    //Add at a later frame and it should appear then
    model2.add(14, "Shape2", rect2);
    Map<Integer, IShape> e2 = new TreeMap<>();
    e2.put(14, rect2);
    expected1.put("Shape2", e2);
    assertEquals(expected1, model2.getShapes());
  }

  //Tests the add method which takes in a String
  @Test
  public void addStringOnly() {
    model2 = new AnimationModel();
    model2.add("SomeShape");
    model2.add("AnotherShape");
    Map<String, Map<Integer, IShape>> expected = new TreeMap<>();
    expected.put("SomeShape", new TreeMap<>());
    expected.put("AnotherShape", new TreeMap<>());
    assertEquals(expected, model2.getShapes());
  }

  //Tests that the add method throws an exception for a negative frame
  @Test(expected = IllegalArgumentException.class)
  public void addInvalid() {
    model2.add(-5, "Shape3", rect3);
  }

  //Tests that the add method throws an exception for a String already in the model
  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateKey() {
    model2.add(10, "Shape2", rect3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addDuplicateKey2() {
    model2.add("Shape2");
  }

  //----------------------------------------------------------------------------------------------
  //THIS SECTION TESTS remove

  //Tests that the remove method works correctly for a valid input at different frames
  @Test
  public void remove() {
    //Remove at frame 0 and it should not exist
    model2.remove(0, "Shape1");
    Map<String, Map<Integer, IShape>> expected1 = new TreeMap<>();
    Map<Integer, IShape> e = new TreeMap<>();
    e.put(0, rect2);
    e.put(15, rect1);
    expected1.put("Shape2", e);
    assertEquals(expected1, model2.getShapes());

    //Remove at a later frame and it should disappear for the rest of the animation
    model2.remove(13, "Shape2");
    Map<String, Map<Integer, IShape>> expected2 = new TreeMap<>();
    e = new TreeMap<>();
    e.put(0, rect2);
    expected2.put("Shape2", e);
    assertEquals(expected2, model2.getShapes());
  }

  //Tests that the remove method throws an exception when the frame is negative
  @Test(expected = IllegalArgumentException.class)
  public void removeInvalidFrame() {
    model2.remove(-10, "Shape2");
  }

  //Tests that the remove method throws an exception when the given shape is not in the model
  @Test(expected = IllegalArgumentException.class)
  public void removeInvalid() {
    model2.remove(18, "Shape3");
  }

  // ---------------------------------------------------------------------------------------------
  // THIS SECTION TESTS equals() AND hashCode()

  // Testing equals() for both same and different models
  @Test
  public void testEquals() {
    this.setUp();

    IAnimationModel modelSame = new AnimationModel(shapes);
    IAnimationModel modelDif = new AnimationModel();

    assertEquals(model, modelSame);
    assertNotEquals(model, modelDif);
    assertNotEquals(modelSame, modelDif);
    assertNotEquals(model, null);
  }

  // Testing hashCode() for both same and different models
  @Test
  public void testHashCode() {
    IAnimationModel modelSame = new AnimationModel(shapes);
    IAnimationModel modelDif = new AnimationModel();

    assertEquals(model.hashCode(), modelSame.hashCode());
    assertNotEquals(model.hashCode(), modelDif.hashCode());
    assertNotEquals(modelSame.hashCode(), modelDif.hashCode());
  }
}