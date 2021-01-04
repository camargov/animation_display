Changes to VisualAnimationView (made in assignment 7)

- The class extends an abstract class that reduces duplicate code that would have appeared in
 both the visual and interactive animation
 - This class has two new fields, tick and timer, which are used in both this view and the
  interactive one to keep track of the animation as it is playing 
  
Other changes: We changed the ViewFactory to create an interactive view when given the
"interactive" argument. 
 
Overview of Controller and View Designs (assignment 7)

- Controller 

    Our Controller interface defines methods to set up and render the view using the model.
    SimpleController is for the textual, SVG, and animation views, and simply renders 
    the view.
    InteractiveController implements a Features interface with methods to be 
    called when buttons are pressed, and handles the interactive features.
    
- View

    The Textual and SVG views have have the same rendering as in assignment 6. Because the 
    visual and interactive views share common features such as a Timer and a Panel, we made an
    abstract class to reduce duplicated code.
    The interactive view implements an interface that has additional methods that allow
    interactivity with the animation, such as pausing, resuming, speed change, etc.
    This interface required a different controller than the other views in order to handle
    the interactive features.
    The interface IAnimationInteractiveView extends the original view interface, which allows
    for the interactive view to be used as either of the interfaces depending on the situation.


Changes to model (made in assignment 6)

- new field for canvas and a new constructor that accepts a map and canvas
- overriding toString() for the shape classes (that implemented IShape) so the textual view has a
 line stating what type of shape is being animated
- revising the toString() method in the model class so that it matches the expected textual view
 with a line describing the canvas and the current shape
- adding another version of the add() method to take in fewer arguments so that we can 
declare shapes without a specified frame to meet the expectations of the builder interface
- in the model constructor, initiating the map of Strings to motions as a LinkedHashMap
instead of a TreeMap so that the order of the shapes as they were declared is preserved
-making getState() in the model return a copy of the shape so that the view cannot
mutate any of the model's shapes while using that method
- allowing there to be negative positions and bounds

Overview of Model Design (assignment 5)

- Interfaces

   Our design consists of two interfaces, one represents the model used for an animation
    (cs3500.animator.model.IAnimationModel) while the other represents a shape that will be included in the animation
    (cs3500.animator.model.IShape). These interfaces contain methods that address details of the animation and allow for 
    modifications to the animation and to shapes while also preserving unwanted mutation. 
     
- Classes

    Our design consists of a class cs3500.animator.model.AnimationModel contains a map of the shapes and frames for
     each shape in order to easily access the details and instances of the animation. There are
      also class for shapes such as cs3500.animator.model.AShape which is an abstract class that performs the methods
       that most shapes share such as setting and getting the appearance of the shape. Other
        classes such as cs3500.animator.model.Circle and cs3500.animator.model.Rect represent the different shapes that can be supported by
        cs3500.animator.model.IShape. There is also a cs3500.animator.model.Position class which describes the position of a given
        shape in the animation. 
     