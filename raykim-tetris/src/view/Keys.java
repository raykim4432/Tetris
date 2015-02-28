/*
 * TCSS 305: Assignment 6
 */

package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;


/**
 * This enum contains information on key location in the KeyPanel layout,
 * the VK set for this action and the string name of the action.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
public enum Keys {
    
    /**
     * Key that moves piece left.
     */
    LEFT,
   
    /**
     * Key that moves piece right.
     */
    RIGHT,
    
    /**
     * Key that moves piece down.
     */
    DOWN,
    
    /**
     * Key that drops piece.
     */
    DROP,
    
    /**
     * Key that rotates piece.
     */
    ROTATE;

    //constants

    
    
    //variables
    /**
     * Contains virtual key designation of LEFT key enum.
     */
    private int myLeftVirtualKey;
    
    /**
     * Contains virtual key designation of RIGHT key enum.
     */
    private int myRightVirtualKey;
    
    /**
     * Contains virtual key designation of DOWN key enum.
     */
    private int myDownVirtualKey;
    
    /**
     * Contains virtual key designation of DROP key enum.
     */
    private int myDropVirtualKey;
    
    /**
     * Contains virtual key designation of ROTATE key enum.
     */
    private int myRotateVirtualKey;
    
    
    //constructor
    /**
     * Initializes virtual key designations for key enums.
     */
    private Keys() {
        myLeftVirtualKey = KeyEvent.VK_LEFT;
        myRightVirtualKey = KeyEvent.VK_RIGHT;
        myDownVirtualKey = KeyEvent.VK_DOWN;
        myDropVirtualKey = KeyEvent.VK_SPACE;
        myRotateVirtualKey = KeyEvent.VK_UP;
    }
    
    //setters
    /**
     * This method sets the virtual key code for the designated enum.
     * @param theVirtualKeyCode int passed in as parameter.
     */
    public void setVirtualKey(final int theVirtualKeyCode) {
        switch (this) {
            case LEFT:
                myLeftVirtualKey = theVirtualKeyCode;
                break;
            case RIGHT:
                myRightVirtualKey = theVirtualKeyCode;
                break;
            case DOWN:
                myDownVirtualKey = theVirtualKeyCode;
                break;
            case DROP:
                myDropVirtualKey = theVirtualKeyCode;
                break;
            default:
                myRotateVirtualKey = theVirtualKeyCode;
                break;
                
        }
    }
    
    //getters
    
    /**
     * Returns the virtual key designation of the Enum.
     * 
     * @return KeyCode containing name of enum.
     */
    public int getVirtualKey() {
        int virtualKey;
        switch (this) {
            case LEFT:
                virtualKey = myLeftVirtualKey;
                break;
            case RIGHT:
                virtualKey = myRightVirtualKey;
                break;
            case DOWN:
                virtualKey = myDownVirtualKey;
                break;
            case DROP:
                virtualKey = myDropVirtualKey;
                break;
            default:
                virtualKey = myRotateVirtualKey;
                break;
                
        }
            
        return virtualKey;
    }
    
    
    /**
     * Returns location on BorderLayout in KeyPanel.
     * 
     * @return positionOnBorderLayout String containing borderlayout position
     */
    public String getPositionOnLayout() {
        String positionOnBorderLayout;
        switch (this) {
            case LEFT:
                positionOnBorderLayout = BorderLayout.WEST;
                break;
            case RIGHT:
                positionOnBorderLayout = BorderLayout.EAST;
                break;
            case DOWN:
                positionOnBorderLayout = BorderLayout.SOUTH;
                break;
            case DROP:
                positionOnBorderLayout = BorderLayout.NORTH;
                break;
            default:
                positionOnBorderLayout = BorderLayout.CENTER;
                break;
                
        }
            
        return positionOnBorderLayout;
    }
}
