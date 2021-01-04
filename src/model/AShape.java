package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a generic shape that has a color, position, width and size.
 */
public abstract class AShape implements IShape {

  protected List<Integer> rgb;
  protected Position pos;
  protected int width;
  protected int height;

  /**
   * Constructs a shape with the given parameters.
   *
   * @param rgb    color in RGB format (integers ranging from 0-255)
   * @param pos    position which has to be non-negative integers
   * @param width  width which has to be non-negative integer
   * @param height height which has to be non-negative integer
   */
  public AShape(List<Integer> rgb, Position pos, int width, int height) {
    if (pos == null) {
      throw new IllegalArgumentException("cs3500.animator.model.Position must be non-null");
    }
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid width or height");
    }
    if (rgb.size() != 3) {
      throw new IllegalArgumentException("Invalid color");
    }
    for (int i : rgb) {
      if (i < 0 || i > 255) {
        throw new IllegalArgumentException("Invalid color");
      }
    }
    this.rgb = new ArrayList<Integer>(Arrays.asList(rgb.get(0), rgb.get(1), rgb.get(2)));
    this.pos = new Position(pos.getX(), pos.getY());
    this.width = width;
    this.height = height;
  }

  /**
   * Constructs a shape that has the same width and height with the given parameters.
   *
   * @param rgb    color in RGB format (integers ranging from 0-255)
   * @param pos    position which has to be non-negative integers
   * @param radius radius which has to be non-negative integer
   */
  public AShape(List<Integer> rgb, Position pos, int radius) {
    if (pos == null) {
      throw new IllegalArgumentException("cs3500.animator.model.Position must be non-null");
    }
    if (pos.getX() < 0 || pos.getY() < 0) {
      throw new IllegalArgumentException("Invalid position");
    }
    if (radius <= 0) {
      throw new IllegalArgumentException("Invalid width or height");
    }
    if (rgb.size() != 3) {
      throw new IllegalArgumentException("Invalid color");
    }
    for (int i : rgb) {
      if (i < 0 || i > 255) {
        throw new IllegalArgumentException("Invalid color");
      }
    }
    this.rgb = new ArrayList<Integer>(Arrays.asList(rgb.get(0), rgb.get(1), rgb.get(2)));
    this.pos = new Position(pos.getX(), pos.getY());
    this.width = radius;
    this.height = radius;
  }

  @Override
  public List<Integer> getColor() {
    return new ArrayList<>(Arrays.asList(rgb.get(0), rgb.get(1), rgb.get(2)));
  }

  @Override
  public Position getPosition() {
    return new Position(pos.getX(), pos.getY());
  }

  @Override
  public List<Integer> getSize() {
    return new ArrayList<>(Arrays.asList(width, height));
  }

  @Override
  public void setColor(int r, int g, int b) {
    if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Invalid color");
    }
    rgb = new ArrayList<>(Arrays.asList(r, g, b));
  }

  @Override
  public void setPosition(Position pos) {
    if (pos == null || pos.getX() < 0 || pos.getY() < 0) {
      throw new IllegalArgumentException("Invalid position");
    }
    this.pos = new Position(pos.getX(), pos.getY());
  }

  @Override
  public void setSize(int width, int height) {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid dimensions");
    }
    this.width = width;
    this.height = height;
  }

  public abstract IShape copyShape(List<Integer> rgb, Position pos, int width, int height)
      throws IllegalArgumentException;
}
