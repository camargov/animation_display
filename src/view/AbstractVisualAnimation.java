package cs3500.animator.view;

import cs3500.animator.model.IViewModel;
import cs3500.animator.model.ViewModel;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Circle;
import cs3500.animator.model.Rect;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Abstract version of a visual animation that performs the common functionality of animations with
 * a visual component.
 */
public abstract class AbstractVisualAnimation extends JFrame implements IAnimationView {
  protected Appendable out;
  protected IViewModel model;
  protected double speed;
  protected final DrawingPanel panel;
  protected int tick;
  protected Timer timer;

  /**
   * Constructs an instance of an AbstractVisualAnimation with default parameters for the output,
   * model, speed, panel, tick, and timer.
   */
  public AbstractVisualAnimation() {
    this.out = new StringBuffer();
    this.model = new ViewModel(new AnimationModel());
    this.speed = 1;
    this.panel = new DrawingPanel();
    this.tick = 1;
    this.setTimer();
  }


  @Override
  public abstract void render() throws IOException;


  @Override
  public void setSpeed(double speed) throws IllegalArgumentException {
    if (speed <= 0) {
      throw new IllegalArgumentException("Invalid speed.");
    }
    this.speed = speed;
    timer.setDelay((int) (1000 / speed));
  }

  @Override
  public void setModel(IViewModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.model = model;

    // sizes the window and canvas based on the model's canvas size
    this.panel.setLocation(model.getCanvas().get(0),  model.getCanvas().get(1));
    this.panel.setPreferredSize(new Dimension(model.getCanvas().get(2), model.getCanvas().get(3)));
  }

  @Override
  public void setOut(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.out = out;
  }

  /**
   * Sets up the timer with a delay according to the animation's speed and an action event that
   * renders each shape in the animation at each tick.
   */
  protected abstract void setTimer();
  /*
  protected void setTimer() {
    timer = new Timer((int) (1000 / speed), e -> {
      renderPanel();
      tick++;
    });
  }*/

  protected void renderPanel() {
    Map<String, Map<Integer, IShape>> shapes = model.getShapes();
    Set<String> shapeNames = shapes.keySet();
    for (String s : shapeNames) {
      IShape shape = model.getState(tick, s);
      if (shape != null) {
        if (shape instanceof Rect) {
          panel.addShape(new ViewRect(shape.getPosition().getX(), shape.getPosition().getY(),
              shape.getSize().get(0), shape.getSize().get(1), new Color(shape.getColor().get(0),
              shape.getColor().get(1), shape.getColor().get(2))));
        }
        if (shape instanceof Circle) {
          panel.addShape(new ViewOval(shape.getPosition().getX(), shape.getPosition().getY(),
              shape.getSize().get(0), shape.getSize().get(1), new Color(shape.getColor().get(0),
              shape.getColor().get(1), shape.getColor().get(2))));
        }
      }
    }
    panel.repaint();
  }
}
