package cs3500.animator.view;

import cs3500.animator.controller.Features;
import cs3500.animator.model.IShape;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Represents an interactive view that displays an animation capable of pausing, resuming,
 * increasing/decreasing its speed, and enabling the animation to loop.
 */
public class InteractiveView extends AbstractVisualAnimation implements IAnimationInteractiveView {
  private InteractiveViewState state;
  private boolean looping;

  // Buttons controlling the interactivity of the animation
  private final JButton startButton;
  private final JButton pauseButton;
  private final JButton resumeButton;
  private final JButton restartButton;
  private final JButton increaseSpeedButton;
  private final JButton decreaseSpeedButton;
  private final JCheckBox loopingCheckbox;



  /**
   * Constructs an instance of an interaction view with default values such as a
   * VisualAnimationView, AnimationModel, and a speed of 1.
   */
  public InteractiveView() {
    super();
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    this.state = InteractiveViewState.NOT_STARTED;
    this.looping = false;


    // setting up the window
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setSize(new Dimension(500,500)); // setting the window's size
    this.add(new JScrollPane(mainPanel)); // adds the JScrollPane and JPanel to the window


    // Interactive elements to change the animation
    JPanel buttonPanel = new JPanel();
    mainPanel.add(buttonPanel);
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

    this.startButton = new JButton("Start");
    this.pauseButton = new JButton("Pause");
    this.resumeButton = new JButton("Resume");
    this.restartButton = new JButton("Restart");
    this.increaseSpeedButton = new JButton("Increase Speed");
    this.decreaseSpeedButton = new JButton("Decrease Speed");
    this.loopingCheckbox = new JCheckBox("Loop");

    // Adding the buttons and checkbox to window
    buttonPanel.add(this.startButton);
    buttonPanel.add(this.pauseButton);
    buttonPanel.add(this.resumeButton);
    buttonPanel.add(this.restartButton);
    buttonPanel.add(this.increaseSpeedButton);
    buttonPanel.add(this.decreaseSpeedButton);
    buttonPanel.add(this.loopingCheckbox);


    mainPanel.add(this.panel);
    this.setVisible(true); // makes the window visible
  }




  @Override
  public void addFeatures(Features features) {
    this.startButton.addActionListener(evt -> features.start());
    this.pauseButton.addActionListener(evt -> features.pause());
    this.resumeButton.addActionListener(evt -> features.resume());
    this.restartButton.addActionListener(evt -> features.restart());
    this.increaseSpeedButton.addActionListener(evt -> features.increaseSpeed());
    this.decreaseSpeedButton.addActionListener(evt -> features.decreaseSpeed());
    this.loopingCheckbox.addActionListener(evt -> features.looping(!this.looping));
  }



  @Override
  public void render() throws IOException {
    // Rendering the state of the animation at the tick at which is was paused at
    if (this.state.equals(InteractiveViewState.PAUSED)
        || this.state.equals(InteractiveViewState.NOT_STARTED)) {
      // stop the timer to stop the animation when the animation is paused/not started
      this.timer.stop();
    }
    else {
      timer.start();
    }
  }

  @Override
  protected void setTimer() {
    timer = new Timer((int) (1000 / speed), e -> {
      renderPanel();
      tick++;
      if (looping && (tick >= this.findLastTick())) {
        this.tick = 1;
      }
    });
  }

  /**
   * Finds the last tick of the animation to determine when the animation ends.
   * @return the final tick of the animation.
   */
  public int findLastTick() {
    int lastTick = 1;

    Collection<Map<Integer, IShape>> shapes = model.getShapes().values();
    for (Map<Integer, IShape> frames: shapes) {
      Collection<Integer> ticks = frames.keySet();
      for (Integer tick : ticks) {
        lastTick = Math.max(lastTick, tick);
      }
    }

    return lastTick;
  }

  @Override
  public void start() throws IllegalStateException {
    if (this.state.equals(InteractiveViewState.NOT_STARTED)) {
      this.state = InteractiveViewState.PLAYING;
      this.tick = 1;
    }
    else {
      throw new IllegalStateException("Cannot start an animation that has started.");
    }
  }

  @Override
  public void pause() throws IllegalStateException {
    if (this.state.equals(InteractiveViewState.PAUSED)) {
      throw new IllegalStateException("Cannot pause an animation that is paused.");
    }
    if (this.state.equals(InteractiveViewState.NOT_STARTED)) {
      throw new IllegalStateException("Cannot pause an animation that has not started yet.");
    }
    if (this.state.equals(InteractiveViewState.PLAYING)) {
      this.state = InteractiveViewState.PAUSED;
      this.timer.stop();
    }
  }



  @Override
  public void resume() throws IllegalStateException {
    if (this.state.equals(InteractiveViewState.PLAYING)) {
      throw new IllegalStateException("Cannot resume an animation that is already playing.");
    }
    if (this.state.equals(InteractiveViewState.NOT_STARTED)) {
      throw new IllegalStateException("Cannot resume an animation that has not started yet.");
    }
    if (this.state.equals(InteractiveViewState.PAUSED)) {
      this.state = InteractiveViewState.PLAYING;
      this.timer.start();
    }
  }


  @Override
  public void restart() {
    if (this.state.equals(InteractiveViewState.NOT_STARTED)) {
      throw new IllegalStateException("Cannot restart an animation that has not started yet.");
    }
    else {
      this.state = InteractiveViewState.PLAYING;
      this.tick = 1;
    }
  }



  @Override
  public void looping(boolean enable) {
    this.looping = enable;
  }


  @Override
  public void increaseSpeed() {
    this.speed += 1;
    timer.setDelay((int) (1000 / this.speed));
  }


  @Override
  public void decreaseSpeed() throws IllegalStateException {
    if (this.speed - 1 <= 0) {
      throw new IllegalStateException("Speed cannot decrease. It must stay greater than zero.");
    }
    else {
      this.speed -= 1;
      timer.setDelay((int) (1000 / this.speed));
    }
  }
}