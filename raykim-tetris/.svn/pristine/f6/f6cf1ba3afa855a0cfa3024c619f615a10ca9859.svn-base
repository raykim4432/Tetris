/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.event.MouseInputAdapter;

import view.Keys;


/**
 * This class contains the GUI presentation of the Board class.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class BoardPanel extends AbstractSubordinatePanel implements PropertyChangeListener {

    //constants
    /**
     * Contains the number of blocks the width of this board will contain.
     */
    private static final int DEFAULT_BOARD_BLOCK_WIDTH = 10;
    
    /**
     * Contains the number of blocks the height of this board will contain.
     */
    private static final int DEFAULT_BOARD_BLOCK_HEIGHT = 20;
    
//    /**
//     * Thickness of grid lines.
//     */
//    private static final int GRID_LINE_WIDTH = 1;
    
    /**
     * Constant used in paintComponent to color first 1/5 of blocks in a certain color.
     */
    private static final double ONE_FIFTH = .2;
    
    /**
     * Constant used in paintComponent to color 1/5 to 2/5 blocks in a certain color.
     */
    private static final double TWO_FIFTHS = .4;
    
    /**
     * Constant used in paintComponent to color 2/5 to 26/5 blocks in a certain color.
     */
    private static final double THREE_FIFTHS = .6;
    
    /**
     * Contains the string "pause".
     */
    private static final String PAUSE = "pause";
    
    /**
     * Contains a propertyName that causes the TetrisGUi to only pause and not unpause.
     */
    private static final String PAUSE_ONLY = "pauseOnly";
    
    /**
     * Contains a message shown on pause of game.
     */
    private static final String PAUSE_MESSAGE = "<html><div style=\"text-align: center;\">"
                                    + "<font color=red size=+24>PAUSED</font><br>"
                                    + "<font color=blue size=+1>Press any button<br>"
                                    + "(besides the game buttons)<br>or click this frame<br>"
                                    + "to pauses or<br>unpauses game.<br><br>Clearing lines"
                                    + "<br>buys silence from<br> the music.<br><br>Speed up"
                                    + " the game<br>to get through<br>the music faster<br>"
                                    + "and advance to<br>the next song!</font></div></html>";
    
    
    //variables
    /**
     * Boolean which determines if the grid is on or not.
     */
    private boolean myGridOn;
    
    /**
     * Boolean which is true when reverse tetris mode is on.
     */
    private boolean myReverseTetrisModeOn;
    
    /**
     * Contains the number of tetris blocks that constitute this BoardPanel's width.
     */
    private int myBoardWidthInBlocks;
    
    /**
     * Contains the number of tetris blocks that constitute this BoardPanel's height.
     */
    private int myBoardHeightInBlocks;
    
    /**
     * Contains the parent container's last recorded width.
     */
    private int myParentWidth;
    
    /**
     * Contains the parent container's last recorded height.
     */
    private int myParentHeight;
    
    /**
     * Contains the size (width and height dimensions) of each tetris block.
     */
    private int myBlockSize;
    
    /**
     * Contains the key code associated with moving the current piece left.
     */
    private int myLeftKeyCode;
    
    /**
     * Contains the key code associated with moving the current piece right.
     */
    private int myRightKeyCode;
    
    /**
     * Contains the key code associated with rotating the current piece.
     */
    private int myRotateKeyCode;
    
    /**
     * Contains the key code associated with dropping the piece.
     */
    private int myDropKeyCode;
    
    /**
     * Contains the key code associated with moving the current piece down.
     */
    private int myDownKeyCode;
    
    /**
     * This int[][] will contain current Board piece block coordinates.
     */
    private int[][] myCurrentBlockCoordArray;
    
    /**
     * Variable that determines what color the current block will be colored.
     */
    private Color myCurrentBlockColor;
    
    
    /**
     * JLabel that displays game over message.
     */
    private JLabel myGameOverMessage;
    
    /**
     * JLabel that displays replay mode message.
     */
    private JLabel myReplayMessage;
    
    /**
     * This deque contains frozen blocks.
     */
    private List<int[]> myFrozenBlocksList;
    
    //constructor and methods
    /**
     * This constructor creates a tetris board piece. This board panel always maintains
     * a width and a length that is a multiple of 10 pixels. 
     * 
     * Zeros are passed into the thePercentageOfParentWidth and thePercentageOfParentHeight
     * parameters for the parent class constructor as the parent adjustPanelSize method
     * is overridden by this class.
     * 
     * Likewise zero is passed in for the theFontPercentageOfParentWidth parameter as the
     * adjustFontSize method is overridden by this class as well.
     * 
     * No string message is displayed by this panel.
     * 
     * @param theWindowWidth int the GUI window starting width
     * @param theWindowHeight int the GUI window starting height
     * @param theBoardWidthInBlocks int board width in blocks
     * @param theBoardHeightInBlocks int board height in blocks
     */
    public BoardPanel(final int theWindowWidth, 
                      final int theWindowHeight,
                      final int theBoardWidthInBlocks,
                      final int theBoardHeightInBlocks) {
        super(theWindowWidth, theWindowHeight, 0, 0, 0, PAUSE_MESSAGE);
        
        myBoardWidthInBlocks = theBoardWidthInBlocks;
        myBoardHeightInBlocks = theBoardHeightInBlocks;
        
        
        myFrozenBlocksList = new LinkedList<>();
        
        //configure BoardPanel
        configureBoardPanel();
        
        //assign key codes to movement(left, right, drop, etc.)
        updateKeyCodes();
        
        //add listeners and observable
        addKeyListener(new TetrisKeyControls());
        addMouseListener(new TetrisMouseControls());
        
    }
    
    /**
     * This method configures this BoardPanel's appearance.
     */
    private void configureBoardPanel() {
        
        //set background color
        setBackground(Color.WHITE);
        
        //set transparent
//        setOpaque(true);
        
        //turn off grids
        myGridOn = false;
        
        /* create game over message */
        final String gameOver = "<html><font color=red size=+16>Game Over</font></html>";
        
        //initialize game over JLabel
        myGameOverMessage = new JLabel(gameOver);
        myGameOverMessage.setVisible(false);
        
        add(myGameOverMessage);
        
        /* create replay message */
        final String replay = "<html><font color=green size=+16>Replay Mode</font></html>";
        
        //initialize game over JLabel
        myReplayMessage = new JLabel(replay);
        myReplayMessage.setVisible(false);
        
        add(myReplayMessage);
        
    }

    /**
     * Draws onto the drawing area part of the GUI.
     * 
     * @param theGraphics graphics object passed in by parent method.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        //convert to Graphics2D class
        final Graphics2D graphics = (Graphics2D) theGraphics;
        
        //smooth the drawings
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                  RenderingHints.VALUE_ANTIALIAS_ON);
        
        /* draw current piece blocks */
        //set color for current piece blocks
        graphics.setColor(myCurrentBlockColor);
        for (final int[] blockCoordinates : myCurrentBlockCoordArray) {
            
            //draw current piece blocks only when they are within the board
            if (blockCoordinates[1] < myBoardHeightInBlocks) {
                graphics.fill(new Rectangle2D.
                              Double(blockCoordinates[0] * myBlockSize, 
                                     Math.abs(blockCoordinates[1] - myBoardHeightInBlocks 
                                              + 1) * myBlockSize, 
                                              myBlockSize,
                                              myBlockSize));
            }
            
                                             
        }
        
        /* draw frozen blocks */
        for (int i = 0; i < myFrozenBlocksList.size(); i++) {
            
            //set color for frozen blocks
            theGraphics.setColor(setFrozenBlockColor(myFrozenBlocksList.get(i)[1]));
            
            //draw frozen block
            final int frozenBlockX = myFrozenBlocksList.get(i)[0] * myBlockSize;
            final int frozenBlockY = Math.abs(myFrozenBlocksList.get(i)[1] 
                                            - myBoardHeightInBlocks + 1) * myBlockSize;
            final Rectangle2D frozenBlocks = new Rectangle2D.Double(frozenBlockX, 
                                                                    frozenBlockY, 
                                                                    myBlockSize, 
                                                                    myBlockSize);
            
            graphics.fill(frozenBlocks);
            //draw border around squares
            graphics.setColor(Color.WHITE);
            graphics.draw(frozenBlocks);
        }
        
        //display grid (if myGridOn is true)
        if (myGridOn) {
            final int currentWidth = getWidth();
            final int currentHeight = getHeight();
            
            //set grid with and color
            graphics.setStroke(new BasicStroke(1));
            graphics.setColor(Color.GRAY);
            
            //draw horizontal lines
            for (int i = 0; i <= currentHeight; i = i + myBlockSize) {
                graphics.drawLine(0, i, currentWidth, i);
            }
            
            //draw vertical lines
            for (int i = 0; i <= currentWidth; i = i + myBlockSize) {
                graphics.drawLine(i, 0, i, currentHeight);
            }
        }
        
    } //end of paintComponent
    
    /**
     * This method chooses a graphics color for the blocks based on the passed in line.
     * @param theLine int current line of frozen blocks being drawn
     * 
     * @return Color new draw color that is selected
     */
    private Color setFrozenBlockColor(final int theLine) {
        Color returnColor = null;
        
        if (myReverseTetrisModeOn) {

            if (theLine <= myBoardHeightInBlocks * ONE_FIFTH) {
                //for first 1/5 of blocks color green
                returnColor = Color.BLUE;
            } else if (theLine <= myBoardHeightInBlocks * TWO_FIFTHS) {
                //for 1/5 to 1/2 blocks color yellow
                returnColor = Color.GREEN;
            } else if (theLine <= myBoardHeightInBlocks * THREE_FIFTHS) {
                //for 1/2 to 4/5 blocks color yellow
                returnColor = Color.YELLOW;   
            } else {
                //everything else color red
                returnColor = Color.RED; 
            }
        } else {
            returnColor = Color.BLACK;
        }
        
        return returnColor;
    }
    
    //refresh methods
    /**
     * This method takes the current height and width of the container which nests the tetris 
     * board and adjusts the size of this BoardPanel so that it occupies as much of the
     * container's area while still keeping its height and width divisible by 10.
     * 
     * @param theParentWidth int width of parent container
     * @param theParentHeight in height of parent container
     */
    @Override
    public void adjustPanelSize(final int theParentWidth, final int theParentHeight) {
        if (myBoardWidthInBlocks == 0) {
            myBoardWidthInBlocks = DEFAULT_BOARD_BLOCK_WIDTH;
            myBoardHeightInBlocks = DEFAULT_BOARD_BLOCK_HEIGHT;
        }
        
        myParentWidth = theParentWidth;
        myParentHeight = theParentHeight;
                                        
        //take the parent width and height, mod it by 10 and subtract the resulting mod
        //from the original parent width and height to get the width and height of this
        //BoardPanel
        final int panelHeight = theParentHeight 
                                        - (theParentHeight % myBoardHeightInBlocks);
        
        final int panelWidth = (int) (panelHeight * ((float) myBoardWidthInBlocks 
                                        / myBoardHeightInBlocks));
        
        
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        
        //set each tetris block's width and height
        myBlockSize = panelHeight / myBoardHeightInBlocks;
        
   
    }
    
    
    /**
     * This method is passed an int[][] of the current piece's block coordinates.
     * It is also passed a List of frozen block coordinates.
     * (all are in board coordinates)
     * 
     * This method also calculates a projection of where the current piece will fall when 
     * the drop key is hit.
     * 
     * @param theCurrentBlockCoordinates int[][] containing blocks of the current piece
     * @param theFrozenBlocksList List<int []> coordinates of frozen blocks

     */
    public void updateGraphicalBlocks(final int[][] theCurrentBlockCoordinates,
                                      final List<int[]> theFrozenBlocksList) {
        myCurrentBlockCoordArray = theCurrentBlockCoordinates.clone();
        myFrozenBlocksList = theFrozenBlocksList;
        
//        //calculate drop projection
//        //retrieve coordinates of myCurrentBlockCoordArray elements have the lowest y coords
//        final List<Integer> xCoordOfLowestBlocks;
//        //find the lowest Y coord
//        int coordsWithLowestY = myCurrentBlockCoordArray[0][1];
//        for (int i = 1; i < myCurrentBlockCoordArray.length; i++) {
//            if (coordsWithLowestY > myCurrentBlockCoordArray[i][1]) {
//                coordsWithLowestY = myCurrentBlockCoordArray[i][1];
//            }
//        }
//        
//        //retrieve all blocks with coordsWithLowestY as their Y coord
//        for (final int[] coords : myCurrentBlockCoordArray) {
//            if (coords[1] == coordsWithLowestY) {
//                xCoordOfLowestBlocks.add(coords[0]);
//            }
//        }
//        
//        //iterate through frozen blocks (starting from the highest frozen blocks) and see
//        //where the lowest blocks of the current piece run into the frozen blocks
//        final Iterator<int[]> frozenBlockReverseIterator = 
//                                        ((LinkedList<int[]>) myFrozenBlocksList).
//                                        descendingIterator();
//        while (frozenBlockReverseIterator.hasNext()) {
//            //see if there is a block occupying the x coord 
//            //(of the lowest current piece block) 
//            //at the current height of frozen blocks being inspected 
//            //during this current iteration
//            for (final int xCoord : xCoordOfLowestBlocks) {
//                if (xCoord) {
//                    
//                }
//            }
//            
//        }
        
        repaint();
    }
    
    /**
     * This method updates the key codes associated with left, right, drop, etc.
     * It is called during the creation of this class, and whenever keys are changed/
     * updated in the KeyPanel class.
     */
    private void updateKeyCodes() {
        myLeftKeyCode = Keys.LEFT.getVirtualKey();
        myRightKeyCode = Keys.RIGHT.getVirtualKey();
        myRotateKeyCode = Keys.ROTATE.getVirtualKey();
        myDropKeyCode = Keys.DROP.getVirtualKey();
        myDownKeyCode = Keys.DOWN.getVirtualKey();
    }
    
    //setters
    /**
     * This method displays or undisplays game over message.
     * 
     * @param theBoolean true sets game over JLabel visible, false sets it invisible
     */
    public void gameOverTextVisible(final Boolean theBoolean) {
        myGameOverMessage.setVisible(theBoolean);
    }
    
    /**
     * This method displays or undisplays replay message.
     * 
     * @param theBoolean true sets replay JLabel visible, false sets it invisible
     */
    public void replayTextVisible(final Boolean theBoolean) {
        myReplayMessage.setVisible(theBoolean);
    }
    
    /**
     * This method sets new values for the board width and height in blocks.
     * Then it calls for a resize on this panel.
     * 
     * @param theBoardWidthInBlocks int new board width in blocks
     * @param theBoardHeightInBlocks int new board height in blocks
     */
    public void setWidthHeightInBlocks(final int theBoardWidthInBlocks,
                                       final int theBoardHeightInBlocks) {
        myBoardWidthInBlocks = theBoardWidthInBlocks;
        myBoardHeightInBlocks = theBoardHeightInBlocks;
        
        adjustPanelSize(myParentWidth, myParentHeight);
    }
    
    /**
     * This method changes the appearance of the BoardPanel once reverse tetris mode
     * is turned on.
     * 
     * @param theBoolean true means reverse tetris mode is on, false means it is not
     */
    public void reverseTetrisModeOn(final boolean theBoolean) {  
        myReverseTetrisModeOn = theBoolean;
        
        if (myReverseTetrisModeOn) {
            //reverse tetris mode on
            setBackground(Color.BLACK);
            myCurrentBlockColor = Color.WHITE;
        } else {
            //reverse tetris mode off
            setBackground(Color.WHITE);
            myCurrentBlockColor = Color.DARK_GRAY;
        }
        
    }
    
    //property change listeners
    /**
     * This method listens for fired property changes from KeyPanel objects.
     * It then updates the key code assignments in this class via calling the
     * updateKeyCoes() method.
     * 
     * @param theEvent that initiated this method
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        switch (theEvent.getPropertyName()) {
            case PAUSE_ONLY:
                firePropertyChange(PAUSE_ONLY, true, false);
                break;
            case "grid":
                if (myGridOn) {
                    myGridOn = false;
                } else {
                    myGridOn = true;
                }
                repaint();
                break;
            default:
                updateKeyCodes();
                break;
        }
  
    }
    
    //inner classes
    //key listeners
    /**
     * Key listener that listens for keys pressed to control the Tetris game.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     */
    public class TetrisKeyControls extends KeyAdapter {
        
        /**
         * This method listens for keys pressed to control the movement of active
         * pieces in the tetris game. Then, it fires a propery change, which is picked
         * up by the TetrisGUI class.
         * 
         * @param theEvent keystrokes in the CENTER pane JPanel that triggered this
         * method.
         */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int keyPressedCode = theEvent.getKeyCode();
            
            //determine which key was pressed and fire property change with the appropriate
            //propertyName
            if (keyPressedCode == myLeftKeyCode) { //left
                firePropertyChange("left", true, false);
            } else if (keyPressedCode == myRightKeyCode) { //right
                firePropertyChange("right", true, false);
            } else if (keyPressedCode == myRotateKeyCode) { //rotate
                firePropertyChange("rotate", true, false);
            } else if (keyPressedCode == myDropKeyCode) { //drop
                firePropertyChange("drop", true, false);
            } else if (keyPressedCode == myDownKeyCode) { //down
                firePropertyChange("down", true, false);
            } else { //pause
                firePropertyChange(PAUSE, true, false);
            }
        }
        
    } //end of TetrisKeyControls


    //mouse listener
    /**
     * This class allows this BoardPanel class to regain focus on click.
     * It also pauses the game on click.
     * 
     * 
     * @author Ray Kim
     * @version Autumn 2014
     *
     */
    public class TetrisMouseControls extends MouseInputAdapter {
        
        /**
         * This method causes this BoardPanel to gain focus.
         * It also pauses or unpauses the game.
         * 
         * @param theEvent which spawned this action.
         * 
         */
        @Override
        public void mouseClicked(final MouseEvent theEvent) {
            //toggle pause of game by firing property changed to TetrisGUI
            firePropertyChange(PAUSE, true, false);
            
            //get focus on this JPanel
            requestFocus();
        }
    } //end of TetrisMouseControls
}
