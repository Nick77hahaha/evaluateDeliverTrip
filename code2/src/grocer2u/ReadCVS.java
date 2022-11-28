package grocer2u;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ReadCVS {
 public void readCsv() throws IOException {
    String csvFile = "C:\\Users\\user\\Desktop\\csv\\customerOrders.csv";
    String line = "";
    String cvsSplitBy = ",";
    BufferedReader br = new BufferedReader(new FileReader(csvFile));
    while ((line = br.readLine()) != null) {
        String[] customerOrder = line.split(cvsSplitBy);//line.split(",");
        System.out.println(Arrays.toString(customerOrder));
        //System.out.println(customerOrder[0][0]);
        //System.out.println(customerOrder[1]);
    }
//        String path="C:\\Users\\user\\Desktop\\csv\\customerOrders.csv";
//        String line="";
//        try {
//            System.out.println("CSVCSV");
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            while((line = br.readLine()) != null){
//                String[] values = line.split(",");
//                System.out.println("CSVCSV" + values[1]);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
 }
}

