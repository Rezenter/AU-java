/**
 * Created by Rezenter on 10.03.2015.
 */
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;


public class Dict {

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("dict.in"));
        String text = "";
        while(in.hasNextLine()){
            text += in.nextLine();
        }
        in.close();
        String[] arr = parse(text);
        AVL dict = new AVL();
        for(int i = 0; i< arr.length; i++){
             if(arr[i].length() != 0 && judjment(arr[i])){
                 dict.push(new Node(arr[i], 1, null, null, 0));
            }
        }

        PrintWriter pw = new PrintWriter(new File("dict.out"));
        pw.print(dict);
        pw.close();
    }

    private static boolean judjment(String word){
        int lim = word.length();
        for(int i =0; i< lim; i ++){
            char curr = word.charAt(i);
            if(curr == '_' || Character.isDigit(curr)){
                return false;
            }
        }
        return true;
    }

    public static String[] parse(String text){
        return text.split("[^a-zA-Z_0-9]");
    }

    public static class Node{
        private String word;
        private int count;
        private Node left;
        private Node right;
        private int diff;

        Node(String w, int c, Node l, Node r, int d){
            word = w;
            count = c;
            left = l;
            right = r;
            diff = d;
        }

        public String toString(){
            String c = "";
            String l = "";
            String r = "";
            if(getCount() != 1){
                c += " " + count;
            }
            if(getLeft() != null){
                l = getLeft().toString();
            }
            if(getRight() != null){
                r = getRight().toString();
            }
            return l + word + c + "\n" + r;
        }

        public int getCount(){
            return count;
        }

        public String getWord() {
            return word;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void addCount(){
            count ++;
        }

        public void setLeft(Node l){
            left = l;
        }

        public void setRight(Node r){
            right = r;
        }

        public void addDiff(){
            diff++;
        }

        public void redDiff(){
            diff--;
        }

        public int getDiff(){
            return diff;
        }


        public void setDiff(int d){
            diff = d;
        }

        public boolean equals(Node condidate){
            String cWord = condidate.getWord();
            if(word.length() != cWord.length()){
                return false;
            }
            for(int i = 0; i < word.length(); i ++){
                if(word.charAt(i) != cWord.charAt(i)){
                    return false;
                }
            }
            return true;
        }

        public int compare(Node condidate){
            if(equals(condidate)){
                return 0;
            }
            String cWord = condidate.getWord();
            return word.compareToIgnoreCase(cWord);
        }
    }

    public static class PathNode{
        private Node val;
        private PathNode prev;

        PathNode(Node v, PathNode p){
            val = v;
            prev = p;
        }

        public Node getVal(){
            return val;
        }

        public PathNode getPrev(){
            return prev;
        }

        public void setVal(Node v){
            val = v;
        }

        public void setPrev(PathNode p){
            prev = p;
        }

        public String toString(){
            if(prev == null){
                return val.toString();
            }
            return prev.toString() + " " + val.toString();
        }
    }

    public static class AVL{

        private Node root;
        private boolean flag = true;

        AVL(){
        }

        public Node getRoot(){
            return root;
        }

        public void push(Node elem){
            if(flag){
                root = elem;
                flag = false;
            }else{
                PathNode path = new PathNode(root, null);
                Node curr = root;
                while(true){
                    if (curr.compare(elem) < 0) {
                        if(curr.getRight() == null){
                            curr.setRight(elem);
                            balance(path);
                            break;
                        }else{
                            curr = curr.getRight();
                            PathNode tmp = path;
                            path = new PathNode(curr, tmp);
                        }
                        curr.redDiff();
                    } else{
                        if(curr.compare(elem) > 0){
                            if(curr.getLeft() == null){
                                curr.setLeft(elem);
                                balance(path);
                                break;
                            }else{
                                curr = curr.getLeft();
                                PathNode tmp = path;
                                path = new PathNode(curr, tmp);
                            }
                            curr.addDiff();
                        } else{
                            curr.addCount();
                            break;
                        }
                    }
                }
            }
        }

        private void balance(PathNode path) {
            while(path != null){
                if(path.getPrev() == null){
                    balance(path.getVal(), null);
                    path = path.getPrev();
                }else{
                balance(path.getVal(), path.getPrev().getVal());
                path = path.getPrev();
                }
            }
        }

        public void balance(Node axis, Node prev){
            if(axis.getDiff() == -2){
                if(axis.getRight().getDiff() == -1){
                    axis.setDiff(0);
                    axis.getRight().setDiff(0);
                }else{
                    if(axis.getRight().getDiff() == 0){
                        axis.setDiff(-1);
                        axis.getRight().setDiff(1);
                    }
                }
                if(axis.getRight().getDiff() != 1){
                    if(prev != null){
                        if(prev.getRight().equals(axis)){
                            prev.setRight(axis.getRight());
                        }else{
                            prev.setLeft(axis.getRight());
                        }
                    }else{
                        root = axis.getRight();
                    }
                    Node tmp = axis.getRight().getLeft();
                    axis.getRight().setLeft(axis);
                    axis.setRight(tmp);
                }else{
                    if(axis.getRight().getLeft().getDiff() == 1){
                        axis.setDiff(0);
                        axis.getRight().setDiff(-1);
                        axis.getRight().getLeft().setDiff(0);
                    }else{
                        if(axis.getRight().getLeft().getDiff() == -1){
                            axis.setDiff(1);
                            axis.getRight().setDiff(0);
                            axis.getRight().getLeft().setDiff(0);
                        }else{
                            axis.setDiff(0);
                            axis.getRight().setDiff(0);
                            axis.getRight().getLeft().setDiff(0);
                        }
                    }
                    Node q = axis.getRight().getLeft().getLeft();
                    Node r = axis.getRight().getLeft().getRight();
                    if(prev != null){
                        if(prev.getRight().equals(axis)){
                            prev.setRight(axis.getRight().getLeft());
                        }else{
                            prev.setLeft(axis.getRight().getLeft());
                        }
                    }else{
                        root = axis.getRight().getLeft();
                    }
                    axis.getRight().getLeft().setRight(axis.getRight());
                    axis.getRight().getLeft().setLeft(axis);
                    axis.getRight().setLeft(r);
                    axis.setRight(q);
                }
            }else{
                if(axis.getDiff() == 2){
                    if(axis.getLeft().getDiff() == -1){
                        axis.setDiff(0);
                        axis.getLeft().setDiff(0);
                    }else{
                        if(axis.getLeft().getDiff() == 0){
                            axis.setDiff(-1);
                            axis.getLeft().setDiff(1);
                        }
                    }
                    if(axis.getLeft().getDiff() != 1){
                        if(prev != null){
                            if(prev.getRight().equals(axis)){
                                prev.setRight(axis.getLeft());
                            }else{
                                prev.setLeft(axis.getLeft());
                            }
                        }else{
                            root = axis.getLeft();
                        }
                        Node tmp = axis.getLeft().getRight();
                        axis.getLeft().setRight(axis);
                        axis.setLeft(tmp);
                    }else{
                        if(axis.getLeft().getRight().getDiff() == 1){
                            axis.setDiff(0);
                            axis.getLeft().setDiff(-1);
                            axis.getLeft().getRight().setDiff(0);
                        }else{
                            if(axis.getLeft().getRight().getDiff() == -1){
                                axis.setDiff(1);
                                axis.getLeft().setDiff(0);
                                axis.getLeft().getRight().setDiff(0);
                            }else{
                                axis.setDiff(0);
                                axis.getLeft().setDiff(0);
                                axis.getLeft().getRight().setDiff(0);
                            }
                        }
                        Node q = axis.getLeft().getRight().getLeft();
                        Node r = axis.getLeft().getRight().getRight();
                        if(prev != null){
                            if(prev.getRight().equals(axis)){
                                prev.setRight(axis.getLeft().getRight());
                            }else{
                                prev.setLeft(axis.getLeft().getRight());
                            }
                        }else{
                            root = axis.getLeft().getRight();
                        }
                        axis.getLeft().getRight().setLeft(axis.getLeft());
                        axis.getLeft().getRight().setRight(axis);
                        axis.getLeft().setRight(q);
                        axis.setLeft(r);
                    }
                }
            }
        }

        public String toString(){
            return root.toString();
        }
    }
}
