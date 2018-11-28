package Workouts;


import Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 12/21/17.
 */

public class WO_Pour extends SensorWorkoutAbstract {
	int repCount;
	float filledPercentage = 100f;
	float pouringThresehold = 15f;
	float removalRate = .1f;
	boolean tooQuicklyFullExplainGiven = false;
	boolean inCooldown = false;
	long coolDownDuration = 5000;
	float emptyThreshold = 15f;
	long startOfCooldown = 0l;
	ZeroCrossCalculation zeroCrossCalculation;

	public WO_Pour(String Name) {
		super.Workout(Name);
		zeroCrossCalculation = new ZeroCrossCalculation();
	}

	@Override
	public void SensorDataIn(long time, float[] data) {
		super.SensorDataIn(time, data);
		if (WorkoutInProgress && !inCooldown) {
			float GravY, GravX;
			GravY = data[1];
			GravX = data[0];
			float Angle = ((GravY + (9.81f)) / (9.81f * 2));
			if (Angle > 1) {
				Angle = 1;
			} else if (Angle < 0) {
				Angle = 0;
			}
			float AngleStandardized = Angle * 100;
			Angle = 1 - Angle;
			if (GravX < 0) {
				Angle = -1 * Angle;
			}
			Angle = (180 * Angle);
			if (Math.abs(AngleStandardized - filledPercentage) <= pouringThresehold) {

				filledPercentage -= removalRate;
				if (filledPercentage < emptyThreshold) {
					repCount++;
					zeroCrossCalculation.endRep();
					if (repCount == reps) {
						workoutComplete = true;
					} else {
						inCooldown = true;
						startOfCooldown = time;
					}
				}

			} else {

			}
			zeroCrossCalculation.dataIn(new float[]{Angle});
		} else if (inCooldown) {
			if (Math.abs(time - startOfCooldown) >= coolDownDuration) {
				inCooldown = false;
				filledPercentage = 100f;
			}
		}
	}

	@Override
	public void StartWorkout() {
		super.StartWorkout();
	}

	@Override
	public boolean isWorkoutComplete() {
		return super.isWorkoutComplete();
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Zero", zeroCrossCalculation.getZeroCrosses(),zeroCrossCalculation.getTimes());
		return workoutScore;
	}

	@Override
	public int getProgress() {
		return repCount;
	}
}
