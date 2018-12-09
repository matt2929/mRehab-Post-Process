package Activity;

import Utilities.CSVScraper;
import Utilities.CSV_Line_Dissect_Abstract;
import Utilities.CSV_Line_Dissect_Sensor;
import Workouts.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SensorActivityToJerk extends ActivityToJerkAbstract {

    private final int CHECK_CODE = 0x1;
    //~~~~~~~~~~~~~~~~~~~~~~~

    private SensorWorkoutAbstract _CurrentWorkout;
    private Boolean _WorkoutInProgress = false;
    private ArrayList<Long> saveDurations = new ArrayList<>();
    private WorkoutDescription _WorkoutDescription = null;
    private CSVScraper csvScraper;
    long duration = 0;

    public SensorActivityToJerk(CSVScraper csvScraper) {
        this.csvScraper = csvScraper;
    }


    @Override
    public CSVScraper getCsvScraper() {
        return csvScraper;
    }

    @Override
    public void RunCSV() {
        SetupWorkout(csvScraper.getWorkoutName(), csvScraper.getReps());
        CSV_Line_Dissect_Abstract csvLineDissectSensor;
        startWorkoutSequence();
        while ((csvLineDissectSensor = csvScraper.readLine()) != null) {
            duration += csvLineDissectSensor.timeOut();
            onSensorChanged(duration, csvLineDissectSensor.dataOut());
        }
        _CurrentWorkout.endWorkout();

    }

    @Override
    public void onSensorChanged(long time, float[] data) {
        if (_CurrentWorkout != null) {
            _CurrentWorkout.SensorDataIn(time, data);
        }
        if (_CurrentWorkout.isWorkoutComplete() && _WorkoutInProgress) {
            _WorkoutInProgress = false;
            endWorkoutSequence();
        }
    }

    @Override
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
