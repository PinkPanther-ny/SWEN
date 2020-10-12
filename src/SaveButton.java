import bagel.util.Point;

public class SaveButton extends AbstractButton{
    public SaveButton() {
        super(new Point(GamePanel.BUTTON_X, 254), 25, ShadowLife.IMAGE_FILE_FOLDER +"save.png");
    }

}
