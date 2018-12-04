package Workouts;


import Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Unlock extends TouchWorkoutAbstract {

    boolean left = false;
    ZeroCrossCalculation zeroCrossCalculation;
    int countReps = 0;
    float width = 1080;
    float height = 0;

    public WO_Unlock(String Name) {
        super.TouchWorkout(Name);
        zeroCrossCalculation = new ZeroCrossCalculation();

    }

    @Override
    public boolean TouchIn(long Time, float x, float y) {
        if (WorkoutInProgress) {
           double angle = Math.atan((y / x));
            zeroCrossCalculation.dataIn(new float[]{(float) angle});
            float margin = (200f);
            if (left) {
                if (x < margin) {
                    completeTurn();
                }
            } else {
                if (x > (width - margin)) {
                    completeTurn();
                }
            }
           }
        return true;
    }

    public void completeTurn() {

        countReps++;
        zeroCrossCalculation.endRep();
        if (countReps == reps) {
            workoutComplete = true;
        } else {
        }
        left = !left;
    }

    @Override
    public void Update() {
        super.Update();
    }


    @Override
    public WorkoutScore getScore() {
        return new WorkoutScore("Smoothness",zeroCrossCalculation.getZeroCrosses(), zeroCrossCalculation.getTimes());
    }


}
