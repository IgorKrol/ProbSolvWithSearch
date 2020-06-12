import java.io.*;
import java.util.Arrays;
import java.util.Queue;
import java.util.Vector;

public class FileParser {
    BufferedReader br;
    /*          Init Buffer         */
    public FileParser(String path){
        File file = new File(path);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.err.println("No input file");
        }

    }
    /*          Parse file as asked         */
    public int[][] parse() throws IOException {
        String line;
        Vector<Integer> black, red;
        int[][] mat;
        for(int i = 0; i<3; i++)
            br.readLine();

        /*        Mat,GOAL init      */
        String[] nm = br.readLine().split("x");   //mat [N][M]
        int rows = Integer.parseInt(nm[0]), colm = Integer.parseInt(nm[1]);
        mat = new int[rows][colm];
        Node.setGOAL(rows,colm);

        /*        Colors        */
        line = br.readLine();   //black
        Node.BLACK = parseColors(line.substring(6));
        line = br.readLine();   //red
        Node.RED = parseColors(line.substring(4));

        /*        Matrix        */
        while(br.ready()) {
            for(int i=0; i<mat.length;i++){
                String[] lineNumbers = br.readLine().split(",");
                int j = 0;
                for (String s : lineNumbers) {
                    mat[i][j++] = s.equals("_") ? 0 : Integer.parseInt(s);
                }
            }
        }
        return mat;
    }
    /*          parse colored blocks vector        */
    private Vector<Integer> parseColors(String str){
        if (str == null || str.isEmpty() || str == ""){
            return new Vector<>();
        }
        else {
            String[] sr = str.substring(1).split(",");
            Vector<Integer> res = new Vector<>();
            for (String s : sr) {
                res.add(Integer.parseInt(s));
            }
            return res;
        }
    }

}
