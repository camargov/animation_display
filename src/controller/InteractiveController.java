package cs3500.animator.controller;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IAnimationInteractiveView;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 * Defines a controller for a visual animation which can be controlled by the user.
 */
public class InteractiveController implements IController, Features {

  private IAnimationModel model;
  private IAnimationInteractiveView view;

  /**
   * Constructs a {@code InteractiveController} object.
   * @param model   The model
   * @param view    The view
   */
  public InteractiveController(IAnimationModel model, IAnimationInteractiveView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    setView(view);
  }

  //Sets the view and gives it access to the features
  private void setView(IAnimationInteractiveView view) {
    this.view = view;
    this.view.setModel(new ViewModel(model));
    this.view.addFeatures(this);
  }

  @Override
  public void start(int speed, Appendable out) throws IOException {
    view.setSpeed(speed);
    view.setOut(out);
    renderView();
  }

  //NOTE: For these methods I wasn't sure whether to render the view after calling the view methods.
  // I can take those out if that's not how the view works

  @Override
  public void start() {
    try {
      view.start();
      renderView();
    }
    catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(null,
          "This animation has already been started.",
          "Invalid action",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void pause() {
    try {
      view.pause();
    }
    catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(null,
          "This animation has already been paused.",
          "Invalid action",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void resume() {
    try {
      view.resume();
    }
    catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(null,
          "This animation is already playing.",
          "Invalid action",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void restart() {
    try {
      view.restart();
    }
    catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(null,
          "This animation has not been started yet.",
          "Invalid action",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void looping(boolean enable) {
    view.looping(enable);
    //renderView();
  }

  @Override
  public void increaseSpeed() {
    view.increaseSpeed();
  }

  @Override
  public void decreaseSpeed() {
    try {
      view.decreaseSpeed();
    }
    catch (IllegalStateException e) {
      JOptionPane.showMessageDialog(null,
          "This animation cannot go any slower.",
          "Invalid action",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof InteractiveController)) {
      return false;
    }
    InteractiveController that = (InteractiveController) o;
    return Objects.equals(model, that.model) &&
        Objects.equals(view, that.view);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, view);
  }

  //Calls view.render() and catches the exception so we don't need to in the other methods
  private void renderView() {
    try {
      view.render();
    }
    catch (IOException e) {
      //this will never happen for this view since there is no appending to the output
    }
  }
}
