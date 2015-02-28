/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

/**
 * Panel used to display next piece. 
 * This class extends TextPanel.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class NextPiecePanel extends TextPanel {

    //constants
    /**
     * The number of blocks per tetris piece.
     */
    private static final int BLOCKS_PER_PIECE = 4;
    
    /**
     * The number of blocks that make up this panels width and height.
     */
    private static final int PANEL_WIDTH_HEIGHT_IN_BLOCKS = 6;
    
    /**
     * Number of blocks the preview piece is padded from the bottom of the panel.
     */
    private static final int PANEL_BOTTOM_BORDER = 1;
    
    /**
     * Contains number of half of this panel's block width/height.
     */
    private static final double HALF_OF_PANEL_BLOCKS = PANEL_WIDTH_HEIGHT_IN_BLOCKS / 2;
    
    /**
     * Contains starter coordinates for myCurrentlyDisplayedBlockCoords that will be erased
     * as soon as coordinates for a new piece are sent via refreshNextPiecePreview.
     */
    private static final int[][] DEFAULT_BLOCK_COORDINATES = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
    
    //variables
    /**
     * Stores the coordinates of the currently displayed piece.
     */
    private int[][] myCurrentBlockCoords;
    
    //constructor and methods
    /**
     * Constructor which sets the percentage width and height this JPanel will have
     * relative to its parent container.
     * 
     * @param theWindowWidth int the GUI window starting width
     * @param theWindowHeight int the GUI window starting height
     * @param thePercentageOfParentWidth percentage of parent width this JPanel occupies
     * @param thePercentageOfParentHeight percentage of parent height this JPanel occupies
     * relative to the JPanel
     * @param theTitle String name that displays with border.
     */
    public NextPiecePanel(final int theWindowWidth,
                          final int theWindowHeight,
                          final double thePercentageOfParentWidth,
                          final double thePercentageOfParentHeight,
                          final String theTitle) {
        super(theWindowWidth, 
              theWindowHeight, 
              thePercentageOfParentWidth, 
              thePercentageOfParentHeight, 
              0, 
              theTitle, 
              null);
        
        //initialize myCurrentlyDisplayedBlockCoords with values
        myCurrentBlockCoords = DEFAULT_BLOCK_COORDINATES;
        
        //set background color
        setBackground(Color.WHITE);

        
    }
    
    /**
     * This method paints the next piece preview.
     * 
     * @param theGraphics that draws in this panel.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        
        final int panelWidthHeight = getPanelHeight() - (getPanelHeight() 
                                        % PANEL_WIDTH_HEIGHT_IN_BLOCKS);
        
        final int blockPixelSize = panelWidthHeight / PANEL_WIDTH_HEIGHT_IN_BLOCKS;
        final int pieceDisplayStartX = (int) ((getPanelWidth() / 2) 
                                        - (blockPixelSize * HALF_OF_PANEL_BLOCKS));
        
        //convert to Graphics2D class
        final Graphics2D graphics = (Graphics2D) theGraphics;
        
        //smooth the drawings
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                                  RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics.setColor(Color.BLACK);
        
        //draw preview blocks
        for (final int[] blockCoordinates : myCurrentBlockCoords) {
            
            //draw current piece blocks only when they are within the board
            graphics.fill(new Rectangle2D.
                          Double(pieceDisplayStartX + blockCoordinates[0] 
                                                          * blockPixelSize + blockPixelSize, 
                                 Math.abs(PANEL_WIDTH_HEIGHT_IN_BLOCKS 
                                          - blockCoordinates[1] - PANEL_BOTTOM_BORDER - 1) 
                                          * blockPixelSize, 
                                 blockPixelSize,
                                 blockPixelSize));    
            
        } //end of for
        
    } //end of paintComponent
    
    
    //refresh methods
    /**
     * This method repaints the preview of the next piece. To cut down on the number
     * of refreshes, it checks to see if the currently displayed piece and the next piece
     * are the same.
     * 
     * @param theNextBlockCoords of blocks next piece
     */
    public void refreshNextPiecePreview(final int[][] theNextBlockCoords) {
        boolean refreshPreview = false;
        
        //check if currently displayed piece coords and theNextBlockCoordinates are the same
        for (int i = 0; i < BLOCKS_PER_PIECE; i++) {
            
            if (myCurrentBlockCoords[i][0] != theNextBlockCoords[i][0] 
                                            || myCurrentBlockCoords[i][1] != theNextBlockCoords
                                            [i][1]) {
                refreshPreview = true;
                break;
            }
        }
        
        //if currently displayed piece coords and theNextBlockCoordinates are different,
        //display theNextBlockCoords
        if (refreshPreview) {
            myCurrentBlockCoords = theNextBlockCoords.clone();
            repaint();
        }
        
        
        
    }
}
