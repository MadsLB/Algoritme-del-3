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
public class HuffTree {
    private Node root;
    
    public HuffTree(Node node){
        this.root = node;
        
       
    }
    
    public Node getRoot(){
        return this.root;
    }
    
}
