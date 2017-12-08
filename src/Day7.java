import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Day7 {

    public static class Tree{
        private String name;
        private int weight;
        private Tree parentTree = null;
        private ArrayList<Tree> subTrees = new ArrayList<>();

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

        public int totalWeight(){
            int total = weight;
            ArrayList<Integer> weights = new ArrayList<>();
            for(Tree sub: subTrees){
                int w = sub.totalWeight();
                weights.add(w);
                total += w;
            }

            //this function is recursive so the first weight to be displayed will be the one that is nearest to the top
            //of the tree and it is this value that we want to change (the next ones that are displayed are just repercussions)
            if(weights.size() != 0){
                for(int i = 0;i<weights.size();i++){
                    if(!weights.get(i).equals(weights.get((i+1)%weights.size()))){
                        if(!weights.get(i).equals(weights.get((i+2)%weights.size()))){
                            System.out.println("weight:"+(subTrees.get(i).weight - (weights.get(i)-weights.get((i+1)%weights.size()))));
                        }else{
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
        bottom.totalWeight();
    }

    public static Tree input() throws IOException {
        BufferedReader re = new BufferedReader(new FileReader("resources/Day7input.txt"));//read the file
        HashMap<String, Tree> map = new HashMap<>();//create a hashMap to store all programs names and the Tree attached to it

        re.mark(0);
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
