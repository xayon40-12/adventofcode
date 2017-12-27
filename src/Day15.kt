package Day15

fun main(args: Array<String>) {
    val file = input()//store input as a string
    val (startA, startB) = parse(file)//parse the input string to have both beginning numbers

    println("match: ${compute(65, 8921)} == 588")//use subject example to check the program
    println("match: ${compute(startA, startB)}")
    println()
    println("match: ${compute2(65, 8921)} == 309")//use subject example to check the program
    println("match: ${compute2(startA, startB)}")
}

fun nextA(l: Long) = (l*16807)%2147483647//function to compute next A
fun nextB(l: Long) = (l*48271)%2147483647//function to compute next B
//to compare the first N bits first trunc both number with an "and" logic operator and a number with only all 1§ first
//bits at 1 and all the other at 0 (by shifting left 1 16 times the 17th bit will be at 1 and by substracting 1
//it will pass at 0 and all the fist 16 bits will pass at 1, therefor the "bits" parameter is by default (1 shl 16) -1 )
fun compareFirst(l1: Long, l2: Long, bits: Long = (1 shl 16)-1) = (l1 and bits) == (l2 and bits)

//parse the input to return the 2 beginning numbers
fun parse(s: String): Pair<Long, Long>{
    //ex:     Generator A starts with 289
    //index:  0         1 2      3    4
    val lines = s.split("\n")//split at "\n" to have both input lines
    val startA = lines[0].split(" ")[4]//then split at each " " to have an array like the exemple above
    val startB = lines[1].split(" ")[4]

    //then return a pair of the parseLong of the number at index 4 for each line
    return Pair(java.lang.Long.parseLong(startA), java.lang.Long.parseLong(startB))
}

//do the 4e7 = 40000000 steps for each program and compare each
fun compute(startA: Long, startB: Long, max: Long = 4e7.toLong()): Long{
    var count: Long = 0
    var A = startA//init A
    var B = startB//init B
    for(i in 1..max){//for each steps
        A = nextA(A)//compute the next value of A
        B = nextB(B)//compute the next value of B
        if(compareFirst(A, B)) count ++ //if the first 16 bits of A and B are equals then increment count
    }

    return count
}

//same here but there is only 5e6 = 5000000 steps and there is a condition of comparison for A and B
fun compute2(startA: Long, startB: Long, max: Long = 5e6.toLong()): Long{
    var count: Long = 0
    var A = startA
    var B = startB
    for(i in 1..max){
        A = nextA(A)
        B = nextB(B)
        while(A%4 != 0.toLong()) A = nextA(A)//compute next A until it is dividable by 4
        while(B%8 != 0.toLong()) B = nextB(B)//compute next B until it is dividable by 8
        if(compareFirst(A, B)) count ++
    }

    return count
}

fun input() = Utils.inputWholeFile(15)