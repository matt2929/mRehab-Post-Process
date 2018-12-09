package Main;

import Activity.ActivityToJerkAbstract;
import Activity.SensorActivityToJerk;
import Activity.TouchActivityToJerk;
import Data_Models.DataPoint;
import Data_Out.DataFilter;
import Data_Out.GenerateDataPoint;
import Data_Models.Program;
import Data_Out.ProgramToCSV;
import Utilities.CSVScraper;
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
        System.out.println("Start");
        ArrayList<File> files = getAllLocalFiles();
        WorkoutNumberConversion workoutNumberConversion = new WorkoutNumberConversion();
        for (int i = 0; i < files.size(); i++) {
            ActivityToJerkAbstract activityToJerk;
            CSVScraper csvScraper = new CSVScraper();
            if (!files.get(i).isDirectory()) {
                if (csvScraper.validateFile(files.get(i).getAbsolutePath()) && csvScraper.loadHeaderCSV()) {
                    for (String str : workoutNumberConversion.getWorkouts()) {
                        if (csvScraper.getSensor()) {
                            activityToJerk = new SensorActivityToJerk(csvScraper);
                        } else {
                            activityToJerk = new TouchActivityToJerk(csvScraper);
                        }
                        if (activityToJerk.getCsvScraper().getWorkoutName().equals(str)) {
                            activityToJerk.RunCSV();
                            filterBySession(activityToJerk);
                            break;
                        }
                    }
                }
            }
        }
        for (HashMap.Entry<String, Double> entry : errorSum.entrySet()) {
            System.out.println("Error Rate of " + entry.getKey() + " is " + (entry.getValue() / ((double) completedActivityCount.get(entry.getKey()))) + "%");
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("Good: [" + good_Count + "]");
        System.out.println("Total:[" + total_Count + "]");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
        sortData();
        createCSV();

    }

    public static void errorCalculation(ActivityToJerkAbstract activityToJerkAbstract) {
        String wn = activityToJerkAbstract.getCsvScraper().getWorkoutName();
        if (!errorSum.containsKey(wn)) {
            errorSum.put(wn, 0d);
            completedActivityCount.put(wn, 0);
        }
        errorSum.put(wn, errorSum.get(wn) + (errorCalc(activityToJerkAbstract.getCsvScraper().getReps(), activityToJerkAbstract.get_CurrentWorkout().getScore().getScore().size())));
        completedActivityCount.put(wn, completedActivityCount.get(wn) + 1);
    }


    /**
     * @return ArrayList of files in AWS folder.
     */
    private static ArrayList<File> getAllLocalFiles() {
        File folder = new File(FileLocationConstants.Home_Adress);
        ArrayList<File> allFiles = new ArrayList<File>(Arrays.asList(folder.listFiles()));
        return allFiles;
    }

    public static void filterBySession(ActivityToJerkAbstract activityToJerk) {
        String activityName = activityToJerk.getCsvScraper().getFileName();
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
            validName = ((((activityName.charAt(0) == 's'&& activityName.split("_")[0].length()>1)|| activityName.split("_")[1].charAt(0) == 's') || (activityName.charAt(0) == '_')));
            errorCalculation(activityToJerk);
        }
        if (validName) {
            countNum[sessionNumber]++;
            generateDataPoint.infoIn(activityToJerk, sessionNumber);
            DataPoint dataPoint = generateDataPoint.getDataPoint();
            studies[sessionNumber].dataPointIn(dataPoint);
            allSubjects.add(dataPoint.getParticipantNumber());
            allWorkouts.add(dataPoint.getActivity());
            good_Count++;
        }
    }

    public static void sortData() {
        System.out.println("Sorting");

        for (int i = 0; i < studies.length; i++) {
            studies[i].sortAllPeople();
        }
    }

    public static double errorCalc(int accepted, int experiment) {
        double acceptedD = accepted;
        double experimentD = experiment;
        double error = ((Math.abs(experimentD - acceptedD)) / acceptedD) * 100d;
        return error;
    }

    public static void createCSV() {
        //Accept all Data
        new ProgramToCSV("5_Pick_Ups_Total.csv", studies[1], new DataFilter() {
            @Override
            public boolean Filter(DataPoint dp) {
                return true;
            }
        });
        Iterator<Integer> iterator = allWorkouts.iterator();
        WorkoutNumberConversion workoutNumberConversion = new WorkoutNumberConversion();
        while (iterator.hasNext()) {
            int workoutNumber = iterator.next();
            new ProgramToCSV("PerWorkout/Workout_" + workoutNumberConversion.intToName(workoutNumber) + ".csv", studies[1], new DataFilter() {
                @Override
                public boolean Filter(DataPoint dp) {
                    return (dp.getActivity() == workoutNumber);
                }
            });
        }
        iterator = allSubjects.iterator();
        while (iterator.hasNext()) {
            int participantNumber = iterator.next();
            new ProgramToCSV("PerSubject/Subject_" + participantNumber + ".csv", studies[1], new DataFilter() {
                @Override
                public boolean Filter(DataPoint dp) {
                    return (dp.getParticipantNumber() == participantNumber);
                }
            });
        }

    }
}
