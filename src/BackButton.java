import bagel.util.Point;

public class BackButton extends AbstractButton{
    public BackButton() {
        super(new Point(GamePanel.BUTTON_X, 190), 25, ShadowLife.SKIN_PACK_FOLDER +"backToMenu.png");
    }

    @Override
    public void doTask() {
        ShadowLife.reset();
        GamePanel.timeScale = 1;
    }
}
