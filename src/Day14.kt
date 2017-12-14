package Day14

fun main(args: Array<String>) {
    assert(compute("flqrgnkx") == 8108)//check the example of the subject

    val input = input()

    println("number of full squares: ${compute(input)}")
    println("number of groups: ${countRegions(input)}")
}

fun parse(s: String): Int{
    //use what was made in Day10 on Knot Hash to compute the knot hash of a string
    val knotHash = Day10.dense(Day10.parse(Day10.prepareToAscii(s), 64))
    var sum = 0
    for(c in knotHash.toCharArray()){//for each char so each hex digit count the number of "1" and add it to the sum
        val digit = Integer.parseInt(c.toString(), 16)
        for(i in 0..3)
            if(digit and (1 shl i) > 0) sum++
    }

    return sum
}

//for a string compute the number of 1 in the whole grid it create
fun compute(s: String): Int{
    var sum = 0
    for(i in 0..127)//for each line of the grid add "-"+i to the input string and add the parse of the string to the sum
        sum += parse("$s-$i")

    return sum
}

fun countRegions(s: String): Int{
    var grid = ""
    //generate a grid with empty an full squares
    for(i in 0..127){//for each lines
        val knotHash = Day10.dense(Day10.parse(Day10.prepareToAscii("$s-$i"), 64))//compute the knot hash
        for(c in knotHash.toCharArray()){//for each digit of the line find the 0 and 1 bits and add a " " or "#" corresponding
            val digit = Integer.parseInt(c.toString(), 16)
            for(i in 0..3){
                if (digit and (1 shl i) > 0) grid += "#"
                else grid += " "
            }
        }
        if(i != 127) grid += "\n"
    }
    var count = 0
    //TODO count how many group there is
    return count
}

fun input(): String{
    return Utils.input(14)
}