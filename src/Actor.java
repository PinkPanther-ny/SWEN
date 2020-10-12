import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Point;

import java.util.ArrayList;

/**
 * <p>This is an abstract class for all actors to be simulated</p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */

public abstract class Actor{

    private final Image actor;
    private Point location;
    private final static Font gameFont = new Font(ShadowLife.FONT_FILE_FOLDER +"VeraMono.ttf", 24);

    public Actor(String filepath, double x, double y){
        this(filepath, new Point(x, y));
    }

    /**
     * Constructor of actor that takes the actor image filepath and a location
     * @param filepath Filepath of actor image
     * @param location Coordinate of the actor
     * */
    public Actor(String filepath, Point location){
        actor = new Image(filepath);
        this.location = location;
    }

    /* Getters and Setters */
    protected Point getLocation(){
        return location;
    }

    protected void setLocation(Point location){
        this.location = location;
    }

    /**
     * Detect if there are any objects of the given class exist in the actor array
     * which has the same location of this object
     * @param className Class that want to be detected collision
     * @param actors List reference of that store all actors for collision detection
     * @return The first actor in the array that collide with this object and is instance of Class cls
     * */
    public Actor collideWith(String className, ArrayList<Actor> actors){
        for(Actor actor: actors){
            // Algorithm works since we assume actor can only collide with one stationary object at any location
            if(className.equalsIgnoreCase(actor.getClass().getName())){
                if(
                        this.getLocation().x == actor.getLocation().x &&
                                this.getLocation().y == actor.getLocation().y
                ){
                    return actor;
                }

            }
        }
        return null;
    }

    /** Draw from topleft using its location */
    public void draw(){
        actor.drawFromTopLeft(location.x, location.y);
    }

    /**
     * Draw from topleft using its location with customised draw options
     * @param drawOptions Customised draw options
     * */
    public void draw(DrawOptions drawOptions){
        actor.drawFromTopLeft(location.x, location.y, drawOptions);
    }

}
