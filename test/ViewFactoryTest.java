import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextualAnimationView;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.VisualAnimationView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing the behavior of the methods in ViewFactory.
 */
public class ViewFactoryTest {
  ViewFactory factory;

  @Before
  public void setUp() {
    factory = new ViewFactory();
  }

  // Testing that createView() throws an exception when given an empty string
  @Test(expected = IllegalArgumentException.class)
  public void testEmptyString() {
    factory.createView("");
  }

  // Testing that createView() throws an exception when given an invalid string
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidString() {
    factory.createView("animate");
  }

  // Testing that createView() returns a textual view when given "text", no matter the
  // capitalization
  @Test
  public void testTextView() {
    assertEquals(new TextualAnimationView(), factory.createView("text"));
    assertEquals(new TextualAnimationView(), factory.createView("TEXT"));
    assertEquals(new TextualAnimationView(), factory.createView("Text"));
  }

  // Testing that createView() returns an SVG view when given "svg"
  @Test
  public void testSVGView() {
    assertEquals(new SVGAnimationView(), factory.createView("svg"));
    assertEquals(new SVGAnimationView(), factory.createView("SVG"));
    assertEquals(new SVGAnimationView(), factory.createView("Svg"));
  }

  // Testing that createView() returns a visual view when given "visual"
  @Test
  public void testVisualView() {
    assertEquals(new VisualAnimationView(), factory.createView("visual"));
    assertEquals(new VisualAnimationView(), factory.createView("VISUAL"));
    assertEquals(new VisualAnimationView(), factory.createView("Visual"));
  }
}