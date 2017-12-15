package Day14

import com.sun.org.apache.xpath.internal.operations.Bool

fun main(args: Array<String>) {
    println("compute(\"flqrgnkx\")=${compute("flqrgnkx")} == 8108")//check the example of the subject
    println("countRegions(\"flqrgnkx\")=${countRegions("flqrgnkx")} == 1242")//check the example of the subject

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
    var grid = Array(128, {IntArray(128)})
    //generate a grid with empty an full squares
    for(y in 0..127){//for each lines
        var x = 0
        val knotHash = Day10.dense(Day10.parse(Day10.prepareToAscii("$s-$y"), 64))//compute the knot hash
        for(c in knotHash.toCharArray()){//for each digit of the line find the 0 and 1 bits and put it in grid
            val digit = Integer.parseInt(c.toString(), 16)
            for(i in 3 downTo 0){//from 3 to 0 because it needs to go from left to right
                if (digit and (1 shl i) > 0) grid[y][x] = 1
                else grid[y][x] = 0
                x++
            }
        }
    }
    var count = 0

    //for each square equal to 1 in the grid remove the block attached and increment count if a block was removed
    for(y in 0..127){
        for(x in 0..127){
            if(removeBolck(grid, x, y)) count++
        }
    }

    return count
}

fun removeBolck(grid: Array<IntArray>, x: Int , y: Int, minx: Int = 0, miny: Int = 0, maxx: Int = 127, maxy: Int = 127): Boolean{
    if((minx<=x) and (x<=maxx) and (miny<=y) and (y<=maxy)){//if the coordinate is inside the square
        if(grid[x][y] == 1) {//if the square is full
            grid[x][y] = 0//empty the square
            removeBolck(grid, x + 1, y)//then do the same for each contiguous squares
            removeBolck(grid, x, y + 1)
            removeBolck(grid, x - 1, y)
            removeBolck(grid, x, y - 1)

            return true//and return true because a block was removed
        }
    }

    return false//if both condition weren't true no block was removed so return false
}

fun input(): String{
    return Utils.input(14)
}