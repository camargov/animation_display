package cs3500.animator.controller;

import java.io.IOException;

/**
 * Defines a controller for our animation program.
 */
public interface IController {

  /**
   * Starts or renders the animation.
   * @param speed           the speed to run the animation at initially
   * @param out             where to put the output
   * @throws IOException    when input or output fails
   */
  public void start(int speed, Appendable out) throws IOException;
}
