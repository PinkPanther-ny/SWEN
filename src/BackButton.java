import bagel.util.Point;

public class BackButton extends AbstractButton{
    public BackButton() {
        super(new Point(990, 190), 25, "res/images/backToMenu.png");
    }

    @Override
    public void doTask() {
        ShadowLife.reset();
        GamePanel.timeScale = 1;
    }
}
