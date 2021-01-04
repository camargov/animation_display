package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents a rectangle that is only meant to be viewed and can't be mutated.
 */
public class ViewRect implements IViewShape {
  private final int x;
  private final int y;
  private final int w;
  private final int h;
  private final Color color;

  /**
   * Constructs an instance of a ViewRect with the given parameters.
   * @param x1 the x position of the rectangle.
   * @param y1 the y position of the rectangle.
   * @param w1 the width of the rectangle.
   * @param h1 the height of the rectangle.
   * @param color1 the color of the rectangle.
   */
  public ViewRect(int x1, int y1, int w1, int h1, Color color1) {
    this.x = x1;
    this.y = y1;
    this.w = w1;
    this.h = h1;
    this.color = color1;
  }

  @Override
  public void render(Graphics g) {
    g.setColor(this.color);
    g.fillRect(x, y, w, h);
  }
}
