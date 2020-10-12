import java.util.ArrayList;

/**
 * <p>This is Gatherer class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A movable type actor, collect fruits in the simulation </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Gatherer extends MobileActor{

    /**
     * Constructor for Gatherer which direction set to default LEFT
     * @param x x location
     * @param y y location
     * */
    public Gatherer(double x, double y) {
        this(x, y, 3);
    }

    /**
     * Constructor for Gatherer
     * @param x x location of the Gatherer
     * @param y y location of the Gatherer
     * @param direction Four direction of the Gatherer, UP, RIGHT, DOWN, LEFT which correspond to 0, 1, 2, 3
     */
    public Gatherer(double x, double y, int direction) {
        super(ShadowLife.IMAGE_FILE_FOLDER +"gatherer.png", x, y, direction);
    }


    /**
     * Update current Gatherer by checking all collision in the whole list of actors
     * @param actors List reference which store all actors for Gatherer collision detection
     */
    public void update(ArrayList<Actor> actors){
        if(this.isActive()) {
            this.move();
        }

        // Fence check
        this.fenceCheck(actors);

        // Pool check
        this.poolCheck(actors);

        // Sign check
        this.signCheck(actors);

        // Tree check
        this.forestCheck(actors);

        // Depot check
        this.depotCheck(actors);

    }

    @Override
    public void depotCheck(ArrayList<Actor> actors){
        Depot hoard = (Hoard) this.collideWith(Hoard.class.getName(), actors);
        if(hoard != null){
            if(this.isCarrying()){
                hoard.storeFruit();
                this.setCarrying(false);
            }
            this.setDirection( (this.getDirection()+2)%4 );
        }

        Depot stockpile = (Stockpile) this.collideWith(Stockpile.class.getName(), actors);
        if(stockpile != null){
            if(this.isCarrying()){
                stockpile.storeFruit();
                this.setCarrying(false);
            }
            this.setDirection( (this.getDirection()+2)%4 );
        }
    }

}
