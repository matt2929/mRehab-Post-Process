package Utilities;

public class CSV_Line_Dissect_Sensor extends CSV_Line_Dissect_Abstract {

    private long time = 0l;
    private float sensorAxis[] = new float[3];



    @Override
    public void dataIn(String[] line) {
        // System.out.println(line.length);
        for (int i = 1; i < 4; i++) {
            sensorAxis[i - 1] = Float.parseFloat(line[i]);
        }
        //System.out.println(line[0]);
        time = (long) Double.parseDouble(line[0]);
        // System.out.println(time);
    }


    @Override
    public long timeOut() {
        return time;
    }

    @Override
    public float[] dataOut() {
        return sensorAxis;
    }

}
