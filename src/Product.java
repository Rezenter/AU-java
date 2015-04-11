/**
 * Created by Rezenter on 10.04.2015.
 */
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class Product {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("product.in"));
        int p = in.nextInt();
        int q = in.nextInt();
        int r = in.nextInt();
        Matrix first = new Matrix(p, q);
        for(int i = 0; i < p; i++){
            for(int j = 0; j < q; j++){
                first.push(in.nextInt());
            }
        }
        Matrix second = new Matrix(q, r);
        for(int i = 0; i < q; i++){
            for(int j = 0; j < r; j++){
                second.push(in.nextInt());
            }
        }
        in.close();

        PrintWriter pw = new PrintWriter(new File("product.out"));
        pw.print(first.mult(second));
        pw.close();
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