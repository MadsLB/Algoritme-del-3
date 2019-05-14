/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Mads
 */
public class Node {
    

    private int bit;
    private Node left = null;
    private Node right = null;
    private boolean isLeaf = false;

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    
    public boolean isLeaf(){
        return this.isLeaf;
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
