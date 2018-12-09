package Utilities;

public class CSV_Line_Dissect_Touch extends CSV_Line_Dissect_Abstract {

    private double time = 0l;
    private float sensorAxis[] = new float[4];


    @Override
    public void dataIn(String[] line) {
        // System.out.println(line.length);
        for (int i = 1; i < sensorAxis.length; i++) {
            sensorAxis[i - 1] = Float.parseFloat(line[i]);
        }
        //System.out.println(line[0]);
        time =  Double.parseDouble(line[0]);
        // System.out.println(time);
    }


    @Override
    public double timeOut() {
        return time;
    }

    @Override
    public float[] dataOut() {
        return sensorAxis;
    }
}
