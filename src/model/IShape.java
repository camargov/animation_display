package cs3500.animator.model;

import java.util.List;

/**
 * Defines methods for a shape with color, position, and dimensions.
 */
public interface IShape {

  /**
   * Gets the color of this cs3500.animator.model.IShape object.
   *
   * @return a list containing the RGB values
   */
  List<Integer> getColor();

  /**
   * Gets the position of this cs3500.animator.model.IShape object.
   *
   * @return the cs3500.animator.model.Position of this cs3500.animator.model.IShape object
   */
  Position getPosition();

  /**
   * Gets the size of this cs3500.animator.model.IShape object.
   *
   * @return a list containing the width and height of this cs3500.animator.model.IShape object
   */
  List<Integer> getSize();

  /**
   * Sets the color of this cs3500.animator.model.IShape object to the specified RGB value.
   *
   * @param r The red value
   * @param g The green value
   * @param b The blue value
   * @throws IllegalArgumentException if the values are invalid RGB values.
   */
  void setColor(int r, int g, int b);

  /**
   * Sets the position of this cs3500.animator.model.IShape object to the specified value.
   *
   * @param pos the cs3500.animator.model.Position object to set the position as
   * @throws IllegalArgumentException if the position contains negative values
   */
  void setPosition(Position pos);

  /**
   * Sets the dimensions of this cs3500.animator.model.IShape object to the specified value.
   *
   * @param width  the width to be set
   * @param height the height to be set
   * @throws IllegalArgumentException if the dimensions are not positive.
   */
  void setSize(int width, int height);

  /**
   * A factory method creating an instance of this implementation of cs3500.animator.model.IShape.
   *
   * @param rgb    RGB value
   * @param pos    cs3500.animator.model.Position
   * @param width  Width
   * @param height Height
   * @return a new cs3500.animator.model.IShape of a certain type with these parameters
   * @throws IllegalArgumentException if any of the parameters are invalid.
   */
  IShape copyShape(List<Integer> rgb, Position pos, int width, int height);
}
