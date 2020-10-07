/**
 * <p>This is Forest class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A stationary type abstract class, all subclasses are trees </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public abstract class Forest extends Actor{
    protected int fruitNum;

    public Forest(String fileName, double x, double y) {
        super(fileName, x, y);

    }

    public void takenFruit(MobileActor mobileActor){

        if(fruitNum != 0){
            fruitNum--;
            mobileActor.setCarrying(true);
            if(mobileActor instanceof Gatherer) {
                mobileActor.setDirection((mobileActor.getDirection() + 2) % 4);
            }

        }
    }


}
