
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Subseq{
    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("subseq.in"));
        int x = in.nextInt();
        int[] arr = new int[x];
        for(int i = 0; i < x; i ++){
            arr[i] = in.nextInt();
        }
        in.close();
        ArrayList<ArrayList> subs = new ArrayList<ArrayList>();
        int dinam[] = new int[x];
        for(int i = 0 ; i < x ; i ++){
            subs.add(new ArrayList<Integer>());
            ArrayList<Integer> tmp = subs.get(i);
            tmp.add(i);
            subs.set(i, tmp);
            dinam[i] = Integer.MIN_VALUE;
        }
        dinam[0] = Integer.MAX_VALUE;
        int length = 0;
        ArrayList<Integer> best = null;
        for(int i = 0; i< x;i++){
            if(subs.get(i).size() > length){
                length = subs.get(i).size();
                best = subs.get(i);
            }
        }
        Collections.reverse(best);

        PrintWriter pw = new PrintWriter(new File("subseq.out"));
        pw.println(length);
        for(int i = 0; i < length; i++){
            pw.println(best.get(i) + 1);
        }
        pw.close();
    }
}
