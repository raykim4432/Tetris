/*
 * TCSS 305: Assignment 6
 */

package view;

import file.interaction.FileInteractionHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import panels.BoardPanel;
import panels.KeyClusterPanel;
import panels.NextPiecePanel;
import panels.SpeedScorePanel;
import panels.SubordinatePanel;
import panels.TextPanel;

/**
 * This class creates a layout to host an object of the Tetris Board class.
 * 
 * Code for the window resize listener is from
 * stackoverflow.com/questions/2303305/window-resize-event
 * 
 * The underlying layout is GridBagLayout
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class TetrisFrame extends JFrame {

    //constants
    /**
     * Contains screen preferred width.
     */
    private static final int FRAME_WIDTH = 620;
    
    /**
     * Contains screen preferred height.
     */
    private static final int FRAME_HEIGHT = 760;
    
    /**
     * Contains screen minimum width.
     */
    private static final int FRAME_MIN_WIDTH = 460;
    
    /**
     * Contains screen minimum height.
     */
    private static final int FRAME_MIN_HEIGHT = 750;
    
    /**
     * Contains value for side borders.
     */
    private static final int SIDE_BORDER = 20;
    
    /**
     * Contains value for side borders.
     */
    private static final int BOTTOM_BORDER = 70;
    
    /**
     * Contains padding values for rightPanel component insets.
     */
    private static final int PANEL_PADDING = 5;
    
    /**
     * Contains the fixed constant pixel height for Border.NORTH (advertisement panel).
     */
    private static final int NORTH_PANEL_HEIGHT = 150;
    
    /**
     * The Index number given to the NORTH pane JPanel as it is added to this JFrame.
     */
    private static final int NORTH_PANEL_INDEX = 0;
    
    /**
     * The Index number given to the EAST pane JPanel as it is added to this JFrame.
     */
    private static final int RIGHT_PANEL_INDEX = 1;
    
    /**
     * The Index number given to the CENTER pane JPanel as it is added to this JFrame.
     */
    private static final int CENTER_PANEL_INDEX = 2;
    
    /**
     * The Index number given to the score display panel as it is added to 
     * myRightComponentList.
     */
    private static final int SCORE_PANEL_INDEX = 0;
    
    /**
     * The Index number given to the speed and score system display panel as it is added to 
     * myRightComponentList.
     */
    private static final int SPEED_SCORE_PANEL_INDEX = 1;
    
    /**
     * The Index number given to next piece display panel as it is added to 
     * myRightComponentList.
     */
    private static final int NEXT_PIECE_DISPLAY_PANEL_INDEX = 3;
    
    /**
     * Contains height of rightPanel relative to the window as a percentage.
     */
    private static final double R_PANEL_HEIGHT_PERCENT = .70;
    
    /**
     * Contains width of rightPanel relative to the window as a percentage.
     */
    private static final double R_PANEL_WIDTH_PERCENT = .38;
    
    /**
     * Contains the percent of space of rightJPanel that component JPanels of 
     * rightJPanel will occupy.
     */
    private static final double R_PANEL_COMPONENT_WIDTH_PERCENT = .90;
    
    /**
     * Contains the percentage of rigthJPanel's height occupied by the score panel.
     */
    private static final double SCORE_PANEL_HEIGHT_PERCENT = .1;
    
    /**
     * Contains the percentage of rigthJPanel's height occupied by the score system
     * summary display panel.
     */
    private static final double SCORE_SYSTEM_PANEL_HEIGHT_PERCENT = .25;
    
    /**
     * Contains the percentage of rigthJPanel's height occupied by Key display panel.
     */
    private static final double KEY_PANEL_HEIGHT_PERCENT = .35;
    
    /**
     * Contains the percentage size of this JPanel's text font relative to the JPanel
     * that contains it.
     */
    private static final double FONT_PERCENT_OF_JPANEL = .075;
    
    /**
     * Contains the label shown in front of score.
     */
    private static final String SCORE_LABEL = "Seconds of Silence: ";
    
    //variables
    /**
     * Contains the board width in blocks set in the constructor.
     */
    private final int myBoardWidthInBlocks;
    
    /**
     * Contains the board height in blocks set in the constructor.
     */
    private final int myBoardHeightInBlocks;
    
    /**
     * Contains the number of times the advert image has been cycled.
     */
    private int myAdvertImageIndex;
    
    /**
     * JLabel that contains the current advert image.
     */
    private JLabel myAdvertLabel;
    
    /**
     * Contains the tetris board object for this frame.
     */
    private JPanel myBoardContainer;
    
    /**
     * Contains the right panel of this frame.
     */
    private JPanel myRightContainer;
    
    /**
     * Contains the panel that displays the tetris board and pieces.
     */
    private BoardPanel myBoardPanel;
    
    /**
     * The KeyClusterPanel that will contain the key panels.
     */
    private KeyClusterPanel myKeyConfigurationPanel;
    
    
    /**
     * ImageIcon that contains the current advertisement picture.
     */
    private ImageIcon myCurrentAdvertImage;
    
    /**
     * List that contains references to the components placed in rightPanel.
     */
    private List<SubordinatePanel> myRightComponentsList;
    
    /**
     * List that contains changing advertisement images in advert panel.
     */
    private List<BufferedImage> myAdvertImageList;
    
    /**
     * List that contains slide show images.
     */
