package cs3500.animator.controller;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.ViewModel;
import cs3500.animator.view.IAnimationView;
import java.io.IOException;
import java.util.Objects;

/**
 * Defines a controller to run the non-interactive views. Renders views with no user input.
 */
public class SimpleController implements IController {
  private IAnimationModel model;
  private IAnimationView view;

  /**
   * Constructs a {@code SimpleController} object.
   * @param model   The model
   * @param view    The view
   */
  public SimpleController(IAnimationModel model, IAnimationView view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.view = view;
    this.view.setModel(new ViewModel(model));
  }

  @Override
  public void start(int speed, Appendable out) throws IOException {
    view.setSpeed(speed);
    view.setOut(out);
    view.render();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SimpleController)) {
      return false;
    }
    SimpleController that = (SimpleController) o;
    return Objects.equals(model, that.model) &&
        Objects.equals(view, that.view);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, view);
  }
}
