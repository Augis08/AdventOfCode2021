import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputReader {

    public List<String> readFromFile(String path) {
        List<String> dataList = new ArrayList<>();
        try {
            File myFile = new File(path);
            Scanner sc = new Scanner(myFile);
            while (sc.hasNextLine()) {
                dataList.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading in data");
            e.printStackTrace();
        }
        return dataList;
    }

}
