/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.BorderLayout;

import view.Keys;


/**
 * Panel used to display texts in the Tetris game.
 * This class can be used to display rules and keep track of points.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public final class KeyClusterPanel extends AbstractSubordinatePanel {

    //constants
    
    /**
     * Contains the key display width.
     */
    private static final double KEY_WIDTH_PERCENT_OF_PARENT = .3;
    
    /**
     * Contains the key display height.
     */
    private static final double KEY_HEIGHT_PERCENT_OF_PARENT = .30;
    
    /**
     * Contains the percentage size of this JPanel's text font relative to the JPanel
     * that contains it.
     */
    private static final double FONT_PERCENT_OF_JPANEL = .25;
    
    
    
    //variables
    
    
    //constructor and methods
    /**
     * Constructs a JPanel with a borderlayout which displays current button configurations.
     * 
     * @param theWindowWidth int the GUI window starting width
     * @param theWindowHeight int the GUI window starting height
     * @param thePercentageOfParentWidth percentage of parent width this JPanel occupies
     * @param thePercentageOfParentHeight percentage of parent height this JPanel occupies
     * @param theFontPercentageOfParentWidth percentage size of this JPanel's text font 
     * relative to the JPanel
     * @param theMessage displayed message in this JPanel
     */
    public KeyClusterPanel(final int theWindowWidth,
                    final int theWindowHeight,
                    final double thePercentageOfParentWidth,
                    final double thePercentageOfParentHeight,
                    final double theFontPercentageOfParentWidth,
                    final String theMessage) {
        super(theWindowWidth, theWindowHeight, thePercentageOfParentWidth, 
              thePercentageOfParentHeight, theFontPercentageOfParentWidth, 
              theMessage);
         
        //configure appearance
        configurePanel();
        
    }
    
    /**
     * This method builds the key panel layout.
     * 
     * KeyCode to String conversion code from:
     * stackoverflow.com/questions/12873657/how-do-i-convert-code-to-char-or-string-to
     * -show-to-show-it-on-textview
     * 
     * Code for centering justifying JLabels from:
     * stackoverflow.com/questions/6810581/how-to-center-text-in-a-jlabel
     */
    private void configurePanel() {
        setLayout(new BorderLayout());
        
        //iterate through each Keys enum and set key display
        for (final Keys key : Keys.values()) {
            

            //create Panel
            final KeyPanel tempPanel = new KeyPanel(getPanelWidth(),
                                                    getPanelHeight(),
                                                    KEY_WIDTH_PERCENT_OF_PARENT,
                                                    KEY_HEIGHT_PERCENT_OF_PARENT,
                                                    FONT_PERCENT_OF_JPANEL,
                                                    key);

            //add to this class Panel
            add(tempPanel, key.getPositionOnLayout());
            
            //add newly created subordinate panel component to subordinatePanelList
            addSubordinatePanel(tempPanel, key);
        } //end of for
        
        
    }
    
    
    

    

    
    
    
}
