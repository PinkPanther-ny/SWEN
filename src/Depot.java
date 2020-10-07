import bagel.util.Point;

/**
 * <p>This is Depot class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A stationary type abstract class, all subclasses can store fruits </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public abstract class Depot extends Actor{

    private int fruitNum;

    public Depot(String filepath, double x, double y) {
        super(filepath, x, y);
        fruitNum = 0;
    }

    @Override
    public void draw() {
        super.draw();
        myUtil.drawNumber( fruitNum, new Point(getLocation().x, getLocation().y) );
    }

    public void storeFruit(){
        fruitNum++;
    }
    public void stolenFruit(Thief thief){
        thief.setCarrying(true);
        fruitNum--;
    }
    public int getFruitNum() {
        return fruitNum;
    }

}
