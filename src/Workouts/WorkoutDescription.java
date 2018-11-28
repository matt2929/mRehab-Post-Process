package Workouts;

/**
 * Created by matt2929 on 12/22/17.
 */

public class WorkoutDescription {
	private String Name = "";
	private String WorkoutType = "";


	public WorkoutDescription(String Name, String WorkoutType) {
		this.Name = Name;
		this.WorkoutType = WorkoutType;

	}

	public String getName() {
		return Name;
	}

	public String getWorkoutType() {
		return WorkoutType;
	}


}
