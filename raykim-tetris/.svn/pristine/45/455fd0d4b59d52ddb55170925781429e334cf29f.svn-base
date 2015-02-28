/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


/**
 * Panel used to display texts in the Tetris game.
 * This class can be used to display rules and keep track of points.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class TextPanel extends AbstractSubordinatePanel {

    
    //variables
    /**
     * Contains the title of this TextPanel.
     */
    private final String myTitle;
    
    //constructor and methods
    /**
     * Constructor which sets the percentage width and height this JPanel will have
     * relative to its parent container.
     * 
     * @param theWindowWidth int the GUI window starting width
     * @param theWindowHeight int the GUI window starting height
     * @param thePercentageOfParentWidth percentage of parent width this JPanel occupies
     * @param thePercentageOfParentHeight percentage of parent height this JPanel occupies
     * @param theFontPercentageOfParentWidth percentage size of this JPanel's text font 
     * relative to the JPanel
     * @param theTitle String name that displays with border.
     * @param theMessage displayed message in this JPanel
     */
    public TextPanel(final int theWindowWidth,
                     final int theWindowHeight,
                     final double thePercentageOfParentWidth,
                     final double thePercentageOfParentHeight,
                     final double theFontPercentageOfParentWidth,
                     final String theTitle,
                     final String theMessage) {
        super(theWindowWidth, theWindowHeight, thePercentageOfParentWidth, 
              thePercentageOfParentHeight, theFontPercentageOfParentWidth, 
              theMessage);
        
        myTitle = theTitle;
        
        //create border for this subordinatePanel
        generateBorder();
    }
    
    /**
     * This method generates a border for this subordinatePanel.
     */
    private void generateBorder() {
        //create outer border that is common to all subordinate rightPanel panels 
        final Border outerBorder = BorderFactory.createEtchedBorder(EtchedBorder. 
                                                                    RAISED,
                                                                    Color.GREEN, 
                                                                    Color.DARK_GRAY);
        
        //set border for this subordinate
        setBorder(BorderFactory.createTitledBorder(outerBorder,
                                                   myTitle));
    }
    
    
    

    
    
}
