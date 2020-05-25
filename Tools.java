import java.util.Arrays;
import java.util.Vector;


public class Tools {
    /*              Deep vector print               */
    public static <T> void printVec(Vector<T> v){
        System.out.println(Arrays.deepToString(v.toArray()));
    }
    /*             Parse number from Nodes name : 11D -> 11             */
    public static int parseNum(String s){
        return Integer.parseInt(s.substring(0,s.length()-1));
    }
    /*              Deep matrix to string           */
    public static String matString(int[][] mat){
        String res = "";
        for (int i = 0; i < mat.length; i++) {
            res+= "[";
            for (int j = 0; j < mat[0].length; j++) {
                res+= " " + mat[i][j] + " ";
            }
            res+= "]\n";
        }
        return res;
    }
    /*             Deep matrix clone                 */
    public static int[][] Clone(int[][] mat){
        int[][] res = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                res[i][j] = mat[i][j];
            }
        }
        return res;
    }
    /*              Matrix swap                       */
    public static int[][] swap(int i1, int j1, int i2, int j2, int[][] mat){
        int temp = mat[i1][j1];
        mat[i1][j1] = mat[i2][j2];
        mat[i2][j2] = temp;
        return mat;
    }
}
