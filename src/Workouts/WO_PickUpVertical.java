package Workouts;

import Utilities.JerkScoreCalculation;

/**
 * Created by matt2929 on 12/20/17.
 */

public class WO_PickUpVertical extends SensorWorkoutAbstract {
    boolean moving = false;
    double thresehold = .25;
    int pickUpCount = 0;
    int belowThresholdCount = 0;
    int belowThresholdMax = 100;
    boolean savingJerk = false;
    JerkScoreCalculation jerkScoreCalculation;

    public WO_PickUpVertical(String Name) {
        super.SensorWorkout(Name);
        jerkScoreCalculation = new JerkScoreCalculation();
    }

    @Override
    public void SensorDataIn(long time, float[] data) {
        super.SensorDataIn(time, data);
        int sensorChoice = 1;
        if (Name.contains("Bowl")) {
            sensorChoice = 0;
        }
        if (WorkoutInProgress) {
            if (savingJerk) {
                jerkScoreCalculation.AccelerationIn(time, data);
            }
            if (AverageDataValue[sensorChoice] > thresehold) {
                if (savingJerk == false) {
                    savingJerk = true;
                }
                moving = true;
                belowThresholdCount = 0;
            }
            if (AverageDataValue[sensorChoice] < thresehold) {
                belowThresholdCount++;
                if (belowThresholdCount > belowThresholdMax) {
                    if (moving == true) {
                        savingJerk = false;
                        jerkScoreCalculation.CalculateJerkSingle(time, 5);
                        pickUpCount++;
                        if (pickUpCount / reps == .5) {
                        }
                        if (pickUpCount == reps) {
                            workoutComplete = true;
                        }
                    }
                    moving = false;
                }
            }
        }
    }

    @Override
    public boolean isWorkoutComplete() {
        return super.isWorkoutComplete();
    }

    @Override
    public WorkoutScore getScore() {

        workoutScore = new WorkoutScore("Jerk", jerkScoreCalculation.getAllJerks(),jerkScoreCalculation.getTimes());
        return workoutScore;
    }

    @Override
    public int getProgress() {
        return pickUpCount;
    }
}