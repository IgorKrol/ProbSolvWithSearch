import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Node node = new Node(new FileParser("input.txt").parse());
    }

//    BFS(Node start, Vector Goals){
//        Queue L = new Queue();
//    }
}
