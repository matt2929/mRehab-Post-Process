package Data_Models;

import java.util.*;

public class Subject {
    HashMap<Integer, ArrayList<DataPoint>> workouts = new HashMap<>();
    private Long dateStartLong = Long.MAX_VALUE;

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
                    if (o1.getTime() < dateStartLong) {
                        dateStartLong = o1.getTime();
                    }
                    if (o2.getTime() < dateStartLong) {
                        dateStartLong = o1.getTime();
                    }
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
        for (Map.Entry<Integer, ArrayList<DataPoint>> entry : workouts.entrySet()) {
            for (DataPoint dp : entry.getValue()) {
                Date dateNow = new Date(dp.getTime());
                Date  dateStart= new Date(this.dateStartLong);
                dp.setExerciseDay(daysBetween(dateNow, dateStart));
                dp.setExerciseWeek(daysBetween(dateNow, dateStart)/7);
            }
        }
    }

    public int daysBetween(Date d1, Date d2) {
        return (int) (Math.abs((d2.getTime() - d1.getTime())) / (1000 * 60 * 60 * 24));
    }

    public HashMap<Integer, ArrayList<DataPoint>> getWorkouts() {
        return workouts;
    }
}
