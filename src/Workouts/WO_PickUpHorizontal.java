package Workouts;


import Utilities.JerkScoreCalculation;
import org.w3c.dom.NameList;

/**
 * Created by matt2929 on 12/20/17.
 */

public class WO_PickUpHorizontal extends SensorWorkoutAbstract {
	boolean moving = false;
	double thresehold = .25;
	int pickUpCount = 0;
	int belowThresholdCount = 0;
	int belowThresholdMax = 100;
	boolean savingJerk = false;
	JerkScoreCalculation jerkScoreCalculation;


	public WO_PickUpHorizontal(String Namelayer) {
		super.SensorWorkout(Namelayer);
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
				jerkScoreCalculation.AccelerationIn(time,data);
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
						jerkScoreCalculation.CalculateJerkSingle(time,5);
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
    public int getProgress(){
        return  pickUpCount;
	}

	@Override
	public WorkoutScore getScore() {
		workoutScore = new WorkoutScore("Jerk", jerkScoreCalculation.getAllJerks(),jerkScoreCalculation.getTimes());
		return workoutScore;
	}

}