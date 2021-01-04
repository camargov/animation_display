package cs3500.animator.model;

import java.util.List;
import java.util.Map;

/**
 * Defines a version of our model that allows a client to read data but not modify it.
 */
public interface IViewModel {

  /**
   * Provides a string version of this animation detailing the frames for each shape that consist of
   * the changes made to each shape. Details include shape name, frame number, position, width,
   * height, and color in RGB format.
   *
   * @return string value of the movements of each shape in the animation.
   */
  String toString();

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
   * Copies the map containing the movements of each shape in this animation along with the copy of
   * the frames for each shape to keep the instance of this animation in tact.
   *
   * @return a copy of the map of this model's shape movements.
   */
  Map<String, Map<Integer, IShape>> getShapes();

  /**
   * Gets the canvas of this animation.
   *
   * @return a copy of the list of values describing the position, width, and height of the canvas.
   */
  List<Integer> getCanvas();
}
