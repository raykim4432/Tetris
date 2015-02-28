/*
 * TCSS 305 - Project Tetris
 */

package model;

import java.io.Serializable;

/**
 * This interface defines the required operations of mutable Tetris pieces.
 * 
 * @author Alan Fowler
 * @version Autumn 2014
 */
public interface Piece extends Serializable {
    /* This class now implements Serializable
     * This is done so that a container class (TetrisGameData), which stores
     * current piece and next piece objects, can serialize Piece objects
     * in the process of saving current game state data.  */
    
    /** Shifts the piece one space to the left. */
    void moveLeft();

    /** Shifts the piece one space to the right. */
    void moveRight();

    /** Shifts the piece one space down. */
    void moveDown();

    /** Rotates the piece one quarter turn clockwise. */
    void rotateCW();

    /** @return the x coordinate of this Piece. */
    int getX();

    /** @return the y coordinate of this Piece. */
    int getY();
}
