package Workouts;


import Utilities.AverageValue;

/**
 * Created by matt2929 on 1/4/18.
 */

public abstract class WorkoutAbstract {
	boolean workoutComplete = false;
	Long lastActivity = System.currentTimeMillis();
	WorkoutScore workoutScore;
	boolean WorkoutInProgress = false;
	float[] dataLast = null;
	AverageValue[] AverageValues;
	float[] AverageDataValue;
	Integer reps = 10;
	String Name = "";

	public void Workout(String Name) {
		this.Name = Name;
	}

	public void StartWorkout() {
		WorkoutInProgress = true;
		lastActivity = System.currentTimeMillis();
	}

	public boolean isWorkoutComplete() {
		return workoutComplete;
	}

	public void endWorkout(){
		workoutComplete=true;
	}

	public WorkoutScore getScore() {
		return new WorkoutScore("Null", null,null);
	}

	public String getName() {
		return Name;
	}

	public Integer getReps() {
		return reps;
	}



}