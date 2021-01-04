package cs3500.animator.view;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Represents an oval that is only meant to be viewed and can't be mutated.
 */
public class ViewOval implements IViewShape {
  private final int x;
  private final int y;
  private final int w;
  private final int h;
  private final Color color;

  /**
   * Constructs an instance of a ViewOval with the given parameters.
   * @param x1  the x position of the oval.
   * @param y1 the y position of the oval.
   * @param w1 the width of the oval.
   * @param h1 the height of the oval.
   * @param color1 the color of the oval.
   */
  public ViewOval(int x1, int y1, int w1, int h1, Color color1) {
    this.x = x1;
    this.y = y1;
    this.w = w1;
    this.h = h1;
    this.color = color1;
  }

  @Override
  public void render(Graphics g) {
    g.setColor(this.color);
    g.fillOval(x, y, w, h);
  }
}
