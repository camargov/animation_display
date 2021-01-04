package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Represents a rectangle that has a color, position, width, and height.
 */
public class Rect extends AShape implements IShape {

  /**
   * Constructs a rectangle with the given parameters.
   *
   * @param rgb    color in RGB format (integers ranging from 0-255)
   * @param pos    position which has to be non-negative integers
   * @param width  width which has to be non-negative integer
   * @param height height which has to be non-negative integer
   */
  public Rect(List<Integer> rgb, Position pos, int width, int height) {
    super(rgb, pos, width, height);
  }

  /**
   * Constructs a square with the given parameters.
   *
   * @param rgb    color in RGB format (integers ranging from 0-255)
   * @param pos    position which has to be non-negative integers
   * @param radius radius which has to be non-negative integer
   */
  public Rect(List<Integer> rgb, Position pos, int radius) {
    super(rgb, pos, radius, radius);
  }


  // Returning a rectangle with the given parameters.
  @Override
  public IShape copyShape(List<Integer> rgb, Position pos, int width, int height)
      throws IllegalArgumentException {
    if (rgb.size() != 3 || rgb == null) {
      throw new IllegalArgumentException("Invalid color.");
    }
    if (pos == null) {
      throw new IllegalArgumentException("Invalid position.");
    }
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid dimensions.");
    }
    List<Integer> copyRGB = new ArrayList<Integer>(Arrays.asList(rgb.get(0), rgb.get(1),
        rgb.get(2)));
    Position copyPos = new Position(pos.getX(), pos.getY());
    return new Rect(copyRGB, copyPos, width, height);
  }

  // Overriding equals() so that two rectangles with the same color, size, and position are
  // considered equal
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rect rect = (Rect) o;
    return width == rect.width
        && height == rect.height
        && Objects.equals(rgb, rect.rgb)
        && Objects.equals(pos, rect.pos);
  }


  // Overriding hashCode() so that two rectangles with the same color, size, and position return
  // same integer
  @Override
  public int hashCode() {
    return Objects.hash(rgb, pos, width, height);
  }

  // Overriding toString() so that the name of the shape this class represents is returned
  @Override
  public String toString() {
    return "rectangle";
  }
}
