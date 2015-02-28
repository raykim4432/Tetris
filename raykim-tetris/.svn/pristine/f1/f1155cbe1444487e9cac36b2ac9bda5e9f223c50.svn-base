/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * The abstract implementation of SubordinatePanel.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractSubordinatePanel extends JPanel implements SubordinatePanel {


    //variables
    /**
     * Contains current panel width.
     */
    private int myPanelWidth;
    
    /**
     * Contains current panel height.
     */
    private int myPanelHeight;
    
    /**
     * Contains percentage of the parent container's width that this JPanel occupies.
     */
    private final double myPercentageOfParentWidth;
    
    /**
     * Contains percentage of the parent container's height that this JPanel occupies.
     */
    private final double myPercentageOfParentHeight;
    
    /**
     * Contains percentage size of this JPanel's text font relative to the JPanel.
     */
    private final double myFontPercentageOfParentWidth;
    
    /**
     * JLabel that contains myDisplayedText.
     */
    private final JLabel myTextLabel;
    
    /**
     * Map that contains the SubordinatePanel components as the key and maps each component
     * to an object.
     */
    private final Map<SubordinatePanel, Object> mySubordinatePanelToValueMap;
    
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
     * @param theMessage displayed message in this JPanel
     */
    protected AbstractSubordinatePanel(final int theWindowWidth,
                                       final int theWindowHeight,
                                       final double thePercentageOfParentWidth,
                                       final double thePercentageOfParentHeight,
                                       final double theFontPercentageOfParentWidth,
                                       final String theMessage) {
        super();
        
        myPercentageOfParentWidth =  thePercentageOfParentWidth;
        myPercentageOfParentHeight = thePercentageOfParentHeight;
        myFontPercentageOfParentWidth = theFontPercentageOfParentWidth;
        myPanelWidth = 0;
        myPanelHeight = 0;
        
        mySubordinatePanelToValueMap = new HashMap<>();
        
        //set message of JPanel
        myTextLabel = new JLabel(theMessage, SwingConstants.CENTER);
        
        //adjust JLabel font size
        adjustPanelSize(theWindowWidth, theWindowHeight);
        
        //add JLabel to JPanel only if text has been passed in through the constructor
        if (theMessage != null) {
            add(myTextLabel);
        }
        
        
    }
    
    //setters
    /**
     * {@inheritDoc}
     */
    @Override
    public void adjustPanelSize(final int theParentWidth, final int theParentHeight) {
        
        myPanelWidth = (int) (myPercentageOfParentWidth * theParentWidth);
        myPanelHeight = (int) (myPercentageOfParentHeight * theParentHeight);
        
        setPreferredSize(new Dimension(myPanelWidth, myPanelHeight));
        
        //adjust font size
        adjustFontSize(myPanelWidth);
        
        //iterate through each subordinate TextPanel component of this Panel, update size
        for (final SubordinatePanel subordPanel : mySubordinatePanelToValueMap.keySet()) {
            subordPanel.adjustPanelSize(getPanelWidth(), getPanelHeight());
        }
            
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void adjustFontSize(final int theJPanelWidth) {
        final int fontSize = (int) (theJPanelWidth * myFontPercentageOfParentWidth);
        
        myTextLabel.setFont(new Font("Serif", Font.PLAIN, fontSize));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(final String theMessage) {
        
        myTextLabel.setText(theMessage);
        
    }
    
    /**
     * This method adds subordinate panels to an underlying collection.
     * @param theSubordinatePanel added to this SubordinatePanel
     * @param theValue to which a subordinatePanel maps to
     */
    protected void addSubordinatePanel(final SubordinatePanel theSubordinatePanel,
                                    final Object theValue) {
        
        mySubordinatePanelToValueMap.put(theSubordinatePanel, theValue);
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setJLabelVisible(final boolean theBoolean) {
        myTextLabel.setVisible(theBoolean);
    }
    
    //getters
    /**
     * This method retrieves an Object value mapped to an argument provided subordinatePanel.
     * 
     * @param theSubordinatePanel used to search Map for Object
     * @return Object value that is mapped to input subordinatePanel
     */
    protected Object retrieveSubordinateMappedObject(final SubordinatePanel 
                                                     theSubordinatePanel) {
       
        final Object mappedValue = mySubordinatePanelToValueMap.get(theSubordinatePanel);
        
        return mappedValue;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getPanelWidth() {
       
        return myPanelWidth;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getPanelHeight() {
       
        return myPanelHeight;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentWidthOccupationPercentage() {
       
        return myPercentageOfParentWidth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentHeightOccupationPercentage() {

        return myPercentageOfParentHeight;
    }
    

    
}
