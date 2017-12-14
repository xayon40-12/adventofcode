package Day13

import kotlin.math.abs

fun main(args: Array<String>) {
    input()
    println("severity: ${FireWall.trip()}")
    println("delay to pass: ${FireWall.safeTrip()}")
}

class FireWall{
    companion object {
        private val map = HashMap<Int, Int>()
        private var maxDepth = 0

        fun parse(s: String){
            val depthRange = s.split(": ")//split the line to have the depth and the range
            val depth = Integer.parseInt(depthRange[0])
            val range = Integer.parseInt(depthRange[1])
            map[depth] = range//add it to the map
            if(depth > maxDepth) maxDepth = depth
        }

        //return severity of the trip
        fun trip(): Int{
            var severity = 0
            for(depth in 0..maxDepth){//for each step (here t represent the time and the current step/depth) count severity
                if(map[depth] != null){//if there is a range compute severity, else do nothing (no scanner)
                    val range = map[depth]!!
                    if(cycling(depth, range) == 0) severity += depth*range
                }
            }

            return severity
        }

        //return the minimum delay to pass without get caught
        fun safeTrip(): Int{
            var delay = 0
            var pass = false
            while(!pass){//until it pass
                pass = true//presume it will be the good one
                for(depth in 0..maxDepth){//for each step (here t represent the time and the current step/depth) count severity
                    if(map[depth] != null){//if there is a range compute severity, else do nothing (no scanner)
                        val range = map[depth]!!
                        if(cycling(depth+delay, range) == 0){//if it get caught
                            delay++;//increment the delay
                            pass = false//say it wasn't the good one
                            break//leave the for
                        }
                    }
                }
            }

            return delay
        }

        fun cycling(t: Int, range: Int): Int{
            return abs((t+(range-1))%(2*(range-1))-(range-1))
        }
    }
}

fun input(){
    val file = Utils.inputWholeFile(13)
    for(line in file.split('\n'))//parse each line in file using FireWall
        FireWall.parse(line)
}