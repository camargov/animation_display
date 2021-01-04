package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;

import cs3500.animator.model.IViewModel;
import cs3500.animator.model.ViewModel;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Represents a view of the animation that's text-based. Details the changes made to each shape
 * along with the duration that those changes occur in.
 */
public class TextualAnimationView implements IAnimationView {

  private Appendable out;
  private IViewModel model;
  private double speed;

  /**
   * Constructs an instance of a TextualAnimation with default parameters for the string output,
   * model, and speed.
   */
  public TextualAnimationView() {
    this.out = new StringBuffer();
    this.model = new ViewModel(new AnimationModel());
    this.speed = 1;
  }

  /**
   * Constructs a new instance of TextualAnimation View with the given input, output, model, and
   * speed.
   * @param out the given output containing the previous description of the animation.
   * @param model the changes to shapes in the animation.
   * @param speed the speed of the animation. Must be greater than 0.
   * @throws IllegalArgumentException when the input, output, or model is null and when the speed
   *         is zero or negative.
   */
  public TextualAnimationView(Appendable out, IAnimationModel model, double speed)
      throws IllegalArgumentException {

    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive");
    }
    if (out == null || model == null) {
      throw new IllegalArgumentException("View does not accept null objects.");
    }

    this.out = out;
    this.model = new ViewModel(model);
    this.speed = speed;
  }

  // Rendering the model as a string and dividing the ticks by this.speed to get the duration
  // as seconds
  @Override
  public void render() throws IOException {
    // canvas of the animation
    StringBuilder view = new StringBuilder("canvas " + model.getCanvas().get(0) + " "
        + model.getCanvas().get(1) + " " + model.getCanvas().get(2) + " "
        + model.getCanvas().get(3));
    // the shape names of the animation
    Set<String> shapeNames = model.getShapes().keySet();

    // Going through the shapes in the animation
    for (String name: shapeNames) {
      int counter = 1; // counting the number of motions

      Map<Integer, IShape> frames = model.getShapes().get(name); // the frames of the current shape
      Set<Integer> ticks = frames.keySet(); // the frame numbers of the current shape

      // For each frame, document the description of the shape
      for (Integer tick: ticks) {

        // Before describing motions of shape, state the shape's name and type and describe first
        // motion
        if (counter == 1) {
          view.append("\nshape ").append(name).append(" ").append(frames.get(tick).toString());
          view.append("\nmotion ").append(name).append(" ").append(String.format("%.2f",
              tick / this.speed)).append(" ").append(this.renderShape(frames.get(tick)));
        }
        // If the motion is last in the description, keep it on the same line as starting motion
        else if (counter == frames.size()) {
          view.append(" ").append(String.format("%.2f", tick / this.speed)).append(" ")
              .append(this.renderShape(frames.get(tick)));
        }
        // For every other motion, describe the state of the shape as an ending and starting motion
        else {
          view.append(" ").append(String.format("%.2f", tick / this.speed)).append(" ")
              .append(this.renderShape(frames.get(tick))).append("\nmotion ").append(name)
              .append(" ").append(String.format("%.2f", tick / this.speed)).append(" ")
              .append(this.renderShape(frames.get(tick)));
        }
        counter++;
      }
    }
    // append the view to the output
    out.append(view.toString());
  }


  /**
   * Provides the string representation of the given shape including the shape's position, size, and
   * color.
   * @param shape the shape that will be represented as a string.
   * @return A string version of the given shape, documenting its appearance as integers.
   */
  private String renderShape(IShape shape) {
    // shape's position as a string
    String positionStr = shape.getPosition().getX() + " " + shape.getPosition().getY() + " ";
    // shape's size as a string
    String sizeStr = shape.getSize().get(0) + " " + shape.getSize().get(1) + " ";
    // shape's color as a string
    String colorStr =
        shape.getColor().get(0) + " " + shape.getColor().get(1) + " " + shape.getColor().get(2);

    return positionStr + sizeStr + colorStr;
  }


  @Override
  public void setSpeed(double speed) throws IllegalArgumentException {
    if (speed <= 0) {
      throw new IllegalArgumentException("Invalid speed.");
    }
    this.speed = speed;
  }

  @Override
  public void setModel(IViewModel model) throws IllegalArgumentException  {
    if (model == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.model = model;
  }

  @Override
  public void setOut(Appendable out) throws IllegalArgumentException  {
    if (out == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.out = out;
  }


  // Overriding equals so that a view of the same type is considered equal to this one
  @Override
  public boolean equals(Object o) {
    return o instanceof TextualAnimationView;
  }

  // Overriding hashcode so that a view of the same type returns the same integer
  @Override
  public int hashCode() {
    return 100 + model.hashCode() + (int) (this.speed * 100);
  }
}