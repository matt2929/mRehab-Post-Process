package Workouts;


import Utilities.AverageValue;

/**
 * Created by matt2929 on 12/19/17.
 */

public abstract class SensorWorkoutAbstract extends WorkoutAbstract {


    public void SensorWorkout(String Name) {
        super.Workout(Name);
    }

    public void SensorDataIn(long time, float[] data) {
        if (dataLast == null) {
            dataLast = data;
            AverageValues = new AverageValue[data.length];
            AverageDataValue = new float[data.length];
            for (int i = 0; i < data.length; i++) {
                AverageValues[i] = new AverageValue(25);
            }
        }
        for (int i = 0; i < data.length; i++) {
            AverageDataValue[i] = AverageValues[i].addData(data[i]);
        }
    }

    public int getProgress(){
        return 0;
    }

}
