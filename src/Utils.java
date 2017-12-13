import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static String input(int day) throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day"+day+"input.txt"));
        return re.readLine();
    }

    public static String inputWholeFile(int day) throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day"+day+"input.txt"));
        StringBuilder sb = new StringBuilder();
        String line = re.readLine();
        while(line != null){
            sb.append(line);
            line = re.readLine();
            if(line != null) sb.append(('\n'));
        }

        return sb.toString();
    }
}
