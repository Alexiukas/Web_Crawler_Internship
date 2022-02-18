import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.List;

public class CSVWorker {
    public CSVWorker(String path, List<String[]> data)  {

        try{
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
            csvWriter.writeAll(data);
            csvWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
