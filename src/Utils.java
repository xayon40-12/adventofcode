import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    public static String input(int day) throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day"+day+"input.txt"));
        return re.readLine();
    }
}
