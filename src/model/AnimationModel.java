package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.Math.floor;


/**
 * Represents an animation with shapes and frames for each shape that document the changes made to
 * each shape on specific frames.
 * Class invariants enforced by this model: 1. A shape doesn't change multiple appearances on a
 * single frame, meaning there are no overlapping changes to the shape. 2. Existing shapes in the
 * animation do not change. There can only be instances of shapes added or removed.
 */
public class AnimationModel implements IAnimationModel {

  private Map<String, Map<Integer, IShape>> shapes;
  private final List<Integer> canvas;


  /**
   * Constructs a new instance of Animation Model with an empty map and a default canvas with
   * length 360 at 200 (x), 70 (y).
   */
  public AnimationModel() {
    this.shapes = new LinkedHashMap<String, Map<Integer, IShape>>();
    this.canvas = new ArrayList<Integer>(Arrays.asList(0, 0, 360, 360));
  }

  /**
   * Constructs a new instance of Animation Model with an empty map and the given canvas.
   * @param canvas the given canvas which will act as the background for the animation. The
   *               listed values are the canvas's x and y position and width and height. Each
   *               value must be non-negative.
   */
  public AnimationModel(List<Integer> canvas) {
    if (canvas == null || canvas.size() != 4) {
      throw new IllegalArgumentException("Invalid canvas.");
    }
    if (canvas.get(0) < 0 || canvas.get(1) < 0 || canvas.get(2) <= 0 || canvas.get(3) <= 0) {
      throw new IllegalArgumentException("Canvas must have a length greater than zero and a "
          + "non-negative position.");
    }
    this.shapes = new LinkedHashMap<String, Map<Integer, IShape>>();
    this.canvas = new ArrayList<Integer>(Arrays.asList(canvas.get(0), canvas.get(1), canvas.get(2),
        canvas.get(3)));
  }

  /**
   * Constructs a new instance of Animation model with the given map and a default canvas with
   * length 360 at 200 (x), 70 (y).
   *
   * @param shapes the given shapes and their frames that will appear in this animation.
   */
  public AnimationModel(Map<String, Map<Integer, IShape>> shapes) {
    if (shapes == null || this.invalidMap(shapes.values())) {
      throw new IllegalArgumentException("Invalid map.");
    }

    this.shapes = this.copyAnimation(shapes);
    this.canvas = new ArrayList<Integer>(Arrays.asList(0, 0, 360, 360));
  }

  /**
   * Constructs a new instance of Animation model with the given map and given canvas.
   * @param shapes the given shapes and their frames that will appear in this animation. Frame
   *               numbers must be non-negative.
   * @param canvas the given canvas which will act as the background for the animation. The
   *               listed values are the canvas's x and y position and width and height. Each
   *               value must be non-negative.
   */
  public AnimationModel(Map<String, Map<Integer, IShape>> shapes, List<Integer> canvas) {
    if (shapes == null || this.invalidMap(shapes.values())) {
      throw new IllegalArgumentException("Invalid map.");
    }
    if (canvas == null || canvas.size() != 4) {
      throw new IllegalArgumentException("Invalid canvas.");
    }

    this.shapes = this.copyAnimation(shapes);
    this.canvas = new ArrayList<Integer>(Arrays.asList(canvas.get(0), canvas.get(1), canvas.get(2),
        canvas.get(3)));
  }



  /**
   * Determines if the given collection of maps contain invalid values such as null values and
   * negative frame numbers.
   *
   * @param map the collection of frames that need to be checked for invalid values.
   * @return true if given map is invalid, false if given map is valid.
   */
  protected boolean invalidMap(Collection<Map<Integer, IShape>> map) {
    if (map == null) {
      return true;
    } else {
      boolean invalid = false;
      // For each map, check if the frames are null
      for (Map<Integer, IShape> frames : map) {
        Set<Integer> keys = frames.keySet();
        invalid = invalid || frames == null;
        // For each frame, check if the keys are negative values and if the shapes are null
        for (Integer num : keys) {
          invalid =
              invalid || num < 0 || frames.get(num) == null;
        }
      }
      return invalid;
    }
  }


