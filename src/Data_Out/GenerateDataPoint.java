package Data_Out;

import Activity.SensorActivityToJerk;
import Data_Models.DataPoint;
import Utilities.WorkoutNumberConversion;

import java.util.HashMap;

public class GenerateDataPoint {
    DataPoint dataPoint;
    HashMap<String, Integer> workoutStringToInteger = new HashMap<>();
    private boolean specialNamingCaseUnset = false;

    public DataPoint infoIn(SensorActivityToJerk saj, int session) {
        dataPoint = new DataPoint();
        dataPoint.setHand(getHand(saj));
        dataPoint.setActivity(getActivity(saj));
        dataPoint.setExerciseWeek(session);
        dataPoint.setParticipantNumber(getParticipantNumber(session, saj));
        dataPoint.setProgram(session);
        dataPoint.setRepSmoothness(saj.get_CurrentWorkout().getScore().getScore());
        dataPoint.setRepDuration(saj.get_CurrentWorkout().getScore().getTimes());
        dataPoint.setTime(saj.getCsvScraper().getDate());
        return dataPoint;
    }


    public DataPoint getDataPoint() {
        return dataPoint;
    }

    private int getHand(SensorActivityToJerk saj) {
        if (saj.getCsvScraper().getHand().equals("Left")) {
            return 0;
        } else {
            return 1;
        }
    }

    private int getActivity(SensorActivityToJerk saj) {
        WorkoutNumberConversion workoutNumberConversion = new WorkoutNumberConversion();
        return workoutNumberConversion.nameToInt(saj.getCsvScraper().getWorkoutName());
    }

    public void setSpecialNamingCaseUnset(boolean specialNamingCaseUnset) {
        this.specialNamingCaseUnset = specialNamingCaseUnset;
    }

    private int getParticipantNumber(int session, SensorActivityToJerk saj) {
        if (specialNamingCaseUnset) {
            if (saj.getCsvScraper().getFileName().charAt(0) == '_') {
                return 4;
            }
        }
        if (session == 0) {
            String first = saj.getCsvScraper().getFileName().split("_")[0];
            String subName = first.substring(1, 3);
            if (subName.charAt(0) == '0') {
                subName = "" + subName.charAt(1);
            }
            System.out.println("session: " + session + " full :" + saj.getCsvScraper().getFileName() + "first " + first + " sub " + subName);
            return Integer.parseInt(subName);
        } else if (session != -1) {
            String first = saj.getCsvScraper().getFileName().split("_")[0];
            String subName = first.substring(1, 3);
            if (subName.charAt(0) == '0') {
                subName = "" + subName.charAt(1);
            }
            System.out.println("session: " + session + " full " + saj.getCsvScraper().getFileName() + "first " + first + " sub " + subName);
            return Integer.parseInt(subName);
        } else {
            System.out.println("nope: " + saj.getCsvScraper().getFileName());
            return -1;
        }
    }


}
