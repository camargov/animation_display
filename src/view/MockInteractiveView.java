package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IViewModel;
import java.io.IOException;

/**
 * A mock view for testing the interactive controller.
 */
public class MockInteractiveView implements IAnimationInteractiveView {

  private Appendable log;

  public MockInteractiveView(Appendable log) {
    this.log = log;
  }

  @Override
  public void start() throws IllegalStateException {
    try {
      log.append("start ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void pause() throws IllegalStateException {
    try {
      log.append("pause ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void resume() throws IllegalStateException {
    try {
      log.append("resume ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void restart() throws IllegalStateException {
    try {
      log.append("restart ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void looping(boolean enable) {
    try {
      if (enable) {
        log.append("enabled ");
      }
      else {
        log.append("disabled ");
      }
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void increaseSpeed() {
    try {
      log.append("increased ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void decreaseSpeed() throws IllegalStateException {
    try {
      log.append("decreased ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void addFeatures(Features features) {
    try {
      log.append("features ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void render() throws IOException {
    try {
      log.append("render ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void setSpeed(double speed) throws IllegalArgumentException {
    try {
      log.append("speed_set ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void setModel(IViewModel model) throws IllegalArgumentException {
    try {
      log.append("model_set ");
    }
    catch (IOException e) {
      //.
    }
  }

  @Override
  public void setOut(Appendable out) throws IllegalArgumentException {
    try {
      log.append("out_set ");
    }
    catch (IOException e) {
      //.
    }
  }
}
