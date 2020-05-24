import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Node node = new Node("", null, new FileParser("input1.txt").parse());
        int[][] gg = {{1,2,3,4},{5,6,7,8},{9,10,11,0}};
//        Node g = new Node("g", null, gg);
        System.out.println(Algorithms.BFS(node));




//        int[][] a ={{1,2},{0,3}};
//        int[][] b ={{1,2},{0,3}};
//        Node an = new Node("",null, a);
//        Node bn = new Node("",null, b);
//        System.out.println(an.equals(bn));

    }

//    BFS(Node start, Vector Goals){
//        Queue L = new Queue();
//    }
}
