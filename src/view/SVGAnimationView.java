package cs3500.animator.view;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.Circle;
import cs3500.animator.model.IViewModel;
import cs3500.animator.model.Rect;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.model.IShape;

import cs3500.animator.model.ViewModel;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Representation of an SVG view that renders the animation in an XML-based format so that the
 * animation can be seen in a browser that supports SVG files.
 */
public class SVGAnimationView implements IAnimationView {
  private Appendable out;
  private IViewModel model;
  private double speed;

  /**
   * Constructs a new instance of an SVG view for an animation with default parameters.
   */
  public SVGAnimationView() {
    this.out = new StringBuffer();
    this.model = new ViewModel(new AnimationModel());
    this.speed = 1;
  }

  /**
   * Constructs a new instance of an SVG view for an animation with the given parameters.
   * @param out the output to append information about the animation.
   * @param model the main access to the animation's details.
   * @param speed the speed of the animation in ticks/seconds
   */
  public SVGAnimationView(Appendable out, IAnimationModel model, double speed) {

    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive");
    }
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model does not accept null objects.");
    }

    this.out = out;
    this.model = new ViewModel(model);
    this.speed = speed;
  }

  @Override
  public void render() throws IOException {
    // The first line containing the canvas's width and height
    String canvasWidth = "width=\"" + model.getCanvas().get(2) + "\" ";
    String canvasHeight = "height=\"" + model.getCanvas().get(3) + "\"";
    StringBuilder view = new StringBuilder("<svg " + canvasWidth + canvasHeight
        + " version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");

    // the shape names of the animation
    Set<String> shapeNames = model.getShapes().keySet();

    // Going through the shapes in the animation
    for (String name: shapeNames) {
      int counter = 1; // counting the number of motions
      int prevTick = 0; // the previous frame number
      Map<Integer, IShape> frames = model.getShapes().get(name); // the frames of the current shape

      // For each frame, document the description of the shape
      for (Integer tick: frames.keySet()) {

        // Before describing motions of shape, create the shape using an XML tag
        if (counter == 1) {
          view.append("\n<").append(this.svgShape(frames.get(tick))).append(" id=\"").append(name)
              .append("\" ").append(this.initialShape(frames.get(tick)));
          counter++;
          prevTick = tick; // recording the previous tick for the next tick
        }
        // Animate the changes made to the shape in the following ticks
        else {
          view.append(this.animateChange(frames.get(prevTick), frames.get(tick), prevTick, tick));
          prevTick = tick; // recording the previous tick for the next tick

          // If this is the last frame, end the tag for this shape and end the
          if (counter == frames.size()) {
            view.append("\n</").append(this.svgShape(frames.get(tick))).append(">");
          }
          counter++;
        }
      }
    }
    // ending the svg tag after animating all the shapes in the animation
    view.append("\n</svg>");
    // append the view to the output
    out.append(view.toString());
  }


  /**
   * The shape's initial state at the start of the animation.
   * @param shape the given shape that will be animated.
   * @return the initial tag used to describe the given shape.
   */
  private String initialShape(IShape shape) {
    Objects.requireNonNull(shape);
    String positionStr = "";
    String sizeStr = "";
    String colorStr = "fill=\"rgb(" + shape.getColor().get(0) + ", " + shape.getColor().get(1)
        + ", " + shape.getColor().get(2) + ")\" ";

    if (shape instanceof Circle) {
      positionStr =
          "cx=\"" + shape.getPosition().getX() + "\" cy=\"" + shape.getPosition().getY() + "\" ";
      if (shape.getSize().get(0).equals(shape.getSize().get(1))) {
        sizeStr = "r=\"" + shape.getSize().get(0) + "\" ";
      }
      else {
        sizeStr = "rx=\"" + shape.getSize().get(0) + "\" ry=\"" + shape.getSize().get(1) + "\" ";
      }
    }
    if (shape instanceof Rect) {
      positionStr =
          "x=\"" + shape.getPosition().getX() + "\" y=\"" + shape.getPosition().getY() + "\" ";
      sizeStr =
          "width=\"" + shape.getSize().get(0) + "\" height=\"" + shape.getSize().get(1) + "\" ";
    }

    return positionStr + sizeStr + colorStr + "visibility=\"visible\" >";
  }



  /**
   * Returns a shape's tag name for an SVG view.
   * @param shape shape whose description will be documented.
   * @return the tag name for the given shape.
   * @throws IllegalArgumentException when the given shape is not an instance of an existing shape
   *         class.
   */
  private String svgShape(IShape shape) throws IllegalArgumentException {
    Objects.requireNonNull(shape);

    if (shape instanceof Circle) {
      if (shape.getSize().get(0).equals(shape.getSize().get(1))) {
        return "circle";
      }
      else {
        return "ellipse";
      }
    }
    else if (shape instanceof Rect) {
      return "rect";
    }
    else {
      throw new IllegalArgumentException("Invalid shape.");
    }
  }



  /**
   * Describes the animation of a shape using XML format.
   * @param prev the previous state of the given shape.
   * @param current the current state of the given shape.
   * @param prevTick the previous frame number of the shape.
   * @param currTick the current frame number of the shape.
   * @return the animation tag for the shape's change of state during a specific duration of time.
   */
  private String animateChange(IShape prev, IShape current, int prevTick, int currTick) {
    Objects.requireNonNull(prev);
    Objects.requireNonNull(current);

    int duration = (int)((currTick - prevTick) / this.speed * 1000);
    int begin = (int)(prevTick * 1000 / this.speed);
    String startTag =
        "\n    <animate attributeType=\"xml\" begin=\"" + begin + "ms\" dur=\"" + duration
            + "ms\" ";
    String animateTag = "";

    // If the shape has moved its x position
    if (prev.getPosition().getX() != current.getPosition().getX()) {
      // If the shape is a circle, the attribute name is cx
      if (current instanceof Circle) {
        animateTag += startTag + "attributeName=\"cx\" from=\"" + prev.getPosition().getX()
            + "\" to=\"" + current.getPosition().getX() + "\" fill=\"freeze\" />";
      }
      // Otherwise, the attribute name is x
      else {
        animateTag += startTag + "attributeName=\"x\" from=\"" + prev.getPosition().getX()
            + "\" to=\"" + current.getPosition().getX() + "\" fill=\"freeze\" />";
      }
    }
    // If the shape has moved its y position
    if (prev.getPosition().getY() != current.getPosition().getY()) {
      // If the shape is a circle, the attribute name is cy
      if (current instanceof Circle) {
        animateTag += startTag + "attributeName=\"cy\" from=\"" + prev.getPosition().getY()
            + "\" to=\"" + current.getPosition().getY() + "\" fill=\"freeze\" />";
      }
      // Otherwise, the attribute name is y
      else {
        animateTag += startTag + "attributeName=\"y\" from=\"" + prev.getPosition().getY()
            + "\" to=\"" + current.getPosition().getY() + "\" fill=\"freeze\" />";
      }
    }
    // If the shape has changed its width
    if (!prev.getSize().get(0).equals(current.getSize().get(0))) {
      // If the shape is a circle, the attribute name is r
      if (current instanceof Circle && current.getSize().get(0).equals(current.getSize().get(1))) {
        animateTag += startTag + "attributeName=\"r\" from=\"" + prev.getSize().get(0)
            + "\" to=\"" + current.getSize().get(0) + "\" fill=\"freeze\" />";
      }
      // If the shape is an ellipse, the attribute name is rx
      else if (current instanceof Circle) {
        animateTag += startTag + "attributeName=\"rx\" from=\"" + prev.getSize().get(0)
            + "\" to=\"" + current.getSize().get(0) + "\" fill=\"freeze\" />";
      }
      // Otherwise, the attribute name is width
      else {
        animateTag += startTag + "attributeName=\"width\" from=\"" + prev.getSize().get(0)
            + "\" to=\"" + current.getSize().get(0) + "\" fill=\"freeze\" />";
      }
    }
    // If the shape has changed its height and the shape is not a circle (to avoid redundancy)
    if (!prev.getSize().get(1).equals(current.getSize().get(1)) && !(current instanceof Circle
        && current.getSize().get(0).equals(current.getSize().get(1)))) {
      // If the shape is an ellipse, the attribute name is ry
      if (current instanceof Circle) {
        animateTag += startTag + "attributeName=\"ry\" from=\"" + prev.getSize().get(1)
            + "\" to=\"" + current.getSize().get(1) + "\" fill=\"freeze\" />";
      }
      // Otherwise, the attribute name is height
      else {
        animateTag += startTag + "attributeName=\"height\" from=\"" + prev.getSize().get(1)
            + "\" to=\"" + current.getSize().get(1) + "\" fill=\"freeze\" />";
      }
    }
    // If the shape has changed its color
    if (!prev.getColor().get(0).equals(current.getColor().get(0))
        || !prev.getColor().get(1).equals(current.getColor().get(1))
        || !prev.getColor().get(2).equals(current.getColor().get(2))) {

      animateTag += startTag + "attributeName=\"fill\" from=\"rgb("
          + prev.getColor().get(0) + ", " + prev.getColor().get(1) + ", " + prev.getColor().get(2)
          + ")\" to=\"rgb(" + current.getColor().get(0) + ", " + current.getColor().get(1) + ", "
          + current.getColor().get(2)
          + ")\" fill=\"freeze\" />";
    }

    return animateTag;
  }


  @Override
  public void setSpeed(double speed) {
    if (speed <= 0) {
      throw new IllegalArgumentException("Invalid speed.");
    }
    this.speed = speed;
  }

  @Override
  public void setModel(IViewModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.model = model;
  }

  @Override
  public void setOut(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Invalid model.");
    }
    this.out = out;
  }

  // Overriding equals so that a view of the same type is considered equal to this one
  @Override
  public boolean equals(Object o) {
    return o instanceof SVGAnimationView;
  }

  // Overriding hashcode so that a view of the same type returns the same integer
  @Override
  public int hashCode() {
    return 150 + model.hashCode() + (int)(this.speed * 100);
  }
}
