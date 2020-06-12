import java.util.*;

public class Algorithms {

    public static int NODECOUNT = 1;
    public static int CHECK = 0;

    public static String UCS(Node start){
        PriorityQueueNode queList = new PriorityQueueNode();
        queList.add(start);
        HashSet<Node> closedList = new HashSet<>();
        while(!queList.isEmpty()){
            Node n = queList.poll();
            if(n.isGoal())
                return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
            closedList.add(n);
            Queue<Node> op = n.operators();
            while (!op.isEmpty()){
                Node g = op.poll();
                NODECOUNT++;
                if(!closedList.contains(g) && !queList.contains(g)){
                    queList.add(g);
                }
                else{
                    if(queList.contains(g)){
                        queList.swapForLowerValue(g);
                    }
                }
            }
        }
        return "no path\nNum: " + NODECOUNT;
    }

    /**************** DFID ****************/
    public static String DFID(Node start){
        String result;
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            HashSet<int[][]> ht = new HashSet<>();
            result = Limited_DFS(start, depth, ht);
            if(!result.equals("Cutoff"))
                return result;
        }
        return "no path\nNum:" + NODECOUNT;
    }

    private static String Limited_DFS(Node n, int limit, HashSet<int[][]> h){
        if(n.isGoal())
            return n.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + n.getCost();
        if(limit == 0)
            return "Cutoff";
        h.add(n.getBlocks());
        boolean isCutoff = false;
        Queue<Node> op = n.operators();
        while(!op.isEmpty()){
            Node g = op.poll();
            NODECOUNT++;
            if(h.contains(g.getBlocks()))
                continue;
            String result = Limited_DFS(g,limit-1,h);
            if(result.equals("Cutoff"))
                isCutoff=true;
            else{
                if(!result.equals("fail")){
                    return result;
                }
            }
        }
        h.remove(n.getBlocks());
        if (isCutoff){
            return "Cutoff";
        }
        else{
            return "fail";
        }
    }

    /**************** BFS ****************/
    public static String BFS(Node start){
        Queue<Node> queList = new LinkedList<Node>();
        queList.add(start);
        HashSet<int[][]> closeList = new HashSet<>();
        while(!queList.isEmpty()){
            Node n = queList.poll();
            closeList.add(n.getBlocks());
            Queue<Node> op = n.operators();
            while(!op.isEmpty()){
                Node g = op.poll();
                NODECOUNT++;
                if(!closeList.contains(g) && !queList.contains(g)){
                    if(g.isGoal())
                        return g.getPath() + "\nNum: " + NODECOUNT + "\nCost: " + g.getCost();
                    queList.add(g);
                }
            }
        }
        return "no path\nNum: " + NODECOUNT;
    }
}
