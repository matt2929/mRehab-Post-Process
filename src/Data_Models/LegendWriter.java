package Data_Models;

import com.sun.tools.internal.ws.wsdl.document.Output;

import java.util.ArrayList;

public class LegendWriter {
    ArrayList<Legend> legends = new ArrayList<>();
    boolean spaceTime = false;
    boolean titleTime = true;
    int legendIndex = 0;
    int legendSubIndex = 0;

    public LegendWriter(ArrayList<Legend> legends) {
        this.legends=legends;
    }

    //Assumption there is no empty legends
    public boolean hasNext() {
        if (spaceTime || titleTime) {
            return true;
        }
        if (legendSubIndex < legends.get(legendIndex).outputLegend().length -1) {
            return true;
        } else if (legendIndex < legends.size()-1) {
            return true;
        }
        return false;
    }

    public String[] next() {
        if (spaceTime) {
            spaceTime = false;
            titleTime = true;
            return new String[]{"", ""};
        }
        if (titleTime) {
            titleTime = false;
            return new String[]{legends.get(legendIndex).title, ""};
        }
        String[] out = new String[]{legends.get(legendIndex).outputLegend()[legendSubIndex][0], legends.get(legendIndex).outputLegend()[legendSubIndex][1]};
        legendSubIndex++;
        if (legendSubIndex >= legends.get(legendIndex).outputLegend().length) {
            legendSubIndex = 0;
            legendIndex++;
            spaceTime = true;
        }
        return out;
    }
}
