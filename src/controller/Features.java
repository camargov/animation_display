package cs3500.animator.controller;

/**
 * Defines methods that a Features object connected to our animation can execute.
 */
public interface Features {

  /**
   * Starts the animation.
   */
  void start();

  /**
   * Pauses the animation.
   */
  void pause();

  /**
   * Resumes the animation if it has been paused.
   */
  void resume();

  /**
   * Restarts the animation from time zero.
   */
  void restart();

  /**
   * Enables and disables looping the animation.
   * @param enable true if looping should be on; false if off
   */
  void looping(boolean enable);

  /**
   * Increases the speed of the animation by 0.25 ticks per second.
   */
  void increaseSpeed();

  /**
   * Decreases the speed of the animation by 0.25 ticks per second.
   */
  void decreaseSpeed();
}
