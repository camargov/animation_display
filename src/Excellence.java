package cs3500.animator;

import cs3500.animator.controller.ControllerFactory;
import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IAnimationModel;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IAnimationView;
import cs3500.animator.view.ViewFactory;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.JOptionPane;

/**
 * Responsible for displaying an animation using IAnimationModel, and IAnimationView. This is
 * made possible by the methods of these interfaces and the AnimationBuilder that reads files
 * when applicable.
 */
public class Excellence {

  /**
   * Displays an animation based on the given file in a given view, either text, SVG, or visual,
   * and at a speed that can also be specified by the given arguments.
   * @param args List of string arguments that detail what and how to display an animation.
   * @throws IOException when the view is unable to append to its output.
   */
  public static void main(String[] args) throws IOException {

    //Initialize variables
    String fileName = null;
    Appendable out = System.out;
    String outputFile = null;
    int speed = 1;
    String viewType = null;

    //Get command line input
    try {
      Iterator<String> iterateArgs = Arrays.stream(args).iterator();
      while (iterateArgs.hasNext()) {
        String next = iterateArgs.next();
        if (next.equals("-in")) {
          fileName = iterateArgs.next();
        }
        else if (next.equals("-out")) {
          out = new StringBuffer();
          outputFile = iterateArgs.next();
        }
        else if (next.equals("-view")) {
          viewType = iterateArgs.next();
        }
        else if (next.equals("-speed")) {
          speed = Integer.valueOf(iterateArgs.next());
        }
        else {
          JOptionPane.showMessageDialog(null,
              "Invalid input.",
              "Input error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    catch (NoSuchElementException e) {
      JOptionPane.showMessageDialog(null,
          "Invalid input.",
          "Input error",
          JOptionPane.ERROR_MESSAGE);
    }
    if (fileName == null || viewType == null) {
      JOptionPane.showMessageDialog(null,
          "Invalid input.",
          "Input error",
          JOptionPane.ERROR_MESSAGE);
    }

    //Set up the model, view, and controller
    AnimationReader reader = new AnimationReader();
    IAnimationModel model =
        reader.parseFile(new FileReader(fileName), new AnimationModel.Builder());
    IAnimationView view = new ViewFactory().createView(viewType);
    IController controller = new ControllerFactory().create(model, view);
    controller.start(speed, out);

    //Write to a file if one was specified
    if (outputFile != null) {
      FileWriter w = new FileWriter(outputFile);
      w.write(out.toString());
      w.close();
    }
  }
}