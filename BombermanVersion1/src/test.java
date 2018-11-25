import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class test {

    public void readfile() {
        InputStream inputStream = getClass().getResourceAsStream("/levels/Level1.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = in.readLine();
            StringTokenizer tokens = new StringTokenizer(line);

            int level = Integer.parseInt(tokens.nextToken());
            int _width = Integer.parseInt(tokens.nextToken());
            int _length = Integer.parseInt(tokens.nextToken());
           // char[][] map = new char[Integer.parseInt(tokens.nextToken())][Integer.parseInt(tokens.nextToken())];

//
            char[][] map = new char[_width][_length];
            for (int i=0; i<_width; i++) {
                line = in.readLine();
                char[] a = line.toCharArray();
                for(int j=0; j<_length; j++)
                    map[i][j] = a[j];
            }
            for (int i=0; i<_width; i++) {
                for(int j=0; j<_length; j++) {
                    System.out.print(map[i][j]);
                }
                System.out.println();

            }
            System.out.println(map[_width-1][_length-1]);
//            String s="22*";
//            char[] te = s.toCharArray();
//            System.out.println(te[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException {
       test t = new test();
       t.readfile();

    }
}
