package cs3500.animator.controller;

import cs3500.animator.model.IAnimationModel;
import cs3500.animator.view.IAnimationInteractiveView;
import cs3500.animator.view.IAnimationView;

/**
 * Defines a class to create an IController object given a model and a view.
 */
public class ControllerFactory {

  /**
   * Constructs a controller--interactive if the given view is interactive, simple if not.
   * @param model   The model to be used in the controller
   * @param view    The view to be used in the controller
   * @return        A controller
   */
  public IController create(IAnimationModel model, IAnimationView view) {
    IController c;
    if (view instanceof IAnimationInteractiveView) {
      c = new InteractiveController(model, (IAnimationInteractiveView) view);
    }
    else {
      c = new SimpleController(model, view);
    }
    return c;
  }
}