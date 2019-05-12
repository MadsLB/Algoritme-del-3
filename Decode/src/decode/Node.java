/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decode;

/**
 *
 * @author Mads
 */
public class Node {
    
    private int bit;
    private Node left = null;
    private Node right = null;
    private boolean isLeaf = false;
    
    public boolean isLeaf(){
        return this.isLeaf;
    }

    public void setIsLeaf(Boolean b){
        this.isLeaf = b;
    }
    
    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }


    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

}
