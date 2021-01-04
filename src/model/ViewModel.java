package cs3500.animator.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * An implementation of IViewModel that delegates operations to an IAnimationModel.
 */
public class ViewModel implements IViewModel {

  private final IAnimationModel model;

  /**
   * Constructs an instance of ViewModel using the given instance of IAnimationModel.
   *
   * @param model The animation model to be delegated to
   */
  public ViewModel(IAnimationModel model) {
    Objects.requireNonNull(model, "Model must not be null");
    this.model = model;
  }

  @Override
  public String toString() {
    return model.toString();
  }

  @Override
  public IShape getState(int frame, String shape) throws IllegalArgumentException {
    return model.getState(frame, shape);
  }

  @Override
  public Map<String, IShape> getFullState(int frame) throws IllegalArgumentException {
    return model.getFullState(frame);
  }

  @Override
  public Map<String, Map<Integer, IShape>> getShapes() {
    return model.getShapes();
  }

  @Override
  public List<Integer> getCanvas() {
    return model.getCanvas();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ViewModel)) {
      return false;
    }
    ViewModel viewModel = (ViewModel) o;
    return model.equals(viewModel.model);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model);
  }
}
