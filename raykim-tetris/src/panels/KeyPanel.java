/*
 * TCSS 305: Assignment 6
 */

package panels;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;

import view.Keys;

/**
 * This class holds individual key panels that go within the KeyClusterPanel.
 * Each key has a listener which allows keys to be changed to different virtual keys.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class KeyPanel extends AbstractSubordinatePanel {

    //constants
    /**
     * Stores JTextField quantity of columns.
     */
    private static final int JTEXTFIELD_COLUMNS = 3;
    
    /**
     * Contains the default border for key TextPanels.
     */
    private static final Border DEFAULT_KEY_TEXT_BORDER = BorderFactory.
                                    createEtchedBorder(EtchedBorder.RAISED,
                                                       Color.BLACK, 
                                                       Color.DARK_GRAY);
    
    /**
     * Contains an etched border used to assist in highlighting this KeyPanel when mouse
     * enters over this KeyPanel.
     */
    private static final Border ETCHED_BORDER = 
                                    BorderFactory.createEtchedBorder(EtchedBorder. 
                                                                     RAISED,
                                                                     Color.RED, 
                                                                     Color.DARK_GRAY);
    
    /**
     * Contains the title border that uses ETCHED_BORDER. This is sued to highlight the
     * KeyPanel when mouse enters over this KeyPanel.
     */
    private static final Border TITLED_BORDER = 
                                    BorderFactory.createTitledBorder(ETCHED_BORDER,
                                                                     "EDIT KEY",
                                                                     TitledBorder.CENTER,
                                                                     TitledBorder.TOP);
    
    //variables
    /**
     * Boolean which determines if the keyListener is active.
     */
    private boolean myKeyListenerActive;
    
    
    /**
     * Stores the underlying enum of this KeyPanel.
     */
    private final Keys myKeyEnum;
    
    /**
     * Contains message to user when cursor is over this KeyPanel. This prompt instructs
     * how to designate new key.
     */
    private final JLabel myRolloverPrompt;
    
    /**
     * The text field that displays when a KeyPanel is pressed.
     */
    private final JTextField myNewKeyField;
    
    //constructor and methods
    /**
     * Constructor for initializing KeyPanel.
     * 
     * @param theWindowWidth int the GUI window starting width
     * @param theWindowHeight int the GUI window starting height
     * @param thePercentageOfParentWidth percentage of parent width this JPanel occupies
     * @param thePercentageOfParentHeight percentage of parent height this JPanel occupies
     * @param theFontPercentageOfParentWidth percentage size of this JPanel's text font 
     * relative to the JPanel
     * @param theKey Keys enum from which a display message is derived
     */
    protected KeyPanel(final int theWindowWidth, 
                       final int theWindowHeight,
                       final double thePercentageOfParentWidth, 
                       final double thePercentageOfParentHeight,
                       final double theFontPercentageOfParentWidth, 
                       final Keys theKey) {
        super(theWindowWidth, theWindowHeight, thePercentageOfParentWidth,
              thePercentageOfParentHeight, theFontPercentageOfParentWidth, 
              buildKeyPanelText(theKey));
        
        myKeyEnum = theKey;
        myRolloverPrompt = new JLabel();
        myNewKeyField =  new JTextField(null, JTEXTFIELD_COLUMNS);
        configureRollOverAndSelection();
        
        
        //set borders to default constant
        setBorder(DEFAULT_KEY_TEXT_BORDER);
        
        //change color
        setBackground(Color.WHITE);
        
        //add mouse listener adapters
        configureListeners();
    }
    
    /**
     * This method configures the JTextField which appears when this KeyPanel is pressed.
     */
    private void configureRollOverAndSelection() {
        //configure roll over prompt
        myRolloverPrompt.setText("<html><div style=\"align: center\">"
                                        + "HOLD CLICK<br>PRESS KEY</html>");
        myRolloverPrompt.setVisible(false);
        add(myRolloverPrompt);
        
        
        //configure the JTextField
        myNewKeyField.setVisible(false);
        add(myNewKeyField);
        myNewKeyField.setText(KeyEvent.getKeyText(myKeyEnum.getVirtualKey()));
        myNewKeyField.setEditable(false);
    }
    
    /**
     * This method configures key and mouse listeners for this KeyPanel.
     */
    private void configureListeners() {
        addMouseListener(new EditOnMouseActionAdapter());
        
        addKeyListener(new NewKeyAssignmentListener());
        setFocusable(true);
    }
    
    /**
     * This method builds the TextPanel text for each key subordinatePanel.
     * 
     * key event to String conversion code from:
     * stackoverflow.com/questions/14117156/convert-keyevent-vk-value-to-string
     * 
     * @param theKeyEnum for which this method is buildling text
     * @return String that will display on TextPanel
     */
    private static String buildKeyPanelText(final Keys theKeyEnum) {
        //get key toString value without "pressed" text
        final String keyText = KeyEvent.getKeyText(theKeyEnum.getVirtualKey());

        
        //create key name and virtual key designation text
        final StringBuilder nameAndKey = new StringBuilder(128);
        nameAndKey.append("<html><div style=\"text-align: center;\">");
        nameAndKey.append(theKeyEnum.toString());
        nameAndKey.append("<br><div style=\"color: blue;\">");
        nameAndKey.append(keyText);
        nameAndKey.append("</div></div></html>");
        
        return nameAndKey.toString();
        
    }
    
    
    
    
    /**
     * This class allows a KeyPanel to display edit option when moused over.
     * 
     * 
     * @author Ray Kim
     * @version Autumn 2014
     *
     */
    public class EditOnMouseActionAdapter extends MouseInputAdapter {
        
        /**
         * Causes a red border to form around this JPanel.
         * 
         * Code to create borders borrowed from:
         * stackoverflow.com/questions/12838978/how-to-surround-java-swing-components
         * -with-border
         * 
         * @param theEvent which spawned this action.
         * 
         */
        @Override
        public void mouseEntered(final MouseEvent theEvent) {
            //if mouse is pressed, then moved while on this KeyPanel, the key change option
            //disappears via the calling of keyReleased()
            mouseReleased(theEvent);
            
            //change border
            setBorder(TITLED_BORDER);
            
            //set underlying JLable displaying current key designation to be invisible
            setJLabelVisible(false);
            
            //show roll over message
            myRolloverPrompt.setVisible(true);
        }
        
        /**
         * Restores default border.
         * @param theEvent which spawned this action.
         */
        @Override
        public void mouseExited(final MouseEvent theEvent) {
            //restore default border
            setBorder(DEFAULT_KEY_TEXT_BORDER);
            
            //set underlying JLable displaying current key designation to be visible
            setJLabelVisible(true);
            
            //hide roll over message
            myRolloverPrompt.setVisible(false);
            
        }
        
        /**
         * This method allows the key listener to change the key associated with this enum.
         * 
         * @param theEvent which spawned this action.
         */
        @Override
        public void mousePressed(final MouseEvent theEvent) {
//            //get reference to object that caused event
//            final TextPanel callingObject = (TextPanel) theEvent.getComponent();
//            
//            //get Keys enum related to the TextPanel that spawned this event
//            final Keys keyEnum = (Keys) retrieveSubordinateMappedObject(callingObject);
            
            //set key listener to be active
            myKeyListenerActive = true;
            
            //hide roll over message
            myRolloverPrompt.setVisible(false);
            
            //set myNewKeyField visible
            myNewKeyField.setVisible(true);
            
            //retrieve focus from window for this JPanel
            requestFocusInWindow();
            
            //fire property change to pause game
            firePropertyChange("pauseOnly", true, false);
            
        } //end of mousePressed
        
        /**
         * This method disallows this KeyPanel object from paying attention to its keyListener.
         * 
         * @param theEvent which spawned this action.
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            
            //set key listener to be inactive
            myKeyListenerActive = false;
            
            //set myNewKeyField invisible
            myNewKeyField.setVisible(false);
            
            //show roll over message
            myRolloverPrompt.setVisible(true);
            
        }

    } //end of KeyConfigMouseAdapter
    
    
    /**
     * This class sets a new VK designation for the underlying enum of this KeyPanel.
     * 
     * 
     * @author Ray Kim
     * @version Autumn 2014
     *
     */
    public class NewKeyAssignmentListener extends KeyAdapter {
        
        /**
         * This method (active only when mouse is pressed over this KeyPanel) sets a new
         * key assignment for the underlying enum of this KeyPanel.
         * 
         * @param theEvent which calls this method
         */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            //code is only active when myKeyListenerActive is true
            if (myKeyListenerActive) {
                final int newKey = theEvent.getKeyCode();
                boolean newKeyIsAvailable = true;
                //check if key is already in use for another Keys enum
                for (final Keys key : Keys.values()) {
                    if (key.getVirtualKey() == newKey) {
                        //if key is already taken, set newKeyIsAvailable to false
                        newKeyIsAvailable = false;
                    }
                }
                
                //designate new key to enum only if it is not already taken
                if (newKeyIsAvailable) {
                    //assign new key
                    myKeyEnum.setVirtualKey(newKey);
                    
                    //display new key char
                    myNewKeyField.setText(String.valueOf(theEvent.getKeyChar()));
                    
                    //change the JLabel text to reflect new key designation
                    setText(buildKeyPanelText(myKeyEnum));
                    
                    //fire property change event to update BoardPanel keycode assignments
                    firePropertyChange("update", true, false);
                }
                
            }
            
        } //end of keyTyped
        
    } //end of NewKeyAssignmentListener
    
}
