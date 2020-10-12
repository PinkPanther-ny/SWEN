/**
 * <p>This is Fence class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A stationary type class. If MobileActor collide, it will stop moving</p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Fence extends Actor{

    Fence(double x, double y){
        super(ShadowLife.IMAGE_FILE_FOLDER +"fence.png", x, y);
    }

}
