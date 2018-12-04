package Activity;

import Utilities.CSVScraper;
import Utilities.CSV_Line_Dissect_Abstract;
import Utilities.CSV_Line_Dissect_Sensor;
import Workouts.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TouchActivityToJerk {

    private final int CHECK_CODE = 0x1;
    //~~~~~~~~~~~~~~~~~~~~~~~
    Long TimeStartWorkout = System.currentTimeMillis();
    Long TimeOfRep = System.currentTimeMillis();
    //Workout Attributes~~~
    String _WorkoutHand; //Which Hand
    String _WorkoutName; //Name of Current Wokrout
    Integer _WorkoutReps;//Number of Repetitions
    private TouchWorkoutAbstract _CurrentWorkout;
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
        if (csvScraper.validateFile(file.getAbsolutePath()) && csvScraper.loadHeaderCSV()) {

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
        CSV_Line_Dissect_Abstract csv_line_dissect_abstract;
        startWorkoutSequence();
        while ((csv_line_dissect_abstract = csvScraper.readLine()) != null) {
            duration += csv_line_dissect_abstract.timeOut();
            onTouchChanged(duration, csv_line_dissect_abstract.dataOut());
        }
        _CurrentWorkout.endWorkout();

    }

    public void onTouchChanged(long time, float[] in) {
        if (_CurrentWorkout != null) {
            _CurrentWorkout.TouchIn(time, in[0],in[1]);
        }
        if (_CurrentWorkout.isWorkoutComplete() && _WorkoutInProgress) {
            _WorkoutInProgress = false;
            endWorkoutSequence();
        }
    }


    public void SetupWorkout(String WorkoutName, int reps) {
        if (WorkoutName.equals("Unlock With Key")) {
            _CurrentWorkout = new WO_Unlock(WorkoutName);
        } else if (WorkoutName.equals("Turn Doorknob")) {
            _CurrentWorkout = new WO_Unlock(WorkoutName);
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

    public WorkoutAbstract get_CurrentWorkout() {
        return _CurrentWorkout;
    }

    public static double error(double expected, double actual) {
        return (Math.abs(expected - actual) / expected);
    }

}
