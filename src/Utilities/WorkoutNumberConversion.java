package Utilities;

public class WorkoutNumberConversion {

    String[] workouts = new String[12];

    public WorkoutNumberConversion() {
        workouts[0] = "Slow Pour";
        workouts[1] = "Horizontal Bowl";
        workouts[2] = "Horizontal Mug";
        workouts[3] = "Vertical Bowl";
        workouts[4] = "Vertical Mug";
        workouts[5] = "Sip From The Mug";
        workouts[6] = "Quick Twist Mug";
        workouts[7] = "Walk with mug";
            workouts[8] =  "Unlock With Key";
            workouts[9] = "Turn Doorknob";
        //    workouts[10] = "Quick Tap";
        //    workouts[11] = "Phone Number";
        //workouts[8] = "";
        //workouts[9] = "";
        workouts[10] = "";
        workouts[11] = "";
    }

    public int nameToInt(String str) {
        for (int i = 0; i < workouts.length; i++) {
            if (str.equals(workouts[i])) {
                return i;
            }
        }
        return -1;
    }

    public String intToName(int i) {

        return this.workouts[i];
    }

    public String[] getWorkouts() {
        return workouts;
    }
}