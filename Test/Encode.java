/*
 * Mads Lykke Bach | Mabac16
 * Tobias Ahrenschneider Sztuk | Toszt17
 * Julie Dittmann Weimar Andersen | Julan17
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;
import javax.lang.model.util.Elements;

public class Encode {

	//frequency to represent the amount of times a specific letter occurs
    private int[] frequencyArray = new int[256];
	//bitcode to represent the Node location in the huffman tree
    private String[] bitcode = new String[256];
	//PQHeap priority queue to get which Element to write as a Node next.
    private static PQHeap priority = new PQHeap(256);

    //for both the bitcode and frequencyArray, the place is the corrensponding
    //to the input when the file is read. 


    private String in, out;
	
	
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String in = args[0];
        String out = args[1];
        Encode encode = new Encode(in, out);
        FileInputStream inFile = new FileInputStream(in);
		
		//find the frequency/occurence of letters in the input file and write them to array.
        encode.findFrequency(inFile);

		//Building our binary tree utilzing Huffman's algorithm and initializing the root element of said binary tree.
        Element root = encode.huffMan(priority);
		//Generating the bitcode for root and each node starting at root.
        String[] encoded = encode.search((Node) root.getData());
		//write the coded message to the output file utilizing the bitcode for root.
        encode.writeFile(encoded);
    }

    public Encode(String in, String out) {
        this.in = in;
        this.out = out;
    }

	/*
	* Function that reads the bitcode and writes the encoded message to the output file.
	*/
    public void writeFile(String[] encoded) throws FileNotFoundException, IOException {
        File outFile = new File(out);
        File inFile = new File(in);
        FileOutputStream fileOut;
        fileOut = new FileOutputStream(outFile);
        BitOutputStream outputStream = new BitOutputStream(fileOut);
        FileInputStream inputStream = new FileInputStream(inFile);

        
		//Writes the frequency array at the start of the file
        
        for (int i = 0; i < frequencyArray.length; i++) {
			//write the frecency on frequencyArray[i]'th place in the arrya to file.
            outputStream.writeInt(frequencyArray[i]);
        }

		//read the next bits of the input file.
        Integer bits = inputStream.read();
		//if returns -1, then there are no more bits to read
        while (bits != -1) {
			//for each character c[StringArray] we send to the Encoded class, we use the .writeBit() method depending on whether or not its binary representation is 0 or 1.
            for (char c : encoded[bits].toCharArray()) {
                if (c == '1') {
                    outputStream.writeBit(1);
                } else if (c == '0') {
                    outputStream.writeBit(0);
                }
            }
            bits = inputStream.read();
        }
		//close input and output stream
        outputStream.close();
	    inputStream.close();

	
    }

	
    public void findFrequency(FileInputStream inFile) throws IOException {
        //counter
        int i = 0;

        //retreive the total lenght of our input file
        int lenght = inFile.available();
	

        //while the lenght of the file is less than our traversal counter
		//fill up the frequencyArray with letters and increment by +1 every time it occurs.
        //the place is the corrensponding to what the read methode returns
        while(lenght > i) {
            int readByte = inFile.read();
            frequencyArray[readByte] = frequencyArray[readByte]+1;
            i++;
        }

        //populate the priority queue of our PQHeap with the Elements
	    //For each array place, there is create a new Element containing a new Node object and the frequency as key.
		//The Node contains whether or not it is a leaf and byte. 
        //In this case all nodes are leaf, as they are used to repressent a "letter"
        for (int j = 0; j < frequencyArray.length; j++) {
            Node node = new Node();
            node.set_Byte(j);
            node.setIsLeaf(true);
            priority.insert(new Element(frequencyArray[j], node));
        }

    }
	
	/*
	* Recursive function that fills out the bitcode string by traversing the huffman tree from the given node.
	*
	*/
    public void recursiveFindTreePath(Node node, String code) {
        if (node != null) {
            recursiveFindTreePath(node.getLeft(), code + "0");
            //only when it hits a leaf
            //then the code will be written in the bitcode array at the byte place of the array
            if (node.isLeaf()){
                bitcode[node.get_Byte()] = code; 
            }
            recursiveFindTreePath(node.getRight(), code + "1");
        }

    }

	//calls the recursive function with a given node and returns the generated bitcode.
    public String[] search(Node root) {
        recursiveFindTreePath(root, "");
        return bitcode;
    }


	/*
	* Implementation of Huffman's algorithm
	* Function that builds binary tree by getting the 2 smallest/least occuring key and building a new Element by combining their keys.
	* Each element contains a Node in the binary tree.
	* Thus building "up" the priority queue with elements untill there is no longer two elements to combine.
	* This function thus returns the smallest and only Element, that have the node as the root of the huffman.
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
