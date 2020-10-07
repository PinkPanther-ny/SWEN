/**
 * <p>A factory class for initializing Actors</p>
 * <p>This is unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>Simple simulation of a fictional universe</p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */

public final class ActorFactory {

    /**
     * Factory method creates actor based on the actor type
     * @param ActorType A string of actor type
     * @param x x location
     * @param y y location
     * @return A concrete instance that extends abstract Actor class
     * @throws InvalidActorNameException If cannot find and instance that match the input actor type
     * */
    public static Actor getActor(String ActorType, double x, double y) throws InvalidActorNameException {

        if(ActorType == null){
            return null;
        }


        if(ActorType.equalsIgnoreCase("Gatherer")){
            return new Gatherer(x, y);

        } else if(ActorType.equalsIgnoreCase("Thief")){
            return new Thief(x, y);

        } else if(ActorType.equalsIgnoreCase("Pool")){
            return new MitosisPool(x, y);

        } else if(ActorType.equalsIgnoreCase("Pad")){
            return new Pad(x, y);

        } else if(ActorType.equalsIgnoreCase("Stockpile")){
            return new Stockpile(x, y);

        } else if(ActorType.equalsIgnoreCase("Hoard")){
            return new Hoard(x, y);

        } else if(ActorType.equalsIgnoreCase("Fence")){
            return new Fence(x, y);

        } else if(ActorType.equalsIgnoreCase("Tree")){
            return new Tree(x, y);

        } else if(ActorType.equalsIgnoreCase("GoldenTree")){
            return new GoldenTree(x, y);

        } else if(ActorType.equalsIgnoreCase("SignUp")){
            return new Sign(x, y, 0);

        } else if(ActorType.equalsIgnoreCase("SignRight")){
            return new Sign(x, y, 1);

        } else if(ActorType.equalsIgnoreCase("SignDown")){
            return new Sign(x, y, 2);

        } else if(ActorType.equalsIgnoreCase("SignLeft")){
            return new Sign(x, y, 3);

        }


        throw new InvalidActorNameException(ActorType);
    }


}
