package Main;

import Activity.SensorActivityToJerk;
import Data_Models.DataPoint;
import Data_Out.DataFilter;
import Data_Out.GenerateDataPoint;
import Data_Models.Program;
import Data_Out.ProgramToCSV;
import Utilities.WorkoutNumberConversion;
import Values.FileLocationConstants;
import Values.WorkoutData;

import java.io.File;
import java.util.*;

public class Runner {
    static HashMap<String, Double> errorSum = new HashMap<>();
    static HashMap<String, Integer> completedActivityCount = new HashMap<>();
    static int good_Count = 0;
    static int total_Count = 0;
    static Program[] studies = new Program[]{new Program("Baseline", 0), new Program("Full", 1), new Program("Final", 2)};
    static Integer[] countNum = new Integer[]{0, 0, 0};
    static Set<Integer> allWorkouts = new HashSet<>();
    static Set<Integer> allSubjects = new HashSet<>();


    public static void main(String[] args) {
        ArrayList<File> files = getAllLocalFiles();
        WorkoutNumberConversion workoutNumberConversion = new WorkoutNumberConversion();
        for (int i = 0; i < files.size(); i++) {
            SensorActivityToJerk sensorActivityToJerk = new SensorActivityToJerk();
            if (!files.get(i).isDirectory() && sensorActivityToJerk.LoadCSV(files.get(i))) {
                for (String str : workoutNumberConversion.getWorkouts()) {
                    if (sensorActivityToJerk.getCsvScraper().getWorkoutName().equals(str)) {
                        sensorActivityToJerk.RunCSV();
                        filterBySession(sensorActivityToJerk);
                        break;
                    }
                }
            }
        }
        for (HashMap.Entry<String, Double> entry : errorSum.entrySet()) {
            System.out.println("Results: " + entry.getKey() + ": " + (entry.getValue() / (double) completedActivityCount.get(entry.getKey())) + "%");

        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Good: [" + good_Count + "]");
        System.out.println("Total:[" + total_Count + "]");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        sortData();
        createCSV();

    }

    /**
     * @return ArrayList of files in AWS folder.
     */
    private static ArrayList<File> getAllLocalFiles() {
        File folder = new File(FileLocationConstants.Home_Adress);
        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return allFiles;
    }

    public static void filterBySession(SensorActivityToJerk sensorActivityToJerk) {
        String activityName = sensorActivityToJerk.getCsvScraper().getFileName();
        GenerateDataPoint generateDataPoint = new GenerateDataPoint();

        int sessionNumber = -1;
        if (activityName.toLowerCase().contains("baseline")) {
            sessionNumber = 0;
        } else if (activityName.toLowerCase().contains("full")) {
            sessionNumber = 1;
        } else if (activityName.toLowerCase().contains("final")) {
            sessionNumber = 2;
        }
        boolean validName = false;
        if (sessionNumber == 1) {
            generateDataPoint.setSpecialNamingCaseUnset(true);
            validName = (((activityName.charAt(0) == 's' || activityName.split("_")[1].charAt(0) == 's') || (activityName.charAt(0) == '_')));

        }
        if (validName) {
            countNum[sessionNumber]++;
            generateDataPoint.infoIn(sensorActivityToJerk, sessionNumber);
            DataPoint dataPoint = generateDataPoint.getDataPoint();
            studies[sessionNumber].dataPointIn(dataPoint);
            allSubjects.add(dataPoint.getParticipantNumber());
            allWorkouts.add(dataPoint.getActivity());
            good_Count++;
        } else {
            System.out.println("Not a valid name " + activityName);
        }


    }

    public static void sortData() {
        for (int i = 0; i < studies.length; i++) {
            studies[i].sortAllPeople();
        }

    }


    public static void createCSV() {
        System.out.println("Generating CSV");
        //Accept all Data
        new ProgramToCSV("5_Pick_Ups_Total.csv", studies[1], new DataFilter() {
            @Override
            public boolean Filter(DataPoint dp) {
                return true;
            }
        });
        Iterator<Integer> iterator = allWorkouts.iterator();
        while(iterator.hasNext()) {
            int workoutNumber = iterator.next();
            new ProgramToCSV("PerWorkout/Workout_"+workoutNumber+".csv", studies[1], new DataFilter() {
                @Override
                public boolean Filter(DataPoint dp) {
                    return (dp.getActivity() == workoutNumber);
                }
            });
        }
        iterator = allSubjects.iterator();
        while(iterator.hasNext()) {
            int participantNumber = iterator.next();
            new ProgramToCSV("PerSubject/Subject_"+participantNumber+".csv", studies[1], new DataFilter() {
                @Override
                public boolean Filter(DataPoint dp) {
                    return (dp.getParticipantNumber() == participantNumber);
                }
            });
        }

    }
}
