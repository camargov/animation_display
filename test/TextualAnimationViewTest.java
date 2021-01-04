import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rect;
import cs3500.animator.model.Circle;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextualAnimationView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing the behavior of the methods in the text view class for exceptions and correctly
 * displaying an animation.
 */
public class TextualAnimationViewTest {

  IShape rect1 =
      new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 6, 4);
  IShape rect2 =
      new Rect(Arrays.asList(100, 200, 50), new Position(20, 0), 2, 2);
  IShape rect3 = new Rect(Arrays.asList(0, 0, 0), new Position(10, 10), 3, 4);
  IAnimationModel model;

  IShape circle1 = new Circle(Arrays.asList(0, 0, 0), new Position(10, 10), 20);
  IShape circle2 = new Circle(Arrays.asList(0, 0, 0), new Position(40, 60), 20);
  IAnimationModel model2Shapes;

  @Before
  public void setUpModels() {
    Map<Integer, IShape> shape1 = new TreeMap<>();
    shape1.put(10, rect1);
    shape1.put(20, rect2);
    shape1.put(14, rect3);

    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Shape1", shape1);
    model = new AnimationModel(shapes);

    Map<Integer, IShape> circles = new TreeMap<>();
    circles.put(1, circle1);
    circles.put(40, circle2);

    Map<String, Map<Integer, IShape>> multipleShapes = new TreeMap<>();
    multipleShapes.put("Circle", circles);
    multipleShapes.put("Rect", shape1);

    model2Shapes = new AnimationModel(multipleShapes);
  }

  // ------------------------------------------------------------------------------------------
  // THIS SECTION TESTS THE EXCEPTIONS THE CONSTRUCTOR MAY THROW

  // Tests that view's constructor throws an exception when given Appendable is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new TextualAnimationView(null, model,1);
  }

  // Tests that view's constructor throws an exception when given model is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new TextualAnimationView(new StringBuffer(), null,1);
  }

  // Tests that view's constructor throws an exception when given speed is negative
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpeed() {
    new TextualAnimationView(new StringBuffer(), model,-5);
  }

  // Tests that view's constructor throws an exception when given speed is zero
  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpeed() {
    new TextualAnimationView(new StringBuffer(), model,0);
  }
  // ------------------------------------------------------------------------------------------

  // Tests that the view correctly renders a model with empty frames
  @Test
  public void testNoShapes() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new TextualAnimationView(out, new AnimationModel(), 1);
    view.render();
    assertEquals("canvas 0 0 360 360", out.toString());
  }

  //Tests that the view renders correctly for a model with a single shape
  @Test
  public void testSingleShape() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new TextualAnimationView(out, model, 1);
    view.render();
    String expected = "canvas 0 0 360 360\n"
        + "shape Shape1 rectangle\n"
        + "motion Shape1 10.00 7 8 6 4 255 255 255 14.00 10 10 3 4 0 0 0\n"
        + "motion Shape1 14.00 10 10 3 4 0 0 0 20.00 20 0 2 2 100 200 50";
    assertEquals(expected, out.toString());
  }


  // Tests that the view correctly renders a model with more than one shape
  @Test
  public void testMoreThanOneShape() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new TextualAnimationView(out, model2Shapes, 1);
    view.render();
    String expected = "canvas 0 0 360 360\n"
        + "shape Circle ellipse\n"
        + "motion Circle 1.00 10 10 20 20 0 0 0 40.00 40 60 20 20 0 0 0\n"
        + "shape Rect rectangle\n"
        + "motion Rect 10.00 7 8 6 4 255 255 255 14.00 10 10 3 4 0 0 0\n"
        + "motion Rect 14.00 10 10 3 4 0 0 0 20.00 20 0 2 2 100 200 50";
    assertEquals(expected, out.toString());
  }


  //Tests that the seconds get rendered properly when the speed is slower than 1
  @Test
  public void slowerSpeed() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new TextualAnimationView(out, model, 0.5);
    view.render();
    String expected = "canvas 0 0 360 360\n"
        + "shape Shape1 rectangle\n"
        + "motion Shape1 20.00 7 8 6 4 255 255 255 28.00 10 10 3 4 0 0 0\n"
        + "motion Shape1 28.00 10 10 3 4 0 0 0 40.00 20 0 2 2 100 200 50";
    assertEquals(expected, out.toString());
  }

  //Tests that the seconds get rendered properly when the speed is faster than 1
  @Test
  public void fasterSpeed() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new TextualAnimationView(out, model, 3);
    view.render();
    String expected = "canvas 0 0 360 360\n"  //don't know what should go here yet
        + "shape Shape1 rectangle\n"
        + "motion Shape1 3.33 7 8 6 4 255 255 255 4.67 10 10 3 4 0 0 0\n"
        + "motion Shape1 4.67 10 10 3 4 0 0 0 6.67 20 0 2 2 100 200 50";
    assertEquals(expected, out.toString());
  }

  //Tests that the render method throws an IOException when the output fails
  @Test(expected = IOException.class)
  public void renderException() throws IOException {
    OutputStreamWriter out = new OutputStreamWriter(new ByteArrayOutputStream());
    out.close();
    IAnimationView view = new TextualAnimationView(out,  model,1);
    view.render();
  }

  // ------------------------------------------------------------------------------------------
  // TESTING THE SETTER METHODS

  // Testing setSpeed() using hashCode() since the view's hashcode includes its speed
  @Test
  public void testSetSpeed() {
    IAnimationView view = new TextualAnimationView();
    int prevCode = view.hashCode(); // saving the view's previous speed
    view.setSpeed(2); // changing the view's speed

    assertNotEquals(prevCode, view.hashCode());
  }

  // Testing setModel() using hashCode() since the view's hashcode is dependent on the model
  @Test
  public void testSetModel() {
    IAnimationView view = new TextualAnimationView();
    int prevCode = view.hashCode(); // saving the view's previous speed
    view.setModel(new ViewModel(model)); // changing the view's model

    assertNotEquals(prevCode, view.hashCode());
  }

  // Testing setOut() using render() to compare the previous and new output strings
  @Test
  public void testSetOut() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new TextualAnimationView(out, new AnimationModel(), 1);
    view.render();

    assertEquals("canvas 0 0 360 360", out.toString());
    view.setOut(new StringBuffer("file.txt"));
    view.render();
    assertEquals("canvas 0 0 360 360", out.toString());
  }

  // ------------------------------------------------------------------------------------------

  // Testing equals()
  @Test
  public void testEquals() {
    IAnimationView viewEmpty1 = new TextualAnimationView();
    IAnimationView viewEmpty2 = new TextualAnimationView();
    IAnimationView svgView = new SVGAnimationView();

    assertTrue(viewEmpty1.equals(viewEmpty2));
    assertFalse(viewEmpty1.equals(svgView));
  }

  // Testing hashCode()
  @Test
  public void testHashCode() {
    IAnimationView viewEmpty1 = new TextualAnimationView();
    IAnimationView viewEmpty2 = new TextualAnimationView();
    IAnimationView svgView = new SVGAnimationView();

    assertEquals(viewEmpty1.hashCode(), viewEmpty2.hashCode());
    assertNotEquals(viewEmpty1.hashCode(), svgView.hashCode());
  }
}