import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Day6 {

    public static void distribute(Integer[] tab){
        int max = 0, index = 0;
        for(int i = 0;i<tab.length;i++){//for each value check if it is greater than max
            if(tab[i] > max){//if it is store the new max and its index
                max = tab[i];
                index = i;
            }
        }
        tab[index] = 0;//remove the value at index
        for(int i = index+1;i<max+index+1;i++){//and add 1 to each indices of tab circularly until max was distributed
            tab[i%tab.length]++;// %tab.length to cycle through the array
        }
    }

    //return a string that is the concatenation of each values inside the array separated by ":"
    //why the need to add a separator:
    //that string need to be different for each combination of value
    //example for arrays that contains 2 values:
    // -> [1, 11] ans [11, 1] they are different because the order is different
    //but if we simply concat them the result is the same "1"+"11"="111" and "11"+"1"="111"
    //by adding a separator the problem is solved: "1"+":"+"11"="1:11" and "11"+":"+"1"="11:1" -> the results are different
    public static String hashString(Integer[] i){
        return Arrays.stream(i).map(e -> e.toString()).reduce("", (a,b) -> a+":"+b);
    }

    public static void main(String args[]){
        //Part 1
        Integer[] tab = input();
        int count = 0;
        HashSet<String> set = new HashSet<>();//use a Set because it store only one copy of each key, and hash because
        //to search if a key already exist it will be in constant time
        //use a String as key because an array is a pointer and equality between pointer is most of the time comparison
        //between both addresses and not what they contains

        //store the String that will be used
        String description = hashString(tab);
        for(; !set.contains(description); count ++){//for each new distribution continue until a configuration as been already seen
            set.add(description);//add the string that describe/identify the array to the set
            distribute(tab);//do the distribution
            description = hashString(tab);//update the value of the description so it machs the new array
        }
        System.out.println(count);

        //Part 2
        //same here but use a hashMap to store the count for each key description
        tab = input();
        count = 0;
        HashMap<String, Integer> map = new HashMap<>();
        description = hashString(tab);
        for(; !map.containsKey(description); count ++){
            map.put(description, count);//store the description and the count linked to it
            distribute(tab);
            description = hashString(tab);
        }
        System.out.println(count-map.get(description));//subtract the last count to the one that has been stored when
        //the same description was seen before
    }

    public static Integer[] input(){
        String s = "4\t1\t15\t12\t0\t9\t9\t5\t5\t8\t7\t3\t14\t5\t12\t3";
        //split the input at each new line and map it to an array on Integer
        return Arrays.stream(s.split("\t")).map(i -> Integer.parseInt(i)).toArray(Integer[]::new);
    }
}
