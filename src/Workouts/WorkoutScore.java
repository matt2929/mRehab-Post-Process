package Workouts;

import java.util.ArrayList;

/**
 * Created by matt2929 on 12/19/17.
 */

public class WorkoutScore {
    String Name;
    ArrayList<Long> times;
    ArrayList<Float> scores;

    public WorkoutScore(String Name, ArrayList<Float> Score, ArrayList<Long> times) {
        this.Name = Name;
        this.scores = Score;
        this.times = times;
    }

    public String getName() {
        return Name;
    }

    public ArrayList<Float> getScore() {
        return scores;
    }

    public ArrayList<Long> getTimes() { return times; }
}
