import bagel.util.Point;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class RandomWorldGenerator {

    public static final String OUTPUT_FILE = ShadowLife.RANDOM_FILENAME;

    public static final int WID_WORLD = ShadowLife.GAME_WINDOW_WID;
    public static final int HEI_WORLD = ShadowLife.GAME_WINDOW_HEI;
    public static final int TILE_WEI = ShadowLife.TILE_WID;

    public static final int X_TILES = WID_WORLD/TILE_WEI;
    public static final int Y_TILES = HEI_WORLD/TILE_WEI;

    public static double ACTOR_DENSITY = 0.6;
    public static int TOTAL_ACTORS = (int)(X_TILES * Y_TILES * ACTOR_DENSITY);

    public static final String[] ALL_Actors = {
            "Gatherer",
            "Thief",
            "Pool",
            "Pad",
            "Stockpile",
            "Hoard",
            "Fence",
            "Tree",
            "GoldenTree",
            "SignUp",
            "SignRight",
            "SignDown",
            "SignLeft"

    };

    public static void GenerateCSV(){
        GenerateCSV(0.1, OUTPUT_FILE);

    }

    public static void GenerateCSV(double density, String fileName){
        setActorDensity(density);
        ArrayList<Point> locations = new ArrayList<>();
        int whichActor;
        Point location;

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(fileName))) {
            pw.printf("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw =
                     new PrintWriter(new FileWriter(fileName, true))) {

            for(int i=0;i<TOTAL_ACTORS;i++){
                whichActor = (int)(Math.random()*ALL_Actors.length);
                location = new Point((int)(Math.random() * X_TILES) * TILE_WEI, (int)(Math.random() * Y_TILES) * TILE_WEI);
                if(!locations.contains(location)){

                    pw.printf("%s,%d,%d\n", ALL_Actors[ whichActor ], (int)location.x, (int)location.y );
                    locations.add(location);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setActorDensity(double actorDensity) {
        ACTOR_DENSITY = actorDensity;
        TOTAL_ACTORS = (int)(X_TILES * Y_TILES * ACTOR_DENSITY);
    }

}
