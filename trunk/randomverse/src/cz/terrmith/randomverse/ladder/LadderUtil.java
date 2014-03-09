package cz.terrmith.randomverse.ladder;

import cz.terrmith.randomverse.Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TERRMITh
 * Date: 9.3.14
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class LadderUtil {

    public static final String FILE_NAME = "C:\\randomverse_ladder.txt";
    public static final String SEPARATOR = "#";

    public static void writeToFile(String name, Player player) {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
            writer.write(name + SEPARATOR + player.getMoney() + "\n");
        } catch (IOException ex) {
            System.out.println("writeToFile failure");
            ex.printStackTrace();
        } finally {
            try {writer.close();} catch (Exception ex) {}
        }
    }

    public static List<LadderEntry> readFromFile() {
        BufferedReader reader = null;
        List<LadderEntry> ret = new ArrayList<LadderEntry>();

        try {
            reader = new BufferedReader(new FileReader(FILE_NAME));
            String line = reader.readLine();
            while (line != null) {
                String[] splitted = line.split(SEPARATOR);
                ret.add(new LadderEntry(splitted[0], Integer.valueOf(splitted[1])));
                line = reader.readLine();
            }
        } catch (IOException ex) {
            System.out.println("readFromFile failure");
            ex.printStackTrace();
        } finally {
            try {reader.close();} catch (Exception ex) {}
        }

        return ret;
    }
}
