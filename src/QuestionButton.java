import bagel.Input;
import bagel.util.Point;


public class QuestionButton extends AbstractButton {

    QuestionButton() {
        super(new Point(GamePanel.BUTTON_X, 120), 25, ShadowLife.IMAGE_FILE_FOLDER +"question.png");
    }

    @Override
    public boolean clicked(Input input){
        return buttonLocation.distanceTo(input.getMousePosition()) < buttonRadius;
    }

}
