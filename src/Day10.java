import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day10 {
    public static void main(String args[]) throws IOException {
        //part 1
        String s = Utils.input(10);
        int[] hash = parse(s, 1);
        System.out.println(hash[0]*hash[1]);

        //part 2
        s = prepareToAscii(s);
        hash = parse(s,64);
        System.out.println(dense(hash));
    }

    public static String prepareToAscii(String s){
        ArrayList<Integer> list = new ArrayList<>();//create a list of ascii values
        for(char c:s.toCharArray()){//by iterating over each characters
            list.add((int)c);
        }
        //them concat it separated by "," and add the ",17,31,73,47,23"
        s = list.stream().map(i -> i.toString()).reduce("",(a,b) -> a.equals("")?b:a+","+b).concat(",17,31,73,47,23");
        return s;
    }

    public static String dense(int[] hash){
        String denseHash = "";
        for(int i = 0;i<16;i++){//for each block of 16
            int xor = hash[i*16];
            for(int j = 1;j<16;j++){//xor each values
                xor ^= hash[i*16+j];
            }
            denseHash += String.format("%02x", xor);//add the hexadecimal string to the dense hash
        }

        return denseHash;
    }

    public static int[] parse(String s, int repeat){
        //map input as array of integer
        Integer[] lengths = Arrays.stream(s.split(",")).map(i -> Integer.parseInt(i)).toArray(Integer[]::new);
        int[] list = new int[256];//list of 256 elements
        int skipSize = 0;
        //initialize the list
        for(int i = 0;i<list.length;i++)
            list[i] = i;

        int index = 0;
        for(int r = 0;r<repeat;r++){
            for(int i:lengths){//for each length reverse the block of length i starting at index
                for(int j = 0;j<i/2;j++){//to do that swap symmetrically from the middle the values of the block
                    //because it is symmetric it just need to loop from the begginning to the middle so 0 to i/2
                    int tmp = list[(index+j)%list.length];//store the value of the first part
                    list[(index+j)%list.length] = list[(index+i-1-j)%list.length];//put the value of the second part in the
                    //one of the first part
                    list[(index+i-1-j)%list.length] = tmp;//put the stored value in the one of the second part
                    //index of the second part is the subtraction of the last index of the block, which is the index of the
                    //beginning plus the length i minus 1, and the moving index j: so it is index+i-1-j
                }
                index = (index+i+skipSize++)%list.length;//increment index by the length i plus the skipSize, and increment
                //the skipSize by 1
            }
        }

        return list;//product of the two first elements
    }
}
