import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.InteractiveController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.view.IAnimationInteractiveView;
import cs3500.animator.view.MockInteractiveView;
import java.io.IOException;
import org.junit.Test;

/**
 * Defines tests for the InteractiveController.
 */
public class InteractiveControllerTest {
  //Tests for exceptions in the constructor
  @Test(expected = IllegalArgumentException.class)
  public void testNullArguments() {
    IController c = new InteractiveController(null, null);
  }

  //Unit tests with a mock interactive view
  //Tests that the controller methods call the correct methods on the view
  @Test
  public void interactiveController() throws IOException {
    Appendable log = new StringBuffer();
    IAnimationModel m = new AnimationModel();
    IAnimationInteractiveView v = new MockInteractiveView(log);

    //controller's constructor
    InteractiveController c = new InteractiveController(m, v);
    assertEquals("model_set features ", log.toString());

    //controller's go method
    c.start(5, new StringBuffer());
    assertEquals("model_set features speed_set out_set render ", log.toString());

    //controller's features methods
    c.start();
    c.pause();
    c.resume();
    c.restart();
    c.looping(true);
    c.looping(false);
    c.increaseSpeed();
    c.decreaseSpeed();
    assertEquals("model_set features speed_set out_set render "
        + "start render pause render resume render restart render enabled render"
        + " disabled render increased decreased ", log.toString());
  }
}
