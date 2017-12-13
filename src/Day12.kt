package Day12

fun main(args: Array<String>) {
    input()
    println("countGroup: "+Prog.get("0")?.countGroup())
}

//class Prog that contains its name and an array of others Prog that it is connected
class Prog(val name: String){
    private val connected = ArrayList<Prog>()

    fun add(p: Prog){//add a Prog to the list of connected prog
        connected.add(p)
    }

    //recursive function that count the total number of Prog interconnected
    fun countGroup(alreadyDone: HashSet<Prog> = HashSet()): Int{
        if(alreadyDone.contains(this)) return 0 //if the set already contains this, return 0 it is already counted

        alreadyDone.add(this)//add this to alreadyDone to be sure not to countGroup it again
        var count = 1//countGroup itself
        for(p in connected){
            count += p.countGroup(alreadyDone)//add the countGroup of each connected Prog to the count
        }

        return count;
    }

    //static part for parsing
    companion object {
        private val map = HashMap<String, Prog>()//map that store Prog by name

        private fun check(prog: String) {//if "prog" doesn't exist, create a Prog in map with this name
            if (map[prog] == null) map[prog] = Prog(prog)
        }

        fun get(prog: String): Prog?{//return Prog by name
            return map[prog]
        }

        //parse the lines in the input file
        fun parse(s: String){
            //line exemple:    0 <-> 1, 2, 3, ...
            val pipe = s.split(" <-> ")//split the current prog left to "<->" and the connected prog
            val prog = pipe[0]//put the name of the current prog int "prog"
            val toConnect = pipe[1].split(", ")//split the different connected prog names in an array
            check(prog)//initialize current prog by using check method
            for(p in toConnect){//for each connected prog name add it to the array of connected program of the actual prog
                check(p)
                map[prog]!!.add(map[p]!!)
            }
        }
    }
}

fun input(){
    var file = Utils.inputWholeFile(12);//get the whole file
    for(line in file.split("\n"))//parse each lines
        Prog.parse(line)
}