//    private List<Pixel[][]> mySlideShowImageList;
    
    
    
    //constructor and methods
    /**
     * Constructor which builds JFrame.
     * 
     * @param theBoardWidthInBlocks int board width in blocks
     * @param theBoardHeightInBlocks int board height in blocks
     */
    public TetrisFrame(final int theBoardWidthInBlocks, final int theBoardHeightInBlocks) {
        super("Tetris Lite");
        
        myBoardWidthInBlocks = theBoardWidthInBlocks;
        myBoardHeightInBlocks = theBoardHeightInBlocks;
        
        //initialize class variables
        initializeClassVariables();

        //configure visibility, resizing behavior, etc.
        configureFrame();
        
        //create layout
        createLayout();
        
        //load advert and slide show images into Lists
        loadImages();
        
        

    }
    
    
    /**
     * This method initializes class variables, collections, arrays, sets.
     */
    private void initializeClassVariables() {
        myBoardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        myRightContainer = new JPanel(new GridBagLayout());
        myRightComponentsList = new ArrayList<>();
        myAdvertImageList = new LinkedList<>();
//        mySlideShowImageList = new LinkedList<>();
        myBoardPanel = new BoardPanel(FRAME_WIDTH, FRAME_HEIGHT, 
                                      myBoardWidthInBlocks, myBoardHeightInBlocks);
    }
    
    /**
     * This method configures aspects of this JFrame.
     */
    private void configureFrame() {
        //add resize listener
        addComponentListener(new ComponentResizeListener());
        
        //visibility
        setVisible(true);
        setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
        pack();
    }
    
    
    
    
    
    /**
     * This method creates the layout of the game.
     * 
     * Code for Box layout borrowed from Alan Fowler's CompositeLayout.java.
     */
    private void createLayout() {
        //initialize BorderLayout
        setLayout(new BorderLayout());
        
        //set up advertisement JPanel and initialize first advertisement picture
        myAdvertLabel = new JLabel();
        final JPanel advertJPanel = new JPanel();
        advertJPanel.add(myAdvertLabel);
        add(advertJPanel, BorderLayout.NORTH, NORTH_PANEL_INDEX);
        advertJPanel.setPreferredSize(new Dimension(FRAME_WIDTH, NORTH_PANEL_HEIGHT));
        
        /* set up right container panel */
        configureRightContainer();
        
        
        /* set up board container panel */
        configureBoardContainer();
        
        //attach property change listener linking KeyPanel object fired events to the 
        //listener in BoardPanel
        for (final Component keyPanel : myKeyConfigurationPanel.getComponents()) {
            keyPanel.addPropertyChangeListener(myBoardPanel);
        }
        
        
        pack();
    }
    
    /**
     * This method builds the right container panel that holds other panels that display
     * score, rules, key configuration and next tetris piece.
     * 
     * Code for borders borrowed from
     * java2s.com/Code/JavaAPI/javax.swing/JPanelsetBorderBorderborder.htm
     */
    private void configureRightContainer() {
        // panel
        myRightContainer.setBackground(Color.DARK_GRAY);
        
        //add right panel to JFrame
        add(myRightContainer, BorderLayout.EAST, RIGHT_PANEL_INDEX);
        
        
        //generate subordinate panels to rightPanel which include scorePanel, 
        //scoreSystemDisplayPanel, keyConfigurationPanel and nextPiecePanel
        generateSubordinateJPanels();
        
        //add subordinate JPanels to rightPanel
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(PANEL_PADDING, 0, PANEL_PADDING, 0);
        
        for (int i = 0; i < myRightComponentsList.size(); i++) {
            ((JPanel) myRightComponentsList.get(i)).setBackground(Color.WHITE);
            
            //adjust position of JPanel
            gridBagConstraints.gridy = i;
            
            //add JPanel
            myRightContainer.add((JPanel) myRightComponentsList.get(i), gridBagConstraints);
        }
        
       
        
    }
    
    /**
     * This method creates the rule and configuration display panels on the right side of the
     * tetris game.
     * 
     * Buffer reader code borrowed from Oracle website.
     * 
     */
    private void generateSubordinateJPanels() {
        
        
        /* create score panel and add into rightComponentList */
        final TextPanel scorePanel = new TextPanel(FRAME_WIDTH,
                                                   FRAME_HEIGHT,
                                                   R_PANEL_COMPONENT_WIDTH_PERCENT, 
                                                   SCORE_PANEL_HEIGHT_PERCENT,  
                                                   FONT_PERCENT_OF_JPANEL,
                                                   SCORE_LABEL,
                                                   "0");
        

        myRightComponentsList.add(SCORE_PANEL_INDEX, scorePanel);
        
         
        /* create score system display panel and add to rightComponentList */
        //retrieve string message from point_scheme.txt
        final String scoreSystemMessage = FileInteractionHelper.
                                        txtFileToHTML("point_scheme.txt");
        
        //create score system SpeedScorePanel
        final SpeedScorePanel scoreSystemPanel = new 
                                        SpeedScorePanel(FRAME_WIDTH,
                                                        FRAME_HEIGHT,
                                                        R_PANEL_COMPONENT_WIDTH_PERCENT, 
                                                        SCORE_SYSTEM_PANEL_HEIGHT_PERCENT, 
                                                        FONT_PERCENT_OF_JPANEL,
                                                        "Scoring Scheme",
                                                        scoreSystemMessage);
        
        
        myRightComponentsList.add(SPEED_SCORE_PANEL_INDEX, scoreSystemPanel);
        
        
        /* create key configuration display */
        //this method creates a keyClusterPanel which contains KeyPanels that allow a
        //player to change key configurations
        myKeyConfigurationPanel =  new KeyClusterPanel(FRAME_WIDTH,
                                                       FRAME_HEIGHT,
                                                       R_PANEL_COMPONENT_WIDTH_PERCENT, 
                                                       KEY_PANEL_HEIGHT_PERCENT, 
                                                       FONT_PERCENT_OF_JPANEL,
                                                       "KEY PANEL");
        myRightComponentsList.add(myKeyConfigurationPanel);
        
        
        /* create next piece panel */
        final NextPiecePanel nextPieceDisplayPanel = 
                                        new NextPiecePanel(FRAME_WIDTH,
                                                           FRAME_HEIGHT,
                                                           R_PANEL_COMPONENT_WIDTH_PERCENT, 
                                                           SCORE_SYSTEM_PANEL_HEIGHT_PERCENT,
                                                           "Next Piece");
        
        myRightComponentsList.add(NEXT_PIECE_DISPLAY_PANEL_INDEX, nextPieceDisplayPanel);
    }
    
    
    /**
     * This method builds the Tetris game board container panel and the actual panel that
     * displays the tetris game.
     */
    private void configureBoardContainer() {
        //configure container
        myBoardContainer.setBackground(Color.GRAY);
        
        
        //add myBoardPanel to the left side container
        myBoardContainer.add(myBoardPanel);
        
        //add right panel to JFrame
        add(myBoardContainer, BorderLayout.CENTER, CENTER_PANEL_INDEX);
    }
    
    //image methods
    /**
     * This method loads images into lists for display on the GUI.
     * Pixel and PixelImage classes are borrowed from TCSS 305
     * Autumn 2014 SnapShop assignment. Credit goes to Marty Stepp
     * Daniel M. Zimmerman and Alan Fowler.
     * 
     * Code for loading images into a buffered image are from
     * stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel
     * 
     */
    private void loadImages() {
        //save all images in advertisements directory to List
        myAdvertImageList = FileInteractionHelper.generateBufferedImageList("advertisements");
        
        //scaled image
//        ImageIcon scaledImage = theimage.getScaledInstance(newWidth, 
        
        //display first advert image in myAdvertImageList if myAdvertImageList is not empty
        myAdvertImageIndex = 0;
        cycleAdvertImage();
    }
    
    /**
     * This method cycles to the next advert image when called. myAdvertImageIndex is 
     * modded by the size of myAdvertImageList so the number used to get the next image
     * is within the bounds of myAdvertImageList's indexes.
     */
    public final void cycleAdvertImage() {
        
        if (!myAdvertImageList.isEmpty()) {
            myCurrentAdvertImage = new ImageIcon(myAdvertImageList.
                                                 get(myAdvertImageIndex 
                                                     % myAdvertImageList.size()));
            
            //increment myAdvertImageIndex by one
            myAdvertImageIndex++;
            
            //refresh new image
            myAdvertLabel.setIcon(myCurrentAdvertImage);
        }
        
    }
    
    //getters
    /**
     * This method returns the index of the NORTH Pane panel.
     * (the index that it was designated as it was added to this JFrame).
     * @return int index number
     */
    public int getNorthPanelIndex() {
        return NORTH_PANEL_INDEX;
    }
    
    /**
     * This method returns the index of the EAST Pane panel.
     * (the index that it was designated as it was added to this JFrame).
     * @return int index number
     */
    public int getRightPanelIndex() {
        return RIGHT_PANEL_INDEX;
    }
    
    /**
     * This method returns the index of the CENTER Pane JPanel
     * (the index that it was designated as it was added to this JFrame).
     * @return int index number
     */
    public int getCenterPanelIndex() {
        return CENTER_PANEL_INDEX;
    }
    
    /**
     * This method returns the index of the score panel within myRightComponentsList
     * (the index that it was designated as it was added to myRightComponentsList).
     * @return int index number
     */
    public int getScorePanelIndex() {
        return SCORE_PANEL_INDEX;
    }
    
    /**
     * This method returns the index of the speed score panel within myRightComponentsList
     * (the index that it was designated as it was added to myRightComponentsList).
     * @return int index number
     */
    public int getSpeedScorePanelIndex() {
        return SPEED_SCORE_PANEL_INDEX;
    }
    
    /**
     * This method returns the index of the next piece panel within myRightComponentsList
     * (the index that it was designated as it was added to myRightComponentsList).
     * @return int index number
     */
    public int getNextPiecePanelIndex() {
        return NEXT_PIECE_DISPLAY_PANEL_INDEX;
    }
    
    /**
     * This method creates a reference for an inner class.
     * 
     * Code borrowed from:
     * stackoverflow.com/questions/1816458/getting-hold-of-the-outer-class-object-from
     * -the-inner-class-object
     * 
     * @return this TetrisFrame object
     */
    private TetrisFrame getObjectOfThisClass() {
        return TetrisFrame.this;
    }
    
    
    
    //inner classes
    /**
     * Inner class that creates a listener response for when this JFrame is resized.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     *
     */
    private final class ComponentResizeListener extends ComponentAdapter {

        //variables
        /**
         * Stores a reference to the object of the outer class.
         */
        private final TetrisFrame myTetrisFrame;
        
        //constructor and methods
        
        /**
         * Constructor that stores a reference to the object of the outer class.
         */
        public ComponentResizeListener() {
            super();
            
            myTetrisFrame = getObjectOfThisClass();
        }
        

    
        /**
         * This method resizes components of this JFrame as it is resized.
         * 
         * Code for getting window size from:
         * stackoverflow.com/questions/8333187/how-to-check-current-window-size-in-java-swing
         * 
         * @param theArgs component event which is not used in this method.
         * 
         */
        @Override
        public void componentResized(final ComponentEvent theArgs) {
            
            //retrieve screen size dimensions
            final Dimension windowSize = myTetrisFrame.getBounds().getSize();
            
            final double frameWidth = windowSize.getWidth();
            final double frameHeight = windowSize.getHeight();
            
            /* adjust rightPanel sizes */
            final int newRightWidth = (int) (R_PANEL_WIDTH_PERCENT * frameWidth);
            final int newRightHeight = (int) (R_PANEL_HEIGHT_PERCENT * frameHeight);
            myRightContainer.setPreferredSize(new Dimension(newRightWidth, newRightHeight));
            
            //adjust size of components within rightPanel
            for (final SubordinatePanel subPanel : myRightComponentsList) {
                //adjust subordinate panels based on current rightPanel size
                subPanel.adjustPanelSize(newRightWidth, newRightHeight);
            }
            
            /* adjust boardPanel sizes */
            final int newBoardWidth = (int) (frameWidth - newRightWidth - (SIDE_BORDER * 2));
            final int newBoardHeight = (int) (frameHeight - NORTH_PANEL_HEIGHT 
                                            - BOTTOM_BORDER);
            
            
            //adjust size of myBoardPanel, which is nested within myBoardContainer
            myBoardPanel.adjustPanelSize(newBoardWidth, newBoardHeight);
            
        } //end of componentResized
    
    } //end of ComponentResizeListener
}
