import java.util.ArrayList;

/**
 * <p>This is Thief class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A movable type actor, steal fruits in the simulation </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Thief extends MobileActor{
    private boolean isConsuming;

    Thief(double x, double y){
        this(x, y, 0);
    }

    Thief(double x, double y, int direction) {
        super(ShadowLife.SKIN_PACK_FOLDER +"thief.png", x, y, direction);
        isConsuming = false;
    }

    @Override
    public void update(ArrayList<Actor> actors) {
        if(this.isActive()) {
            this.move();
        }
        // Fence check
        this.fenceCheck(actors);

        // Pool check
        this.poolCheck(actors);

        // Sign check
        this.signCheck(actors);

        // Pad check
        this.padCheck(actors);

        // Gatherer check
        this.gathererCheck(actors);

        // Forest check
        this.forestCheck(actors);

        // Depot check
        this.depotCheck(actors);

    }

    public boolean isConsuming() {
        return isConsuming;
    }

    public void setConsuming(boolean consuming) {
        isConsuming = consuming;
    }

    private void padCheck(ArrayList<Actor> actors){
        if(this.collideWith(Pad.class.getName(), actors) != null){
            this.setConsuming(true);
        }
    }

    private void gathererCheck(ArrayList<Actor> actors){
        if(this.collideWith(Gatherer.class.getName(), actors) != null){
            this.setDirection( (this.getDirection()+3)%4 );
        }
    }

    @Override
    public void depotCheck(ArrayList<Actor> actors){
        Depot hoard = (Hoard) this.collideWith(Hoard.class.getName(), actors);
        if(hoard != null){
            if(this.isConsuming()) {
                this.setConsuming(false);
                if (!this.isCarrying()) {
                    if(hoard.getFruitNum()>=1){
                        hoard.stolenFruit(this);
                    }else {
                        this.setDirection( (this.getDirection()+1)%4 );
                    }
                }
            }else if(this.isCarrying()){
                this.setCarrying(false);
                hoard.storeFruit();
                this.setDirection( (this.getDirection()+1)%4 );
            }
        }

        Depot stockpile = (Stockpile) this.collideWith(Stockpile.class.getName(), actors);
        if(stockpile != null){
            if(!this.isCarrying()){
                if(stockpile.getFruitNum() >= 1){
                    this.setCarrying(true);
                    this.setConsuming(false);
                    stockpile.stolenFruit(this);
                    this.setDirection( (this.getDirection()+1)%4 );
                }
            }else{
                this.setDirection( (this.getDirection()+1)%4 );
            }
        }
    }


}
