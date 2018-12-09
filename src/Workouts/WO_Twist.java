package Workouts;

import Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Twist extends SensorWorkoutAbstract {
	boolean down = false;
	float lastValue = 0;
	int count = 0;
	float threshold = -3.75f;
	ZeroCrossCalculation zeroCrossCalculation;

	public WO_Twist(String Name) {
		super.SensorWorkout(Name);
		zeroCrossCalculation = new ZeroCrossCalculation();
	}

	//TODO: clockwise right hand  CCW LEFT HAND

	@Override
	public void SensorDataIn(long time, float[] data) {
		super.SensorDataIn(time, data);
		if (WorkoutInProgress) {
			zeroCrossCalculation.dataIn(time,data);
			if (AverageDataValue[1] < threshold && lastValue >= threshold) {
				count++;
				zeroCrossCalculation.endRep(time);
				if (count == reps) {
					workoutComplete = true;
				}
			}
		}
		lastValue = AverageDataValue[1];
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Zero", zeroCrossCalculation.getZeroCrosses(),zeroCrossCalculation.getTimes());
		return workoutScore;
	}
}
