import bagel.*;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * <p>Main entry of the program, A Simple simulation of a fictional universe</p>
 * <p>This is unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */

public class ShadowLife extends AbstractGame {

    /* Aha! Nice and clean : ) */
    /** See my customisation whenever you want, just turn on the boolean */
    public static final boolean SHADOWLIFE_CUSTOMISATION = true;

    public static final int TILE_WID = 64;
    /** Simulation background */
    public static final String backgroundFile = ShadowLife.IMAGE_FILE_FOLDER +"background.png";

    private static int tickCount = 0;
    private static Timer timer;

    private static ArrayList<Actor> actors;
    private final Image background;

    // Debug thing..
    public static boolean SHOW_GRID = false;
    public static String CURRENT_SAVING_FILENAME;
    public static final String DATAFILE_EXTENSION = "csv";
    public static final String DATAFILE_FOLDER = "res/";
    public static boolean SHOW_DATAFILES_DIRS = false;
    public static final boolean SHOW_CSV = false;
    public static final boolean EXIT_ON_NO_ACTIVE = false;

    /* Project Customisation Attributes */
    private static final int maxTicks = 1000;
    private static final int tick = 500; // in milliseconds
    private static boolean isGameStart = false;
    private static StartMenu startMenu;
    private final GamePanel gamePanel = new GamePanel();
    private static ArrayList<String> directories = new ArrayList<>();

    private static final String FILE_EXTENSION = ".csv";
    private static final String SAVED_FILE_EXTENSION = "_savedProgress.csv";

    public static final String RANDOM_FILENAME = "res/worlds/AN_RANDOM_ACTORS_PARTY.csv";
    private static final Timer RANDOM_GENERATOR_TIMER = new Timer(1200, false);

    public static final int GAME_WINDOW_WID = 1024;
    public static final int GAME_WINDOW_HEI = 768;
    public static final String IMAGE_FILE_FOLDER = "res/images1/";
    public static final String FONT_FILE_FOLDER = "res/font/";

    public ShadowLife() {
        super(GAME_WINDOW_WID, GAME_WINDOW_HEI, "ShadowLife NuoyanChen");
        RandomWorldGenerator.GenerateCSV();
        background = new Image(backgroundFile);
        actors = new ArrayList<>();
        directories = myUtil.listAllFile(DATAFILE_EXTENSION, DATAFILE_FOLDER);

        startMenu = new StartMenu(directories);
        timer = new Timer(tick, false);
    }

    /**
     * The entry point for the program.
     * @param args commandline arguments
     */
    public static void main(String[] args) {

        ShadowLife shadowLife = new ShadowLife();
        // Remove throttle in case of the tick time less than 1/60 seconds
        Window.removeFrameThrottle();
        shadowLife.run();
    }

    @Override
    public void update(Input input) {

        if(tickCount >= maxTicks){
            myUtil.exitTimeOut();
        }

        //background.drawFromTopLeft(0,0);

        // Stretch by window size
        background.draw(Window.getWidth()*0.5,Window.getHeight()*0.5, new DrawOptions().setScale( GAME_WINDOW_WID / background.getWidth(), GAME_WINDOW_HEI / background.getHeight() ));




        if(!isGameStart){

            if(RANDOM_GENERATOR_TIMER.isCool()){
                RandomWorldGenerator.GenerateCSV(0.4 * Math.random(), RANDOM_FILENAME);
            }

            startMenu.draw();
            CURRENT_SAVING_FILENAME = startMenu.selectMap(input);

            if(CURRENT_SAVING_FILENAME!=null){

                myUtil.readFile(CURRENT_SAVING_FILENAME, actors);

                if(!CURRENT_SAVING_FILENAME.contains(SAVED_FILE_EXTENSION)){
                    CURRENT_SAVING_FILENAME = CURRENT_SAVING_FILENAME.replace(FILE_EXTENSION, SAVED_FILE_EXTENSION);
                }

                isGameStart = true;

            }else{
                return;
            }
        }

        if(timer.isCool()) {
            boolean haveActiveActor = false;
            /* Store the size in case of mitosis pool modify the number*/
            int len = actors.size();
            /* Follow the spec, check thief "after the gatherer’s tick has been performed" */
            for (int i = 0; i < len; i++) {
                Actor actor = actors.get(i);
                if (actor instanceof Gatherer && ((MobileActor) actor).isActive()) {
                    ((MobileActor) actor).update(actors);
                    haveActiveActor = true;
                }
            }
            for (int i = 0; i < len; i++) {
                Actor actor = actors.get(i);
                if (actor instanceof Thief && ((MobileActor) actor).isActive()) {
                    ((MobileActor) actor).update(actors);
                    haveActiveActor = true;
                }
            }
            if(!haveActiveActor && EXIT_ON_NO_ACTIVE){
                myUtil.exitGame( tickCount, actors);
            }
            tickCount++;
        }


        /* Movable actors drawn at top layer */
        actors.sort(Comparator.comparingInt(a -> ((a instanceof MobileActor) ? 1 : -1)));
        actors.forEach(Actor::draw);

        /* Project Customisation Methods */
        if(SHADOWLIFE_CUSTOMISATION) {
            gamePanel.update(input, actors, tickCount, maxTicks, timer);
        }

    }


    public static void reset(){
        tickCount = 0;
        isGameStart = false;
        actors = new ArrayList<>();
        directories = myUtil.listAllFile(DATAFILE_EXTENSION, DATAFILE_FOLDER);
        startMenu = new StartMenu(directories);
        timer = new Timer(tick, false);
    }
}
