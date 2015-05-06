/**
 * Created by Rezenter on 24.04.2015.
 * fails test 24
 */

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class Linear {
    public static final double EPS = 0.00005;
    public static void main(String[] args) throws FileNotFoundException {
            String res = "";
            try {
                Scanner in = new Scanner(new File("linear.in"));
                int n = in.nextInt();
                in.nextLine();
                Matrix matrix = new Matrix(n, n + 1);
                Matrix clone = new Matrix(n, n + 1);
                for (int i = 0; i < n; i++) {
                    for (int l = 0; l < n + 1; l++) {
                        double tmp = (double) in.nextInt();
                        matrix.set(i, l, tmp);
                        clone.set(i, l, tmp);
                    }
                }
                in.close();
                res = verdict(solve(matrix), matrix);
            }catch(Exception e){
                res = "internal error";
            }


            PrintWriter pw = new PrintWriter(new File("linear.out"));
            pw.print(res);
            pw.close();
    }

    public static double[] solve(Matrix mat) {
        int n = mat.getMat().length;
        double[] x = new double[n];
        boolean flag = false;

        for(int i = 0;i < n; i ++){
            if(mat.pivote(i) == i && Math.abs(mat.getMat()[i][i]) <= EPS){
                flag = true;
            }
            mat.swapStrings(i, mat.pivote(i));
            mat.extract(i);
        }

        for(int i = n - 1; i >= 0; i--){
            mat.extractRev(i);
        }

        for(int i = 0;i < n; i ++){
            x[i] = mat.getMat()[i][n]/mat.getMat()[i][i];
        }

        if(flag){
            for(int i = 0; i< n ; i++){
                if(Math.abs(mat.getMat()[i][i]) <= EPS && Math.abs(mat.getMat()[i][n]) > EPS){
                    x[0] = Double.NaN;
                    return x;
                }
            }
            return new double[n+1];
        }
        return x;
    }

    public static String verdict(double[] x, Matrix mat){
        int n = mat.getMat().length;
        String res = "single\n";
        if(x.length != n){
            return "infinity";
        }
        if(Math.abs(mat.getMat()[n-1][n]) <= EPS && Math.abs(mat.getMat()[n-1][n-1]) <= EPS){
            return "infinity";
        }
        for (int i = 0; i < x.length; i++) {
            if(Double.isInfinite(x[i]) || Double.isNaN(x[i])){
                return "impossible";
            }
            res += new BigDecimal(x[i]).setScale(3, RoundingMode.HALF_UP).doubleValue() + " ";
        }
        return (res.substring(0, res.length() - 1));
    }

    public static class Matrix {
        int v = 0;
        int h = 0;
        int height;
        int wide;
        double[][] mat;

        Matrix(int n, int m) {
            height = n;
            wide = m;
            mat = new double[n][m];
        }

        public void push(double elem) {
            if (v == wide && h == height + 1) {
            } else {
                mat[h][v] = elem;
                if (v == wide - 1) {
                    v = 0;
                    h++;
                } else {
                    v++;
                }
            }
        }

        public void set(int i, int j, double elem) {
            mat[i][j] = elem;
        }

        public void swapStrings(int f, int s){
            double[] tmp = mat[f];
            mat[f] = mat[s];
            mat[s] = tmp;
        }

        public void extract(int start){
            for(int i = start + 1; i < height; i ++){
                double dif = mat[i][start]/mat[start][start];
                for(int j = start; j < wide; j ++){
                    this.set(i,j, mat[i][j] - (mat[start][j] * dif));
                }
            }
        }

        public void extractRev(int finish){
            for(int i = finish - 1; i >= 0; i--){
                double dif = mat[i][finish]/mat[finish][finish];
                for(int j = finish; j < wide; j ++){
                    this.set(i,j, mat[i][j] - (mat[finish][j] * dif));
                }
            }
        }

        public int pivote(int column){
            int res = column;
            double max = 0;
            for(int i = column; i < height; i++){
                if(mat[i][column] > max){
                    max = mat[i][column];
                    res = i;
                }
            }
            return res;
        }

        public Matrix mult(int c, int d){
            Matrix res = new Matrix(height, wide);
            for(int i = 0; i< height; i++) {
                for (int k = 0; k < wide; k++) {
                    if(k != wide - 1){
                        res.push(c * mat[i][k]);
                    }else{
                        res.push(d * mat[i][k]);
                    }
                }
            }
            return res;
        }

        public String toString() {
            String res = "";
            for (int i = 0; i < height; i++) {
                for (int k = 0; k < wide; k++) {
                    res += mat[i][k] + " ";
                }
                res = res.substring(0, res.length() - 1);
                res += "\n";
            }
            return res.substring(0, res.length() - 1);
        }

        public double[][] getMat() {
            return mat;
        }
    }
}