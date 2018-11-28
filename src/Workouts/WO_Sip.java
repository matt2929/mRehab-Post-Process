package Workouts;


import Utilities.ZeroCrossCalculation;

/**
 * Created by matt2929 on 1/16/18.
 */

public class WO_Sip extends SensorWorkoutAbstract {

	private double thresholdPickup = 3.0;
	private boolean pickedUp = false;
	private double threseholdDrink = 2;
	private long timeToDrink = 4000, timeDrank = 0, checkPoint = 0;
	private int repCount = 0;
	private boolean inCoolDown = false;
	private long coolDownLength = 5000, cooldownStart = 0;
	private ZeroCrossCalculation zeroCrossCalculation;

	public WO_Sip(String Name) {
		super.SensorWorkout(Name);
		zeroCrossCalculation = new ZeroCrossCalculation();
	}


	@Override
	public void SensorDataIn(long time, float[] data) {
		super.SensorDataIn(time, data);

		if (WorkoutInProgress && !inCoolDown) {

			zeroCrossCalculation.dataIn(data);
			if (data[1] > thresholdPickup && !pickedUp) {
				pickedUp = true;
				checkPoint = time;
			}
			if (pickedUp) {
				if (Math.abs(data[2]) > threseholdDrink || Math.abs(data[0]) > threseholdDrink) {
					timeDrank += (Math.abs(checkPoint - time));

				}
				checkPoint = time;
			}
			if (timeDrank > timeToDrink) {
				repCount++;
				zeroCrossCalculation.endRep();
				inCoolDown = true;
				cooldownStart = time;
				if (repCount == reps) {
					workoutComplete = true;
				}
			}
		}
		if (inCoolDown) {
			if (Math.abs(cooldownStart - time) > coolDownLength) {
				inCoolDown = false;
				timeDrank = 0;
				pickedUp = false;
				checkPoint = time;
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
	public String getName() {
		return super.getName();
	}

	@Override
	public Integer getReps() {
		return super.getReps();
	}

    @Override
    public int getProgress() {
        return repCount;
    }
}
