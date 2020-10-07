import bagel.DrawOptions;
import bagel.Drawing;
import bagel.Font;
import bagel.Window;
import bagel.util.Colour;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>This is Utility class for unimelb SWEN20003 2020s2 assignment 2</p>
 * <p>Simple simulation of a fictional universe</p>
 * <p>Created on 9/18/2020</p>
 * @author NuoyanChen (nuoyanc@student.unimelb.edu.au)
 */
public final class myUtil {
    private static final Font gameFont = new Font("res/font/VeraMono.ttf", 24);
    private static final Font LOG_FONT = new Font("res/font/VeraMono.ttf", 16);

    /**
     * Reading a csv to Arraylist of Actors
     * @param datafile The source data file for creating the simulation map
     * @param actors List reference that store all actors
     * */
    public static void readFile(String datafile, ArrayList<Actor> actors){
        int lineCount = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(datafile))) {
            String text;
            String[] fields;

            while ((text = br.readLine()) != null) {

                fields = text.split(",");

                actors.add(
                        ActorFactory.getActor(
                                fields[0],
                                Double.parseDouble(fields[1]),
                                Double.parseDouble(fields[2])
                        )
                );
                if(ShadowLife.SHOW_CSV) { System.out.printf("Line %s: %s\n", lineCount, text); }
                lineCount++;
            }

        } catch (IOException | InvalidActorNameException e) {
            System.out.printf("In %s line: %d\n", datafile, lineCount);
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /** Draw the grid for debugging purpose */
    public static void showGrid(){
        double tileWidth = ShadowLife.TILE_WID;
        Colour colour = new Colour(0,0,0,1);
        double thickness = 1;
        for (int i = 0; i < Window.getWidth() / tileWidth; i++) { //16
            for (int j = 0; j < Window.getHeight() / tileWidth; j++) { //12
                Drawing.drawLine(new Point(i, tileWidth * j), new Point(Window.getWidth(), tileWidth * j), thickness, colour);
            }
            Drawing.drawLine(new Point(tileWidth * i, 0), new Point(tileWidth * i, Window.getHeight()), thickness, colour);
        }
    }

    /**
     * Terminate current running simulation
     * when tickCount exceeds maxTicks
     * */
    public static void exitTimeOut(){
        System.out.println("Timed out");
        System.exit(-1);
    }

    /**
     * Terminate current running simulation
     * if commandline arguments are invalid
     * */
    public static void exitInvalidDirs(){
        System.out.printf("Fatal: Cannot find any <%s> file under the <%s> directory, program aborted.",
                ShadowLife.DATAFILE_EXTENSION, ShadowLife.DATAFILE_FOLDER);
        System.exit(-1);
    }

    /**
     * Terminate current running simulation normally.
     * And print the amount of fruit at each stockpile and hoard,
     * in the order they appear in the world file.
     * @param tickCount The number of ticks the simulation went through
     * @param actors List reference to all actors
     * */
    public static void exitGame(int tickCount, ArrayList<Actor> actors){
        System.out.println(tickCount + " ticks");
        for(Actor actor: actors){
            if(actor instanceof Depot){
                System.out.println(((Depot) actor).getFruitNum());
            }
        }
        System.exit(0);
    }

    /**
     * Draw a number at given location
     * @param num The number will be drawn
     * @param location The location that number will be drawn
     * */
    public static void drawNumber(int num, Point location) {
        int yLocationSift = 18;
        gameFont.drawString(Integer.toString(num), location.x, location.y+yLocationSift);
    }

    public static ArrayList<String> listAllFile(String fileExtension, String dataFolder){
        ArrayList<String> directories = new ArrayList<>();
        Path start = Paths.get(dataFolder);

        try (Stream<Path> paths = Files.walk(start, 5)) {
            List<String> collect = paths.map(Path::toString).filter(file -> file.endsWith("." + fileExtension.toLowerCase()))
                    .collect(Collectors.toList());
            directories.addAll(collect);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(directories.size()==0){
            myUtil.exitInvalidDirs();
        }

        if(ShadowLife.SHOW_DATAFILES_DIRS){
            directories.forEach(System.out::println);
        }

        return directories;
    }



    public static void drawCentralizedString(String string, double percentHeight){
        drawCentralizedString(string, percentHeight, new DrawOptions().setBlendColour(1,1,1));
    }

    public static void drawCentralizedString(Font logFont, String string, double percentHeight){
        drawCentralizedString(logFont, string, percentHeight, new DrawOptions().setBlendColour(1,1,1));
    }

    public static void drawCentralizedString(String string, double percentHeight, DrawOptions drawOptions){
        drawCentralizedString(LOG_FONT, string, percentHeight, drawOptions);
    }

    public static void drawCentralizedString(Font logFont, String string, double percentHeight, DrawOptions drawOptions){
        logFont.drawString(string, Window.getWidth()/2.0 - logFont.getWidth(string)/2.0, Window.getHeight() * percentHeight, drawOptions );
    }




    /* Project Customisation Methods */
    public static void drawBorder(Point barTopLeft, double wid, double hei, double thickness){

        Colour black = new Colour(0, 0, 0, 1);
        drawBorder(barTopLeft, wid, hei, thickness, black);
    }

    /* Project Customisation Methods */
    public static void drawBorder(Point barTopLeft, double wid, double hei, double thickness, Colour colour){

        Point borderTopLeft = new Point(barTopLeft.x-thickness/2.0, barTopLeft.y);
        Point borderBottomLeft = new Point(barTopLeft.x-thickness/2.0, barTopLeft.y + hei+thickness/2.0);
        Point borderTopRight = new Point(barTopLeft.x + wid+thickness/2.0, barTopLeft.y);
        Point borderBottomRight = new Point(barTopLeft.x + wid+thickness/2.0, barTopLeft.y + hei+thickness/2.0);

        Drawing.drawLine(new Point(borderTopLeft.x + thickness/2.0, borderTopLeft.y), new Point(borderBottomLeft.x + thickness/2.0, borderBottomLeft.y), thickness, colour);
        Drawing.drawLine(new Point(borderTopRight.x - thickness/2.0, borderTopRight.y), new Point(borderBottomRight.x - thickness/2.0, borderBottomRight.y), thickness, colour);
        Drawing.drawLine(borderTopLeft, borderTopRight, thickness, colour);
        Drawing.drawLine(borderBottomLeft, borderBottomRight, thickness, colour);

    }


}
