import bagel.util.Point;

import java.util.ArrayList;

/**
 * <p>This is MobileActor class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>All of the subclasses are not stationary</p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public abstract class MobileActor extends Actor{

    private static final double speed = ShadowLife.TILE_WID;
    private int direction;
    private boolean isCarrying;
    private boolean isActive;


    /**
     * Constructor for MobileActor
     * @param fileName the fileName of the MobileActor image
     * @param x x location of the MobileActor
     * @param y y location of the MobileActor
     * @param direction Four direction of the MobileActor, UP, RIGHT, DOWN, LEFT which correspond to 0, 1, 2, 3
     */
    MobileActor(String fileName, double x, double y, int direction){
        super(fileName, x, y);
        this.direction = direction;
        this.isCarrying = false;
        this.isActive = true;
    }

    public abstract void update(ArrayList<Actor> actors);

    /** Logic for checking fence collision
     * @param actors List reference that store all actors
     * */
    public void fenceCheck(ArrayList<Actor> actors){
        if(this.collideWith(Fence.class.getName(), actors) != null){
            deactivateMobileActor(this);
            this.back();
        }
    }

    /** Logic for checking pool collision
     * @param actors List reference that store all actors
     * */
    public void poolCheck(ArrayList<Actor> actors){
        if(this.collideWith(MitosisPool.class.getName(), actors) != null){
            MobileActor split;
            if(this instanceof Gatherer){
                split = new Gatherer(this.getLocation().x, this.getLocation().y, (this.getDirection()+3)%4 );
            }else{
                split = new Thief(this.getLocation().x, this.getLocation().y, (this.getDirection()+3)%4 );
            }

            split.move();
            actors.add(split);
            // Pseudo destroy
            this.setDirection((this.getDirection()+1)%4);
            this.move();
            this.setCarrying(false);
            if(this instanceof Thief){
                ((Thief) this).setConsuming(false);
            }
        }
    }

    /** Logic for checking sign collision
     * @param actors List reference that store all actors
     * */
    public void signCheck(ArrayList<Actor> actors){
        Sign sign = (Sign) this.collideWith(Sign.class.getName(), actors);
        if( sign != null){
            this.setDirection(sign.getDirection());

        }
    }

    /** Logic for checking trees collision
     * @param actors List reference that store all actors
     * */
    public void forestCheck(ArrayList<Actor> actors){
        Forest tree = (Tree) this.collideWith(Tree.class.getName(), actors);
        if( tree!= null && !this.isCarrying()){
            tree.takenFruit(this);
        }
        Forest gTree = (GoldenTree) this.collideWith(GoldenTree.class.getName(), actors);
        if( gTree!= null && !this.isCarrying()){
            gTree.takenFruit(this);
        }

    }

    /** Logic for checking depot collision
     * @param actors List reference that store all actors
     * */
    public abstract void depotCheck(ArrayList<Actor> actors);

    /** Move the actor in its direction one step */
    public void move(){
        Point p = getLocation();
        // Up, Right, Down, Left
        if (direction == 0){
            setLocation(new Point(p.x, p.y - speed));
        }else if(direction == 1){
            setLocation(new Point(p.x + speed, p.y));
        }else if(direction == 2){
            setLocation(new Point(p.x, p.y + speed));
        }else if(direction == 3){
            setLocation(new Point(p.x - speed, p.y));
        }
    }

    /** Move the actor in its anti-direction one step */
    public void back(){
        Point p = getLocation();
        // Up, Right, Down, Left
        if (direction == 0){
            setLocation(new Point(p.x, p.y + speed));
        }else if(direction == 1){
            setLocation(new Point(p.x - speed, p.y));
        }else if(direction == 2){
            setLocation(new Point(p.x, p.y - speed));
        }else if(direction == 3){
            setLocation(new Point(p.x + speed, p.y));
        }
    }

    /** Set actor status to deactivate
     * @param actor the actor will be set to deactivated
     * */
    public void deactivateMobileActor(MobileActor actor){
        actor.setActive(false);
    }


    /* Getter and Setter */
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isCarrying() {
        return isCarrying;
    }

    public void setCarrying(boolean carrying) {
        isCarrying = carrying;
    }

}
