package Day16

import com.sun.xml.internal.fastinfoset.util.StringArray

fun main(args: Array<String>) {
    val file = input()

    println("test part 1: ${parse("s1,x3/4,pe/b", progs = "abcde")} == baedc")
    println("part 1: ${parse(file)}")
    println()
    println("test part 2: ${parse("s1,x3/4,pe/b", progs = "abcde", repetitions = 1000000000)} == ceadb")
    println("part 2: ${parse(file, repetitions = 1000000000)}")
}

fun parse(input: String, progs: String= "abcdefghijklmnop", repetitions: Long = 1): String{
    val moves = input.split(",")//split the input at each "," to have each movements
    var dance = progs//init the dance (the iterative result) to the beginning arrangement of progs
    var repeat = -1.toLong()//for part 2 init the index of the repetition (when it become the beginning arrangement again)
    for(i in 1..repetitions){//until it did the whole repetition or it came back to the beginning compute the dance again
        dance = compute(moves, dance)
        if(dance == progs){//if it came back to the beginning store the index and break the loop
            repeat = i
            break
        }
    }

    if(repeat != -1.toLong()){//if an index was found only the last steps need to be done so repetitions%repeat
        dance = progs//init again
        for(i in 1..(repetitions%repeat)){//do only the necessary steps
            dance = compute(moves, dance)
        }
    }

    return dance
}

fun compute(moves: List<String>, progs: String): String{
    var dance = progs//init a new string at "progs"
    for(move in moves){//for each moves update the dance string
        val movement = move.substring(0, 1)
        val action = move.substring(1)
        dance = when(movement) {//compare the first character
            "s" -> spin(dance, action)
            "x" -> exchange(dance, action)
            "p" -> partner(dance, action)
            else -> dance//if nothing match do noting just return it as is
        }
    }

    return dance
}

//make the programs spin: for each spin the last character becomes the first
//so for N spins the N last character become the firsts
//if N is greater than the number of programs it do more than one turn, so just keep the modulus of N byt the number of
//programs
fun spin(progs: String, spins: String): String{
    val N = Integer.parseInt(spins)//parse "spins" to Int
    val l = progs.length//strore the length
    val beg = progs.substring((l - N)%l, l)//take the last "N" programs
    val end = progs.substring(0, (l - N)%l)//take the first "l - N" programs
    return beg + end//return the new concatenation
}

fun exchange(progs: String, x: String): String{
    //split the "ex" parater at "/" to have indices of the programs to swap, and then map it have the names (char) of the
    //programs to exchange
    val (A,B) = x.split("/").map { progs[Integer.parseInt(it)] }
    //map progs to change A by B and B by A then reduce the list of string produced to have one concatenated string
    return progs.map { when(it){ A -> B; B-> A; else -> it }.toString() }.reduce { acc, s -> acc+s }
}

//same than exchange but simply transform to char array to extract A and B
fun partner(progs: String, p: String): String{
    val (A,_,B) = p.toCharArray()
    return progs.map { when(it){ A -> B; B-> A; else -> it }.toString() }.reduce { acc, s -> acc+s }
}

fun input() = Utils.input(16)