/*
 * TCSS 305: Assignment 6
 */

package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * JPanel that displays credit to owners of music content used in this game.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class AboutJPanel extends JPanel {

    //constants
    /**
     * Contains constant for string <br>.
     */
    private static final String BREAK = "<br>";
    
    
    /**
     * Constructor just calls a method that creates JPanel.
     */
    public AboutJPanel() {
        super();
        
        createJPanel();
    }
    
    /**
     * This method creates a JPanel.
     */
    private void createJPanel() {
        
        final StringBuilder text = new StringBuilder(512);
        
        text.append("<html>Credit for audio and music go to:");
        text.append(BREAK);
        text.append("Matt Mullholland - My Heart Will Go On - Recorder by Candlelight");
        text.append(BREAK);
        text.append("Rebecca Black - Friday");
        text.append(BREAK);
        text.append("Justin Bieber and Slipknot - Psychosocial Baby");
        text.append(BREAK);
        text.append("Knife Party - Begin Again");
        text.append(BREAK);
        text.append("The developer of this game does not own any of these works "
                                        + "and has reproduced them under the fair use act "
                                        + "of 1976, 17 U.S.C. 107</html>");
        
        
        final JLabel content = new JLabel(text.toString());
        
        add(content);
    }
    
}
