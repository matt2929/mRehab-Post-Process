package Workouts;


import Utilities.ZeroCrossCalculation;

import static javafx.scene.input.KeyCode.R;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WO_Walk extends SensorWorkoutAbstract {
	ZeroCrossCalculation zeroCrossCalculation;
	float threshold = 3;
	Boolean inCooldown = false;
	Boolean startData = false;
	Long startRep = 0L;
	int repCount = 0;
	Long walkLength = 10000L;
	Long cooldownLength = 10000L;
	Long cooldownStart = 0L;

	public WO_Walk(String Name) {
		super.SensorWorkout(Name);
		zeroCrossCalculation = new ZeroCrossCalculation();
	}


	@Override
	public void SensorDataIn(long time, float[] data) {
		super.SensorDataIn(time, data);
		if (WorkoutInProgress) {
			if (!inCooldown) {
				if (!startData) {
					startRep = time;
					startData = true;
				}
				zeroCrossCalculation.dataIn(data);
				if (data[1] > threshold && data[2] > threshold) {

				} else {

				}
				if (time - startRep > walkLength) {
					repCount++;
					zeroCrossCalculation.endRep();
					if (repCount == reps) {
						workoutComplete = true;
					} else {

					}
					cooldownStart = time;
					inCooldown = true;
				}
			} else {
				if (time - cooldownStart > cooldownLength) {
					inCooldown = false;
					startData = false;

				}
			}
		}
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Zero", zeroCrossCalculation.getZeroCrosses(),zeroCrossCalculation.getTimes());
		return workoutScore;
	}
}
