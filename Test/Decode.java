/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;


/*
 * Mads Lykke Bach | Mabac16
 * Tobias Ahrenschneider Sztuk | Toszt17
 * Julie Dittmann Weimar Andersen | Julan17
 */
public class Decode {
    //String to represent the decoded message.
    private String decoded;
	//frequency array to keep track of the amount of times a letter appears
    private int[] frequencyArray = new int[256];
	//priority queue for our table when building the huffman tree
    private PQHeap priority = new PQHeap(256);
	//in/out files
    private String in, out;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
		//Instaciate our Decode class and set the correct files.
        String in = args[0];
        String out = args[1];
        Decode decode = new Decode(in, out);
        FileInputStream fileIn = new FileInputStream(in);
		
        //Pass the infile to our BitInputStream and fill the tables.
        BitInputStream inputStream = new BitInputStream(fileIn);
        decode.fillTable(inputStream);
		//Decode the message by building the huffman tree and getting the bytecode.
        Element root = decode.huffMan(decode.priority);
        //write the decoded mesasge to our OutPutSteam utilizing the built huffman tree and 
        decode.writeFile(root, inputStream);
        
        fileIn.close();
        inputStream.close();
    }
    
	//constructor
    public Decode(String in, String out){
        this.in = in;
        this.out = out;
    }
    
	
	/*
	* Write to new file utilizing the bitoutputstream class.
	*
	* Mehod takes the root Element of our Huffman tree in order to know where to start building the string of bits.
	* The bits represent the occurrence of the letters 
	*/
    public void writeFile(Element root, BitInputStream inputStream) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(new File(out));
        Node node = (Node) root.getData();
		
		/*
		* Specify an integer that keeps track of the lenght of our frequencyArray.
		*/
        int bit = inputStream.readBit();
        int freqSum = 0;
        for (int i = 0; i < frequencyArray.length; i++){
            freqSum = freqSum + frequencyArray[i];
        }

		/*
		* 
		* Traverse the built huffman tree, and  determine if the node visited during traversal, starting at the root, 
		* is a left or right child and if this node is marked as a child.
		* Write the byte contained in the child to the file and increment the counter.
		*
		*/
        int leafvisit = 0;
        while(bit != -1 && freqSum > leafvisit){
            if(bit == 1){
                node = node.getRight();
                if(node.isLeaf()){
                    fileOut.write(node.getByte());
                    node = (Node) root.getData();
                    leafvisit++;
                }
            }else{
                node = node.getLeft();
                if(node.isLeaf()){
                    fileOut.write(node.getByte());
                    node = (Node) root.getData();
                    leafvisit++;
                }
            }
			//read next bit
            bit = inputStream.readBit();
        }
        
        //inputStream.close();
        fileOut.close();
    }
    
	/*
	Method used to fill the table during decoding
	*/
    public void fillTable(BitInputStream input) throws IOException{
        for (int i = 0; i < frequencyArray.length; i++) {
            frequencyArray[i] = input.readInt();
        }
        
		//Populate the priority queue of our PQHeap with the Elements
	    //For each letter in our textfile, we create a new Element containing a new Node object and a frequency array.
		//The Node contains whether or not it is a leaf, a byte, and the amount of times said byte occurs. 
        for (Integer j = 0; j < frequencyArray.length; j++) {
            Node node = new Node();
            node.setByte(j);
            node.setIsLeaf(true);
            priority.insert(new Element(frequencyArray[j], node));
        }
    }
    
/*
	* Implementation of Huffman's algorithm
	* Function that builds binary tree by getting the 2 smallest/least occuring data and building a new Element by combining their data.
	* Each element contains a Node in the binary tree.
	* Thus building "up" the priority queue with elements untill there is no two elements that have had their data combined.
	* This function thus returns the smallest or least occuring object of data as the Element that will become the root of our binary tree.
	*/
    public Element huffMan(PQHeap heap) {
        int n = heap.lenght();
        PQHeap q = heap;

        for (int i = 0; i < (n - 1); i++) {
            Node z = new Node();

            Element x = q.extractMin();
            z.setLeft((Node) x.getData());
            Element y = q.extractMin();
            z.setRight((Node) y.getData());

            int key = x.getKey() + y.getKey();
            q.insert(new Element(key, z));

        }

        return q.extractMin();
    }
    
}
