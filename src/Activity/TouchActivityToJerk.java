package Activity;

import Utilities.CSVScraper;
import Utilities.CSV_Line_Dissect_Abstract;
import Utilities.CSV_Line_Dissect_Sensor;
import Workouts.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TouchActivityToJerk extends ActivityToJerkAbstract{

    private final int CHECK_CODE = 0x1;
    //~~~~~~~~~~~~~~~~~~~~~~~
    private TouchWorkoutAbstract _CurrentWorkout;
    private Boolean _WorkoutInProgress = false;
    private CSVScraper csvScraper;
    long duration = 0;
    public TouchActivityToJerk(CSVScraper csvScraper) {
        this.csvScraper = csvScraper;
    }
    @Override
    public CSVScraper getCsvScraper() {
        return csvScraper;
    }
    @Override
    public void RunCSV() {
        SetupWorkout(csvScraper.getWorkoutName(), csvScraper.getReps());
        CSV_Line_Dissect_Abstract csv_line_dissect_abstract;
        startWorkoutSequence();
        while ((csv_line_dissect_abstract = csvScraper.readLine()) != null) {
            duration += csv_line_dissect_abstract.timeOut();
            onSensorChanged(duration, csv_line_dissect_abstract.dataOut());
        }
        _CurrentWorkout.endWorkout();

    }
    @Override
    public void onSensorChanged(long time, float[] in) {
        if (_CurrentWorkout != null) {
            _CurrentWorkout.TouchIn(time, in[0],in[1]);
        }
        if (_CurrentWorkout.isWorkoutComplete() && _WorkoutInProgress) {
            _WorkoutInProgress = false;
            endWorkoutSequence();
        }
    }

    @Override
    public void SetupWorkout(String WorkoutName, int reps) {
        if (WorkoutName.equals("Unlock With Key")) {
            _CurrentWorkout = new WO_Unlock(WorkoutName);
        } else if (WorkoutName.equals("Turn Doorknob")) {
            _CurrentWorkout = new WO_Unlock(WorkoutName);
        }
    }
    @Override
    public void endWorkoutSequence() {
        //do something when done

    }
    @Override
    public void startWorkoutSequence() {
        _CurrentWorkout.StartWorkout();
        _WorkoutInProgress = true;
    }
    @Override
    public WorkoutAbstract get_CurrentWorkout() {
        return _CurrentWorkout;
    }

}
