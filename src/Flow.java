import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rezenter on 08.05.2015.
 */
public class Flow {

    public static void main(String args[]) throws FileNotFoundException {
        Scanner in = new Scanner(new File("flow.in"));
        int v = in.nextInt();
        int p = in.nextInt();
        List<Pipe> pipes = new ArrayList<>();
        for (int i = 0; i < p; i++) {
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            int c = in.nextInt();
            pipes.add(new Pipe(a, b, c));
        }

        int tmp = 1;
        while (tmp != 0) {
            tmp = needMoreFlow(pipes, new boolean[v], 0, v - 1, Integer.MAX_VALUE);
        }
        in.close();

        String res = "\n";
        int flow = 0;
        for(int i = 0; i < pipes.size(); i ++){
            flow += Math.abs(pipes.get(i).flow);
            res += (pipes.get(i).flow) + "\n";
        }

        res = flow + res;

        PrintWriter pw = new PrintWriter(new File("flow.out"));
        pw.print(res);
        pw.close();
    }

    public static int needMoreFlow(List<Pipe> pipes, boolean[] visited, int start, int finish, int addFlow) {
        if (start == finish) {
            return addFlow;
        }
        visited[start] = true;
        for (int i = 0; i < visited.length; i++) {
            Pipe temp = pipes.get(0);
            for(int c = 0; c < pipes.size(); c++){
                if(Math.abs(pipes.get(c).ifExists(start, i)) > Math.abs(temp.ifExists(start, i))){
                    temp = pipes.get(c);
                }
            }
            if (!visited[i] && temp.ifExists(start, i) > 0) {
                int tmp = needMoreFlow(pipes, visited, i, finish, Math.min(addFlow, Math.abs(temp.ifExists(start, i))));
                if (tmp > 0) {
                    temp.limit -= tmp;
                    temp.flow += tmp;
                    temp.revLimit -= tmp;
                    return tmp;
                }
            }
            if (!visited[i] && temp.ifExists(start, i) < 0) {
                int tmp = needMoreFlow(pipes, visited, i, finish, Math.min(addFlow, Math.abs(temp.ifExists(start, i))));
                if (tmp > 0) {
                    temp.limit += tmp;
                    temp.revLimit += tmp;
                    temp.flow -= tmp;
                    return tmp;
                }
            }
        }
        return 0;
    }

    public static class Pipe {
        public int from;
        public int to;
        public int limit = 0;
        public int revLimit = 0;
        public int flow = 0;


        public Pipe(int from, int to, int limit) {
            this.from = from;
            this.to = to;
            this.limit = limit;
            this.revLimit = - limit;
        }

        public int ifExists(int from, int to){
            if(this.from == from && this.to == to){
                return limit;
            }
            if(this.to == from && this.from == to){
                return revLimit;
            }
            return 0;
        }
    }
}
