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
        Matrix tmp = new Matrix(n, n);
        for(int i = 0; i < n; i++){
            for(int l = in.nextInt(); l > 0; l--){
                tmp.set(i, in.nextInt() - 1, 1);
            }
        }

        int[] defaults = new int[n];
        for(int i = 0; i < n; i++){
            defaults[i] = in.nextInt();
        }
        in.close();
        Matrix matrix = new Matrix(n, n+1);
        for(int i = 0; i < n; i++){
            for(int l = 0; l < n; l++){
                matrix.set(i,l,tmp.getMat()[i][l]);
            }
            matrix.set(i, n, defaults[i]);
        }
        int[] x = solve(matrix, defaults);
        String res = "";
        String temp = "";
        if(x[0] == -100){
            res = "-1";
        }else{
            int count = 0;
            for(int i = 0; i < x.length; i++){
                if(x[i] == 1){
                    count ++;
                    temp += (i+1) + " ";
                }
            }
            res += count+ "\n" + temp;
            res = res.substring(0,res.length()-1);
        }
        PrintWriter pw = new PrintWriter(new File("puzzle.out"));
        pw.print(res);
        pw.close();
    }
    
    public static int[] solve(Matrix mat, int[] b) {
        int n = mat.getMat().length;
        int[] x = new int[n];

        for(int i = 0;i < n; i ++){
            mat.swapStrings(i, mat.pivote(i));
            mat.extract(i);
        }

        for(int i = n - 1; i >= 0; i--){
            mat.extractRev(i);
        }

        for(int i = 0;i < n; i ++){
            if(mat.getMat()[i][i] == 0){
                if(mat.getMat()[i][n] == 0){
                    x[i]= 0;
                }else{
                    x[0]=-100;
                }
            }else{
                x[i] = (mat.getMat()[i][n]/mat.getMat()[i][i])%2;
            }
        }
        return x;
    }

    public static class Matrix{
        int v = 0;
        int h = 0;
        int height;
        int wide;
        int[][] mat;

        public void extract(int start){
            for(int i = start + 1; i < height; i ++){
                if(mat[i][start] != 0){
                    for(int j = start; j < wide; j ++) {
                        this.set(i, j, Math.abs((mat[i][j] - mat[start][j]) % 2));
                    }
                }
            }
        }

        public void extractRev(int finish){
            for(int i = finish - 1; i >= 0; i--){
                if(mat[i][finish] != 0){
                for(int j = finish; j < wide; j ++){
                    this.set(i,j, Math.abs((mat[i][j] - mat[finish][j])%2));
                }}
            }
        }

        public int pivote(int column){
            for(int i = column; i < height; i++){
                if(mat[i][column] > 0){
                    return i;
                }
            }
            return column;
        }

        Matrix(int n ,int  m){
            height = n;
            wide = m;
            mat = new int[n][m];
        }

        public void push(int elem){
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

        public void swapStrings(int f, int s){
            int[] tmp = mat[f];
            mat[f] = mat[s];
            mat[s] = tmp;
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