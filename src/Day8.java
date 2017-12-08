import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class Day8 {
    public static void main(String args[]) throws FileNotFoundException {
        input();
        System.out.println("highest:"+Proc.highestValue());
        System.out.println("highest ever:"+Proc.highestValueEver());
    }

    //this class manage all the parsing and the register actions
    public static class Proc{
        private static HashMap<String, Integer> registers = new HashMap<>();//store register by name and value
        private static int highestEver = Integer.MIN_VALUE;//store the highest value ever during the process

        public static void parse(String action){
            //ex:         js inc 257 if wn < 9
            //indices:    0  1   2   3  4  5 6
            String act[] = action.split(" ");//split at each space to have an array with the indices as above
            if(compare(act))//if the comparison at the right of the "if" in the String if true
                proceed(act);//proceed the incrementing/decrementing
        }

        public static int highestValue(){
            if(registers.size() == 0)return 0;//if there is no register, everything is 0 the highest is 0
            return registers.values().stream().reduce(Integer.MIN_VALUE, (a,b) -> b>a?b:a);//reduce and always keep the highest
        }

        public static int highestValueEver(){
            return highestEver;
        }

        private static void proceed(String act[]){
            String reg = act[0];
            check(reg);
            //the new value is the sum of the actual value and the value at index 2 multiplied by 1 or -1 depending
            //of the value at index 1: inc is 1 ans dec is -1
            int val = registers.get(reg)+(act[1].equals("inc")?1:-1)*Integer.parseInt(act[2]);
            registers.put(reg, val);//update the register to the new value
            if(val > highestEver) highestEver = val;//update highestEver if val is greater to it
        }

        private static boolean compare(String act[]){
            //for each indices of the act array refer to the example in parse() method
            String reg = act[4];
            check(reg);
            int regval = registers.get(reg), val = Integer.parseInt(act[6]);
            String comp = act[5];
            switch(comp){//switch over the different comparison and return the result of the comparison
                case "==": return regval == val;
                case "!=": return regval != val;
                case "<": return regval < val;
                case ">": return regval > val;
                case "<=": return regval <= val;
                case ">=": return regval >= val;
                default: return false;
            }
        }

        //verify if the register already exist, if not create it and initialize it at 0
        private static void check(String reg){
            if(!registers.containsKey(reg))
                registers.put(reg, 0);
        }
    }

    public static void input() throws FileNotFoundException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day8input.txt"));
        //fo each line in the file parse it
        re.lines().forEach(l -> Proc.parse(l));
    }
}
