package Data_Models;

import java.util.*;

public class Subject {
    HashMap<Integer, ArrayList<DataPoint>> workouts = new HashMap<>();

    public void subjectDataIn(DataPoint dp) {
        if (!workouts.containsKey(dp.activity)) {
            workouts.put(dp.activity, new ArrayList<DataPoint>());
        }
        workouts.get(dp.activity).add(dp);
    }


    public void determineDayOfActivity() {
        for (Map.Entry<Integer, ArrayList<DataPoint>> entry : workouts.entrySet()) {
            Collections.sort(entry.getValue(), new Comparator<DataPoint>() {
                @Override
                public int compare(DataPoint o1, DataPoint o2) {
                    if ((o1.getTime() - o2.getTime()) > 0l) {
                        return 1;
                    } else if ((o1.getTime() - o2.getTime()) < 0l) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

        }
    }

    public HashMap<Integer, ArrayList<DataPoint>> getWorkouts() {
        return workouts;
    }
}
