import bagel.DrawOptions;

/**
 * <p>This is Sign class for unimelb SWEN20003 2020s2 assignment 2 </p>
 * <p>A stationary type Actor, change the direction of MobileActor </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Sign extends Actor{
    private final int direction;

    /**
     * Constructor for Sign
     * @param x x location of the sign
     * @param y y location of the sign
     * @param direction Four direction of the sign, UP, RIGHT, DOWN, LEFT which correspond to 0, 1, 2, 3
     */
    public Sign(double x, double y, int direction) {
        super("res/images/up.png", x, y);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void draw() {
        if(direction == 0){
            super.draw();
        }else if(direction == 1){
            super.draw( new DrawOptions().setRotation(Math.PI/2) );
        }else if(direction == 2){
            super.draw( new DrawOptions().setRotation(Math.PI) );
        }else if(direction == 3){
            super.draw( new DrawOptions().setRotation(-Math.PI/2) );
        }
    }
}
