import java.util.ArrayList;
import java.util.Arrays;

public class PriorityQueueNode {

    ArrayList<Node> pl;

    public PriorityQueueNode(){
        pl = new ArrayList<Node>();
    }


    public void add(Node n) {
        pl.add(n);
        pl.sort(Node::compareTo);
    }

    public boolean isEmpty() {
        return pl.isEmpty();
    }

    public Node poll() {
        Node t = pl.get(0);
        pl.remove(0);
        return t;
    }

    public boolean contains(Node g) {
        for(Node n : pl){
            if(n.equals(g)) return true;
        }
        return false;
    }

    public void swapForLowerValue(Node n){
        for (int i = 0; i < pl.size(); i++) {
            Node t = pl.get(i);
            if(t.equals(n)){
                if(t.compareTo(n)>0){
                    pl.remove(i);
                    this.add(t);
                    break;
                }
            }
        }
    }

    public String toString(){
        return Arrays.deepToString(pl.toArray());
    }

    public int size() {
        return pl.size();
    }
}
