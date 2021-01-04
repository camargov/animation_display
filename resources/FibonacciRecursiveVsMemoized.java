import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Defines a class to create an animation file demonstrating how memoization makes computing
 * Fibonacci numbers more efficient. Rectangles represent Fibonacci numbers and can be green for
 * solved, red for in progress, or gray for unknown. Each recursive call or addition operation takes
 * 20 ticks. Timer bars keep track of the time taken.
 */
public final class FibonacciRecursiveVsMemoized {

  /**
   * Defines the three possible states for a rectangle.
   */
  public enum previousState { SOLVED, IN_PROGRESS, UNKNOWN }

  static int tick = 1;
  static int tick2 = 1;

  //Uses a recursive algorithm to solve all the rectangles
  private static void fibonacciRecursive(int n, Appendable out, int x, int y) throws IOException {
    if (n <= 1) {
      solved(n, out, x, y, "R", tick, previousState.UNKNOWN);
      keepTime(tick, "R", out, x, y);
      tick += 20;
    } else {
      inProgress(n, out, x, y, "R", tick, previousState.UNKNOWN);
      keepTime(tick, "R", out, x, y);
      tick += 20;
      fibonacciRecursive(n - 1, out, x, y);
      fibonacciRecursive(n - 2, out, x, y);
      solved(n, out, x, y, "R", tick, previousState.IN_PROGRESS);
      keepTime(tick, "R", out, x, y);
      tick += 20;
    }
  }

  //Declares a map of rectangles to states so that we can keep track of them in memoization
  static Map<Integer, previousState> states = new TreeMap<>();

  private static void initMemoization(int n) {
    states = new TreeMap<>();
    for (int i = 0; i <= n; i++) {
      states.put(i, previousState.UNKNOWN);
    }
  }

  //Uses memoization to solve all the rectangles--once they are solved, they stay solved
  private static void fibonacciMemoized(int n, Appendable out, int x, int y) throws IOException {
    if (states.get(n).equals(previousState.SOLVED)) {
      //already solved; do nothing
    } else if (n <= 1) {
      solved(n, out, x, y, "M", tick2, states.get(n));
      states.remove(n);
      states.put(n, previousState.SOLVED);
      keepTime(tick2, "M", out, x, y);
      tick2 += 20;
    } else {
      inProgress(n, out, x, y, "M", tick2, states.get(n));
      states.remove(n);
      states.put(n, previousState.IN_PROGRESS);
      keepTime(tick2, "M", out, x, y);
      tick2 += 20;
      fibonacciMemoized(n - 1, out, x, y);
      fibonacciMemoized(n - 2, out, x, y);
      solved(n, out, x, y, "M", tick2, states.get(n));
      states.remove(n);
      states.put(n, previousState.SOLVED);
      keepTime(tick2, "M", out, x, y);
      tick2 += 20;
    }
  }

  //Append text to the buffer declaring n distinct rectangles and an initial motion for them
  private static void declareShapes(int n, Appendable out, int x, int y, String name)
      throws IOException {
    for (int i = 0; i < n; i++) {
      String shapeName = name + String.valueOf(i);
      out.append("shape " + shapeName + " rectangle\n");
      out.append("motion " + shapeName +
          " 1 " + String.valueOf(x + 60 * i) + " " + String.valueOf(y) + " 50 20 133 153 158 1 "
          + String.valueOf(x + 60 * i) + " " + String.valueOf(y) + " 50 20 133 153 158\n");
    }
    //Declare timer bars
    out.append("shape " + name + "Timer rectangle\n");
    out.append("motion " + name + "Timer 1 " + String.valueOf(x) + " " + String.valueOf(y + 70)
        + " 1 10 255 182 25 1 "
        + String.valueOf(x) + " " + String.valueOf(y + 70) + " 1 10 255 182 25\n");
  }

