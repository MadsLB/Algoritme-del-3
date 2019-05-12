/*
 * Mads Lykke Bach | Mabac16
 * Tobias Ahrenschneider Sztuk | Toszt17
 * Julie Dittmann Weimar Andersen | Julan17
 */
package encode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;
import javax.lang.model.util.Elements;

public class Encode {
    
    private Integer[] frequencyArray = new Integer[256];
    private String[] bitcode = new String[256];
    private static PQHeap priority = new PQHeap(256);

    private String in, out;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String in = args[0];
        String out = args[1];
        Encode encode = new Encode(in, out);
        FileInputStream inFile = new FileInputStream(in);

        encode.findFrequency(inFile);
        Element root = encode.huffMan(priority);
        String[] encoded = encode.search((Node) root.getData());
        encode.writeFile(encoded);
    }

    public Encode(String in, String out) {
        this.in = in;
        this.out = out;
    }

    public void writeFile(String[] encoded) throws FileNotFoundException, IOException {
        File outFile = new File(out);
        File inFile = new File(in);
        FileOutputStream fos = new FileOutputStream(outFile);
        BitOutputStream bitOutput = new BitOutputStream(fos);
        FileInputStream inputStream = new FileInputStream(inFile);

        for (int i = 0; i < frequencyArray.length; i++) {
            bitOutput.writeInt(frequencyArray[i]);
        }
        
        Integer bits = inputStream.read();

        while (bits != -1) {
            
            for (char c : encoded[bits].toCharArray()) {
                if (c == '1') {
                    bitOutput.writeBit(1);
                } else if(c == '0'){
                    bitOutput.writeBit(0);
                }
            }
            bits = inputStream.read();
        }

        bitOutput.close();
    }

    public void findFrequency(FileInputStream inFile) throws IOException {
        //counter
        int i = 0;

        //retreive the total lenght of our input file
        int lenght = inFile.available();

        //while the lenght of the file is less than our traversal counter
        while (lenght > i) {
            //read the next byte in the file
            int readbyte = inFile.read();
            //if our read byte is not null
            if (frequencyArray[readbyte] != null) {
                //read
                frequencyArray[readbyte] = frequencyArray[readbyte] + 1;
            } else {
                frequencyArray[readbyte] = 1;
            }
            i++;
        }

        //fill the priority queue
        for (Integer j = 0; j < frequencyArray.length; j++) {
            Node node = new Node();
            int frequency = 0;
            node.setBit(j.toString());

            if (frequencyArray[j] != null) {
                frequency = frequencyArray[j];
            }

            priority.insert(new Element(frequency, node));
        }

    }
    
    public void recursiveFindTreePath(Node node, String code) {

        
        if (node != null ) {
            
            recursiveFindTreePath(node.getLeft(), code + "0");
            int treePathPlacement = Integer.parseInt(node.getBit()) ;
            bitcode[treePathPlacement] = code;
            recursiveFindTreePath(node.getRight(), code + "1");
        }

    }
    
    public String[] search(Node root){
        recursiveFindTreePath(root, "");
        return bitcode;
    }

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
