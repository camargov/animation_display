package cs3500.animator.model;

import java.util.Objects;

/**
 * Represents a 2D position on x and y axes.
 */
public class Position {

  private final int x;
  private final int y;

  /**
   * Constructs a new instance of position with the given x and y value.
   *
   * @param x the x-position. Must be non-negative.
   * @param y the y-position. Must be non-negative.
   */
  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x value of this position.
   *
   * @return this position's x value.
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y value of this position.
   *
   * @return this position's y value.
   */
  public int getY() {
    return y;
  }

  // Overriding equals() so that two positions with the same x and y values are considered equal.
  @Override
  public boolean equals(Object o) {
    if (o instanceof Position) {
      Position position = (Position) o;
      return this.x == position.getX() && this.y == position.getY();
    } else {
      return false;
    }
  }

  // Overriding hashCode() so that two positions with the same x and y values return the same
  // integer.
  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  // Overriding toString() to represent this position as its x and y values.
  @Override
  public String toString() {
    return "Position{" + "x=" + x + ", y=" + y + '}';
  }
}
