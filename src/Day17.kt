fun main(args: Array<String>) {
    val steps = input()
    val listTest = generate(3)//use 3 step like in the example on the subject
    val after2017Test = listTest[listTest.indexOf(2017)+1]//find the index of "2017" and take the value after
    val list = generate(steps)
    val after2017 = list[list.indexOf(2017)+1]//same here
    println("test part 1: ${after2017Test} == 638")
    println("part 1: ${after2017}")
    println()
    val part2 = generate2(steps.toLong(), 50000000)
    println("part 2: ${part2}")
}

fun generate2(steps: Long, toInsert: Long = 2017): Long{
    var after0: Long = 1//init with 1 wich is the fist value that appear after 0
    var pos: Long = 1//init the pos at 1 wich is the fist position that appear after 0
    for(i in 2..toInsert){//then loop over all the value
        pos = (pos+steps)%i + 1 //compute the new step with i to cycle ("i" correspond to the length of the list)
        if(pos == 1L) after0 = i // if the position is 1 update the value of "after0" with the current value "i"
    }
    return after0
}

fun generate(steps: Int, toInsert: Int = 2017): List<Int>{
    val list = ArrayList<Int>()//create the list that will contains all elements
    list.add(0)//init the list with 0
    var pos = 0 + 1//init current position, +1 because we want to insert after
    for(i in 1..toInsert){
        list.add(pos, i)//insert the value at the current position
        pos = (pos+steps)%list.size + 1 //add steps to pos and cycle through the list, then add 1 to insert after
    }

    return list
}

fun input(): Int{
    val file = Utils.input(17)
    return Integer.parseInt(file)
}