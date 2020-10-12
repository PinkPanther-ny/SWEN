import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

import java.util.ArrayList;

public class StartMenu {
    private ArrayList<Actor> actors = new ArrayList<>();
    private final ArrayList<String> directories;
    private static final int fontHei = 26;
    private static final Font MAP_SELECT_FONT = new Font(ShadowLife.FONT_FILE_FOLDER +"dpcomic.ttf", fontHei);

    private static final int titleHei = 48;
    private static final String title = "ShadowLife Game";
    private static final Font titleFont = new Font(ShadowLife.FONT_FILE_FOLDER +"lilliput steps.ttf", titleHei);

    private static final int titleHei1 = 30;
    private static final String title1 = "\nCreated by Nuoyan Chen";
    private static final Font titleFont1 = new Font(ShadowLife.FONT_FILE_FOLDER +"lilliput steps.ttf", titleHei1);

    private static final Point FIRST_LINE_LOCATION = new Point(Window.getWidth()*0.25, Window.getHeight()*0.3);
    private static final boolean RANDOM_BG = false;

    private static final Colour GREY = new Colour(0.66275, 0.66275, 0.66275, 0.3);

    StartMenu(ArrayList<String> directories){
        this.directories = directories;
        if(RANDOM_BG) {
            myUtil.readFile(directories.get( (int)(Math.random() * directories.size())), actors);
        }else{
            myUtil.readFile("res/worlds/startMenu", actors);

        }
    }

    public void draw(){

        //actors.forEach(Actor::draw);
        for(Actor actor: actors){
            actor.draw(new DrawOptions().setBlendColour(1,1,1,0.65));
        }

        myUtil.drawCentralizedString(titleFont, title, 0.1);
        myUtil.drawCentralizedString(titleFont1, title1, 0.1);

    }

    public String selectMap(Input input){
        Point drawLocation = new Point(FIRST_LINE_LOCATION.x, FIRST_LINE_LOCATION.y);
        double maxLen=0;
        double shift = -6;
        titleFont1.drawString("Please select map:",drawLocation.x, drawLocation.y - 1.5 * fontHei);
        for(String dir: directories){
            MAP_SELECT_FONT.drawString(dir, drawLocation.x, drawLocation.y+shift, new DrawOptions().setBlendColour(0,0,0));
            drawLocation = new Point(drawLocation.x, drawLocation.y + fontHei);
            if(MAP_SELECT_FONT.getWidth(dir) > maxLen){
                maxLen = MAP_SELECT_FONT.getWidth(dir);
            }
        }
        Point mouse = input.getMousePosition();
        if(mouse.x >= FIRST_LINE_LOCATION.x && mouse.x <= FIRST_LINE_LOCATION.x + maxLen){

            int num = directories.size();
            for(int i=0;i<num;i++){
                if(mouse.y >= FIRST_LINE_LOCATION.y+(i-1)*fontHei && mouse.y < FIRST_LINE_LOCATION.y+(i)*fontHei ){

                    Point topLeft = new Point(FIRST_LINE_LOCATION.x, FIRST_LINE_LOCATION.y+(i-1)*fontHei);
                    myUtil.drawBorder(topLeft, maxLen, fontHei, 1, Colour.BLUE);
                    Drawing.drawRectangle(topLeft, maxLen, fontHei, GREY);
                    actors = new ArrayList<>();
                    myUtil.readFile(directories.get(i), actors);
                    if(input.wasReleased(MouseButtons.LEFT)){

                        return directories.get(i);
                    }

                    break;
                }
            }

        }

        return null;
    }

}
