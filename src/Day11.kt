import java.lang.Math.abs
import kotlin.math.max

fun main(args: Array<String>) {
    val s = Utils.input(11)

    println(parse(s))
    println(furthest(s))
}

fun furthest(s: String): Int{
    var dist = 0
    //each new step is separated by a "," so find the index the first "," to have the first distance
    var index = s.indexOf(',')
    while(index != -1){//for each index of "," compute the parse method from the beginning of the string to this ","
        val tmp = parse(s.substring(0,index));
        if(tmp > dist) dist = tmp//if the current distance is greater than the max, max become this distance
        index = s.indexOf(',', index+1)//find next index of ","
    }

    val tmp = parse(s);//at the end check the entire string
    if(tmp > dist) dist = tmp

    return dist
}

//parse a string input and return the distance (the shortest number of step to reach the last position of the child)
fun parse(s: String): Int{
    val dirs = s.split(",")
    //create a hashmap of string direction and int steps
    val moves = hashMapOf("n" to 0, "s" to 0, "ne" to 0, "nw" to 0, "se" to 0, "sw" to 0);
    for(d in dirs){//for each direction increment the corresponding value
        moves[d] = moves[d]!! + 1
    }

    //compute the vector of the three directions, and because one step at ne is one step at n plus one at se,
    //add ne to n and se to have only to direction (in a plan only two are needed)
    val ne = moves["ne"]!!-moves["sw"]!!
    val n = moves["n"]!!-moves["s"]!! + ne
    val se = moves["se"]!!-moves["nw"]!! +ne

    val steps = if(n*se < 0) abs(n-se) else max(abs(n),abs(se))

    return steps
}