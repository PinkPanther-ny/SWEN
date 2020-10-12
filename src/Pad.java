/**
 * <p>This is Pad class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A stationary type Actor, when Thief collide with it, it start consuming </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class Pad extends Actor{

    public Pad(double x, double y) {
        super(ShadowLife.IMAGE_FILE_FOLDER +"pad.png", x, y);
    }

}
