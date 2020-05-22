import java.util.Arrays;
import java.util.Vector;


public class Tools {

    public static <T> void printVec(Vector<T> v){
        System.out.println(Arrays.deepToString(v.toArray()));
    }
}
