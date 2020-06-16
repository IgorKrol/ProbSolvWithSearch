import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;

public class Ex1 {

    public static void main(String[] args) throws IOException {
        Node node = new Node("", null, new FileParser("input2.txt").parse());
    	String result = Algorithms.run(node);
	    BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
	    System.out.println(result);
	    writer.write(result);    
	    writer.close();
    }
}
