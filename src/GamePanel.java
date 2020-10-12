import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * <b>This is the GamePanel of the simulation, the customisation part</b>
 * <p>However, this is not my whole customisation, since I decided to eliminate the commandline arguments </p>
 * <p>when I finished current part of the customisation and went on to further customisation </p>
 * <p>But anyway this one still has some cool feature, and you can turn on/off the customisation by </p>
 * <p>switching on the boolean value SHADOWLIFE_CUSTOMISATION in ShadowLife.java </p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public class GamePanel {

    private boolean isSelect;
    private Point selectedTile;
    private boolean canPlace = true;
    private double last_add_time = System.currentTimeMillis();

    public static double timeScale = 1;

    private static double SAVE_PRE_TIME = 0;
    private static final double SAVE_DURATION = 4000;
    private static final int SAVE_HEI = 28;
    private static final String SAVE_LOG = "CURRENT GAME STATUS SAVED TO \n";
    private static final Font SAVE_FONT = new Font(ShadowLife.FONT_FILE_FOLDER +"lilliput steps.ttf", SAVE_HEI);


    /* ---------------------------- IMAGE SETTINGS ---------------------------- */
    public static final double BUTTON_X = 0.967 * Window.getWidth();
    private static final AbstractButton questionButton = new QuestionButton();
    private static final AbstractButton backButton = new BackButton();
    private static final AbstractButton saveButton = new SaveButton();

    private static boolean SHOW_PANEL = false;
    private static final Image PANEL = new Image(ShadowLife.IMAGE_FILE_FOLDER +"panel.png");

    /* ---------------------------- DIALOG SETTINGS ---------------------------- */
    private static final double lineSpacing = 0.035;
    private static final double LOG_HEI = 0.9;
    private static final double LOG_SPACING = 0.025;
    private static final double PANEL_HEI = 75;
    private static final double ICON_WID = 48;

    private static final Font INS_FONT = new Font(ShadowLife.FONT_FILE_FOLDER +"VeraMono.ttf", 22);

    private static final double INS_START_HEI = 0.3;
    private static final String[] INSTRUCTIONS = {
            "This is a simulation game created by Nuoyan Chen",
            "Press Space to pause/continue",
            "Press S to sell everything in selected tile",
            "Press K/L to speed UP/DOWN the simulation",
            "Press F to save current frame to its copy",
            "Press Z to unselect the tile",
            "Hold G to show grid",
            "HAVE FUN!!!"
    };

    private static final Font SAYING_FONT_NAME = new Font(ShadowLife.FONT_FILE_FOLDER +"yoster.ttf", 19);
    private static final String[] MY_SAYINGS = {
            "So.. Do you believe in God?",
            "Are you sure you are not one of them? \nAn actor have your own algorithm?",
            "Sometimes he did send them around you, \nor take them away",
            "Living without purpose is dangerous.",
            "Simulation of life? No you can't.",
            "Once the actor goes out of the screen, \nyou can never create a same one.",
            "They are identical but unique.",
            "Aha, you yourself extends AbstractActor!",
            "Not fair, you close the game, they lose all apples",
            "I can let you save current frame, \nbut not thieves' apples",
            "Nah, they're not gonna follow your algorithm \nwhen you don't make observation",
            "Keep an eye on it.",
            "Override method in AbstractHuman class, please do so.",
            "The quick brown fox jumps over the lazy dog.",
            "People who insist on picking their teeth \nwith their elbows are so annoying!",

    };
    private static int SAYING_INDEX = (int)(Math.random() * MY_SAYINGS.length);
    private static final double SAYING_MAX_DUR = 6000;
    private static final double SAYING_MIN_DUR = 4000;
    private static double SAYING_DUR = (SAYING_MAX_DUR - SAYING_MIN_DUR) * Math.random() + SAYING_MIN_DUR;

    private static final double SAYING_UNSHOW_MAX_DUR = 1200;
    private static final double SAYING_UNSHOW_MIN_DUR = 700;

    private static double SAYING_PRE_TIME = System.currentTimeMillis();
    private static boolean SHOW_SAYING = (int) (Math.random() + 0.1) == 1;

    private static final Timer colorTimer = new Timer(300, true);
    private static final DrawOptions[] drawOptions = new DrawOptions[8];

    private static final String PLACE_LOG = "You can place actor by clicking panel or using shortcut key";
    private static final String WARN_LOG = "You cannot place at current selected tile!";
    private static final String SELL_LOG = "Press S to sell everything in this tile";
    private static final Image PAUSE = new Image(ShadowLife.IMAGE_FILE_FOLDER +"pause.png");
    
    /* ---------------------------- HOURGLASS SETTINGS ---------------------------- */
    private static final String HG_LOG = "This is an Hourglass";
    private static final double HG_THICKNESS = 5;
    private static final double HG_TRANSPARENCY = 0.75;
    private static final double HG_DETECT_RADIUS = 90;
    private static final double HG_WID = 20, HG_HEI = 120;
    private static final Point HG_TOPLEFT = new Point(0.96 * Window.getWidth(), 0.82 * Window.getHeight());

    private static final Colour HG_FULL_COLOR = new Colour(0, 0, 1, HG_TRANSPARENCY);
    private static final Colour HG_EMPTY_COLOR = new Colour(1, 0, 0, HG_TRANSPARENCY);

    private static final Point TS_LOCATION = new Point(HG_TOPLEFT.x - 197, HG_TOPLEFT.y + 106);
    private static final Font TS_FONT = new Font(ShadowLife.FONT_FILE_FOLDER +"VeraMono.ttf", 14);

    
    GamePanel(){
        reset();
    }

    private void reset(){
        canPlace = true;
        isSelect = false;
        selectedTile = null;
    }

    public void update(Input input, ArrayList<Actor> actors, int tickCount, int maxTicks, Timer timer){

        boolean canplace = true;
        for (Actor actor: actors){
            if(actor.getLocation().equals(selectedTile)){
                canplace = false;
                break;
            }
        }
        this.canPlace = canplace;

        if(isSelect && canPlace && addActor(selectedTile, actors, input)){
            reset();
        }

        if(input.wasReleased(Keys.S)){
            ArrayList<Actor> removeList = new ArrayList<>();

            actors.forEach(actor->{
                if (actor.getLocation().equals(selectedTile)) {
                    removeList.add(actor);
                }
            });

            removeList.forEach(actors::remove);

        }

        Point mouse = input.getMousePosition();
        if(System.currentTimeMillis() - last_add_time >100 && input.wasReleased(MouseButtons.LEFT)){
            double tileWid = ShadowLife.TILE_WID;
            canPlace = true;
            isSelect = true;
            
            /* Do the auto align */
            selectedTile = new Point((int)(mouse.x/tileWid)*tileWid, (int)(mouse.y/tileWid)*tileWid);

        }else if(isSelect){
            if(input.wasReleased(MouseButtons.RIGHT)){
                reset();
                SHOW_PANEL = false;
            }
        }

        this.keyboardOperation(input, timer, actors);
        drawHourglass((double) tickCount / maxTicks, tickCount, maxTicks, timer, input);

        if(saveButton.clicked(input)){
            GamePanel.saveCurrent(actors, ShadowLife.CURRENT_SAVING_FILENAME);
        }
        draw(input);

    }

    public void draw(Input input){

        if(SHOW_SAYING) {
            myUtil.drawCentralizedString(SAYING_FONT_NAME, MY_SAYINGS[SAYING_INDEX], LOG_HEI - 0.1);
        }
        if(System.currentTimeMillis() - SAYING_PRE_TIME > SAYING_DUR){
            SAYING_INDEX = (int)(Math.random() * MY_SAYINGS.length);
            SAYING_DUR = (SAYING_MAX_DUR - SAYING_MIN_DUR) * Math.random() + SAYING_MIN_DUR;
            SHOW_SAYING = (int) (Math.random() + 0.3) == 1;
            if(!SHOW_SAYING){
                SAYING_DUR = (SAYING_UNSHOW_MAX_DUR - SAYING_UNSHOW_MIN_DUR) * Math.random() + SAYING_UNSHOW_MIN_DUR;
            }

            SAYING_PRE_TIME = System.currentTimeMillis();
        }


        questionButton.draw();
        backButton.draw();
        saveButton.draw();

        if(questionButton.clicked(input)){
            if(colorTimer.isCool()) {

                for (int i = 0; i < drawOptions.length; i++) {
                    drawOptions[i] = (new DrawOptions().setBlendColour(0.9*Math.random(), 0.2*Math.random(), 0.8*Math.random() ) );
                }
            }

            for(int i = 0; i< INSTRUCTIONS.length; i++){
                myUtil.drawCentralizedString(INS_FONT, INSTRUCTIONS[i], INS_START_HEI + i * lineSpacing, drawOptions[i]);
            }

        }else if(isSelect) {
            if(canPlace) {
                myUtil.drawBorder(selectedTile, ShadowLife.TILE_WID, ShadowLife.TILE_WID, 1, Colour.WHITE);
                myUtil.drawCentralizedString(PLACE_LOG, LOG_HEI);
            }else {
                myUtil.drawBorder(selectedTile, ShadowLife.TILE_WID, ShadowLife.TILE_WID, 1, Colour.RED);
                myUtil.drawCentralizedString(WARN_LOG, LOG_HEI, new DrawOptions().setBlendColour(1,0,0));
                myUtil.drawCentralizedString(SELL_LOG, LOG_HEI + LOG_SPACING);
            }
            SHOW_PANEL = true;
        }

        if(backButton.clicked(input)){
            RandomWorldGenerator.GenerateCSV();
            backButton.doTask();
        }


        if(SHOW_PANEL){
            PANEL.drawFromTopLeft(0,0);
        }

    }

    public void sellSelected(ArrayList<Actor> actors){
        ArrayList<Actor> removeList = new ArrayList<>();

        for(Actor actor: actors){
            if (actor.getLocation().equals(selectedTile)){
                removeList.add(actor);
            }
        }
        for(Actor actor: removeList){
            actors.remove(actor);
        }
    }

    private void keyboardOperation(Input input, Timer timer, ArrayList<Actor> actors){

        ShadowLife.SHOW_GRID = input.isDown(Keys.G);
        if (ShadowLife.SHOW_GRID) {
            myUtil.showGrid();
        }

        if (input.isDown(Keys.F)){
            saveCurrent(actors, ShadowLife.CURRENT_SAVING_FILENAME);
        }

        if(System.currentTimeMillis() - SAVE_PRE_TIME < SAVE_DURATION){
            myUtil.drawCentralizedString(SAVE_FONT, SAVE_LOG + ShadowLife.CURRENT_SAVING_FILENAME, 0.25, new DrawOptions().setBlendColour(0.1, 0, 1));
        }

        if(input.wasReleased(Keys.Z)){
            reset();
        }

        if(input.wasReleased(Keys.S)){
            this.sellSelected(actors);
        }

        if(input.wasReleased(Keys.K)){
            GamePanel.timeScale *= 1.1;
            timer.scale(1.1);
        }

        if(input.wasReleased(Keys.L)){
            GamePanel.timeScale *= 0.9;
            timer.scale(0.9);
        }

        if(input.wasReleased(Keys.SPACE)){
            timer.pauseTimer();
        }


    }

    public boolean addActor(Point location, ArrayList<Actor> actors, Input input){
        Point mouse = input.getMousePosition();
        boolean isClick = input.wasReleased(MouseButtons.LEFT);
        boolean validY = isClick && mouse.y < PANEL_HEI;


        if (input.wasReleased(Keys.NUM_1) || (validY && mouse.x<ICON_WID * 1)){

            actors.add(new Gatherer(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_2) || (validY && mouse.x<ICON_WID * 2)){

            actors.add(new Thief(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_3) || (validY && mouse.x<ICON_WID * 3)){

            actors.add(new MitosisPool(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_4) || (validY && mouse.x<ICON_WID * 4)){

            actors.add(new Pad(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_5) || (validY && mouse.x<ICON_WID * 5)){

            actors.add(new Stockpile(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_6) || (validY && mouse.x<ICON_WID * 6)){

            actors.add(new Hoard(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_7) || (validY && mouse.x<ICON_WID * 7)){

            actors.add(new Tree(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_8) || (validY && mouse.x<ICON_WID * 8)){

            actors.add(new GoldenTree(location.x, location.y));
        }else if (input.wasReleased(Keys.NUM_9) || (validY && mouse.x<ICON_WID * 9)){

            actors.add(new Fence(location.x, location.y));
        } else if (input.wasReleased(Keys.NUM_0) || (validY && mouse.x<ICON_WID * 10)){

            actors.add(new Sign(location.x, location.y, 0));
        }else if (input.wasReleased(Keys.A) || (validY && mouse.x<ICON_WID * 11)){

            actors.add(new Sign(location.x, location.y, 1));
        }else if (input.wasReleased(Keys.B) || (validY && mouse.x<ICON_WID * 12)){

            actors.add(new Sign(location.x, location.y, 2));
        }else if (input.wasReleased(Keys.C) || (validY && mouse.x<ICON_WID * 13)){

            actors.add(new Sign(location.x, location.y, 3));
        }else{
            return false;
        }
        last_add_time = System.currentTimeMillis();
        return true;

    }


    private static void drawHourglass(double timeLeft, int tickCount, int maxTicks, Timer timer, Input input){
        String info;
        Point mouse = input.getMousePosition();

        if (timer.isPause()){
            info = "Timer pause\n" + "Run " + tickCount + " of total " + maxTicks + " ticks";
            PAUSE.draw(Window.getWidth()*0.5,Window.getHeight()*0.5);
        }else {
            info = "Timescale : " + Math.round((1 / timeScale) * 100) / 100.0 + "x\n" + "Run " + tickCount + " of total " + maxTicks + " ticks";
        }

        // Draw progress bar
        Drawing.drawRectangle(HG_TOPLEFT, HG_WID,HG_HEI, HG_FULL_COLOR);
        Drawing.drawRectangle(HG_TOPLEFT, HG_WID,HG_HEI * timeLeft, HG_EMPTY_COLOR);

        // Draw rectangular border
        myUtil.drawBorder(HG_TOPLEFT, HG_WID, HG_HEI, HG_THICKNESS);

        // Draw help dialog
        TS_FONT.drawString(info, TS_LOCATION.x, TS_LOCATION.y);
        
        if(mouse.distanceTo(new Point(HG_TOPLEFT.x - TS_FONT.getWidth(HG_LOG), HG_TOPLEFT.y + 0.5*HG_HEI)) <= HG_DETECT_RADIUS){
            TS_FONT.drawString(HG_LOG, mouse.x, mouse.y, new DrawOptions().setBlendColour(0.5,0.1,0.6));

        }

    }


    public static void saveCurrent(ArrayList<Actor> actors, String fileName){

        SAVE_PRE_TIME = System.currentTimeMillis();
        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(fileName))) {
            pw.printf("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(fileName, true))) {
            Point location;
            for(Actor actor: actors){
                location = actor.getLocation();
                if(actor instanceof Gatherer){

                    pw.printf("Gatherer,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Thief){

                    pw.printf("Thief,%f,%f\n",location.x, location.y);
                }else if(actor instanceof MitosisPool){

                    pw.printf("Pool,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Pad){

                    pw.printf("Pad,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Stockpile){

                    pw.printf("Stockpile,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Hoard){

                    pw.printf("Hoard,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Fence){

                    pw.printf("Fence,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Tree){

                    pw.printf("Tree,%f,%f\n",location.x, location.y);
                }else if(actor instanceof GoldenTree){

                    pw.printf("GoldenTree,%f,%f\n",location.x, location.y);
                }else if(actor instanceof Sign){
                    if(((Sign) actor).getDirection() == 0){

                        pw.printf("SignUp,%f,%f\n",location.x, location.y);
                    }else if(((Sign) actor).getDirection() == 1){

                        pw.printf("SignRight,%f,%f\n",location.x, location.y);
                    }else if(((Sign) actor).getDirection() == 2){

                        pw.printf("SignDown,%f,%f\n",location.x, location.y);
                    }else if(((Sign) actor).getDirection() == 3){

                        pw.printf("SignLeft,%f,%f\n",location.x, location.y);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
