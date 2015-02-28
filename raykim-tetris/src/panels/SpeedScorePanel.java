/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Panel used to display the scoring scheme. On mouse over, a JSlider, that can be used to 
 * alter game speed, appears.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class SpeedScorePanel extends TextPanel {

    //constants
    /**
     * JSlider mySpeedSlider's min speed.
     */
    private static final int JSLIDER_MIN_PERCENTAGE = 0;
    
    /**
     * JSlider mySpeedSlider's max speed.
     */
    private static final int JSLIDER_MAX_PERCENTAGE = 100;
    
    /**
     * JSlider mySpeedSlider's major tick intervals.
     */
    private static final int MAJOR_TICK_INTERVALS = 25;
    
    
    //variable
    /**
     * Boolean, true toggles the slider into visibility. False makes it invisible.
     */
    private boolean myShowSpeedJSlider;
    
    /**
     * JLabel that instructs to click on this panel to show JSlider.
     */
    private JLabel myMouseEnteredMessage;
    
    /**
     * JLabel that instructs on what the JSlider does.
     */
    private JLabel mySliderMessage;
    
    /**
     * JSlider that sets speed of tetris game.
     */
    private final JSlider mySpeedSlider;
    
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
    public SpeedScorePanel(final int theWindowWidth,
                           final int theWindowHeight,
                           final double thePercentageOfParentWidth,
                           final double thePercentageOfParentHeight,
                           final double theFontPercentageOfParentWidth,
                           final String theTitle,
                           final String theMessage) {
        super(theWindowWidth, theWindowHeight, thePercentageOfParentWidth, 
              thePercentageOfParentHeight, theFontPercentageOfParentWidth, 
              theTitle, theMessage);
        
        mySpeedSlider = new JSlider(JSlider.HORIZONTAL, JSLIDER_MIN_PERCENTAGE, 
                                    JSLIDER_MAX_PERCENTAGE, JSLIDER_MIN_PERCENTAGE);
        
        createJSliderAndMessages();
        
        addActionListeners();
    }
    
    /**
     * This method creates the JSlider for controlling the game speed.
     */
    private void createJSliderAndMessages() {
        //create "click to edit speed" text JLabel
        myMouseEnteredMessage = new JLabel("<html><font color=red>Click for game speed slider"
                                        + "</font><html>");
        myMouseEnteredMessage.setVisible(false);
        
        add(myMouseEnteredMessage);
        
        //create jslider message
        mySliderMessage = new JLabel("+ Game Speed %");
        mySliderMessage.setVisible(false);
        
        add(mySliderMessage);
        
        //create jslider
        mySpeedSlider.setMajorTickSpacing(MAJOR_TICK_INTERVALS);
        mySpeedSlider.setPaintTicks(true);
        mySpeedSlider.setSnapToTicks(true);
        mySpeedSlider.setPaintLabels(true);
        
        mySpeedSlider.setVisible(false);
        mySpeedSlider.setOpaque(false);
        
        //add change listener
        mySpeedSlider.addChangeListener(new ChangeListener() {

            /**
             * This method sends new game speeds to TetrisGUI.
             */
            @Override
            public void stateChanged(final ChangeEvent theArg) {
                firePropertyChange("game speed", 0, 
                                   ((JSlider) theArg.getSource()).getValue());
                
            }
            
        }); //end of PropertyChangeListener
        
        add(mySpeedSlider);
    }
    
    
    
    /**
     * This method adds listeners to objects of this class.
     */
    private void addActionListeners() {
        addMouseListener(new SpeedSelectorMouseListener());
    }
    
    
    //inner classes
    //mouse listener
    /**
     * This class allows for panel to display a JSlider on mouse on.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     *
     */
    public class SpeedSelectorMouseListener extends MouseAdapter {
        
        /**
         * Clicking on this panel will make the speed JSlider visible.
         * Reclicking it will make it invisible.
         * 
         * @param theEvent which triggered this method
         * 
         */
        @Override
        public void mouseClicked(final MouseEvent theEvent) {
            
            if (myShowSpeedJSlider) {
                //make JSlider invisible
                //set JLabel text visible
                setJLabelVisible(true);
                
                //set slider and slider label invisible
                mySpeedSlider.setVisible(false);
                mySliderMessage.setVisible(false);
                
                myShowSpeedJSlider = false;
            } else {
                //make JSlider visible
                //set JLabel text invisible
                setJLabelVisible(false);
                
                //set slider and slider label visible
                mySpeedSlider.setVisible(true);
                mySliderMessage.setVisible(true);
                
                myShowSpeedJSlider = true;
                
                myMouseEnteredMessage.setVisible(false);
                
            }
                
            
            
            
            
        } //end of mouseEntered
        
        /**
         * On mouse entered, the user is instructed to click on this panel to 
         * display game speed slider.
         * 
         * @param theEvent which triggered this method
         * 
         */
        @Override
        public void mouseEntered(final MouseEvent theEvent) {
            if (!myShowSpeedJSlider) {
                //cause the game to pause
                firePropertyChange("pauseOnly", false, true);

                myMouseEnteredMessage.setVisible(true);
                
            }
            
            
        } //end of mouseEntered
        
        
    }
    
}
