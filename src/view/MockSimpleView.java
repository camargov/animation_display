package cs3500.animator.view;

import cs3500.animator.model.IViewModel;
import java.io.IOException;

/**
 * A mock view for testing the SimpleController.
 */
public class MockSimpleView implements IAnimationView {
  private Appendable log;

  public MockSimpleView(Appendable log) {
    this.log = log;
  }

  @Override
  public void render() throws IOException {
    log.append("render ");
  }

  @Override
  public void setSpeed(double speed) throws IllegalArgumentException {
    try {
      log.append("speed " + speed + " ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setModel(IViewModel model) throws IllegalArgumentException {
    try {
      log.append("model set ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setOut(Appendable out) throws IllegalArgumentException {
    try {
      log.append("output set ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
