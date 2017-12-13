package Day12

fun main(args: Array<String>) {
    input()
    println("countGroup: ${Prog.countGroupByName("0")}")
    println("nbGroup: ${Prog.countNbGroup()}")
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

        fun countGroupByName(name: String, inGroup: HashSet<Prog> = HashSet()): Int{//return the countGroup of Prog "name"
            return get(name)?.countGroup(inGroup) ?: 0
        }

        fun countNbGroup(): Int{
            var count = 0;
            val names = ArrayList<String>(map.keys)
            while(names.size != 0){//until there is no names left, take the first name, find the group, remove group names
                count++ //for each group
                val inGroup = HashSet<Prog>()//prepare an empty hashSet for countGroupByName
                countGroupByName(names[0], inGroup)//use countGroupByName to fill inGroup with all Prog in that group
                inGroup.forEach { names.remove(it.name) }//remove each names of that group from names list
            }

            return count
        }
    }
}

fun input(){
    var file = Utils.inputWholeFile(12);//get the whole file
    for(line in file.split("\n"))//parse each lines
        Prog.parse(line)
}
