import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.IController;
import cs3500.animator.controller.SimpleController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.AnimationModel.Builder;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.MockSimpleView;
import cs3500.animator.view.TextualAnimationView;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

/**
 * Defines tests for the SimpleController class.
 */
public class SimpleControllerTest {

  //Tests for exceptions in the constructor
  @Test(expected = IllegalArgumentException.class)
  public void nullArguments() throws IOException {
    IController c = new SimpleController(null, null);
  }

  //Unit tests with a mock view to make sure the right methods get called on go()
  @Test
  public void mockView() throws IOException {
    Appendable log = new StringBuffer();
    IController c = new SimpleController(new AnimationModel(), new MockSimpleView(log));
    assertEquals("model set ", log.toString());
    c.start(5, new StringBuffer());
    assertEquals("model set speed 5.0 output set render ", log.toString());
  }

  //Integration test with the textual view
  @Test
  public void realView() throws IOException {
    Appendable output = new StringBuffer();
    IAnimationModel m
        = new AnimationReader().parseFile(new FileReader("smalldemo.txt"), new Builder());
    IController c = new SimpleController(m, new TextualAnimationView());
    c.start(10, output);
    assertEquals("canvas 200 70 360 360\n"
        + "shape R rectangle\n"
        + "motion R 0.10 200 200 50 100 255 0 0 1.00 200 200 50 100 255 0 0\n"
        + "motion R 1.00 200 200 50 100 255 0 0 5.00 300 300 50 100 255 0 0\n"
        + "motion R 5.00 300 300 50 100 255 0 0 5.10 300 300 50 100 255 0 0\n"
        + "motion R 5.10 300 300 50 100 255 0 0 7.00 300 300 25 100 255 0 0\n"
        + "motion R 7.00 300 300 25 100 255 0 0 10.00 200 200 25 100 255 0 0\n"
        + "shape C ellipse\n"
        + "motion C 0.60 440 70 120 60 0 0 255 2.00 440 70 120 60 0 0 255\n"
        + "motion C 2.00 440 70 120 60 0 0 255 5.00 440 250 120 60 0 0 255\n"
        + "motion C 5.00 440 250 120 60 0 0 255 7.00 440 370 120 60 0 170 85\n"
        + "motion C 7.00 440 370 120 60 0 170 85 8.00 440 370 120 60 0 255 0\n"
        + "motion C 8.00 440 370 120 60 0 255 0 10.00 440 370 120 60 0 255 0", output.toString());
  }
}