  /**
   * Making a copy of the given map which will be used in this model to prevent unwanted mutation.
   *
   * @param animation the map that contains the frames of the shapes to be used for this model.
   * @return a copy of the given map.
   */
  protected Map<String, Map<Integer, IShape>> copyAnimation(Map<String, Map<Integer, IShape>>
      animation) {

    Map<String, Map<Integer, IShape>> copyMap = new LinkedHashMap<String, Map<Integer, IShape>>();
    Set<String> copyShapeNames = animation.keySet();

    // For each shape name, add a new entry to copyMap (entry = copy of map of frames)
    for (String shapeName : copyShapeNames) {
      copyMap.put(shapeName, this.copyFrames(animation.get(shapeName)));
    }

    return copyMap;
  }


  @Override
  public void move(int frame, String shape, int x, int y) throws IllegalArgumentException {
    // If the frame is negative
    if (frame < 0) {
      throw new IllegalArgumentException("Invalid frame.");
    }
    else {
      // If the map is empty
      if (this.shapes.isEmpty()) {
        throw new IllegalArgumentException("There isn't a shape to move.");
      }
      // If the map doesn't contain the given key
      if (!this.shapes.containsKey(shape)) {
        throw new IllegalArgumentException("Invalid shape.");
      }

      List<Integer> colorList = new ArrayList<Integer>(); // color of shape
      List<Integer> positionList = new ArrayList<Integer>(Arrays.asList(x, y)); // position of shape
      List<Integer> sizeList = new ArrayList<Integer>(); //size of shape

      // Using a helper method to make a new frame entry with the shape of the new size
      this.addEntry(frame, shape, new ArrayList<List<Integer>>(Arrays.asList(colorList,
          positionList, sizeList)));
    }
  }


