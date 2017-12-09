import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Day7 {

    //A Tree is composed of a bottom tree that contains multiple subtrees that itself contains ...
    //A Tree contains as well a pointer to its parent tree so it can lead to the bottom tree
    public static class Tree{
        private String name;
        private int weight;//weight of the tree itself, not the weight of the subtrees included
        private Tree parentTree = null;//if the parentTree stays null it means it is the bottom tree
        private ArrayList<Tree> subTrees = new ArrayList<>();//array of subtrees

        public Tree(String name, int weight){
            this.name = name;
            this.weight = weight;
        }

        public void setParentTree(Tree t){
            parentTree = t;
        }

        public void addSubTree(Tree sub){
            subTrees.add(sub);
        }

        public String getName(){
            return name;
        }

        public Tree getParent(){
            return parentTree;
        }

        //recursive method that sum the weight of the actual tree and the total weight of each of its subtrees
        public int totalWeight(){
            int total = weight;//total start with its own weight
            ArrayList<Integer> weights = new ArrayList<>();//store all the total sub weights
            for(Tree sub: subTrees){//for each subtree compute its total weight and at it to the actual total
                int w = sub.totalWeight();
                weights.add(w);//add it as well to the storing array for later
                total += w;
            }

            //then with the storing array we need to check if all the sub weights are equal, else there is a problem
            //so print it

            //this function is recursive so the first weight to be displayed will be the one that is nearest to the top
            //of the tree and it is this value that we want to change (the next ones that are displayed are just repercussions)
            if(weights.size() != 0){
                for(int i = 0;i<weights.size();i++){//for each weight compare it to the next one to see if there is a difference
                    if(!weights.get(i).equals(weights.get((i+1)%weights.size()))){//if there is:
                        //we need to print the weight that it should have: its weight plus the difference of the two unequal
                        //total weights
                        //check against the one after the next one to know which one is different to all the others
                        //if the one at index i is different to both the next one and the one after, index i is the problem
                        if(!weights.get(i).equals(weights.get((i+2)%weights.size()))){
                            System.out.println("weight:"+(subTrees.get(i).weight - (weights.get(i)-weights.get((i+1)%weights.size()))));
                        }else{//else index i+1 is the problem (don't forget the %weights.size() to cycle around the array)
                            System.out.println("weight:"+(subTrees.get((i+1)%weights.size()).weight - (weights.get((i+1)%weights.size())-weights.get(i))));
                        }
                        break;
                    }
                }
            }
            return total;
        }
    }

    public static void main(String args[]) throws IOException {
        Tree bottom = input();
        System.out.println(bottom.getName());
        bottom.totalWeight();//compute the total weight of the bottom tree so if there is a problem of weight it will be printed
    }

    public static Tree input() throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day7input.txt"));//read the file
        HashMap<String, Tree> map = new HashMap<>();//create a hashMap to store all programs names and the Tree attached to it

        re.lines().forEach(s -> {//for each lines in the file
            String name = s.substring(0, s.indexOf(" "));//extract the name
            int weight = Integer.parseInt(s.substring(s.indexOf("(")+1, s.indexOf(")")));//and the weight
            map.put(name, new Tree(name, weight));//add to the map
        });

        re = new BufferedReader(new FileReader("resources/Day7input.txt"));//reset the file

        re.lines().forEach(s -> {
            if(s.contains("->")){
                String name = s.substring(0, s.indexOf(" "));
                String sub = s.substring(s.indexOf("->")+3, s.length());
                String subNames[] = sub.split(", ");
                Tree parent = map.get(name);
                for(String tmpName: subNames){
                    Tree tmpSubTree = map.get(tmpName);
                    parent.addSubTree(tmpSubTree);
                    tmpSubTree.setParentTree(parent);
                }
            }
        });

        Tree bottom = map.values().iterator().next();
        while(bottom.getParent() != null)
            bottom = bottom.getParent();

        return bottom;
    }
}
