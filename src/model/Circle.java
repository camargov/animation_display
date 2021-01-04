package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a circle that has a color, position, width and height.
 */
public class Circle extends AShape implements IShape {

  /**
   * Constructs an oval with the given parameters.
   *
   * @param rgb    color in RGB format (integers ranging from 0-255)
   * @param pos    position which has to be non-negative integers
   * @param width  width which has to be non-negative integer
   * @param height height which has to be non-negative integer
   */
  public Circle(List<Integer> rgb, Position pos, int width, int height) {
    super(rgb, pos, width, height);
  }

  /**
   * Constructs a circle with the given parameters.
   *
   * @param rgb    color in RGB format (integers ranging from 0-255)
   * @param pos    position which has to be non-negative integers
   * @param radius radius which has to be non-negative integer
   */
  public Circle(List<Integer> rgb, Position pos, int radius) {
    super(rgb, pos, radius, radius);
  }

  // Returning a circle with the given parameters.
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

    return new Circle(copyRGB, copyPos, width, height);
  }

  // Overriding equals() so that two circles with the same color, size, and position are considered
  // equal
  @Override
  public boolean equals(Object o) {
    if (o instanceof Circle) {
      IShape circle2 = (Circle) o;

      boolean samePos = pos.equals(circle2.getPosition());
      boolean sameSize = width == circle2.getSize().get(0) && height == circle2.getSize().get(1);
      boolean sameR = rgb.get(0).equals(circle2.getColor().get(0));
      boolean sameG = rgb.get(1).equals(circle2.getColor().get(1));
      boolean sameB = rgb.get(2).equals(circle2.getColor().get(2));

      return samePos && sameSize && sameR && sameG && sameB;
    }
    else {
      return false;
    }
  }

  // Overriding hashCode() so that two circles with the same color, size, and position return the
  // same integer
  @Override
  public int hashCode() {
    return 45 * (width * height) + rgb.get(0) + rgb.get(1) + rgb.get(2) + pos.getX() + pos.getY();
  }

  // Overriding toString() so that the name of the shape this class represents is returned
  @Override
  public String toString() {
    return "ellipse";
  }
}