  @Override
  public void changeColor(int frame, String shape, int r, int g, int b)
      throws IllegalArgumentException {
    // If frame is negative
    if (frame < 0) {
      throw new IllegalArgumentException("Invalid frame.");
    }
    // if RGB values are not between 0-255
    else if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
      throw new IllegalArgumentException("Invalid color.");
    } else {
      // If the map is empty
      if (this.shapes.isEmpty()) {
        throw new IllegalArgumentException("There isn't a shape to change the color of.");
      }
      // If the map doesn't contain the given key
      if (!this.shapes.containsKey(shape)) {
        throw new IllegalArgumentException("Invalid shape.");
      }

      List<Integer> colorList = new ArrayList<Integer>(Arrays.asList(r, g, b)); // color of shape
      List<Integer> positionList = new ArrayList<Integer>(); // position of the shape
      List<Integer> sizeList = new ArrayList<Integer>(); //size of shape

      // Using a helper method to make a new frame entry with the shape of the new size
      this.addEntry(frame, shape, new ArrayList<List<Integer>>(Arrays.asList(colorList,
          positionList, sizeList)));
    }
  }


  @Override
  public void changeSize(int frame, String shape, int width, int height)
      throws IllegalArgumentException {
    // If frame is negative
    if (frame < 0) {
      throw new IllegalArgumentException("Invalid frame.");
    }
    // If width/height is negative or zero
    else if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Invalid dimensions.");
    } else {
      // If the map is empty
      if (this.shapes.isEmpty()) {
        throw new IllegalArgumentException("There isn't a shape to change the size of.");
      }
      // If the map doesn't contain the given key
      if (!this.shapes.containsKey(shape)) {
        throw new IllegalArgumentException("Invalid shape.");
      }

      List<Integer> colorList = new ArrayList<Integer>(); // color of the shape
      List<Integer> positionList = new ArrayList<Integer>(); // position of the shape
      List<Integer> sizeList = new ArrayList<Integer>(Arrays.asList(width, height)); //size of shape

      // Using a helper method to make a new frame entry with the shape of the new size
      this.addEntry(frame, shape, new ArrayList<List<Integer>>(Arrays.asList(colorList,
          positionList, sizeList)));
    }
  }


  /**
   * Adds a new entry to the map containing the frames of the given shape. This new entry will
   * contain the new appearances according to the provided list.
   *
   * @param frame           the frame at which the change of appearance occurs.
   * @param shape           the shape that will be changing appearance.
   * @param shapeAppearance Contains the new appearances for the shape: list of rgb values, list of
   *                        position values (x, y), and list of size values (width, height).
   */
  protected void addEntry(int frame, String shape, List<List<Integer>> shapeAppearance) {
    Objects.requireNonNull(shapeAppearance); // ensuring list of shape's description is not null
    Map<Integer, IShape> shapeMap = this.shapes.get(shape); // the frames of the given shape
    IShape current; // the shape that appears before the new entry
    IShape replacement; // the shape that will be added to the map of frames
    List<Integer> colorList = shapeAppearance.get(0); // shape's color
    List<Integer> positionList = shapeAppearance.get(1); // shape's position
    List<Integer> sizeList = shapeAppearance.get(2); // shape's size

    // Getting the shape present before the desired frame
    for (int i = frame; i >= 0; i--) {
      if (shapeMap.containsKey(i)) {
        // Getting the current shape at that frame
        current = shapeMap.get(i);
        replacement = current;

        // Creating a new shape with the given color
        if (!colorList.isEmpty()) {
          replacement = current.copyShape(new ArrayList<Integer>(Arrays.asList(colorList.get(0),
              colorList.get(1), colorList.get(2))), current.getPosition(), current.getSize().get(0),
              current.getSize().get(1));
        }
        // Creating a new shape with the given position
        if (!positionList.isEmpty()) {
          replacement = current.copyShape(current.getColor(), new Position(positionList.get(0),
              positionList.get(1)), current.getSize().get(0), current.getSize().get(1));
        }
        // Creating a new shape with the given width and height
        if (!sizeList.isEmpty()) {
          replacement = current.copyShape(current.getColor(), current.getPosition(),
              sizeList.get(0), sizeList.get(1));
        }
        // Adding the new shape to the map
        shapeMap.put(frame, replacement);

        // Ending the loop after adding the new entry
        break;
      }
    }
  }

  //A helper method for the builder class. Adds an arbitrary instance of the shape at the given
  //frame, which will then be replaced.
  private void addArbitraryEntry(int frame, String shape) {
    Map<Integer, IShape> shapeMap = shapes.get(shape);
    IShape aShape = shapeMap.values().iterator().next();
    shapeMap.put(frame, aShape.copyShape(Arrays.asList(1, 1, 1), new Position(1, 1), 1, 1));
  }

  @Override
  public String toString() {
    String animation = "canvas " + this.canvas.get(0) + " " + this.canvas.get(1) + " "
        + this.canvas.get(2) + " " + this.canvas.get(3);
    Set<String> shapeNames = this.shapes.keySet(); // keys of this animation

    // For each shape name, create a new string that documents that shape's movements
    for (String name : shapeNames) {
      Map<Integer, IShape> frames = this.shapes.get(name); // the frames of the current shape
      Set<Integer> frameNums = frames.keySet(); // the frame numbers of the current shape
      int counter = 1;  // counting the number of motions


      // For each frame, document the description of the shape
      for (Integer num : frameNums) {
        // Before describing motions of shape, state the shape's name and type and describe first
        // motion
        if (counter == 1) {
          animation += "\nshape " + name + " " + frames.get(num); // name & type of shape
          animation += "\nmotion " + name + " " + num + " " + this.shapeToString(frames.get(num));
        }
        // If the motion is last in the description, keep it on the same line as starting motion
        else if (counter == frameNums.size()) {
          animation += " " + num + " " + this.shapeToString(frames.get(num));
        }
        // Otherwise, make the current motion an ending and starting motion
        else {
          animation += " " + num + " " + this.shapeToString(frames.get(num))
              + "\nmotion " + name + " " + num + " " + this.shapeToString(frames.get(num));
        }
        counter++;
      }
    }

    return animation;
  }

  /**
   * Provides the string representation of the given shape including the shape's position, size, and
   * color.
   *
   * @param shape the shape that will be represented as a string.
   * @return A string version of the given shape, documenting its appearance as numbers.
   */
  protected String shapeToString(IShape shape) {
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
  public Map<String, Map<Integer, IShape>> changeSpeed(double num) {
    Map<String, Map<Integer, IShape>> result = new LinkedHashMap<>();
    for (Map.Entry<String, Map<Integer, IShape>> shape : shapes.entrySet()) {
      Map<Integer, IShape> shapeMap = new TreeMap<>();
      for (Map.Entry<Integer, IShape> frame : shape.getValue().entrySet()) {
        shapeMap.put((int) (frame.getKey() * num), frame.getValue());
      }
      result.put(shape.getKey(), shapeMap);
    }
    return result;
  }

  @Override
  public IShape getState(int frame, String shape) {
    if (!(shapes.containsKey(shape))) {
      throw new IllegalArgumentException("Shape not present in model");
    }
    if (frame < 0) {
      throw new IllegalArgumentException("Frame must not be negative");
    }
    Map<Integer, IShape> theShape = shapes.get(shape);
    TreeMap<Integer, IShape> thisShape = (TreeMap<Integer, IShape>) copyFrames(theShape);
    Map.Entry<Integer, IShape> shapeBefore;
    Map.Entry<Integer, IShape> shapeAfter;

    if (thisShape.containsKey(frame)) { //frame is already specified
      IShape copyShape = thisShape.get(frame);
      return copyShape.copyShape(copyShape.getColor(), copyShape.getPosition(),
          copyShape.getSize().get(0), copyShape.getSize().get(1));
    }
    if (thisShape.floorKey(frame) == null) { //frame is before the first specified frame
      return null;
    } else {
      shapeBefore = thisShape.floorEntry(frame);
    }
    if (thisShape.ceilingKey(frame) == null) { //frame is after the last specified frame
      return thisShape.lastEntry().getValue();
    } else {
      shapeAfter = thisShape.ceilingEntry(frame);
    }
    //Otherwise, frame is between two specified frames
    double prop;
    prop = ((double) (frame - shapeBefore.getKey()))
        / ((double) (shapeAfter.getKey() - shapeBefore.getKey()));
    IShape copyShape = proportionShapes(shapeBefore.getValue(), shapeAfter.getValue(), prop);
    return copyShape.copyShape(copyShape.getColor(), copyShape.getPosition(),
        copyShape.getSize().get(0), copyShape.getSize().get(1));
  }

  /**
   * Returns a shape that is in a transition state between the two given integers.
   *
   * @param s1   The first shape
   * @param s2   The second shape
   * @param prop The proportion to travel between two integers.
   * @return the transition state of a shape.
   */
  protected static IShape proportionShapes(IShape s1, IShape s2, double prop) {
    List<Integer> myRgb = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      myRgb.add(proportion(s1.getColor().get(i), s2.getColor().get(i), prop));
    }

    Position myPos = new Position(
        proportion(s1.getPosition().getX(), s2.getPosition().getX(), prop),
        proportion(s1.getPosition().getY(), s2.getPosition().getY(), prop));
    int myWidth = proportion(s1.getSize().get(0), s2.getSize().get(0), prop);
    int myHeight = proportion(s1.getSize().get(1), s2.getSize().get(1), prop);

    return s1.copyShape(myRgb, myPos, myWidth, myHeight);
  }

  /**
   * Splits the difference between two integers based on a proportion.
   *
   * @param i1         the first integer
   * @param i2         the second integer
   * @param proportion the proportion to travel between them
   * @return the integer that splits the difference (floor if it is not a whole number)
   */
  protected static int proportion(int i1, int i2, double proportion) {
    if (i1 > i2) {
      return (int) floor(i1 - (abs(i1 - i2) * proportion));
    } else {
      return (int) floor(i1 + (abs(i1 - i2) * proportion));
    }
  }

  @Override
  public Map<String, IShape> getFullState(int frame) {
    if (frame < 0) {
      throw new IllegalArgumentException("Frame must not be negative");
    }
    Map<String, IShape> state = new TreeMap<>();
    for (String key : shapes.keySet()) {
      state.put(key, getState(frame, key));
    }
    return state;
  }

  @Override
  public void add(int frame, String name, IShape shape) {
    if (shapes.containsKey(name)) {
      throw new IllegalArgumentException("This shape name is already included");
    }
    if (frame < 0) {
      throw new IllegalArgumentException("Frame must be non-negative");
    }
    Map<Integer, IShape> thisShape = new TreeMap<>();
    thisShape.put(frame, shape);
    shapes.put(name, thisShape);
  }

  @Override
  public void add(String name) throws IllegalArgumentException {
    if (shapes.containsKey(name)) {
      throw new IllegalArgumentException("This shape name is already included");
    }
    Map<Integer, IShape> thisShape = new TreeMap<>();
    shapes.put(name, thisShape);
  }

  @Override
  public void remove(int frame, String shape) {
    if (!shapes.containsKey(shape)) {
      throw new IllegalArgumentException("Shape not present in model");
    }
    if (frame < 0) {
      throw new IllegalArgumentException("Frame must be non-negative");
    }
    Map<Integer, IShape> thisShape = copyFrames(shapes.get(shape));
    List<Integer> toRemove = new ArrayList<>();
    for (int key : thisShape.keySet()) {
      if (key >= frame) {
        toRemove.add(key);
      }
    }
    for (int key : toRemove) {
      thisShape.remove(key);
    }
    shapes.remove(shape);
    if (thisShape.size() != 0) {
      shapes.put(shape, thisShape);
    }
  }

  @Override
  public Map<String, Map<Integer, IShape>> getShapes() {
    Map<String, Map<Integer, IShape>> copyMap = new LinkedHashMap<String, Map<Integer, IShape>>();
    Set<String> copyShapeNames = this.shapes.keySet();

    // For each shape name, add a new entry to copyMap (entry = copy of map of frames)
    for (String shapeName : copyShapeNames) {
      copyMap.put(shapeName, this.copyFrames(this.shapes.get(shapeName)));
    }

    return copyMap;
  }

  @Override
  public List<Integer> getCanvas() {
    return new ArrayList<Integer>(Arrays.asList(this.canvas.get(0), this.canvas.get(1),
        this.canvas.get(2), this.canvas.get(3)));
  }

  /**
   * Copies the map that contains the frame number and shape description at those instances by
   * copying the shape descriptions to prevent unwanted mutation.
   *
   * @param frames current map containing the frames for a shape
   * @return copy of the map containing the frames for a shape
   * @throws IllegalArgumentException when the given map is null
   */
  protected Map<Integer, IShape> copyFrames(Map<Integer, IShape> frames)
      throws IllegalArgumentException {

    // If given frames is null, throw exception
    if (frames == null) {
      throw new IllegalArgumentException("Invalid map of frames.");
    }

    Map<Integer, IShape> copyMap = new TreeMap<Integer, IShape>();
    Set<Integer> copyFrameNum = frames.keySet();

    // For each frame number, add a new entry to copyMap (entry = frame number and copy of shape)
    for (Integer frameNum : copyFrameNum) {
      if (frames.get(frameNum) == null) {
        throw new IllegalArgumentException("Invalid shape.");
      } else {
        IShape currentShape = frames.get(frameNum);
        IShape newShape = currentShape.copyShape(currentShape.getColor(),
            currentShape.getPosition(), currentShape.getSize().get(0),
            currentShape.getSize().get(1));

        copyMap.put(frameNum, newShape);
      }
    }
    return copyMap;
  }


  // Overriding equals() so that a model with the same contents as this model is considered equal
  // to this model even if those contents don't appear in the correct order.
  @Override
  public boolean equals(Object o) {
    if (o instanceof AnimationModel) {
      boolean isSame = true;
      IAnimationModel model2 = (AnimationModel) o; // make the given object an Animation Model
      Set<String> keys = this.shapes.keySet(); // collection of keys in this model's map

      // For each key, check if object has the same key with the same value
      for (String s : keys) {
        if (!model2.getShapes().isEmpty()) {
          isSame = isSame && this.framesEquals(this.shapes.get(s), model2.getShapes().get(s));
        } else {
          isSame = isSame && (model2.getShapes().isEmpty() && this.shapes.isEmpty());
        }
      }

      return isSame;
    } else {
      return false;
    }
  }


  /**
   * Determining if the given maps are equal to each other by comparing their keys and values.
   *
   * @param map1 the map of this model to compare.
   * @param map2 the map of another model to compare.
   * @return true if the models contain the same contents, false if they do not.
   */
  protected boolean framesEquals(Map<Integer, IShape> map1, Map<Integer, IShape> map2) {
    // Ensuring neither map is null
    Objects.requireNonNull(map1);
    Objects.requireNonNull(map2);
    // Keep tracking if the maps contain the same contents and getting the keys of map1
    boolean sameFrame = true;
    Set<Integer> keys1 = map1.keySet();

    // For each frame, determine if the value of map1 is equal to the value of map2
    for (Integer frame : keys1) {
      sameFrame = sameFrame && map1.get(frame).equals(map2.get(frame));
    }

    return sameFrame;
  }

  //Overriding the hashCode method
  @Override
  public int hashCode() {
    return Objects.hash(shapes.entrySet());
  }


  /**
   * Builds an AnimationModel after reading a file the details an animation.
   */
  public static final class Builder implements AnimationBuilder<IAnimationModel> {

    private final AnimationModel model;
    private List<Integer> canvas;
    private final Map<String, IShape> types;

    /**
     * Constructor setting defaults.
     */
    public Builder() {
      this.model = new AnimationModel();
      this.canvas = new ArrayList<>(Arrays.asList(0, 0, 360, 360));
      this.types = new TreeMap<>();
    }

    @Override
    public IAnimationModel build() {
      return new AnimationModel(model.getShapes(), canvas);
    }

    @Override
    public AnimationBuilder<IAnimationModel> setBounds(int x, int y, int width, int height) {
      this.canvas = Arrays.asList(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<IAnimationModel> declareShape(String name, String type) {
      if (type.equals("rectangle")) {
        model.add(name);
        types.put(name,
            new Rect(Arrays.asList(1, 1, 1), new Position(1, 1), 1, 1));
      }
      else if (type.equals("ellipse")) {
        model.add(name);
        types.put(name,
            new Circle(Arrays.asList(1, 1, 1), new Position(1, 1), 1, 1));
      }
      else {
        throw new IllegalArgumentException(name + " is not supported by this builder.");
      }
      return this;
    }

    @Override
    public AnimationBuilder<IAnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                       int h1, int r1, int g1, int b1, int t2,
                                                       int x2, int y2, int w2, int h2, int r2,
                                                       int g2, int b2) {
      if (!model.getShapes().containsKey(name)) {
        throw new IllegalArgumentException("This shape does not exist");
      }
      Map<Integer, IShape> shapeMap = model.getShapes().get(name);
      //case where this is the first movement to be added to the shape
      if (shapeMap.isEmpty()) {
        model.remove(0, name);
        model.add(t1, name, types.get(name).copyShape(Arrays.asList(r1, g1, b1),
            new Position(x1, y1), w1, h1));
        model.move(t2, name, x2, y2);
        model.changeSize(t2, name, w2, h2);
        model.changeColor(t2, name, r2, g2, b2);
      }
      else {
        // This section checks if there is a record of this shape from before this motion starts.
        // If not, one is added so addEntry will work.
        boolean firstMotion = true;
        for (int i = t1; i >= 0; i--) {
          if (shapeMap.containsKey(i)) {
            firstMotion = false;
          }
        }
        if (firstMotion) {
          model.addArbitraryEntry(t1, name);
        }

        model.move(t1, name, x1, y1);
        model.move(t2, name, x2, y2);
        model.changeSize(t1, name, w1, h1);
        model.changeSize(t2, name, w2, h2);
        model.changeColor(t1, name, r1, g1, b1);
        model.changeColor(t2, name, r2, g2, b2);
      }
      return this;
    }
  }
}
