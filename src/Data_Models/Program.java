package Data_Models;

import java.util.HashMap;
import java.util.Map;

public class Program {
    Integer programNumberKey;
    String name;
    HashMap<Integer, Subject> subjects = new HashMap<>();

    public Program(String name, Integer prgramNum){
        this.name=name;
        this.programNumberKey=prgramNum;
    }

    public void dataPointIn(DataPoint dp){
        if(!subjects.containsKey(dp.participantNumber)){
            subjects.put(dp.participantNumber,new Subject());
        }
        subjects.get(dp.participantNumber).subjectDataIn(dp);
    }

    public void sortAllPeople(){
        for(Map.Entry<Integer, Subject> entry: this.subjects.entrySet()){
            entry.getValue().determineDayOfActivity();
        }
    }

    public HashMap<Integer, Subject> getSubjects() {
        return subjects;
    }

    public Integer getProgramNumberKey() {
        return programNumberKey;
    }

}
