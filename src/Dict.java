/**
 * Created by Rezenter on 10.03.2015.
 */
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.File;


public class Dict {

    static Node head = new Node("", 0 , null);

    public static void main(String[] args) throws FileNotFoundException{
        Scanner in = new Scanner(new File("dict.in"));
        String text = "";
        while(in.hasNextLine()){
            text += in.nextLine();
            text += " ";
        }
        in.close();
        parse(text);
        Node curr = head;
        while(curr.getNext() != null){
            System.out.println(curr);
            curr = curr.getNext();
        }
        System.out.println(curr);

        PrintWriter pw = new PrintWriter(new File("dict.out"));
        pw.print(0);
        pw.close();
    }

    public static void parse(String text){
        String curr = "";

    }

    public static void push(String newWord){
        if(head.getWord() == ""){
            head = new Node(newWord, 1, null);
        }else{
            if(head.getWord().equalsIgnoreCase(newWord)){
                head.addCount();
            }else{
                Node curr = head;
                boolean flag = true;
                while (true) {
                    if (newWord.equalsIgnoreCase(curr.getWord())) {
                        curr.addCount();
                        flag = false;
                        break;
                    }
                    if(curr.getNext() == null){
                        break;
                    }
                    curr = curr.getNext();
                }
                if(flag) {
                    sortedInsertion(new Node(newWord.toLowerCase(), 1, null));
                }
            }
        }
    }

    public static void sortedInsertion(Node newWord){
        Node curr = head;
        Node prev = head;
        if(head.compare(newWord) > 0){
            Node tmp = head;
            head = newWord;
            head.setNext(tmp);
        }else {
            while (curr.getNext() != null && curr.compare(newWord) < 0) {
                prev = curr;
                curr = curr.getNext();
            }
            newWord.setNext(curr);
            prev.setNext(newWord);
        }
    }

    public static class Node{
        private String word;
        private int count;
        private Node next;

        Node(String w, int c, Node n){
            word = w;
            count = c;
            next = n;
        }

        public String toString(){
            return "Word: " + word + " Count = " + count;
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