  //Append text to the buffer indicating the nth rectangle has been solved
  private static void solved(int n, Appendable out, int x, int y, String name, int tick,
      previousState state)
      throws IOException {
    String shapeName = name + String.valueOf(n);
    switch (state) {
      case SOLVED:
        out.append(
            "motion " + shapeName + " " + String.valueOf(tick) + " " + String.valueOf(x + 60 * n)
                + " " + String.valueOf(y) + " 50 20 48 240 89 "
                + String.valueOf(tick + 20) + " " + String.valueOf(x + 60 * n) + " " + String
                .valueOf(y) + " 50 20 48 240 89\n");
        break;
      case IN_PROGRESS:
        out.append(
            "motion " + shapeName + " " + String.valueOf(tick) + " " + String.valueOf(x + 60 * n)
                + " " + String.valueOf(y) + " 50 20 235 108 156 "
                + String.valueOf(tick + 20) + " " + String.valueOf(x + 60 * n) + " " + String
                .valueOf(y) + " 50 20 48 240 89\n");
        break;
      case UNKNOWN:
        out.append(
            "motion " + shapeName + " " + String.valueOf(tick) + " " + String.valueOf(x + 60 * n)
                + " " + String.valueOf(y) + " 50 20 133 153 158 "
                + String.valueOf(tick + 20) + " " + String.valueOf(x + 60 * n) + " " + String
                .valueOf(y) + " 50 20 48 240 89\n");
        break;
      default:
        //nothing
    }
    if (n > 1) { //temporarily enlarge the two rectangles to the left to visualize adding
      //For a memoized algorithm, keep them green as they are still solved
      if (name.equals("M")) {
        out.append("motion " + name + (n - 1) + " " + tick + " " + (x + 60 * (n - 1)) + " " + y
            + " 50 20 48 240 89 "
            + (tick + 10) + " " + (x + 60 * (n - 1)) + " " + y + " 55 22 40 240 89\n");
        out.append("motion " + name + (n - 2) + " " + tick + " " + (x
            + 60 * (n - 2)) + " " + y + " 50 20 48 240 89 "
            + (tick + 10) + " " + (x + 60 * (n - 2)) + " " + y + " 55 22 40 240 89\n");
        out.append(
            "motion " + name + (n - 1) + " " + (tick + 10) + " " + (x + 60 * (n - 1)) + " " + y
                + " 55 22 48 240 89 "
                + (tick + 20) + " " + (x + 60 * (n - 1)) + " " + y + " 50 20 40 240 89\n");
        out.append(
            "motion " + name + (n - 2) + " " + (tick + 10) + " " + (x + 60 * (n - 2)) + " " + y
                + " 55 22 48 240 89 "
                + (tick + 20) + " " + (x + 60 * (n - 2)) + " " + y + " 50 20 40 240 89\n");
      }
      //For a recursive algorithm, they will turn gray because we forget
      // about them after adding them
      else if (name.equals("R")) {
        out.append("motion " + name + (n - 1) + " " + tick + " " + (x + 60 * (n - 1)) + " " + y
            + " 50 20 48 240 89 "
            + (tick + 10) + " " + (x + 60 * (n - 1)) + " " + y + " 55 22 133 153 158\n");
        out.append("motion " + name + (n - 2) + " " + tick + " " + (x + 60 * (n - 2)) + " " + y
            + " 50 20 48 240 89 "
            + (tick + 10) + " " + (x + 60 * (n - 2)) + " " + y + " 55 22 133 153 158\n");
        out.append(
            "motion " + name + (n - 1) + " " + (tick + 10) + " " + (x + 60 * (n - 1)) + " " + y
                + " 55 22 48 240 89 "
                + (tick + 20) + " " + (x + 60 * (n - 1)) + " " + y + " 50 20 133 153 158\n");
        out.append(
            "motion " + name + (n - 2) + " " + (tick + 10) + " " + (x + 60 * (n - 2)) + " " + y
                + " 55 22 48 240 89 "
                + (tick + 20) + " " + (x + 60 * (n - 2)) + " " + y + " 50 20 133 153 158\n");
      }
    }
  }

  //Append text to the buffer indicating the nth rectangle is in the process of being solved
  private static void inProgress(int n, Appendable out, int x, int y, String name, int tick,
      previousState state)
      throws IOException {
    String shapeName = name + n;
    switch (state) {
      case SOLVED:
        out.append(
            "motion " + shapeName + " " + tick + " " + (x + 60 * n) + " " + y + " 50 20 48 240 89 "
                + (tick + 20) + " " + (x + 60 * n) + " " + y + " 50 20 235 108 156\n");
        break;
      case IN_PROGRESS:
        out.append("motion " + shapeName + " " + tick + " " + (x + 60 * n) + " " + y
            + " 50 20 235 108 156 "
            + (tick + 20) + " " + (x + 60 * n) + " " + y + " 50 20 235 108 156\n");
        break;
      case UNKNOWN:
        out.append("motion " + shapeName + " " + tick + " " + (x + 60 * n) + " " + y
            + " 50 20 133 153 158 "
            + (tick + 20) + " " + (x + 60 * n) + " " + y + " 50 20 235 108 156\n");
        break;
      default:
        //nothing
    }
  }

  //Grow the timer bar
  private static void keepTime(int tick, String name, Appendable out, int x, int y)
      throws IOException {
    out.append("motion " + name + "Timer " + tick + " " + x + " " + (y + 40) + " " + (int) Math
        .ceil(tick / 2 + 1) + " 10 255 182 25 "
        + (tick + 20) + " " + x + " " + (y + 40) + " " + (int) (Math.ceil(tick / 2 + 1) + 10)
        + " 10 255 182 25\n");
  }

  /**
   * Creates a text file animating two different ways to calculate the n-th Fibonacci number.
   *
   * @param args The Fibonacci number to be calculated
   * @throws IOException if input or output fails
   */
  public static void main(String[] args) throws IOException {
    int n = Integer.parseInt(args[0]);
    initMemoization(n - 1);

    Appendable buffer = new StringBuffer();

    //Define canvas
    buffer.append("canvas 0 0 500 300\n");

    //Use the private methods to define the shapes and motions
    declareShapes(n, buffer, 50, 100, "R");
    declareShapes(n, buffer, 50, 200, "M");
    fibonacciRecursive(n - 1, buffer, 50, 100);
    fibonacciMemoized(n - 1, buffer, 50, 200);

    //Write to a file
    FileWriter w = new FileWriter("fib-recursive-memoized.txt");
    w.write(buffer.toString());
    w.close();
  }

}
