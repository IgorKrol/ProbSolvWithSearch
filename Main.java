import java.io.IOException;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws IOException {
        Node node = new Node("", null, new FileParser("input6.txt").parse());

        System.out.println(Algorithms.BFS(node));
        System.out.println(Algorithms.DFID(node));
        System.out.println(Algorithms.UCS(node));
        System.out.println(Algorithms.IDA(node));
        System.out.println(Algorithms.DFBnB(node));

//
//        int[][] gg = {{1,2,3,4},{5,6,7,8},{9,10,11,0}};
//        Node g = new Node("g", null, gg);
//        int[][] bb = {{11,2,3,4},{5,6,7,8},{9,10,1,0}};
//        Node b = new Node("b", null, bb);
//        PriorityQueueNode p = new PriorityQueueNode();

//        s.add(g);
    }
}
