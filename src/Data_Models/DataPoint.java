package Data_Models;

import java.util.ArrayList;

public class DataPoint {
    int participantNumber = -1;
    int hand = -1;
    Long time = -1l;
    int program = -1;
    int exerciseDay = -1;
    int exerciseWeek = -1;
    int activity = -1;
    ArrayList<Long> repDuration;
    ArrayList<Float> repSmoothness;

    public DataPoint(int participantNumber, int hand, Long time, int program, int exerciseDay, int exerciseWeek, int activity, ArrayList<Long> repDuration, ArrayList<Float> repSmoothness) {
        this.participantNumber = participantNumber;
        this.hand = hand;
        this.time = time;
        this.program = program;
        this.exerciseDay = exerciseDay;
        this.exerciseWeek = exerciseWeek;
        this.activity = activity;
        this.repDuration = repDuration;
        this.repSmoothness = repSmoothness;
    }

    public DataPoint(){}

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public void setExerciseDay(int exerciseDay) {
        this.exerciseDay = exerciseDay;
    }

    public void setExerciseWeek(int exerciseWeek) {
        this.exerciseWeek = exerciseWeek;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public void setParticipantNumber(int participantNumber) {
        this.participantNumber = participantNumber;
    }

    public void setProgram(int program) {
        this.program = program;
    }



    public void setRepSmoothness(ArrayList<Float> repSmoothness) {
        this.repSmoothness = repSmoothness;
    }

    public void setRepDuration(ArrayList<Long> repDuration) {
        this.repDuration = repDuration;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ArrayList<Float> getRepSmoothness() {
        return repSmoothness;
    }

    public int getActivity() {
        return activity;
    }

    public int getExerciseDay() {
        return exerciseDay;
    }

    public int getExerciseWeek() {
        return exerciseWeek;
    }

    public int getHand() {
        return hand;
    }

    public int getParticipantNumber() {
        return participantNumber;
    }

    public int getProgram() {
        return program;
    }

    public Long getTime() {
        return time;
    }

    public ArrayList<Long> getRepDuration() {
        return repDuration;
    }


}

