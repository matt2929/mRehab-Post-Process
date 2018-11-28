package Data_Out;

import Activity.SensorActivityToJerk;
import Data_Models.DataPoint;

import java.util.HashMap;

public class GenerateDataPoint {
    DataPoint dataPoint;
    HashMap<String, Integer> workoutStringToInteger = new HashMap<>();

    public DataPoint infoIn(SensorActivityToJerk saj, int session) {
        dataPoint= new DataPoint();
        dataPoint = new DataPoint();
        dataPoint.setHand(getHand(saj));
        dataPoint.setActivity(getActivity(saj));
        dataPoint.setExerciseSession(session);
        dataPoint.setParticipantNumber(getParticipantNumber(session, saj));
        dataPoint.setProgram(session);
        dataPoint.setRepSmoothness(saj.get_CurrentWorkout().getScores());
        dataPoint.setRepDuration(saj.get_CurrentWorkout().getTimes());
        dataPoint.setTime(saj.getCsvScraper().getDate());
        return dataPoint;
    }


    public DataPoint getDataPoint(){
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
        switch (saj.getCsvScraper().getWorkoutName()) {
            case ("Horizontal Bowl"):
                return 0;
            case ("Horizontal Mug"):
                return 1;
            case ("Vertical Bowl"):
                return 2;

            case ("Vertical Mug"):
                return 3;
            case ("Slow Pour"):
                return 4;
            case ("Sip From The Mug"):
                return 5;
        }
        return -1;
    }

    private int getParticipantNumber(int session, SensorActivityToJerk saj) {
        System.out.println("session: "+ session+" full "+ saj.getCsvScraper().getFileName());

        if (session == 0) {
            String first = saj.getCsvScraper().getFileName().split("_")[0];
            String subName = first.substring(1, 3);
            if (subName.charAt(0) == '0') {
                subName = "" + subName.charAt(1);
            }
            System.out.println("session: "+ session+" full :"+ saj.getCsvScraper().getFileName()+"first "+first+" sub "+subName);
            return Integer.parseInt(subName);
        } else if(session!=-1){
            String first = saj.getCsvScraper().getFileName().split("_")[0];
            String subName = first.substring(1, 3);
            if (subName.charAt(0) == '0') {
                subName = "" + subName.charAt(1);
            }
            System.out.println("session: "+ session+" full "+ saj.getCsvScraper().getFileName()+"first "+first+" sub "+subName);
            return Integer.parseInt(subName);
        } else{
            System.out.println("nope: "+ saj.getCsvScraper().getFileName());
            return -1;
        }
    }

}
