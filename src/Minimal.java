import java.io.*;
import java.util.*;

/**
 * Created by Rezenter on 04.06.2015.
 */

public class Minimal {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("minimal.in"));
        int m = in.nextInt();
        int n = in.nextInt();
        int x;
        ArrayList<Integer>[] a = new ArrayList[4000];
        for (int i = 0; i < m; i++) {
            a[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            x = in.nextInt();
            for (int j = 0; j < x; j++) {
                a[i].add(in.nextInt() - 1);
            }
        }
        boolean[] ma = new boolean[4000];
        boolean[] q = new boolean[4000];
        int[] p = new int[4000];
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }
        int c = 0;
        for (int i = 0; i < m; i++) {
            x = in.nextInt();
            if (x != 0) {
                p[x - 1] = i;
                q[i] = true;
                ma[i] = true;
                c++;
            }
        }
        in.close();

        int ca = c;
        int cb = 0;
        boolean[] mb = new boolean[4000];
        for (int i = 0; i < m; i++) {
            if (!q[i]) {
                for (int j = 0; j < a[i].size(); j++) {
                    if (p[a[i].get(j)] != -1) {
                        if (ma[p[a[i].get(j)]]) {
                            ca--;
                            cb++;
                        }
                        mb[a[i].get(j)] = true;
                        ma[p[a[i].get(j)]] = false;
                    }
                }
            }
        }

        PrintWriter out = new PrintWriter(new File("minimal.out"));
        out.println(c);
        out.print((ca) + " ");
        for (int i = 0; i < m; i++) {
            if (ma[i]) {
                out.print((i + 1) + " ");
            }
        }
        out.print("\n" +(cb) + " ");
        for (int i = 0; i < n; i++) {
            if (mb[i]) {
                out.print((i + 1) + " ");
            }
        }

        out.close();
    }
}
