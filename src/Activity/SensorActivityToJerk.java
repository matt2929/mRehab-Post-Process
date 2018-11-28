package Activity;

import Utilities.CSVScraper;
import Utilities.CSV_Line_Dissect;
import Workouts.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SensorActivityToJerk {

    private final int CHECK_CODE = 0x1;
    //~~~~~~~~~~~~~~~~~~~~~~~
    Long TimeStartWorkout = System.currentTimeMillis();
    Long TimeOfRep = System.currentTimeMillis();
    //Workout Attributes~~~
    String _WorkoutHand; //Which Hand
    String _WorkoutName; //Name of Current Wokrout
    Integer _WorkoutReps;//Number of Repetitions
    private SensorWorkoutAbstract _CurrentWorkout;
    //Refrence ID for TTS~~~~
    private Boolean _WorkoutInProgress = false;
    private ArrayList<Long> saveDurations = new ArrayList<>();
    private WorkoutDescription _WorkoutDescription = null;
    private CSVScraper csvScraper = new CSVScraper();
    long duration = 0;
    HashMap<String, Double> averageErrors = new HashMap<>();
    int total = 0;
//There is an update from the Sensor


    public boolean LoadCSV(File file) {
        if (csvScraper.validateFile(file.getAbsolutePath())) {
            csvScraper.loadHeaderCSV();
            return true;
        } else {
            return false;
        }
    }

    public CSVScraper getCsvScraper() {
        return csvScraper;
    }

    public void RunCSV() {
        SetupWorkout(csvScraper.getWorkoutName(), csvScraper.getReps());
        CSV_Line_Dissect csv_line_dissect;
        startWorkoutSequence();
        while ((csv_line_dissect = csvScraper.readLine()) != null) {
            duration += csv_line_dissect.getTime();
            onSensorChanged(duration, csv_line_dissect.getSensorAxis());
        }
        _CurrentWorkout.endWorkout();

    }

    public void onSensorChanged(long time, float[] data) {
        if (_CurrentWorkout != null) {
            _CurrentWorkout.SensorDataIn(time, data);
        }
        if (_CurrentWorkout.isWorkoutComplete() && _WorkoutInProgress) {
            _WorkoutInProgress = false;
            endWorkoutSequence();
        }
    }


    public void SetupWorkout(String WorkoutName, int reps) {
        if (WorkoutName.equals("Horizontal Bowl")) {
            _CurrentWorkout = new WO_PickUpHorizontal(WorkoutName);
        } else if (WorkoutName.equals("Horizontal Mug")) {
            _CurrentWorkout = new WO_PickUpHorizontal(WorkoutName);
        } else if (WorkoutName.equals("Vertical Bowl")) {
            _CurrentWorkout = new WO_PickUpVertical(WorkoutName);
        } else if (WorkoutName.equals("Horizontal Mug")) {
            _CurrentWorkout = new WO_PickUpHorizontal(WorkoutName);
        } else if (WorkoutName.equals("Vertical Mug")) {
            _CurrentWorkout = new WO_PickUpVertical(WorkoutName);
        } else if (WorkoutName.equals("Walk with mug")) {
            _CurrentWorkout = new WO_Walk(WorkoutName);
        } else if (WorkoutName.equals("Quick Twist Mug")) {
            _CurrentWorkout = new WO_Twist(WorkoutName);
        } else if (WorkoutName.equals("Slow Pour")) {
            _CurrentWorkout = new WO_Pour(WorkoutName);
        } else if (WorkoutName.equals("Sip From The Mug")) {
            _CurrentWorkout = new WO_Sip(WorkoutName);
        }

    }

    public void endWorkoutSequence() {
        //do something when done

    }

    public void startWorkoutSequence() {
        TimeStartWorkout = System.currentTimeMillis();
        TimeOfRep = System.currentTimeMillis();
        _CurrentWorkout.StartWorkout();
        _WorkoutInProgress = true;
    }

    public SensorWorkoutAbstract get_CurrentWorkout() {
        return _CurrentWorkout;
    }

    public static double error(double expected, double actual) {
        return (Math.abs(expected - actual) / expected);
    }

}
