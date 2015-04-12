/**
 * Created by Rezenter on 11.04.2015.
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class Puzzle {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("puzzle.in"));
        int n = in.nextInt();
        in.nextLine();
        Matrix matrix = new Matrix(n, n);
        for(int i = 0; i < n; i++){
            for(int l = in.nextInt(); l > 0; l--){
                matrix.set(i, in.nextInt()-1, 1);
            }
        }

        int[] defaults = new int[n];
        for(int i = 0; i < n; i++){
            defaults[i] = -in.nextInt();
        }
        in.close();
        int[] solvation = solve(matrix.getMat(), defaults);
        String res = "";
        if(solvation[0] == -2){
            res = "-11";
        }else {
            int count = 0;
            for(int i = 0; i < solvation.length; i++){
                if(Math.abs(solvation[i])%2 == 1){
                    count ++;
                }
            }
            res += count + "\n";
            for (int i = 0; i < solvation.length; i++) {
                if(Math.abs(solvation[i])%2 == 1){
                    res += i+1 + " ";
                }
            }
        }
        PrintWriter pw = new PrintWriter(new File("puzzle.out"));
        pw.print(res.substring(0, res.length() - 1));
        pw.close();
    }

    private static final int EPSILON = 0;
    
    public static int[] solve(int[][] mat, int[] b) {
        int n  = b.length;

        for (int p = 0; p < n; p++) {
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(mat[i][p]) > Math.abs(mat[max][p])) {
                    max = i;
                }
            }
            int[] temp = mat[p];
            mat[p] = mat[max];
            mat[max] = temp;
            int t = b[p];
            b[p] = b[max];
            b[max] = t;
            if (Math.abs(mat[p][p]) <= EPSILON) {
                int[] error = new int[1];
                error[0] = -2;
                return error;
            }
            for (int i = p + 1; i < n; i++) {
                int alpha = (mat[i][p] / mat[p][p]);
                b[i] -= (alpha * b[p]);
                for (int j = p; j < n; j++) {
                    mat[i][j] -= alpha * mat[p][j];
                    mat[i][j] = mat[i][j]%2;
                }
            }
        }
        int[] x = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            int sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += mat[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / mat[i][i];
        }
        return x;
    }

    public static class Matrix{
        int v = 0;
        int h = 0;
        int height;
        int wide;
        int[][] mat;

        Matrix(int n ,int  m){
            height = n;
            wide = m;
            mat = new int[n][m];
        }

        public void push(Integer elem){
            if(v == wide && h == height + 1){
            }else{
                mat[h][v] = elem;
                if(v == wide - 1){
                    v = 0;
                    h ++;
                }else{
                    v ++;
                }
            }
        }

        public void set(int i, int j,int elem){
            mat[i][j] = elem;
        }

        public String toString(){
            String res = "";
            for(int i = 0; i< height; i++) {
                for (int k = 0; k < wide; k++) {
                    res += mat[i][k] + " ";
                }
                res = res.substring(0,res.length()-1);
                res += "\n";
            }
            return res.substring(0,res.length()-1);
        }

        public int getWide(){
            return wide;
        }

        public int getHeight(){
            return height;
        }

        public int[][] getMat(){
            return mat;
        }

        public Matrix mult(Matrix sec){
            Matrix res = new Matrix(height, sec.getWide());
            for(int i = 0; i < height; i++){
                for(int j = 0; j < sec.getWide(); j++){
                    int tmp = 0;
                    for(int k = 0; k < wide; k++){
                        tmp += mat[i][k]*sec.getMat()[k][j];
                    }
                    res.push(tmp);
                }
            }
            return res;
        }

        public Matrix mult(int c){
            Matrix res = new Matrix(height, wide);
            for(int i = 0; i< height; i++) {
                for (int k = 0; k < wide; k++) {
                    res.push(c * mat[i][k]);
                }
            }
            return res;
        }

        public Matrix add(Matrix sec){
            Matrix res = new Matrix(height, wide);
            for(int i = 0; i< height; i++) {
                for (int k = 0; k < wide; k++) {
                    res.push(mat[i][k] + sec.getMat()[i][k]);
                }
            }
            return res;
        }

        public Matrix add(int c){
            Matrix res = new Matrix(height, wide);
            for(int i = 0; i< height; i++) {
                for (int k = 0; k < wide; k++) {
                    res.push(c + mat[i][k]);
                }
            }
            return res;
        }
    }
}