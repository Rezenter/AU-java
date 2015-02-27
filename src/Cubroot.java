import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Cubroot{

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("cubroot.in"));
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int d = in.nextInt();
        double l = (double)Integer.MIN_VALUE;
        double r = (double)Integer.MAX_VALUE;
        in.close();

        while(Math.abs(r - l) > 0.000001){
            double val = func(a,b,c,d,(r+l)/2);
            if(val > 0){
                if(func(a,b,c,d,r) > 0){
                    r = (r+l)/2;
                }else{
                    l = (r+l)/2;
                }
            }else{
                if(val == 0){
                    break;
                }else{
                    if(func(a,b,c,d,r) > 0){
                        l = (l+r)/2;
                    }else{
                        r = (l+r)/2;
                    }
                }
            }
            System.out.println(l+" "+r);
        }
        System.out.println((r+l)/2);

        PrintWriter pw = new PrintWriter(new File("cubroot.out"));
        pw.print((l+r)/2);
        pw.close();
    }



    public static double func(int a,int b,int c,int d, double x){
        double res = (double) (a*Math.pow(x, 3) + b*Math.pow(x, 2) + c*x + d);
        return res;
    }

}
