/*
 * TCSS 305: Assignment 6
 */

package view;

/**
 * This class stores Board.class coordinates of the Block objects that compose Piece Objects.
 * 
 * Objects of this class are kept in collections in the BoardPanel class (such as frozen
 * blocks and currentpiece blocks) and help graphically represent the blocks in the Board
 * class logic.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
public class GraphicalBlock {

    //variables
    /**
     * Contains a reference to the Board.class x coordinate.
     */
    private int myXCoord;
    
    /**
     * Contains a reference to the Board.class y coordinate.
     */
    private int myYCoord;
    
    
    //constructor and methods
    /**
     * This constructor takes in the starting coordinates of this object.
     * 
     * @param theXCoord the starting x coordinate
     * @param theYCoord the starting y coordinate
     */
    public GraphicalBlock(final int theXCoord, final int theYCoord) {
        
        myXCoord = theXCoord;
        
        myYCoord = theYCoord;
    }
    
    //getters
    /**
     * Returns the x coordinate of this object.
     * 
     * @return int x coordinate of this object
     */
    public int getX() {
        return myXCoord;
    }
    
    /**
     * Returns the y coordinate of this object.
     * 
     * @return int y coordinate of this object
     */
    public int getY() {
        return myYCoord;
    }
    
    //setters
    /**
     * Sets this objects new x coordinate.
     * 
     * @param theNewX int the new x coordinate
     */
    public void setX(final int theNewX) {
        myXCoord = theNewX;
    }
    
    /**
     * Sets this objects new y coordinate.
     * 
     * @param theNewY int the new y coordinate
     */
    public void setY(final int theNewY) {
        myYCoord = theNewY;
    }
}
