/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

/**
 *
 * @author Mads
 */
public class Node {
    
    private String bit = null;
    private Node left = null;
    private Node right = null;

    public String getBit() {
        return bit;
    }

    public void setBit(String bit) {
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
