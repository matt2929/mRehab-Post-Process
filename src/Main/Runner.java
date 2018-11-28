package Main;

import Activity.SensorActivityToJerk;
import Data_Models.DataPoint;
import Data_Out.GenerateDataPoint;
import Data_Models.Program;
import Values.FileLocationConstants;
import Values.WorkoutData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Runner {
    static HashMap<String, Double> errorSum = new HashMap<>();
    static HashMap<String, Integer> completedActivityCount = new HashMap<>();
    static int good_Count = 0;
    static int total_Count = 0;
    static Program[] studies = new Program[]{new Program("Baseline",0), new Program("Full",1), new Program("Final",2)};
    public static GenerateDataPoint generateDataPoint = new GenerateDataPoint();

    public static void main(String[] args) {
        ArrayList<File> files = getAllLocalFiles();
        for (int i = 0; i < files.size(); i++) {
            SensorActivityToJerk sensorActivityToJerk = new SensorActivityToJerk();
            if (!files.get(i).isDirectory() && sensorActivityToJerk.LoadCSV(files.get(i))) {
                for (String str : WorkoutData.ValidNames) {
                    if (sensorActivityToJerk.getCsvScraper().getWorkoutName().contains(str)) {
                        sensorActivityToJerk.RunCSV();
                        calculateError(sensorActivityToJerk);
                        filterBySession(sensorActivityToJerk);
                        sortData();
                        createCSV();
                        break;
                    }
                }
                // System.out.println("Valid");
            } else {
                //  System.out.println("Not Valid!");
            }

        }
        for (HashMap.Entry<String, Double> entry : errorSum.entrySet()) {
            System.out.println("Results: " + entry.getKey() + ": " + (entry.getValue() / (double) completedActivityCount.get(entry.getKey())) + "%");

        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Good: [" + good_Count + "]");
        System.out.println("Total:[" + total_Count + "]");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * @return ArrayList of files in AWS folder.
     */
    private static ArrayList<File> getAllLocalFiles() {
        File folder = new File(FileLocationConstants.Home_Adress);
        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return allFiles;
    }

    public static double error(double expected, double actual) {
        return (Math.abs(expected - actual) / expected) * 100;
    }

    public static void calculateError(SensorActivityToJerk sensorActivityToJerk) {
        if (sensorActivityToJerk.getCsvScraper().getReps() == sensorActivityToJerk.get_CurrentWorkout().getProgress()) {

        } else {
            System.out.println(sensorActivityToJerk.getCsvScraper().getReps() + " vs. " + sensorActivityToJerk.get_CurrentWorkout().getProgress());

        }
        total_Count++;
        if (!errorSum.containsKey(sensorActivityToJerk.getCsvScraper().getWorkoutName())) {
            errorSum.put(sensorActivityToJerk.getCsvScraper().getWorkoutName(), error(sensorActivityToJerk.getCsvScraper().getReps(), sensorActivityToJerk.get_CurrentWorkout().getProgress()));
            completedActivityCount.put(sensorActivityToJerk.getCsvScraper().getWorkoutName(), 1);
        } else {
            errorSum.put(sensorActivityToJerk.getCsvScraper().getWorkoutName(), errorSum.get(sensorActivityToJerk.getCsvScraper().getWorkoutName()) + error(sensorActivityToJerk.getCsvScraper().getReps(), sensorActivityToJerk.get_CurrentWorkout().getProgress()));
            completedActivityCount.put(sensorActivityToJerk.getCsvScraper().getWorkoutName(), completedActivityCount.get(sensorActivityToJerk.getCsvScraper().getWorkoutName()) + 1);
        }
    }

    public static void filterBySession(SensorActivityToJerk sensorActivityToJerk) {
        String activityName = sensorActivityToJerk.getCsvScraper().getFileName();
        int sessionNumber = -1;
        if (activityName.toLowerCase().contains("baseline")) {
            sessionNumber = 0;
        } else if (activityName.toLowerCase().contains("full")) {
            sessionNumber = 1;
        } else if (activityName.toLowerCase().contains("final")) {
            sessionNumber = 2;
        }
        if (sessionNumber != -1 && (activityName.charAt(0) == 's' || activityName.split("_")[1].charAt(0) == 's')) {
            generateDataPoint.infoIn(sensorActivityToJerk, sessionNumber);
            DataPoint dataPoint = generateDataPoint.getDataPoint();
            studies[sessionNumber].dataPointIn(dataPoint);
        }
    }

    public static void sortData(){
        for(int i =0; i< studies.length;i++){
            studies[i].sortAllPeople();
        }
    }

    public static void createCSV(){

    }
}
