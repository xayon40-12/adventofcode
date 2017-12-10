import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day9 {
    public static void main(String args[]) throws IOException {
        String s = input();
        //clear "!"
        s = s.replaceAll("!.","");
        int trash = countTrash(s);
        //remove trash: the regex "<.*?>" find all parts that begin with "<" and end with ">" with anything between
        s = s.replaceAll("<.*?>","");
        int score = parse(s);
        System.out.println("score:"+score);
        System.out.println("nb trash:"+trash);
    }

    public static int parse(String s){
        int count = 0, depth = 0;
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i) == '{') depth++;//each that '{' means it goes deeper in the tree of blocks: increment depth
            if(s.charAt(i) == '}') count += depth--;//each '}' means it come back one level higher, so its score must
            //be added to the count (the score is the actual depth) them the depth needs to be decremented
        }
        return count;
    }

    public static int countTrash(String s){
        Matcher mat = Pattern.compile("<.*?>").matcher(s);//use a pattern matcher to find all trashes
        int count = 0;
        while(mat.find()){
            count += mat.group().length()-2;//sum everything in the trashes except the '<' and '>', so it is simply the
            //length the string (number of characters) minus 2
        }
        return count;
    }

    public static String input() throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day9input.txt"));
        return re.readLine();
    }
}
