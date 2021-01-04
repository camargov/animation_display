package cs3500.animator.view;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;


/**
 * The canvas used to draw shapes on. Uses JPanel to paint the shapes onto the canvas and
 * delegates the rendering to IViewShapes.
 */
public class DrawingPanel extends JPanel {
  List<IViewShape> shapes;

  /**
   * Constructs a new instance of Drawing Panel with the default parameters as JPanel and a new
   * empty list of shapes.
   */
  public DrawingPanel() {
    super();
    shapes = new ArrayList<>();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    // delegate the rendering to each shape in the list of shapes
    for (IViewShape shape : shapes) {
      shape.render(g);
    }
    shapes.clear();
  }

  /**
   * Adds the given IViewShape to the list of shapes.
   * @param shape The shape that will be added to the canvas.
   */
  public void addShape(IViewShape shape) {
    shapes.add(Objects.requireNonNull(shape));
  }

}