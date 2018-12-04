package Data_Out;

import Data_Models.*;
import com.opencsv.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProgramToCSV {
    CSVWriter csvWriter = null;
    LegendWriter legendWriter;

    public ProgramToCSV(String filename, Program program) {
        openFile(filename);
        generateTitle();
        writeToFile(program, new LegendWriter(generateLegend()));
        closeFile();

    }

    public void openFile(String filepath) {
        try {
            csvWriter = new CSVWriter(new FileWriter(filepath), ',');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateTitle() {
        String[] out = new String[]{"Participant #", "Hand", "Date", "Program", "Exercise Day", "Exercise Session", "Activity", "Rep Number", "Rep Smoothness", "Rep Duration", "Date MS", "Date Long"};
        csvWriter.writeNext(out);
    }

    public ArrayList<Legend> generateLegend() {
        ArrayList<Legend> legends = new ArrayList<>();
        Legend hand = new Legend("Hand");
        hand.addPoint("0", "Left");
        hand.addPoint("1", "Right");
        Legend user = new Legend("Activity");
        user.addPoint("0", "Slow Pour");
        user.addPoint("1", "Horizontal Bowl");
        user.addPoint("2", "Horizontal Mug");
        user.addPoint("3", "Vertical Bowl");
        user.addPoint("4", "Vertical Mug");
        user.addPoint("5", "Sip From The Mug");
        user.addPoint("6", "Quick Twist Mug");
        user.addPoint("7", "Sip From The Mug");
        user.addPoint("8", "Slow Pour");
        user.addPoint("9", "Sip From The Mug");
        user.addPoint("10", "Walk with mug");
        Legend activity = new Legend("Subject");
        activity.addPoint("1", "s01");
        activity.addPoint("2", "s02");
        activity.addPoint("3", "s03");
        activity.addPoint("4", "s04");
        activity.addPoint("5", "s05");
        legends.add(activity);
        legends.add(user);
        legends.add(hand);
        return legends;
    }

    public void writeToFile(Program program, LegendWriter lw) {
        for (HashMap.Entry<Integer, Subject> subjects : program.getSubjects().entrySet()) {
            for (Map.Entry<Integer, ArrayList<DataPoint>> workouts : subjects.getValue().getWorkouts().entrySet()) {
                for (DataPoint dp : workouts.getValue()) {
                    long addation = 0;
                    for (int dataPointIndex = 0; dataPointIndex < dp.getRepDuration().size(); dataPointIndex++) {
                        long time = dp.getTime();
                        time += addation;
                        addation = dp.getRepDuration().get(dataPointIndex);
                        String[] out = new String[16];
                        out[0] = "" + dp.getParticipantNumber();
                        out[1] = "" + dp.getHand();
                        out[2] = "" + time;
                        out[3] = "" + program.getProgramNumberKey();
                        out[4] = "" + 0;
                        out[5] = "" + 0;
                        out[6] = "" + dp.getActivity();
                        out[7] = "" + dataPointIndex;
                        out[8] = "" + dp.getRepSmoothness().get(dataPointIndex);
                        out[9] = "" + dp.getRepDuration().get(dataPointIndex);
                        out[10] = new SimpleDateFormat("yyyy-MMM-dd HH:MM:SS").format(new Date(time));
                        out[11] = "" + time;
                        if (lw.hasNext()) {
                            String[] next = lw.next();
                            out[13] = next[0];
                            out[14] = next[1];
                        }
                        csvWriter.writeNext(out);
                    }
                }
            }
        }
    }

    public void closeFile() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
