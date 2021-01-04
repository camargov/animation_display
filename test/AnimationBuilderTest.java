import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Circle;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rect;
import cs3500.animator.util.AnimationBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Defines tests for our implementation of AnimationBuilder in AnimationModel.
 */
public class AnimationBuilderTest {

  IShape rect1 =
      new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 6, 4);
  IShape rect2 =
      new Rect(Arrays.asList(100, 200, 50), new Position(20, 0), 2, 2);
  IShape rect3 = new Rect(Arrays.asList(0, 0, 0), new Position(10, 10), 3, 4);
  IAnimationModel model;

  IShape circle1 = new Circle(Arrays.asList(0, 0, 0), new Position(10, 10), 20);
  IShape circle2 = new Circle(Arrays.asList(0, 0, 0), new Position(40, 60), 20);
  IAnimationModel model2Shapes;
  AnimationBuilder<IAnimationModel> builder = new AnimationModel.Builder();

  @Before
  public void setUpModels() {
    builder = new AnimationModel.Builder();
    Map<Integer, IShape> shape1 = new TreeMap<>();
    shape1.put(10, rect1);
    shape1.put(20, rect2);
    shape1.put(14, rect3);

    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Shape1", shape1);
    model = new AnimationModel(shapes, Arrays.asList(4, 5, 100, 200));

    Map<Integer, IShape> circles = new TreeMap<>();
    circles.put(1, circle1);
    circles.put(40, circle2);

    Map<String, Map<Integer, IShape>> multipleShapes = new TreeMap<>();
    multipleShapes.put("Circle", circles);
    multipleShapes.put("Rect", shape1);

    model2Shapes = new AnimationModel(multipleShapes);
  }

  //Tests that we can declare shapes in the animation
  @Test
  public void testDeclareShape() {
    builder.declareShape("Shape1", "rectangle")
        .declareShape("Shape2", "ellipse");
    IAnimationModel testModel = builder.build();

    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Shape1", new TreeMap<>());
    shapes.put("Shape2", new TreeMap<>());
    assertEquals(shapes, testModel.getShapes());
  }

  //Tests that declareShape throws an exception for an unsupported shape
  @Test(expected = IllegalArgumentException.class)
  public void testUnsupportedShape() {
    builder.declareShape("Shape1", "triangle");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeAlreadyThere() {
    builder.declareShape("Shape1", "rectangle")
        .declareShape("Shape1", "rectangle");
  }

  //Tests that we can build a model with multiple motions on multiple shapes in different orders
  @Test
  public void testAddMotion() {
    builder.declareShape("Circle", "ellipse")
        .declareShape("Rect", "rectangle")
        .addMotion("Rect", 10, 7,8, 6,4,255,255,255,14,
            10,10,3,4,0,0,0)
        .addMotion("Circle", 1, 10, 10, 20, 20, 0, 0, 0,
            40, 40, 60, 20, 20, 0, 0, 0)
        .addMotion("Rect", 14,10,10,3,4,0,0,0,20,20,
            0,2,2,100,200,50);
    IAnimationModel testModel = builder.build();
    assertEquals(model2Shapes.getShapes(), testModel.getShapes());
  }

  //Tests that movements which are illegal in the model are illegal in the builder too
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMotion() {
    builder.declareShape("Shape1", "rectangle").addMotion("Shape1",
        -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShapeNotThere() {
    builder.addMotion("Shape1", 1, 2, 3, 4, 5, 6, 7, 8, 9,
        10, 11, 12, 13, 14, 15, 16);
  }

  //Tests that we can set the bounds of the animation using the setBounds method
  @Test
  public void testSetBounds() {
    builder.setBounds(45, 67, 300, 400);
    IAnimationModel expected = new AnimationModel(Arrays.asList(45, 67, 300, 400));
    assertEquals(expected, builder.build());
  }

  //Tests that we can chain all the methods and build successfully
  @Test
  public void testBuild() {
    assertEquals(model, builder.declareShape("Shape1", "rectangle")
        .addMotion("Shape1", 14, 10, 10, 3, 4, 0, 0, 0, 20,
            20, 0, 2, 2, 100, 200, 50)
        .addMotion("Shape1", 10, 7, 8, 6, 4, 255, 255, 255,
            14, 10, 10, 3, 4, 0, 0, 0)
        .setBounds(4, 5, 100, 200)
        .build());
  }
}