package cs3500.animator.view;

import cs3500.animator.controller.Features;


/**
 * Represents a view for an interactive animation that provides the user the ability to start,
 * pause, resume, and restart the animation and enable/disable looping and increase/decrease
 * speed of the animation as it is playing.
 */
public interface IAnimationInteractiveView extends IAnimationView {


  /**
   * Starts the animation if it has not already started playing. The default state of the
   * animation once the window opens is paused, requiring this method to be called to start the
   * animation.
   * @throws IllegalStateException if the animation has already started.
   */
  void start() throws IllegalStateException;


  /**
   * Pauses the animation if it is currently playing.
   * @throws IllegalStateException if the animation is already paused.
   */
  void pause() throws IllegalStateException;


  /**
   * Resumes the animation if it is currently paused.
   * @throws IllegalStateException if the animation is not paused and is instead playing.
   */
  void resume() throws IllegalStateException;


  /**
   * Restarts the animation by starting the animation starting at the first available frame. The
   * animation will continue to play once it is restarted, resulting in "play" being the default
   * state everytime this method is called.
   * @throws IllegalStateException if the animation has not started yet.
   */
  void restart() throws IllegalStateException;


  /**
   * Enables looping if given true, disables looping if given false. Looping causes the animation
   * to automatically restart once it reaches its last available frame.
   * @param enable whether or not the animation enables looping.
   */
  void looping(boolean enable);


  /**
   * Increases the speed of the animation as it is playing by a default increment of 1.
   */
  void increaseSpeed();


  /**
   * Decreases the speed of the animation as it is playing by a default increment of 1.
   * @throws IllegalStateException if this method will result in the speed being zero or negative.
   */
  void decreaseSpeed() throws IllegalStateException;

  /**
   * Assigns the given features to action/event listeners that will change the animation in some
   * way.
   * @param features The features to be added to the view of the animation.
   */
  void addFeatures(Features features);
}
