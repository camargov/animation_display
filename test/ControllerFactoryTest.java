import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cs3500.animator.controller.ControllerFactory;
import cs3500.animator.controller.IController;
import cs3500.animator.controller.InteractiveController;
import cs3500.animator.controller.SimpleController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.view.IAnimationInteractiveView;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.InteractiveView;
import cs3500.animator.view.SVGAnimationView;
import cs3500.animator.view.TextualAnimationView;
import cs3500.animator.view.VisualAnimationView;
import org.junit.Test;

/**
 * Tests for the ControllerFactory.
 */
public class ControllerFactoryTest {

  IAnimationView v1 = new VisualAnimationView();
  IAnimationView v2 = new TextualAnimationView();
  IAnimationView v3 = new SVGAnimationView();
  IAnimationView v4 = new InteractiveView();
  IAnimationModel m = new AnimationModel();

  IController c1 = new SimpleController(m, v1);
  IController c2 = new SimpleController(m, v2);
  IController c3 = new SimpleController(m, v3);
  IController c4 = new InteractiveController(m, (IAnimationInteractiveView) v4);

  ControllerFactory f = new ControllerFactory();

  @Test
  public void testCreate() {
    assertEquals(c1, f.create(m, v1));
    assertEquals(c2, f.create(m, v2));
    assertEquals(c3, f.create(m, v3));
    assertEquals(c4, f.create(m, v4));
    assertNotEquals(c4, f.create(m, v1));
    assertTrue(f.create(m, v1) instanceof SimpleController);
    assertTrue(f.create(m, v4) instanceof InteractiveController);
  }
}
