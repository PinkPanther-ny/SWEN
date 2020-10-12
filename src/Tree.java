import bagel.util.Point;

/**
 * <p>This is Tree class for unimelb SWEN20003 2020s2 assignment 2 </p>
 * <p>A stationary type Actor, reserve fruit for MobileActor to collect </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Tree extends Forest{

    public Tree(double x, double y) {
        super(ShadowLife.IMAGE_FILE_FOLDER +"tree.png", x, y);
        fruitNum = 3;

    }

    @Override
    public void draw() {
        super.draw();
        myUtil.drawNumber( this.fruitNum, new Point(getLocation().x, getLocation().y) );

    }

}
