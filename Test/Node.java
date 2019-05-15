/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 * Mads Lykke Bach | Mabac16
 * Tobias Ahrenschneider Sztuk | Toszt17
 * Julie Dittmann Weimar Andersen | Julan17
 */
public class Node {
    
	
    private int byte_;
    private Node left = null;
    private Node right = null;
    private boolean isLeaf = false;

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    
    public boolean isLeaf(){
        return this.isLeaf;
    }

    public int getByte() {
        return byte_;
    }

    public void setByte(int bit) {
        this.bit = byte_;
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
