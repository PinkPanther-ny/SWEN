import bagel.Image;
import bagel.Input;
import bagel.MouseButtons;
import bagel.util.Point;

public abstract class AbstractButton {
    protected final Point buttonLocation;
    protected final double buttonRadius;
    private final Image buttonImage;

    public AbstractButton(Point buttonLocation, double buttonRadius, String filePath) {
        this.buttonLocation = buttonLocation;
        this.buttonRadius = buttonRadius;
        this.buttonImage = new Image(filePath);
    }

    public boolean clicked(Input input){
        return input.isDown(MouseButtons.LEFT) && buttonLocation.distanceTo(input.getMousePosition()) < buttonRadius;
    }

    public void doTask(){
        System.out.println(this.getClass().toString() + " button is clicked!");
    }

    public final void draw(){
        buttonImage.draw(buttonLocation.x, buttonLocation.y);
    }
}
