package Data_Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Legend {
    String title = "not set";
    ArrayList<ArrayList<String>> keyValues = new ArrayList<>();

    public Legend(String title){
        this.title=title;
    }

    public void addPoint(String key, String value) {
        keyValues.add(new ArrayList<>());
        keyValues.get(keyValues.size()-1).add(key);
        keyValues.get(keyValues.size()-1).add(value);
    }

    public String[][] outputLegend(){
        String[][] output = new String[keyValues.size()][keyValues.get(0).size()];
        for(int i =0;i<keyValues.size();i++){
            for(int j=0;j<2;j++){
                output[i][j]=keyValues.get(i).get(j);
            }
        }
        return output;
    }
}
