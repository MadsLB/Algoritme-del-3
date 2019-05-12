/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;


/**
 *
 * @author Mads
 */
public class Decode {
    
    private String decoded;
    private int[] frequencyArray = new int[256];
    private PQHeap priority = new PQHeap(256);

    private String in, out;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String in = args[0];
        String out = args[1];
        Decode decode = new Decode(in, out);
        FileInputStream fileIn = new FileInputStream(in);
        
        BitInputStream inputStream = new BitInputStream(fileIn);
        decode.fillTable(inputStream);
        Element root = decode.huffMan(decode.priority);
        
        while(inputStream.readBit() != -1)
            decode.writeFile(root, inputStream);
        
        fileIn.close();
        inputStream.close();
    }
    
    public Decode(String in, String out){
        this.in = in;
        this.out = out;
    }
    
    public void writeFile(Element root, BitInputStream inputStream) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(new File(out));
        Node node = (Node) root.getData();
        int bit = inputStream.readBit();
        while(bit != -1){
            if(bit == 1){
                node = node.getRight();
                if(node.isLeaf()){
                    fileOut.write(node.getBit());
                    node = (Node) root.getData();
                }
            }else{
                node = node.getLeft();
                if(node.isLeaf()){
                    fileOut.write(node.getBit());
                    node = (Node) root.getData();
                }
            }
            bit = inputStream.readBit();
        }
        
        inputStream.close();
    }
    
    public void fillTable(BitInputStream input) throws IOException{
        for (int i = 0; i < frequencyArray.length; i++) {
            frequencyArray[i] = input.readInt();
        }
        
        for (Integer j = 0; j < frequencyArray.length; j++) {
            Node node = new Node();
            int frequency = 0;
            node.setBit(j);
            node.setIsLeaf(true);

            if (frequencyArray[j] != 0) {
                frequency = frequencyArray[j];
            }

            priority.insert(new Element(frequency, new HuffTree(node)));
        }
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
            q.insert(new Element(key, new HuffTree(z)));

        }

        return q.extractMin();
    }
    
}
