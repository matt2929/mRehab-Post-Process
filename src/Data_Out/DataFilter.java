package Data_Out;

import Data_Models.DataPoint;

public  abstract class DataFilter {
    public boolean Filter(DataPoint dp) {
        return true;
    }
}
