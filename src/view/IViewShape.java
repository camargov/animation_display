package cs3500.animator.view;

import java.awt.Graphics;

/**
 * Represents a shape that is only meant to be viewed and rendered on a canvas and isn't able to
 * be mutated.
 */
public interface IViewShape {

  /**
   * Renders a shape using the given Graphics.
   * @param g the Graphics used to render the shape.
   */
  void render(Graphics g);
}