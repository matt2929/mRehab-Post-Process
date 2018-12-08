package Utilities;

public class WorkoutNumberConversion {

    String[] workouts = new String[13];

    public WorkoutNumberConversion() {
        workouts[0] = "Slow Pour";
        workouts[1] = "Horizontal Bowl";
        workouts[2] = "Horizontal Mug";
        workouts[3] = "Vertical Bowl";
        workouts[4] = "Vertical Mug";
        workouts[5] = "Sip From The Mug";
        workouts[6] = "Quick Twist Mug";
        workouts[7] = "Sip From The Mug";
    //    workouts[8] = "Walk with mug";
    //    workouts[9] =  "Unlock With Key";
    //    workouts[10] = "Turn Doorknob";
    //    workouts[11] = "Quick Tap";
    //    workouts[12] = "Phone Number";
        workouts[8] = "";
            workouts[9] =  "";
            workouts[10] = "";
            workouts[11] = "";
            workouts[12] = "";
    }

    public int nameToInt(String str){
        for(int i=0;i<workouts.length;i++){
            if(str.equals(workouts[i])){
                return i;
            }
        }
        return -1;
    }

    public String intToName(int i){
        return this.workouts[i];
    }

    public String[] getWorkouts() {
        return workouts;
    }
}