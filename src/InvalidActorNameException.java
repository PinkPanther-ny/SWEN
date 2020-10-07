/**
 * <p>This is customised Exception class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>The InvalidActorNameException will be thrown when encounter unknown Actor type when read in data </p>
 * <p>Created on 10/6/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class InvalidActorNameException extends Exception{
    InvalidActorNameException(String ActorType){
        super(String.format("ActorFactory encounter unknown Actor <%s>, program aborted.\n", ActorType));
    }

}
