/*
 * TCSS 305: Assignment 6
 */

package panels;

/**
 * This interface sets method signatures necessary to have components in the TetrisFrame
 * rightPanel adjust size as the Tetris program window size is changed.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
public interface SubordinatePanel {
    

    /**
     * This method takes in the current higher elements container size and adjusts
     * this JPanel element's width and height so that it remains a constant percentage of
     * the parent container's size.
     * 
     * This method recursively calls adjustPanelSize() on subordinate panels held within an
     * a collection in this class.
     * 
     * @param theParentWidth int width of parent container
     * @param theParentHeight in height of parent container
     */
    void adjustPanelSize(int theParentWidth, int theParentHeight);
    
    /**
     * This method is called from adjustPanelSize and adjusts the font of the underyling 
     * JLabel of this JPanel so that text fits within this JPanel.
     * 
     * @param theJPanelWidth int width of JPanel
     */
    void adjustFontSize(int theJPanelWidth);
    
    /**
     * This method sets/adjusts the text displayed by this JPanel.
     * @param theMessage String that replaces currently displayed message in JPanel
     */
    void setText(String theMessage);
    
    /**
     * Getter method for retrieving this JPanel's current width.
     * 
     * @return int pixel width.
     */
    int getPanelWidth();
    
    /**
     * Getter method for retrieving this JPanel's current height.
     * 
     * @return int pixel height.
     */
    int getPanelHeight();

    /**
     * Getter method to retrieve this JPanel's parent width occupation percentage.
     * 
     * @return double percentage of parent width that this JPanel occupies.
     */
    double getParentWidthOccupationPercentage();
    
    /**
     * Getter method to retrieve this JPanel's parent height occupation percentage.
     * 
     * @return double percentage of parent height that this JPanel occupies.
     */
    double getParentHeightOccupationPercentage();
    
    /**
     * This method allows the JLabel to be visible/invisible.
     * @param theBoolean true sets to visible
     */
    void setJLabelVisible(boolean theBoolean);
    

    
}
