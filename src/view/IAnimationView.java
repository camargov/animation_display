package cs3500.animator.view;

import cs3500.animator.model.IViewModel;
import java.io.IOException;

/**
 * Represents a view for an animation and allows for flexibility based on the type of view and
 * the type of rendering performed by each view that implements this interface.
 */
public interface IAnimationView {

  /**
   * Renders an animation for the specified view.
   * @throws IOException when information can't be appended to the output.
   */
  void render() throws IOException;


  /**
   * Setting the speed of this view to the given speed.
   * @param speed the given speed to use for the animation.
   * @throws IllegalArgumentException if the given speed is zero or less than zero.
   */
  void setSpeed(double speed) throws IllegalArgumentException;


  /**
   * Setting the model of this view to the given model.
   * @param model the given model to be used.
   * @throws IllegalArgumentException if the given model is null.
   */
  void setModel(IViewModel model) throws IllegalArgumentException;


  /**
   * Setting the output of this view to the given output.
   * @param out the given Appendable to be used.
   * @throws IllegalArgumentException if the given Appendable is null.
   */
  void setOut(Appendable out) throws IllegalArgumentException;


}
