package cs3500.animator.view;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Representation of a visual animation that uses Java Swing to show the view in a separate window.
 */

public class VisualAnimationView extends AbstractVisualAnimation {

  /**
   * Constructs an instance of a visual view for an animation with default parameters.
   */
  public VisualAnimationView() {
    super();

    // setting up the window
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setSize(new Dimension(500,500)); // setting the window's size
    this.add(new JScrollPane(this.panel)); // adds the JScrollPane and JPanel to the window
    this.setVisible(true); // makes the window visible
  }


  @Override
  public void render() {
    timer.setDelay((int) (1000 / this.speed));
    timer.start();
  }

  @Override
  protected void setTimer() {
    timer = new Timer((int) (1000 / speed), e -> {
      renderPanel();
      tick++;
    });
  }


  // Overriding equals so that a view with the same parameters is considered equal to this one
  @Override
  public boolean equals(Object o) {
    return o instanceof VisualAnimationView;
  }

  // Overriding hashcode so that a view with the same parameters returns the same integer
  @Override
  public int hashCode() {
    return 200 + (int)(this.speed * 100);
  }
}
