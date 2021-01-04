
import cs3500.animator.model.Circle;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.VisualAnimationView;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Testing the behavior of the methods in VisualAnimationView.
 */
public class VisualAnimationViewTest {
  IAnimationModel model;


  @Before
  public void setUp() {
    IShape circle1 = new Circle(Arrays.asList(0, 0, 0), new Position(10, 10), 20);
    Map<Integer, IShape> circles = new TreeMap<>();
    circles.put(1, circle1);
    Map<String, Map<Integer, IShape>> shapes = new TreeMap<>();
    shapes.put("Circle", circles);
    model = new AnimationModel(shapes);
  }


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

  // Testing equals()
  @Test
  public void testEquals() {
    IAnimationView viewEmpty1 = new VisualAnimationView();
    IAnimationView viewEmpty2 = new VisualAnimationView();
    IAnimationView svgView = new SVGAnimationView();

    assertTrue(viewEmpty1.equals(viewEmpty2));
    assertFalse(viewEmpty1.equals(svgView));
  }

  // Testing hashCode()
  @Test
  public void testHashCode() {
    IAnimationView viewEmpty1 = new VisualAnimationView();
    IAnimationView viewEmpty2 = new VisualAnimationView();
    IAnimationView svgView = new SVGAnimationView();

    assertEquals(viewEmpty1.hashCode(), viewEmpty2.hashCode());
    assertNotEquals(viewEmpty1.hashCode(), svgView.hashCode());
  }
}