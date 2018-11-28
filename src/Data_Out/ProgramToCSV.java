package Data_Out;

import Data_Models.DataPoint;
import Data_Models.Legend;
import Data_Models.Program;
import Data_Models.Subject;
import com.opencsv.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProgramToCSV {
    CSVWriter csvWriter = null;

    public ProgramToCSV(Program program) {
        openFile("test1");
        generateLegend();
        generateTitle();
        writeToFile(program);

    }

    public void openFile(String filepath) {
        CSVWriter csvWriter = null;
        try {
            csvWriter = new CSVWriter(new FileWriter(filepath), '\t');
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
        Legend session = new Legend("Session");
        session.addPoint("1", "mom");
        session.addPoint("2", "dad");
        Legend session2 = new Legend("Session");
        session.addPoint("1", "mom");
        session.addPoint("2", "dad");
        legends.add(session);
        legends.add(session2);
        return legends;
    }

    public void writeToFile(Program program) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        ArrayList<Legend> legends = generateLegend();
        for (HashMap.Entry<Integer, Subject> subjects : program.getSubjects().entrySet()) {
            for (Map.Entry<Integer, ArrayList<DataPoint>> workouts : subjects.getValue().getWorkouts().entrySet()) {
                for (DataPoint dp : workouts.getValue()) {
                    for (int i = 0; i < dp.getRepSmoothness().length; i++) {
                        String[] out = new String[12];
                        out[0] = "" + dp.getParticipantNumber();
                        out[1] = "" + dp.getHand();
                        out[2] = "" + dp.getTime();
                        out[3] = "" + program.getProgramNumberKey();
                        out[4] = "" + 0;
                        out[5] = "" + 0;
                        out[6] = "" + dp.getActivity();
                        out[7] = "" + i;
                        out[8] = "" + dp.getRepSmoothness()[i];
                        out[9] = "" + dp.getRepDuration()[i];
                        out[10] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(dp.getTime()));
                        out[11] = ""+ dp.getTime();
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
