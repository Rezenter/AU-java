/**
 * Created by Rezenter on 10.03.2015.
 */
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;


public class Dict {

    static Node head;
    static Node tail;

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("dict.in"));
        long x = (long)in.nextInt();
        in.close();
        Node head;
        Node tail;
        PrintWriter pw = new PrintWriter(new File("dict.out"));
        pw.print(0);
        pw.close();
    }

    public static void push(String newWord){
        if(head.getNext() == null){
            head = new Node(newWord, 1, null);
        }else{
            Node curr = head;
            Node prev = null;
            while(curr.getNext() != null){
                if(newWord.equalsIgnoreCase(curr.getWord())){
                    //smth needed here
                    break;
                }
                prev = curr;
            }
            curr.setNext(new Node(newWord.toLowerCase(), 1, null));
        }
    }

    public static class Node{
        private String word = null;
        private int count = 0;
        private Node next = null;

        Node(String w, int c, Node n){
            word = w;
            count = c;
            next = n;
        }

        public int getCount(){
            return count;
        }

        public String getWord() {
            return word;
        }

        public Node getNext() {
            return next;
        }

        public void addCount(){
            count ++;
        }

        public void setNext(Node n){
            next = n;
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
            int minLength = Math.min(word.length(), cWord.length());
            int f = 0;
            while(true) {
                try {
                    char w = word.charAt(f);
                } catch (IndexOutOfBoundsException e) {
                    return -1;
                }
                try {
                    char cw = cWord.charAt(f);
                } catch (IndexOutOfBoundsException e) {
                    return 1;
                }
                if (word.charAt(f) != cWord.charAt(f)) {
                    return Character.compare(word.charAt(f), cWord.charAt(f));
                }
                f++;
            }
        }
    }
}
