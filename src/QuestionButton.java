import bagel.Input;
import bagel.util.Point;


public class QuestionButton extends AbstractButton {

    QuestionButton() {
        super(new Point(GamePanel.BUTTON_X, 120), 25, ShadowLife.SKIN_PACK_FOLDER +"question.png");
    }

    @Override
    public boolean clicked(Input input){
        return buttonLocation.distanceTo(input.getMousePosition()) < buttonRadius;
    }

}
