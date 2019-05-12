/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encode;

/**
 * @author Mads Lykke Bach - mabac16@student.sdu.dk - mabac16
 * @author Tobias Ahrenschneider Sztuk - toszt17@student.sdu.dk - toszt17
 * @author Julie Dittmann Weimar Andersen - julan17@student.sdu.dk - julan17
 */
import java.util.Arrays;

public class PQHeap implements PQ{

    /**
     * This is the array with no size or elements in it
     */
    private Element[] elementArray;
    
    /**
     * is the current position of how many elements has been put into the array so far
     */
    private int heapSize = 0;
    
    /**
     * The constructor
     * @param maxElms is the size of the array
     */
    public PQHeap(int maxElms){
        elementArray = new Element[maxElms];
    }
    
    /**
     * Does the commonly known parent calculation for a tree. It subtracts 1 
     * before doing the calculation in order to take index 0 into account as well
     * @param pos is the position in regards to the array
     * @return the result of the calculation for the parent
     */
    private int parent(int pos){
        pos--;
        return (pos/2);
    }
    
    /**
     * Does the commonly known left child calculation for a tree.
     * @param pos is the position in regards to the array
     * @return the result of the calculation for the left child
     */
    private int left(int pos){
        return (pos * 2 + 1);
    }
    
    /**
     * Does the commonly known right child calculation for a tree.
     * @param pos is the position in regards to the array
     * @return the result of the calculation for the right child
     */
    private int right(int pos){
        return (pos * 2 + 2);
    }
    
    /**
     * Extracts the smallest element of the array
     * @return the smallest element
     */
    @Override
    public Element extractMin() {
        Element min = elementArray[0]; //Set mi to the element on index 0
        heapSize--; // Decreases the counter for the elements in the array
        elementArray[0] = elementArray[heapSize]; // replaces the element on intex 0 with the last element
        minHeapify(0); // calls minHeapify
        return min; //returns the min
    }
    
    /**
     * Makes sure that the tree/array is in min heap
     * @param parentIndex is the parent node
     */
    private void minHeapify(int parentIndex){
        int left = left(parentIndex); //gets the index of the left child
        int right = right(parentIndex); //gets the index of the right child
        int smallest;
        
        //check if the left child has the index of max the heapsize og if it is smallert then its parent
        //if true smallest is set to the left else it is the parent
        if(left <= heapSize && elementArray[left].getKey() < elementArray[parentIndex].getKey()){
            smallest = left;
        }else{
            smallest = parentIndex;
        }
        
        //check if the right child has the index of max the heapsize og if it is smallert then the already set smallest
        if(right <= heapSize && elementArray[right].getKey() < elementArray[smallest].getKey()){
            smallest = right;
        }
        
        //if the smallest is not the parent index, the elements of the parent index and smallest index are switch
        if(smallest != parentIndex){
            Element temp = elementArray[parentIndex];
            elementArray[parentIndex] = elementArray[smallest];
            elementArray[smallest] = temp;
            
            //calls the minHeapify to check on the smallest
            minHeapify(smallest);
        } 
        
        
    }
    
    public int lenght(){
        
        return elementArray.length;
    }
    
    /**
     * insert an element into the array and makes sure that the children and parent
     * are correctly setup in corresponds to each other
     * The parent has to be smaller then its children
     * @param e is the new element that should be put into the array
     */
    @Override
    public void insert(Element e) {
        int i = heapSize; // sets i to the same count as heapsize
        elementArray[i] = e; //places the new element on index i
        heapSize++; //counts one up, as there have been add a new element
        
        /**
         * checks if i is bigger the 0 and if the key of the parent is bigger then the new elements key
         * if the parent key is bigger than the new key, them they switch indexes.
         * The new is the checked at its new index
         */
        while(i >= 0 && elementArray[parent(i)].getKey() > elementArray[i].getKey()){
            Element temp = elementArray[i];
            elementArray[i] = elementArray[parent(i)];
            elementArray[parent(i)] = temp;
            i = parent(i);
        }
        
    }
    
}



