package cs3500.animator.model;

import java.util.List;
import java.util.Map;

/**
 * Represents an animation that contains shapes and a record of each shape's movements and changes
 * in appearance and the frame number in which those changes occurred.
 */
public interface IAnimationModel {


  /**
   * Adds a new entry for the given shape with a new position for that shape at the given frame.
   *
   * @param frame the frame at which the shape will be at its new position. Must be non-negative.
   * @param shape the shape that will be moved in the animation. Must already exist in this
   *              animation.
   * @param x     The new x-position for the given shape. Must be non-negative.
   * @param y     The new y-position for the given shape. Must be non-negative.
   * @throws IllegalArgumentException when the position values and/or frame are negative or when the
   *                                  shape does not already exist in the animation.
   */
  void move(int frame, String shape, int x, int y) throws IllegalArgumentException;


  /**
   * Adds a new entry for the given shape with a new color for that shape at the given frame.
   *
   * @param frame the frame at which the shape will change its color. Must be non-negative.
   * @param shape the shape that will change color in the animation. Must already exist in this
   *              animation.
   * @param r     the r value for the shape's new RGB values. Must be within the range of 0-255.
   * @param g     the g value for the shape's new RGB values. Must be within the range of 0-255.
   * @param b     the b value for the shape's new RGB values. Must be within the range of 0-255.
   * @throws IllegalArgumentException when the given RGB values are not within the range of 0-255,
   *                                  when the given frame is negative, or when the shape does not
   *                                  already exist in the animation.
   */
  void changeColor(int frame, String shape, int r, int g, int b) throws IllegalArgumentException;


  /**
   * Adds a new entry for the given shape with a new size for that shape at the given frame.
   *
   * @param frame  the frame at which the shape will change its size. Must be non-negative.
   * @param shape  the shape that will change size in the animation. Must already exist in this
   *               animation.
   * @param width  the new width for the given shape. Must be non-negative and not zero.
   * @param height the new height for the given shape. Must be non-negative and not zero.
   * @throws IllegalArgumentException when the width/height is negative or zero, when the frame is
   *                                  negative, or when the given shape does not already exist in
   *                                  the animation.
   */
  void changeSize(int frame, String shape, int width, int height) throws IllegalArgumentException;

  /**
   * Provides a string version of this animation detailing the frames for each shape that consist of
   * the changes made to each shape. Details include shape name, frame number, position, width,
   * height, and color in RGB format.
   *
   * @return string value of the movements of each shape in the animation.
   */
  String toString();

  /**
   * Multiplies all of the frame values by the given parameter. If num is less than 1, speed will
   * increase, and if num is greater than 1, speed will decrease.
   *
   * @param num the factor by which to multiply the frames
   * @return the new Map with the changed speed
   */
  Map<String, Map<Integer, IShape>> changeSpeed(double num);


  /**
   * Returns the state of the given shape at the given frame. If the frame is in between two
   * existing shape states, returns a shape in the middle.
   *
   * @param frame the timestamp to return the state for.
   * @param shape the key of the shape to return the state for.
   * @return a shape object with the characteristics of the given shape at that time.
   * @throws IllegalArgumentException if the shape is not present in the model, or if the frame is
   *                                  negative.
   */
  IShape getState(int frame, String shape) throws IllegalArgumentException;

  /**
   * Returns the state of the whole model at the given frame for all shapes.
   *
   * @param frame the timestamp to return the state for
   * @return a map containing the original keys each mapped to a single shape
   * @throws IllegalArgumentException if the frame is negative.
   */
  Map<String, IShape> getFullState(int frame) throws IllegalArgumentException;

  /**
   * Adds the given shape appearing at the given frame with the given key.
   *
   * @param frame the frame at which the shape should appear
   * @param name  the String key for this shape
   * @param shape the shape to add
   * @throws IllegalArgumentException if the frame is negative or if the key already exists.
   */
  void add(int frame, String name, IShape shape) throws IllegalArgumentException;


  /**
   * Adds a shape with the given name with empty frames.
   * @param name the name of the shape.
   * @throws IllegalArgumentException if the shape name already exists
   */
  void add(String name) throws IllegalArgumentException;

  /**
   * Removes all of the specified frames from the given shape after the given frame. If no frames
   * are left, removes the whole shape from the model.
   *
   * @param name the key of the shape to remove
   * @throws IllegalArgumentException if shape is not contained in the model, or frame is negative.
   */
  void remove(int frame, String name) throws IllegalArgumentException;


  /**
   * Copies the map containing the movements of each shape in this animation along with the copy of
   * the frames for each shape to keep the instance of this animation in tact.
   *
   * @return a copy of the map of this model's shape movements.
   */
  Map<String, Map<Integer, IShape>> getShapes();

  /**
   * Gets the canvas of this animation.
   * @return a copy of the list of values describing the position, width, and height of the canvas.
   */
  List<Integer> getCanvas();
}
