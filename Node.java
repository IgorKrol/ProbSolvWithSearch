import java.util.Queue;
import java.util.Vector;

public class Node {
    /*      Static Info     */
    public static int[][] GOAL;
    public static Vector<Integer> BLACK;
    public static Vector<Integer> RED;
    /*   Parameters   */
    int[][] blocks;
    Node parent;

    public Node(int[][] m){
        blocks = m;
    }

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
        return blocks.equals(GOAL);
    }

    public boolean equals(Node other){
        return blocks.equals(other);
    }


}
