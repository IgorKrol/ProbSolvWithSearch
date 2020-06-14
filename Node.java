import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Node implements Comparable<Node>, Comparator<Node> {
    /*      Static Info     */
    public static int[][] GOAL;
    public static Vector<Integer> BLACK;
    public static Vector<Integer> RED;

    /*       Parameters       */
    int cost = 0;
    int eval = 0;
    int[][] blocks;
    Node parent;
    String name;    //  NUM-DIRACTION. 4exmple: 7L
    boolean isOut = false;		//for IDA*, marks if node was already out or not.



    public Node(String name, Node parent, int[][] mat){
        this.name = name;
        this.blocks = mat;
        this.parent = parent;
        setCost();
        setEval();
        isOut = false;
    }
    /*          Set Goal for the puzzle at parse            */
    public static void setGOAL(int n, int m){
        GOAL = new int[n][m];
        int c = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                GOAL[i][j] = c++;
            }
        }
        GOAL[n-1][m-1] = 0;
    }
    public boolean isGoal(){
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if(blocks[i][j] != GOAL[i][j])
                    return false;
            }
        }
        return true;
    }
    /*          Deep equals for game board              */
    @Override
    public boolean equals(Object o){
        Node second = (Node)o;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if(blocks[i][j] != second.blocks[i][j])
                    return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode(){
        int c = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                c+=blocks[i][j]*(i+1)*(j+1);
            }
        }
        return c;
    }
    /*      Create new possible nodes       */
    public Queue<Node> operators(){
        Queue<Node> q = new LinkedList<>();
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if(blocks[i][j] == 0){ //   L->U->R->D
                    if(j<blocks[0].length-1 && !name.contains("R")){
                        if(BLACK==null || !BLACK.contains(blocks[i][j+1]))
                            q.add(new Node(""+blocks[i][j+1]+"L",this,  Tools.swap(i,j,i,j+1,Tools.Clone(this.blocks))));
                    }
                    if(i<blocks.length-1 && !name.contains("D")){
                        if(BLACK==null || !BLACK.contains(blocks[i+1][j]))
                            q.add(new Node(""+blocks[i+1][j]+"U",this, Tools.swap(i,j,i+1,j,Tools.Clone(this.blocks))));
                    }
                    if(j>0 && !name.contains("L")){
                        if(BLACK==null || !BLACK.contains(blocks[i][j-1]))
                            q.add(new Node(""+blocks[i][j-1]+"R",this,  Tools.swap(i,j,i,j-1,Tools.Clone(this.blocks))));
                    }
                    if(i>0 && !name.contains("U")){
                        if(BLACK==null || !BLACK.contains(blocks[i-1][j]))
                            q.add(new Node(""+blocks[i-1][j]+"D",this,  Tools.swap(i,j,i-1,j,Tools.Clone(this.blocks))));
                    }
                }
            }
        }
        return q;
    }
    /*      Creates path after goal was found       */
    public String getPath(){
        if(parent.parent==null)
            return name;
        else
            return parent.getPath()+"-"+name;
    }
    /*      Getters/Setters     */
    public int[][] getBlocks(){
        return blocks;
    }

    public int getCost() {
        return cost;
    }

    public Node getParent() {
        return parent;
    }
    
    public boolean isOut() {
    	return isOut;
    }
    
    public void markOut() {
    	isOut = true;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }
    /*      Set cost for node at initialization          */
    public void setCost(){
            if(name != null && name != "") {
                int c = Integer.parseInt(name.substring(0,name.length()-1));
                if (RED.contains(c))
                    cost = parent.getCost() + 30;
                else
                    cost = parent.getCost() + 1;
            }
            else{
                cost = 0;
            }
        }

    public void setName(String name) {
        this.name = name;
    }

    public int getEval(){
        return eval;
    }

    public void setEval(){
        int res = 0;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                for (int i1 = 0; i1 < blocks.length; i1++) {
                    for (int j2 = 0; j2 < blocks[0].length; j2++) {
                        if(blocks[i][j]==GOAL[i1][j2] && blocks[i][j] != 0){
                            int temp = Math.abs(i-i1)+Math.abs(j-j2);
                            if(RED.contains(blocks[i][j])) temp = temp * 30;
                            res += temp;
                        }
                    }
                }
            }
        }
        eval = res + cost;
    }
    /*      Comparators     */
    @Override
    public int compareTo(Node o) {
        if(this.getEval() > o.getEval())
            return 1;
        if(this.getEval() < o.getEval())
            return -1;
        return 0;
    }

    @Override
    public int compare(Node x, Node y) {
        if(x.getEval() > y.getEval())
            return 1;
        if(x.getEval() < y.getEval())
            return -1;
        return 0;
    }
    @Override
    public String toString(){
//        return ""+Tools.matString(blocks);
        return ""+getEval();
    }
}
