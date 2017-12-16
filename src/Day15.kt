package Day15

fun main(args: Array<String>) {
    val file = input()
    val (startA, startB) = parse(file)

    println("match: ${compute(65, 8921)} == 588")
    println("match: ${compute(startA, startB)}")
    println()
    println("match: ${compute2(65, 8921)} == 309")
    println("match: ${compute2(startA, startB)}")
}

fun nextA(l: Long) = (l*16807)%2147483647
fun nextB(l: Long) = (l*48271)%2147483647
fun compareFirst(l1: Long, l2: Long, bits: Long = (1 shl 16)-1) = (l1 and bits) == (l2 and bits)

fun parse(s: String): Pair<Long, Long>{
    //ex:     Generator A starts with 289
    //index:  0         1 2      3    4
    val lines = s.split("\n")
    val startA = lines[0].split(" ")[4]
    val startB = lines[1].split(" ")[4]

    return Pair(java.lang.Long.parseLong(startA), java.lang.Long.parseLong(startB))
}

fun compute(startA: Long, startB: Long, max: Long = 4e7.toLong()): Long{
    var count: Long = 0
    var A = startA
    var B = startB
    for(i in 1..max){
        A = nextA(A)
        B = nextB(B)
        if(compareFirst(A, B)) count ++
    }

    return count
}


fun compute2(startA: Long, startB: Long, max: Long = 5e6.toLong()): Long{
    var count: Long = 0
    var A = startA
    var B = startB
    for(i in 1..max){
        A = nextA(A)
        B = nextB(B)
        while(A%4 != 0.toLong()) A = nextA(A)
        while(B%8 != 0.toLong()) B = nextB(B)
        if(compareFirst(A, B)) count ++
    }

    return count
}

fun input() = Utils.inputWholeFile(15)