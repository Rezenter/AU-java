import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Function{
    final static double MOD = (double) Math.pow(2, 32);

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("function.in"));
        double x = (double)in.nextInt();
        in.close();

        PrintWriter pw = new PrintWriter(new File("function.out"));
        pw.print((func(x)));
        pw.close();
    }

    static Map<Double, Double> done = new HashMap<Double, Double>();

    public static double func(double x){
        if(done.containsKey(x)){
            return done.get(x);
        }
        if(x <= 2){
            done.put(x, (double) 1);
        }else{
            if(x % 2 == 1){
                done.put(x, (func(6 * x / 7)%MOD + func(2 * x / 3)%MOD)%MOD);
            }else{
                done.put(x,(func(x - 1)%MOD + func(x - 3)%MOD)%MOD);
            }
        }
        return done.get(x);
    }
}