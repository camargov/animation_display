
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import cs3500.animator.model.Rect;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Circle;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IAnimationView;

import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextualAnimationView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



/**
 * Testing the behavior of the methods in the SVG view class for exceptions and correctly
 * displaying an animation using XML format.
 */
public class SVGAnimationViewTest {
  IShape rect1 =
      new Rect(Arrays.asList(255, 255, 255), new Position(7, 8), 6, 4);
  IShape rect2 =
      new Rect(Arrays.asList(100, 200, 50), new Position(20, 0), 2, 2);
  IShape rect3 = new Rect(Arrays.asList(0, 0, 0), new Position(10, 10), 3, 4);
  IAnimationModel model = new AnimationModel();

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
    multipleShapes.put("Rect", shape1);
    multipleShapes.put("Circle", circles);

    model2Shapes = new AnimationModel(multipleShapes);
  }

  //THIS SECTION TESTS FOR EXCEPTIONS THROWN IN THE CONSTRUCTOR

  // Tests that view's constructor throws an exception when given Appendable is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new SVGAnimationView(null, model,1);
  }

  // Tests that view's constructor throws an exception when given model is null
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    new SVGAnimationView(new StringBuffer(), null,1);
  }

  // Tests that view's constructor throws an exception when given speed is negative
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSpeed() {
    new SVGAnimationView(new StringBuffer(), model,-5);
  }

  // Tests that view's constructor throws an exception when given speed is zero
  @Test(expected = IllegalArgumentException.class)
  public void testZeroSpeed() {
    new SVGAnimationView(new StringBuffer(), model,0);
  }

  //THIS SECTION TESTS WHETHER THE VIEW RENDERS THE CORRECTLY FORMATTED TEXT

  //Tests that the view renders correctly for an empty model (no shapes)
  @Test
  public void testEmptyModel() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new SVGAnimationView(out, new AnimationModel(), 1);
    view.render();
    String expected = "<svg width=\"360\" height=\"360\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n</svg>";

    assertEquals(expected, out.toString());
  }

  //Tests that the view renders correctly for a model with a single shape
  @Test
  public void testSingleShape() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new SVGAnimationView(out, model, 1);
    view.render();
    String expected =
        "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"Shape1\" x=\"7\" y=\"8\" width=\"6\" height=\"4\" fill=\"rgb(255, 255, 255)\""
            + " visibility=\"visible\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" attributeName=\"x\""
            + " from=\"7\" to=\"10\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" attributeName=\"y\""
            + " from=\"8\" to=\"10\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" "
            + "attributeName=\"width\" from=\"6\" to=\"3\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" "
            + "attributeName=\"fill\" from=\"rgb(255, 255, 255)\" to=\"rgb(0, 0, 0)\" "
            + "fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" attributeName=\"x\""
            + " from=\"10\" to=\"20\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" attributeName=\"y\""
            + " from=\"10\" to=\"0\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"width\" from=\"3\" to=\"2\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"height\" from=\"4\" to=\"2\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(100, 200, 50)\" "
            + "fill=\"freeze\" />\n"
        + "</rect>"
        + "\n</svg>";
    assertEquals(expected, out.toString());
  }

  //Tests that the view renders correctly for a model with multiple shapes
  @Test
  public void testMultipleShapes() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new SVGAnimationView(out, model2Shapes, 1);
    view.render();
    String expected =
        "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<circle id=\"Circle\" cx=\"10\" cy=\"10\" r=\"20\" fill=\"rgb(0, 0, 0)\" "
            + "visibility=\"visible\" >\n"
            + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"39000ms\" "
            + "attributeName=\"cx\" from=\"10\" to=\"40\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"1000ms\" dur=\"39000ms\" "
            + "attributeName=\"cy\" from=\"10\" to=\"60\" fill=\"freeze\" />\n"
            + "</circle>\n"
            + "<rect id=\"Rect\" x=\"7\" y=\"8\" width=\"6\" height=\"4\" fill=\""
            + "rgb(255, 255, 255)\" "
            + "visibility=\"visible\" >\n"
            + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" "
            + "attributeName=\"x\" "
            + "from=\"7\" to=\"10\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" "
            + "attributeName=\"y\" "
            + "from=\"8\" to=\"10\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" "
            + "attributeName=\"width\" from=\"6\" to=\"3\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"4000ms\" "
            + "attributeName=\"fill\" from=\"rgb(255, 255, 255)\" to=\"rgb(0, 0, 0)\" "
            + "fill=\"freeze\" "
            + "/>\n"
            + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"x\" "
            + "from=\"10\" to=\"20\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"y\" "
            + "from=\"10\" to=\"0\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"width\" from=\"3\" to=\"2\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"height\" from=\"4\" to=\"2\" fill=\"freeze\" />\n"
            + "    <animate attributeType=\"xml\" begin=\"14000ms\" dur=\"6000ms\" "
            + "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(100, 200, 50)\" "
            + "fill=\"freeze\" />\n"
            + "</rect>"
            + "\n</svg>";
    assertEquals(expected, out.toString());
  }

  //Tests that the seconds get rendered properly when the speed is slower than 1
  @Test
  public void slowerSpeed() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new SVGAnimationView(out, model, 0.5);
    view.render();
    String expected =
        "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"Shape1\" x=\"7\" y=\"8\" width=\"6\" height=\"4\" fill=\"rgb(255, 255, 255)\""
            + " visibility=\"visible\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"20000ms\" dur=\"8000ms\" attributeName=\"x\" "
            + "from=\"7\" to=\"10\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"20000ms\" dur=\"8000ms\" attributeName=\"y\" "
            + "from=\"8\" to=\"10\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"20000ms\" dur=\"8000ms\" "
            + "attributeName=\"width\" from=\"6\" to=\"3\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"20000ms\" dur=\"8000ms\" "
            + "attributeName=\"fill\" from=\"rgb(255, 255, 255)\" to=\"rgb(0, 0, 0)\" "
            + "fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"28000ms\" dur=\"12000ms\" attributeName=\"x\""
            + " from=\"10\" to=\"20\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"28000ms\" dur=\"12000ms\" attributeName=\"y\""
            + " from=\"10\" to=\"0\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"28000ms\" dur=\"12000ms\" "
            + "attributeName=\"width\" from=\"3\" to=\"2\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"28000ms\" dur=\"12000ms\" "
            + "attributeName=\"height\" from=\"4\" to=\"2\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"28000ms\" dur=\"12000ms\" "
            + "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(100, 200, 50)\" "
            + "fill=\"freeze\" />\n"
        + "</rect>"
        + "\n</svg>";
    assertEquals(expected, out.toString());
  }

  //Tests that the seconds get rendered properly when the speed is faster than 1
  @Test
  public void fasterSpeed() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new SVGAnimationView(out, model, 4);
    view.render();
    String expected =
        "<svg width=\"360\" height=\"360\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
        + "<rect id=\"Shape1\" x=\"7\" y=\"8\" width=\"6\" height=\"4\" fill=\"rgb(255, 255, 255)\""
            + " visibility=\"visible\" >\n"
        + "    <animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"x\" "
            + "from=\"7\" to=\"10\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" attributeName=\"y\" "
            + "from=\"8\" to=\"10\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" "
            + "attributeName=\"width\" from=\"6\" to=\"3\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"2500ms\" dur=\"1000ms\" "
            + "attributeName=\"fill\" from=\"rgb(255, 255, 255)\" to=\"rgb(0, 0, 0)\" "
            + "fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"x\" "
            + "from=\"10\" to=\"20\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" attributeName=\"y\" "
            + "from=\"10\" to=\"0\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" "
            + "attributeName=\"width\" from=\"3\" to=\"2\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" "
            + "attributeName=\"height\" from=\"4\" to=\"2\" fill=\"freeze\" />\n"
        + "    <animate attributeType=\"xml\" begin=\"3500ms\" dur=\"1500ms\" "
            + "attributeName=\"fill\" from=\"rgb(0, 0, 0)\" to=\"rgb(100, 200, 50)\" "
            + "fill=\"freeze\" />\n"
        + "</rect>"
        + "\n</svg>";
    assertEquals(expected, out.toString());
  }

  //Tests that a customized canvas size is rendered correctly
  @Test
  public void canvasSize() throws IOException {
    Appendable out = new StringBuffer();
    model = new AnimationModel(Arrays.asList(4, 6, 23, 45));
    IAnimationView view = new SVGAnimationView(out, model, 1);
    view.render();
    String expected = "<svg width=\"23\" height=\"45\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n</svg>";

    assertEquals(expected, out.toString());
  }

  //Tests that the render method throws an IOException when the output fails
  @Test(expected = IOException.class)
  public void renderException() throws IOException {
    OutputStreamWriter out = new OutputStreamWriter(new ByteArrayOutputStream());
    out.close();
    SVGAnimationView view = new SVGAnimationView(out,  model,1);
    view.render();
  }

  //Tests that the view successfully outputs to an .svg file if given a FileWriter
  @Test
  public void fileOutput() throws IOException {
    FileWriter out = new FileWriter("test_output.svg");
    SVGAnimationView view = new SVGAnimationView(out, model, 1);
    view.render();
    String readFile = Files.readString(Path.of("test_output.svg"));
    assertEquals("", readFile);
  }

  // ------------------------------------------------------------------------------------------
  // TESTING THE SETTER METHODS

  // Testing setSpeed() using hashCode() since the view's hashcode includes its speed
  @Test
  public void testSetSpeed() {
    IAnimationView view = new SVGAnimationView();
    int prevCode = view.hashCode(); // saving the view's previous speed
    view.setSpeed(2); // changing the view's speed

    assertNotEquals(prevCode, view.hashCode());
  }

  // Testing setModel() using hashCode() since the view's hashcode is dependent on the model
  @Test
  public void testSetModel() {
    IAnimationView view = new SVGAnimationView();
    int prevCode = view.hashCode(); // saving the view's previous speed
    view.setModel(new ViewModel(model)); // changing the view's model

    assertNotEquals(prevCode, view.hashCode());
  }

  // Testing setOut() using render() to compare the previous and new output strings
  @Test
  public void testSetOut() throws IOException {
    Appendable out = new StringBuffer();
    IAnimationView view = new SVGAnimationView(out, new AnimationModel(), 1);
    view.render();
    String expected = "<svg width=\"360\" height=\"360\" version=\"1.1\" "
        + "xmlns=\"http://www.w3.org/2000/svg\">\n</svg>";

    assertEquals(expected, out.toString());
    view.setOut(new StringBuffer("file.svg"));
    view.render();
    assertEquals(expected, out.toString());
  }

  // ------------------------------------------------------------------------------------------

  // Testing equals()
  @Test
  public void testEquals() {
    IAnimationView viewEmpty1 = new SVGAnimationView();
    IAnimationView viewEmpty2 = new SVGAnimationView();
    IAnimationView textView = new TextualAnimationView();

    assertTrue(viewEmpty1.equals(viewEmpty2));
    assertFalse(viewEmpty1.equals(textView));
  }

  // Testing hashCode()
  @Test
  public void testHashCode() {
    IAnimationView viewEmpty1 = new SVGAnimationView();
    IAnimationView viewEmpty2 = new SVGAnimationView();
    IAnimationView textView = new TextualAnimationView();

    assertEquals(viewEmpty1.hashCode(), viewEmpty2.hashCode());
    assertNotEquals(viewEmpty1.hashCode(), textView.hashCode());
  }
}