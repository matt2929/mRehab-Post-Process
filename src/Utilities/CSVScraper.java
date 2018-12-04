package Utilities;

import Values.WorkoutData;

import java.io.*;

public class CSVScraper {
    private BufferedReader br;
    private String line;
    private String cvsSplitBy = ",";
    private int lineCount = 0;
    private String[] titleBar;
    private String[] header;
    private String Name = "";
    private Long Date;
    private String WorkoutName = "nill";
    private Double Duration;
    private Double Smoothness;
    private int Reps;
    private String Hand;
    private String fileName = "";
    private boolean sensor = false;

    /**
     * @return cvs starting with data if no more lines returns null.
     */
    public CSV_Line_Dissect_Abstract readLine() {

        CSV_Line_Dissect_Abstract csv_line_dissect_abstract;

        if (sensor) {
            csv_line_dissect_abstract = new CSV_Line_Dissect_Sensor();
        } else {
            csv_line_dissect_abstract = new CSV_Line_Dissect_Touch();
        }
        String line;
        try {
            if ((line = br.readLine()) != null) {
                if (line.split(cvsSplitBy).length > 0) {
                    csv_line_dissect_abstract.dataIn(line.split(cvsSplitBy));
                    return csv_line_dissect_abstract;
                }
            } else {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean validateFile(String filePath) {
        try {
            File file = new File(filePath);
            fileName = file.getName();
            file = null;
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = "";
        lineCount = 0;
        try {
            line = br.readLine();
            if (line == null) {
                return false;
            }
            titleBar = line.split(cvsSplitBy).clone();
        } catch (IOException e) {
            e.printStackTrace();

        }
        //  System.out.println(line);
        return (line != null && !line.equals("") && line.charAt(0) != '{');
    }

    public boolean loadHeaderCSV() {
        String[] values;
        try {
            values = line.split(cvsSplitBy);
            header = values.clone();
            for (String str : values) {
                if (str.contains("Name")) {

                } else if (str.contains("Workout")) {
                    WorkoutName = str.split(":")[1];
                } else if (str.contains("Date HR")) {
                } else if (str.contains("Date MS")) {
                    Date = Long.valueOf(str.split(":")[1]);
                } else if (str.contains("Duration")) {
                    Duration = Double.valueOf(str.split(":")[1]);
                } else if (str.contains("Smoothness")) {
                    Smoothness = Double.valueOf(str.split(":")[1]);
                } else if (str.contains("Reps")) {
                    Reps = Integer.parseInt(str.split(":")[1]);
                } else if (str.contains("Hand")) {
                    Hand = str.split(":")[1];
                }
            }
            line = br.readLine();
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        if (WorkoutName != null) {
            for (String str : WorkoutData.SensorNames) {
                if (str.equals(WorkoutName)) {
                    sensor = true;
                }
            }
        }
        return true;

    }

    public boolean getSensor() {
        return sensor;
    }

    public Double getDuration() {
        return Duration;
    }

    public Double getSmoothness() {
        return Smoothness;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getReps() {
        return Reps;
    }

    public String getHand() {
        return Hand;
    }

    public String getWorkoutName() {
        return WorkoutName;
    }

    public Long getDate() {
        return Date;
    }

    public String getFileName() {
        return fileName;
    }
}

