package grocer2u;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Grocer2UMain {
    public static void main(String[] args) throws IOException {
    //    ReadCVS csv = new ReadCVS();
     //   csv.readCsv();
        // 1. new Grocer2UController
        Controller controller = new Grocer2UController();
        // 2. new TUI
        new TUI(controller);
    }
}
