import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Circle;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.IViewModel;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rect;
import cs3500.animator.model.ViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Defines tests for our implementation of ViewModel.
 */
public class ViewModelTest {

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

  //Tests that the constructor throws an exception when given a null model
  @Test(expected = NullPointerException.class)
  public void testNullModel() {
    IViewModel vm = new ViewModel(null);
  }

  //Tests that the toString method works the same as in the regular model
  @Test
  public void testToString() {
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

    IViewModel tempViewModel = new ViewModel(tempModel);

    // 1 space between numbers and 3 spaces between start/end entries
    assertEquals(tempModel.toString(),
        tempViewModel.toString());
  }

  //Tests that the getState method works the same as in the regular model
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

    IViewModel vm2 = new ViewModel(model2);

    assertNull(vm2.getState(5, "Shape1"));
    assertEquals(rect1, vm2.getState(10, "Shape1"));
    assertEquals(rect5, vm2.getState(18, "Shape1"));
    assertEquals(rect4, vm2.getState(15, "Shape1"));
    assertEquals(rect2, vm2.getState(20, "Shape1"));
    assertEquals(rect2, vm2.getState(25, "Shape1"));
  }

  //Tests that the getFullState method works the same as in the regular model
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

    IViewModel vm2 = new ViewModel(model2);

    assertEquals(stateAt0, vm2.getFullState(0));
    //assertEquals(stateAt10, vm2.getFullState(10));
    assertEquals(stateAt15, vm2.getFullState(15));
    assertEquals(stateAt20, vm2.getFullState(20));
    assertEquals(stateAt20, vm2.getFullState(25));
  }

  //Tests that the getShapes method works the same as in the regular model
  @Test
  public void testGetShapes() {
    this.setUp();

    IViewModel vm = new ViewModel(model);

    Map<String, Map<Integer, IShape>> actualMap = vm.getShapes();
    Collection<Map<Integer, IShape>> actualFrames = actualMap.values();

    assertTrue(actualMap.containsKey("Circle"));
    assertTrue(actualMap.containsValue(circles));
  }

  //Tests that the getCanvas method works the same as in the regular model
  @Test
  public void testGetCanvas() {
    IViewModel vm = new ViewModel(model);

    assertEquals(model.getCanvas(), vm.getCanvas());
  }
}
