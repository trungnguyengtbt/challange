package vn.com.taptap;

import vn.com.taptap.functions.ImportData;
import vn.com.taptap.functions.SimulateData;

import java.util.function.Function;

public class ImportDataApplication {
    public static void main(String[] args) {
        /*
        * Simulate BUS data file
        * */
        int total=100000;
        Function simulateData=new SimulateData();
        simulateData.apply(total);

        /*
         * Import BUS data to DataCenter
         * */
        Function importData=new ImportData();
        importData.apply("data/import/import.txt");
    }
}
