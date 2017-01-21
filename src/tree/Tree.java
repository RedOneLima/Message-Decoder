package tree;

import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * This program takes in a text file containing a words followed by their code
 * in 0 and 1 patterns and builds a tree of the code words. Once the program sees
 * an asterisk (*) it will know that it is starting a set of codes in a message. 
 * @author Kyle Hewitt
 */
public class Tree {
    public String word;// will contain the word assosiated with a code while building the tree
    public File fileName;//the file being read
    public Scanner myFile;//to read the file
    public Node root;//empty root node in tree
    public int i = 0;//index for char arrays
    public String seperator = "";//used to hold the string of the current scanner spot
    public ArrayList<String> decodedMessage = new ArrayList();//holds the series of words in the message
    public JFileChooser chooseFile;//GUI for chosing file
    public String buildingTheTree = "";//message output to show when an element is added
    public String finalDisplay = "";//output message for decoded messages
    public String code;//contains the code assosiated with a word while building the tree

    public static void main(String[] args) {
        GUI run = new GUI();
    }//main
    
    public Tree(File fileName){
        this.fileName = fileName;
        word = "";
        root = new Node("");
        chooseFile = new JFileChooser();
        readFile();
        buildTree();
        
        while(myFile.hasNext()){//this loop runs while there's codes left to decode        
            decode(root);
            printMessage();
            decodedMessage.clear();
        }
    }
    
    /**
     * for reading the file
     */
    public void readFile(){
        try{
           myFile = new Scanner(fileName);
        }
        catch(IOException e){
            System.out.println("Enter a valid txt document");
            int result = chooseFile.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                fileName = chooseFile.getSelectedFile();
            }
        }
    }
    /**
    *builds the tree
    */ 
    public void buildTree(){
        while(myFile.hasNext()){
            seperator = myFile.next();
            if(!seperator.startsWith("*")){
                i=0;
                word = seperator;
                code = myFile.next();
                insert(root, code.toCharArray());
            }
            else 
                break;
        }
        buildingTheTree += "You added "+(root.getCount()-1)+" elements to the tree\n";
    }
    
 
    /**
     * helper method for build tree. Inserts into the tree 
     * @param n parent node or root
     * @param s a char array of the code
     */
    public void insert(Node n, char [] s){
        if(i<s.length){
            char key = s[i++];
                        
            if(key == '0'){//go left
                if(n.hasLeft())
                    insert(n.getLeft(),s);
                else{
                    buildingTheTree += "You added "+word+": "+code+"\n";
                    n.setLeft(new Node(word));
                }
             }
            else{//go right
                if(n.hasRight())
                    insert(n.getRight(), s);
                else{
                    buildingTheTree += "You added "+word+": "+code+"\n";
                    n.setRight(new Node (word));
                }
            }
        }
    }
    
    //After the tree is built this is the method that searches the tree for the 
    //given code
    public void decode(Node n){
        String code;
        while(myFile.hasNext()){
            seperator = myFile.next();
            if(!seperator.startsWith("*")){
                i=0;
                code = seperator;
                finalDisplay += code+ "\n";
                searchTree(root, code.toCharArray());
            }
            else
                break;
        }
    }
    
    //helper search method for the decode method
    public void searchTree(Node n, char [] s){
        if(i<s.length){
            char key = s[i++];
                        
            if(key == '0')//go left
                searchTree(n.getLeft(),s);
            else//go right
                searchTree(n.getRight(),s);
                                
        }
        else
            decodedMessage.add(n.getData());
    }
    
    //Creates output display
    public void printMessage(){
        finalDisplay += "Your message says: \n";
        for (int i =0; i<decodedMessage.size(); i++){
            System.out.print(decodedMessage.get(i)+" ");
            finalDisplay += decodedMessage.get(i)+" ";
        }
        System.out.println();
        finalDisplay += "\n";
    }
    //returns display
    public String getFinalDisplay(){
        return finalDisplay;
    }
    //returns the message showing what was added to the tree
    public String getBuildingTheTree(){
        return buildingTheTree;
    }

    
    //abstract for the tree's nodes
    public class Node{
        private String data;
        private Node left;
        private Node right;
        
        //sets the data to the node being created
        public Node(String data){
            this.data = data;
            }
        //returns the count of the tree's nodes
        public int getCount( ){
            int count = 1;
            if (left != null)
               count += left.getCount();
            if (right != null)
                count += right.getCount();
            return count;
        }
        //returns if the current node has a left child
        public boolean hasLeft(){
            return (left != null);
        }
        //returns if the current node has a right child
        public boolean hasRight(){
            return (right != null);
        }
        //sets the left child of the current node
        public void setLeft(Node child){
            left = child;
        }
        //sets the right child of the current node
        public void setRight(Node child){
            right = child;
        }
        //returns the left child of the current node
        public Node getLeft(){
            return left;
        }
        //returns the right child of the current node
        public Node getRight(){
            return right;
        }
        //returns the data of the current node
        public String getData(){
            return data;
        }
        
    }
}
