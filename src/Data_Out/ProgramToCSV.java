package Data_Out;

import Data_Models.*;
import Utilities.WorkoutNumberConversion;
import com.opencsv.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProgramToCSV {
    CSVWriter csvWriter = null;
    LegendWriter legendWriter;
    DataFilter dataFilter;

    public ProgramToCSV(String filename, Program program, DataFilter dataFilter) {
        System.out.println("Start CSV Generation: "+ filename);
        try {
            File file = new File(filename.split("/")[0]);
            if(file.exists()){
                file.delete();
            }
            file.mkdir();
            file = new File(filename);
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openFile(filename);
        generateTitle();
        this.dataFilter = dataFilter;
        writeToFile(program, new LegendWriter(generateLegend()));
        closeFile();
        System.out.println("End CSV Generation: "+ filename);
    }

    public void openFile(String filepath) {
        try {
            csvWriter = new CSVWriter(new FileWriter(filepath), ',');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateTitle() {
        String[] out = new String[]{
                "Participant #",
                "Hand",
                "Date",
                "Program",
                "Exercise Day",
                "Exercise Week",
                "Activity",
                "Rep Number",
                "Rep Smoothness",
                "Rep Duration",
                "Date MS",
                "Date Long"};
        csvWriter.writeNext(out);
    }

    public ArrayList<Legend> generateLegend() {
        ArrayList<Legend> legends = new ArrayList<>();
        Legend hand = new Legend("Hand");
        hand.addPoint("0", "Left");
        hand.addPoint("1", "Right");
        hand.addPoint("1", "Right");

        Legend user = new Legend("Activity");
        WorkoutNumberConversion workoutNumberConversion = new WorkoutNumberConversion();
        for(String str: workoutNumberConversion.getWorkouts()){
            user.addPoint(""+workoutNumberConversion.nameToInt(str),str);
        }

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
                        if (dataFilter.Filter(dp)) {
                            long time = dp.getTime();
                            time += addation;
                            addation = dp.getRepDuration().get(dataPointIndex);
                            String[] out = new String[16];
                            out[0] = "" + dp.getParticipantNumber();
                            out[1] = "" + dp.getHand();
                            out[2] = "" + time;
                            out[3] = "" + program.getProgramNumberKey();
                            out[4] = "" + dp.getExerciseDay();
                            out[5] = "" + dp.getExerciseWeek();
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
    }

    public void closeFile() {
        try {
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
