package Workouts;



/**
 * Created by matt2929 on 1/4/18.
 */

public class TouchWorkoutAbstract extends WorkoutAbstract {

	public void TouchWorkout(String Name) {
		super.Workout(Name);

	}

	public boolean TouchIn(float x, float y) {
		return false;
	}

	public void Update() {

	}
}
