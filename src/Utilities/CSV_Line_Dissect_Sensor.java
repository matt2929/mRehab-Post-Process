package Utilities;

public class CSV_Line_Dissect_Sensor extends CSV_Line_Dissect_Abstract {

    private Double time = 0d;
    private float sensorAxis[] = new float[3];



    @Override
    public void dataIn(String[] line) {
        // System.out.println(line.length);
        for (int i = 1; i < 4; i++) {
            sensorAxis[i - 1] = Float.parseFloat(line[i]);
        }
        //System.out.println(line[0]);
        time = Double.parseDouble(line[0]);
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
