package cs3500.animator.view;

/**
 * Factory of views that provides instances of views to be used for an animation.
 */
public class ViewFactory {

  /**
   * Constructs an instance of ViewFactory that doesn't require any parameters.
   */
  public ViewFactory(){
    //empty
  }

  /**
   * Creates an instance of a view for an animation.
   * @param name the name of the view to use for an animation.
   * @return the type of view indicated by the given string.
   * @throws IllegalArgumentException when the given name is not an existing name for a view.
   */
  public IAnimationView createView(String name) throws IllegalArgumentException {
    switch (name.toLowerCase()) {
      case "text":
        return new TextualAnimationView();
      case "svg":
        return new SVGAnimationView();
      case "visual":
        return new VisualAnimationView();
      case "interactive":
        return new InteractiveView();
      default:
        throw new IllegalArgumentException("Invalid view.");
    }
  }
}
