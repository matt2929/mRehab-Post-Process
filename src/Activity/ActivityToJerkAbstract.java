package Activity;

import Utilities.CSVScraper;
import Utilities.CSV_Line_Dissect_Abstract;
import Workouts.*;

import java.io.File;

public abstract class ActivityToJerkAbstract {


    public CSVScraper getCsvScraper() {
        return null;
    }

    public void RunCSV() {
    }

    public void onSensorChanged(long time, float[] data) {

    }


    public void SetupWorkout(String WorkoutName, int reps) {

    }

    public void endWorkoutSequence() {
    }

    public void startWorkoutSequence() {

    }

    public WorkoutAbstract get_CurrentWorkout() {
        return null;
    }

}
