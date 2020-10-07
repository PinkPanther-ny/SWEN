/**
 * <p>This is GoldenTree class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>A stationary type actor, provide infinite number of fruits </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class GoldenTree extends Forest{

    public GoldenTree(double x, double y) {

        super("res/images/gold-tree.png", x, y);
        fruitNum = -1;
    }

}
