package Data_Models;

public class DataPoint {
    int participantNumber = -1;
    int hand = -1;
    Long time = -1l;
    int program = -1;
    int exerciseDay = -1;
    int exerciseSession = -1;
    int activity = -1;
    long[] repDuration;
    double[] repSmoothness;

    public DataPoint(int participantNumber, int hand, Long time, int program, int exerciseDay, int exerciseSession, int activity, long[] repDuration, double[] repSmoothness) {
        this.participantNumber = participantNumber;
        this.hand = hand;
        this.time = time;
        this.program = program;
        this.exerciseDay = exerciseDay;
        this.exerciseSession = exerciseSession;
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

    public void setExerciseSession(int exerciseSession) {
        this.exerciseSession = exerciseSession;
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


    public void setRepSmoothness(double[] repSmoothness) {
        this.repSmoothness = repSmoothness;
    }

    public void setRepDuration(long[] repDuration) {
        this.repDuration = repDuration;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public double[] getRepSmoothness() {
        return repSmoothness;
    }

    public int getActivity() {
        return activity;
    }

    public int getExerciseDay() {
        return exerciseDay;
    }

    public int getExerciseSession() {
        return exerciseSession;
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

    public long[] getRepDuration() {
        return repDuration;
    }


}

