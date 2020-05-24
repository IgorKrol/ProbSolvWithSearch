import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithms {

    public static int NODECOUNT = 1;

    public static String BFS(Node start){
        Queue<Node> queList = new LinkedList<Node>();
        queList.add(start);
        Hashtable<int[][], Node> closeList = new Hashtable<>();
        while(!queList.isEmpty()){
            Node n = queList.poll();
            closeList.put(n.getBlocks(),n);
            Queue<Node> op = n.operators();
            while(!op.isEmpty()){
                Node g = op.poll();
                NODECOUNT++;
                if(!closeList.containsKey(g) && !queList.contains(g)){
                    if(g.isGoal())
                        return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
                    queList.add(g);
                }
            }
        }
        return "no path\nNum: " + NODECOUNT;
    }
}
