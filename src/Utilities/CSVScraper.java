package Utilities;

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
    private String fileName="";

    /**
     * @return cvs starting with data if no more lines returns null.
     */
    public CSV_Line_Dissect readLine() {
        CSV_Line_Dissect csv_line_dissect;
        String line;
        try {
            if ((line = br.readLine()) != null) {
          //      System.out.println(line);
                if (line.split(cvsSplitBy).length > 0) {
                    csv_line_dissect = new CSV_Line_Dissect(line.split(cvsSplitBy));
                    return csv_line_dissect;
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
            fileName=file.getName();
            file=null;
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        line = "";
        lineCount = 0;
        try {
            line = br.readLine();
            if(line==null){return false;}
            titleBar = line.split(cvsSplitBy).clone();
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  System.out.println(line);
        return (line != null && !line.equals("") && line.charAt(0) != '{');
    }

    public void loadHeaderCSV() {
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
        }


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

