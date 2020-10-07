import bagel.Input;
import bagel.util.Point;


public class QuestionButton extends AbstractButton {

    QuestionButton() {
        super(new Point(990, 120), 25, "res/images/question.png");
    }

    @Override
    public boolean clicked(Input input){
        return buttonLocation.distanceTo(input.getMousePosition()) < buttonRadius;
    }

}
