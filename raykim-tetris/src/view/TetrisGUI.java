/*
 * TCSS 305: Assignment 6
 */

package view;

import file.interaction.FileInteractionHelper;
import file.interaction.TetrisGameData;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import media.AdvertVideo;
import media.BGAudio;
import model.AbstractPiece;
import model.Block;
import model.Board;
import model.Piece;
import model.ReverseTetrisBoard;
import panels.BoardPanel;
import panels.NextPiecePanel;
import panels.SpeedScorePanel;
import panels.TextPanel;



/**
 * GUI class for Tetris.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 */
public class TetrisGUI implements Observer {

    //constants
    /**
     * Max clearable blocks per clear.
     */
    private static final int MAX_CLEARABLE_BLOCKS_PER_CLEAR = 4;
    
    /**
     * Contains the number of blocks the width of this board will contain.
     */
    private static final int BOARD_BLOCK_WIDTH = 10;
    
    /**
     * Contains the number of blocks the height of this board will contain.
     */
    private static final int BOARD_BLOCK_HEIGHT = 20;
    
    /**
     * Base speed of game in milliseconds.
     */
    private static final int BASE_GAME_SPEED = 1000;
    
    /**
     * Milliseconds in one second.
     */
    private static final int MILLISECONDS_IN_SECOND = 1000;
    
    /**
     * Contains the millisecond delay between each advertisement change.
     */
    private static final int MILLISEC_DELAY_BETWEEN_ADVERTS = 7000;
    
    
    /**
     * Index for music played in reverse tetris mode.
     */
    private static final int REVERSE_TETRIS_MODE_INDEX = 3;
    
    /**
     * Contains the number the game speed is divided by in order to get the value the
     * game music speed is increased by (when speed is changed in the JSlider).
     */
    private static final double HUNDRED_PERCENT = 100;
    
    /**
     * Game speed multiplier that is incremented each level.
     */
    private static final double GAME_SPEED_MULTIPLIER = .15;
    
    //variables
    /**
     * Contains the args passed in from the main method.
     */
    private final String[] myArgs;
    
    /**
     * Boolean, true means game is not paused, false means game is paused.
     */
    private boolean myGameIsRunning;
    
    /**
     * Boolean that tracks if replay mode is on.
     */
    private boolean myReplayModeOn;
    
    /**
     * Boolean, true means reverse tetris mode is activated.
     */
    private boolean myReverseTetrisModeOn;
    
    /**
     * Boolean that tracks if game is over.
     */
    private boolean myGameIsOver;
    
    /**
     * Boolean, set to true when a new game is started, forcing user to watch video
     * on unpause of new game.
     */
    private boolean myPlayVideoOnNewGameUnpause;
    
    /**
     * Variable tracking current level.
     */
    private int myCurrentLevel;
    
    /**
     * Variable to hold the number of blocks in the board width.
     */
    private int myBoardRowsInBlocks;
    
    /**
     * Variable to hold the number of blocks in the board height.
     */
    private int myBoardColumnsInBlocks;
    
    /**
     * Number of cycles that have elapsed since start of timer.
     * This value is reset if the user starts a new game.
     */
    private int myTrackedCycles;
    
    /**
     * The number of cycles that have elapsed since the start of the replay.
     */
    private int myReplayCycles;
    
    /**
     * Contains the delay (in milliseconds) for the Timer myGameTimer. This effectively 
     * is the interval at which events in the game are triggered.
     */
    private int myGameSpeed;
    

    /**
     * Contains the amount of milliseconds that have elapsed since the last advertisement
     * change.
     */
    private int mySecondsSinceLastAdvertChange;
    
    /**
     * Contains mute time remaining if points are scored.
     */
    private int myMillisecondsOfMute;
    
    /**
     * The timer which cues the ActionOnBoard ActionListener to move events in the
     * Board class.
     */
    private Timer myGameTimer;
    
    /**
     * The JFrame for this game.
     */
    private final TetrisFrame myTetrisFrame;
    
    /**
     * Contains the JMenubar for the Tetris JFrame.
     */
    private TetrisMenuBar myTetrisMenuBar;
    
    /**
     * The GUI display of this tetris game.
     */
    private BoardPanel myTetrisBoard;
    
    /**
     * Panel which displays current score.
     */
    private TextPanel myScorePanel;
    
    /**
     * Panel which displays the score scheme and also the speed selector for this game.
     */
    private SpeedScorePanel mySpeedScorePanel;
    
    /**
     * Panel which displays the next piece.
     */
    private NextPiecePanel myNextPieceDisplayPanel;
    
    /**
     * This variable is a reference to the logical class that drives this game.
     */
    private Board myBoard;
    
    /**
     * Object that holds replay data and later saves current game board pieces to save game
     * data.
     */
    private final TetrisGameData myGameData;
    
    /**
     * Object that generates background music for this game. 
     */
    private BGAudio myBGAudio;
    
    
    /**
     * The most recent current piece pulled from myBoard.
     */
    private Piece myCurrentPiece;
    
    /**
     * The next piece pulled from myBoard.
     */
    private Piece myNextPiece;

    /**
     * The current frozen blocks (in the format originally derived from the Board class).
     */
    private List<List<Block>> myFrozenBlocksList;
    
    /**
     * Contains mapping of number of lines cleared to seconds of silence.
     */
    private int[] myPointSchemeArray;
    
    
    //constructor and methods
    /**
     * Instantiates a TetrisGUI object.
     * @param theArgs the main method's args
     */
    public TetrisGUI(final String[] theArgs) {
        myArgs = theArgs.clone();
        
        //initialize board
        myBoard = new Board(BOARD_BLOCK_WIDTH, BOARD_BLOCK_HEIGHT);
        
        //create tetris frame, which includes the visual game board
        myTetrisFrame = new TetrisFrame(BOARD_BLOCK_WIDTH, BOARD_BLOCK_HEIGHT);
        myTetrisFrame.setLocationRelativeTo(null);
        myTetrisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //initializeVariables
        initializeVariables();
        
        //initialize field to hold game replay data(and later used to save game state)
        myGameData = new TetrisGameData();
        
      
    }
    
    /**
     * This method initializes variables.
     */
    private void initializeVariables() {
        //create tetris menu bar
        myTetrisMenuBar = new TetrisMenuBar(BOARD_BLOCK_WIDTH, BOARD_BLOCK_HEIGHT);
        myTetrisFrame.setJMenuBar(myTetrisMenuBar);
        
        //initialize the game event interval speed
//        myEventIntervalSpeed = INITIAL_EVENT_INTERVAL_SPEED;
        
        //start the timer for the game
        myGameTimer = new Timer(BASE_GAME_SPEED, new ActionOnBoard());
        
        //create point scheme
        generatePointSchemeArray();
        
        //initialize background music object
        myBGAudio = new BGAudio();

        
        
    }
    
    /**
     * This method generates an array that contains mappings of points to seconds of silence.
     */
    private void generatePointSchemeArray() {
        final List<int[]> pointSchemeList = FileInteractionHelper.
                                        generatePointList("point_scheme.txt");
        myPointSchemeArray = new int[pointSchemeList.size()];
                                        
        for (int i = 0; i < pointSchemeList.size(); i++) {
            myPointSchemeArray[i] = pointSchemeList.get(i)[1];
        }
         
    }
    
    /**
     * This method creates visual elements of the TetrisGUI and starts the game.
     */
    public void start() {
        //retrieve references to GUI display panels
        retrieveGUIDisplayReferences();
        
        //set observers
        setObserversAndListeners();
        
        //start the game
        myCurrentLevel = 0;
        myBoardRowsInBlocks = BOARD_BLOCK_WIDTH;
        myBoardColumnsInBlocks = BOARD_BLOCK_HEIGHT;
        startNewGame(myCurrentLevel);
        
        //set show advert video to true when new game is started
        myPlayVideoOnNewGameUnpause = true;
        
        //set focus on tetris board
        myTetrisBoard.requestFocus(true);
        
        //start game timer
        myGameTimer.start();
        
        
    } //end of start
    
    /**
     * This method navigates the main JFrame hierarchy for references to GUI display panels.
     */
    private void retrieveGUIDisplayReferences() {
        //set key listener on JPanel in myTetrisFrame CENTER pane
        final Container jRootPane = (Container) myTetrisFrame.getComponent(0);
        final Container jLayeredPane = (Container) jRootPane.getComponent(1);
        final Container jFrame = (Container) jLayeredPane.getComponent(0);
        final Container rightPanel = (Container) jFrame.
                                        getComponent(myTetrisFrame.getRightPanelIndex());
        
        //retrieve reference for GUI tetris board
        final Container tetrisBoardContainer = (Container) jFrame.
                                        getComponent(myTetrisFrame.getCenterPanelIndex());
        myTetrisBoard = (BoardPanel) tetrisBoardContainer.getComponent(0);
        
        //retrieve reference to score panel
        myScorePanel = (TextPanel) rightPanel.
                                        getComponent(myTetrisFrame.getScorePanelIndex());
        
        //retrieve reference for speed score panel
        
        
        mySpeedScorePanel = (SpeedScorePanel) rightPanel.
                                        getComponent(myTetrisFrame.getSpeedScorePanelIndex());
        
        //retrieve reference for next piece display panel
        myNextPieceDisplayPanel = (NextPiecePanel) rightPanel.
                                        getComponent(myTetrisFrame.getNextPiecePanelIndex());
        
        
        
    }
    
    /**
     * This method sets listeners for this class.
     * It also sets observers between the GUI and the back end and vice versa.
     */
    private void setObserversAndListeners() {
        /* listeners */
        // View to Model
        myTetrisBoard.addPropertyChangeListener(new TetrisBoardPropertyChangeListener());
        myTetrisMenuBar.addPropertyChangeListener(new MenuBarPropertyChangeListener());
        myTetrisMenuBar.addPropertyChangeListener(new BoardDimenGameSpeedChangeListener());
        mySpeedScorePanel.addPropertyChangeListener(new BoardDimenGameSpeedChangeListener());
        
        //propertyChangeListeners to pause game
        myTetrisBoard.addPropertyChangeListener(new PausePropertyChangeListener()); 
        myTetrisMenuBar.addPropertyChangeListener(new PausePropertyChangeListener()); 
        mySpeedScorePanel.addPropertyChangeListener(new PausePropertyChangeListener()); 
        
        //View to View
        myTetrisMenuBar.addPropertyChangeListener(myTetrisBoard); //to toggle grid
        
        //Audio to this class
        myBGAudio.addPropertyChangeListener(new PropertyChangeListener() {
            /**
             * Listens for change level property change from BGAudio and then
             * adjusts the level.
             * @param theEvent that called this method
             */
            @Override
            public void propertyChange(final PropertyChangeEvent theEvent) { 
                //if current level is reverse tetris mode, do not increment level
                if (myCurrentLevel < REVERSE_TETRIS_MODE_INDEX) {
                    myGameIsRunning = false;
                    myCurrentLevel++;
                    startNewGame(myCurrentLevel);
                    pauseToggle();
                } else {
                    setGameOver();
                }
            }
            
        });
        
        /* Observers */
        // Model to View Observer relation 
        myBoard.addObserver(this);
        
        // View to Model Observer relation
   
        
    }
    
    /**
     * This method starts a new game.
     * 
     * @param theLevel int that determines speed and bg music of level
     */
    private void startNewGame(final int theLevel) {
        
        myGameIsOver = false;
        
        //reset timer clock
        myTrackedCycles = 0;
        
        //reset visuals
        myTetrisBoard.gameOverTextVisible(false);
        
        //set music for level
        myBGAudio.loadTrack(theLevel);
        
        //clear collections that hold replay data
        if (myReplayCycles >= 0) {
            myGameData.clearData();
        }
        
        //create new board
        myBoard.newGame(myBoardRowsInBlocks, myBoardColumnsInBlocks, null);
        
        //resize tetris GUI board
        myTetrisBoard.setWidthHeightInBlocks(myBoardRowsInBlocks, 
                                             myBoardColumnsInBlocks);
        myTetrisBoard.repaint();
        
        //adjust speed on timer
        //game speed is based on level number times GAME_SPEED_MULTIPLIER
        myGameSpeed = (int) (BASE_GAME_SPEED * (1 - GAME_SPEED_MULTIPLIER * myCurrentLevel));
        myGameTimer.setDelay(myGameSpeed);
        
        //reset score to zero
        myScorePanel.setText(String.valueOf(myGameData.getScore()));
        
        //display pause for new game
        myTetrisBoard.setJLabelVisible(true);
    }
    
    /**
     * This method, when called, toggles pause.
     */
    private void pauseToggle() {
        //play video if new game has been started and new game is unpaused
        if (myPlayVideoOnNewGameUnpause) {
            //video application must run in new thread separate from that of the event
            //dispatch thread that runs this GUI. Otherwise, closing the video application
            //keeps the control in the JavaFX App Thread, which doesn't close because 
            //Platform is set to setImplicitExit(false). However, JavaFX App Thread must
            //stay open for some unexplained reason so that the JFXPanel that plays music
            //does not lose references to its variables and throws an IllegalStateException
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Application.launch(AdvertVideo.class, myArgs);
                }
            }).start();
            myPlayVideoOnNewGameUnpause = false;
            
        } else if (myGameIsRunning) { //pause game
            myTetrisBoard.setJLabelVisible(true);
            myGameIsRunning = false;
            
            //pause audio
            myBGAudio.pauseTrack();

            //if replay mode, hitting will turn it off
            if (myReplayModeOn) {
                myTetrisBoard.replayTextVisible(false);
                myReplayModeOn = false;
            }
            
        } else { //unpause game
            if (!myGameIsOver) {
                myTetrisBoard.setJLabelVisible(false);
                myGameIsRunning = true;
                
                //resume audio if replay mode is off
                if (!myReplayModeOn) {
                    myBGAudio.resumeTrack();
                }
            }
        }
    }
    
    /**
     * This method ends the game.
     */
    private void setGameOver() {
        myGameIsOver = true;
        myBGAudio.stopTrack();
        //display game over JLabel in BoardPanel object
        myTetrisBoard.gameOverTextVisible(true);
        //disable left, right, drop, etc.
        myGameIsRunning = false;
        //hide pause message
        myTetrisBoard.setJLabelVisible(false);
        
    }
    
    /**
     * This method iterates one step through replay mode on each call.
     */
    private void iterateReplayMode() {
        //continue replay mode only if myReplayCycles is less than cycles that have
        //elapsed during the game (recorded in myTrackedCycles)
        if (myReplayCycles < myTrackedCycles) {
          //draw active and frozen blocks for replay
            myTetrisBoard.updateGraphicalBlocks(myGameData.
                                                getCurrentPieceReplayCoords(myReplayCycles),
                                                myGameData.
                                                getFrozenBlockReplayCoords(myReplayCycles));
            //draw next piece for replay
            myNextPieceDisplayPanel.
            refreshNextPiecePreview(myGameData.getNextPieceReplayCoords(myReplayCycles));
            myReplayCycles++;
        } else {
            //pause game once replay is over
            pauseToggle();
            myReplayModeOn = false;
            myTetrisBoard.replayTextVisible(false);
        }
    }
    
    /**
     * This method saves game state.
     */
    private void saveGame() {
        myGameData.saveState(myCurrentLevel, myBoardRowsInBlocks, 
                             myBoardColumnsInBlocks, myBGAudio.getPosition(), 
                             myCurrentPiece, myNextPiece, myFrozenBlocksList);
        FileInteractionHelper.saveGameData(myGameData);
    }
    
    /**
     * This method loads a saved game.
     */
    private void loadGame() {
        myGameIsOver = false;
        myTetrisBoard.gameOverTextVisible(false);
        
        myBGAudio.stopTrack();
        
        //retrieve saved game data
        final TetrisGameData gameData = FileInteractionHelper.loadGameData();
        myCurrentLevel = gameData.getLevel();
        
        //if game is in reverse tetris mode, toggle back to regular(and vice versa)
        if (myReverseTetrisModeOn && myCurrentLevel != REVERSE_TETRIS_MODE_INDEX) {
            reverseTetrisModeOn(false);
            myTetrisMenuBar.setReverseTetrisCheckedInMenuBar(true);
        } else if (!myReverseTetrisModeOn && myCurrentLevel 
                                        == REVERSE_TETRIS_MODE_INDEX) {
            reverseTetrisModeOn(true);
            myTetrisMenuBar.setReverseTetrisCheckedInMenuBar(true);
        }
          
        myBoard.loadGame(gameData.getCurrentPiece(), gameData.getNextPiece(),
                         gameData.getFrozenBlocks(), 
                         gameData.getBoardWidthInBlocks(), 
                         gameData.getBoardHeightInBlocks());
        
        //pick up track from where it left off
        myBGAudio.setPosition(gameData.getLevel(), gameData.getPositionInMusic());
        
        //adjust tetris board width/height in blocks
        myTetrisBoard.setWidthHeightInBlocks(gameData.getBoardWidthInBlocks(), 
                                             gameData.getBoardHeightInBlocks());
        
        myGameData.setScore(gameData.getScore());
        myScorePanel.setText(String.valueOf(myGameData.getScore()));
        
        pauseToggle();
    }
    
    /**
     * Turns on remote tetris mode if it is off and vice versa.
     */
    private void reverseTetrisModeToggle() {
        if (myReverseTetrisModeOn) {
            reverseTetrisModeOn(false);
        } else {
            reverseTetrisModeOn(true);
        }
        startNewGame(myCurrentLevel);
    }
    
    /**
     * This method switches the GUI between regular and reverse tetris mode.
     * 
     * @param theBoolean true turns reverse tetris mode on, false turns it off
     */
    private void reverseTetrisModeOn(final boolean theBoolean) {
        if (theBoolean) {
            //turning on reverse tetris mode
            myBoard = new ReverseTetrisBoard(myBoardRowsInBlocks, myBoardColumnsInBlocks);
            myReverseTetrisModeOn = true;
            myTetrisBoard.reverseTetrisModeOn(true);
            myCurrentLevel = REVERSE_TETRIS_MODE_INDEX;
            
        } else {
            //turning off reverse tetris mode
            myBoard = new Board(myBoardRowsInBlocks, myBoardColumnsInBlocks);
            myReverseTetrisModeOn = false;
            myTetrisBoard.reverseTetrisModeOn(false);
            myCurrentLevel = 0;
            
        }
        //reattach observer to new board (which has a different memory location than
        //the last board
        myBoard.addObserver(this);

    }
    
    /**
     * This method replays game.
     */
    private void viewReplayMode() {
        myReplayModeOn = true;
        myGameIsRunning = false;
        myReplayCycles = 0;
        myTetrisBoard.replayTextVisible(true);
        pauseToggle();
    }
    
    //Observer
    /**
     * 
     * This method observes logical changes in the Board class and uses BoardPanel's
     * updateGraphicalBlocks() methods to draw the logical 
     * blocks in TetrisGUI's BoardPanel object. 
     * 
     * This method also gets the next piece.
     * 
     * @param theObservable that has triggered this event
     * @param theObject passed to this method with the observer
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {

        /* current piece */
        //get location of current piece's blocks
        myCurrentPiece = ((Board) theObservable).getCurrentPiece();
        final int[][] currentBlockCoordinates = ((AbstractPiece) myCurrentPiece).
                                        getBoardCoordinates();
        
        /* frozen blocks */
        //get location of frozen blocks and translate List<List<Block>> into an List<int[]>
        myFrozenBlocksList = ((Board) theObservable).getFrozenBlocks();
        final int frozenBlockWidth = ((Board) theObservable).getWidth();
        final List<int[]> returnFrozenBlockList = new LinkedList<>();
        // for each row in frozenBlocksList
        for (int r = 0; r < myFrozenBlocksList.size(); r++) {
            final List<Block> row = myFrozenBlocksList.get(r);
            // for each column in frozenBlockList
            for (int c = 0; c < frozenBlockWidth; c++) {
                //if Block enum is not Block.empty, store coordinates in returnFrozenBlockList
                if (row.get(c) != Block.EMPTY) {
                    final int[] frozenCoordinates = {c, r};
                    returnFrozenBlockList.add(frozenCoordinates);
                }
            }
        }
        
        //send current piece and frozen block coordinates to myBoardPanel
        myTetrisBoard.updateGraphicalBlocks(currentBlockCoordinates, 
                                           returnFrozenBlockList);
        
        /* next piece */
        myNextPiece = ((Board) theObservable).getNextPiece();
        final int[][] nextBlockCoordinates = ((AbstractPiece) myNextPiece).getRotation();
        
        
        //send next block coordinates to 
        myNextPieceDisplayPanel.refreshNextPiecePreview(nextBlockCoordinates);
        
        //save currentBlockCoordinates, returnFrozenBlockList and nextBlockCoordinates for
        //for instant replay
        myGameData.addToCurrentPieceReplay(currentBlockCoordinates);
        myGameData.addToFrozenBlockReplay(returnFrozenBlockList);
        myGameData.addToNextPieceReplay(nextBlockCoordinates);
        
        //get the number of lines reduced from last update
        final int newFrozenBlockHeight = myFrozenBlocksList.size();
        if (myGameData.getLastFrozenBlockHeight() > newFrozenBlockHeight) {
            final int heightDifference = myGameData.getLastFrozenBlockHeight()
                                            - newFrozenBlockHeight;
            if (!(heightDifference > MAX_CLEARABLE_BLOCKS_PER_CLEAR)) {
                myGameData.addToScore(myPointSchemeArray[heightDifference - 1]);
                //each line cleared executes an update. Thus, 2 lines is actually two
                //different update events so you get 6 points instead of 7
                myScorePanel.setText(String.valueOf(myGameData.getScore()));
                
                myMillisecondsOfMute += myPointSchemeArray[heightDifference - 1] 
                                                * MILLISECONDS_IN_SECOND;
            }
            
        }
        //set new row count for myLastFrozenBlockHeight
        myGameData.setLastFrozenBlockHeight(newFrozenBlockHeight);
        
        //mute music if lines have been cleared
        if (myMillisecondsOfMute > 0) {
            if (!myBGAudio.trackIsMuted()) {
                myBGAudio.muteTrack(true); 
            }
            
        } else {
            myMillisecondsOfMute = 0;
            if (myBGAudio.trackIsMuted()) {
                myBGAudio.muteTrack(false); 
            }
        }
        
        
        

    }
    
    //inner classes
    //timer listeners
    /**
     * Inner class that listens for actions from the timer.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     */
    private class ActionOnBoard implements ActionListener {

        /**
         * This action listener's actionPerformed causes events in the Board class.
         * 
         * @param theEvent the timer which spawned this event
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {

            //select mode (replay/game over/game active)
            if (myReplayModeOn) { //replay mode
                iterateReplayMode();
            } else if (myBoard.isGameOver()) { //if game is over
                setGameOver();
            } else if (myGameIsRunning) {
                //progress the game forward
                myBoard.step();
                //increment seconds elapsed since first start of timer
                myTrackedCycles++;
                //decrement myMillisecondsOfMute if greater than 0
                if (myMillisecondsOfMute > 0) {
                    myMillisecondsOfMute -= myGameSpeed;
                }
                
            }

            //measure time elapsed since last advert change
            mySecondsSinceLastAdvertChange += myGameSpeed;
            
            //change board advertisement
            if (mySecondsSinceLastAdvertChange > MILLISEC_DELAY_BETWEEN_ADVERTS) {
                myTetrisFrame.cycleAdvertImage();
                
                mySecondsSinceLastAdvertChange = 0;
            }
        }
        
    } //end of ActionOnBoard
    
    
    //property change listeners
    /**
     * PropertyChangeListener for tetris board.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     */
    public class TetrisBoardPropertyChangeListener implements PropertyChangeListener {
        /**
         * This method listens for fired property changes in the BoardPanel class. 
         * It then determines what key( left,  right, drop, etc.) was pressed and calls
         * the appropriate Board class method.
         * 
         * @param theEvent propertyChange event that was fired, which contains the action of
         * the key that was pushed
         */
        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
            final String propertyName = theEvent.getPropertyName();
            
            if (myGameIsRunning) {
                if ("left".equals(propertyName)) {
                    myBoard.moveLeft();
                } else if ("right".equals(propertyName)) {
                    myBoard.moveRight();
                } else if ("rotate".equals(propertyName)) {
                    myBoard.rotateCW();
                } else if ("drop".equals(propertyName)) {
                    myBoard.hardDrop();
                } else if ("down".equals(propertyName)) {
                    myBoard.moveDown();
                } 
                
                myTrackedCycles++;
            }
            
            
        } //end of propertyChange
        
    } //end of TetrisBoardPropertyChangeListener
    
    /**
     * PropertyChangeListener to pause the game.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     */
    public class PausePropertyChangeListener implements PropertyChangeListener {
        
        /**
         * "pause" toggles the game while "pauseOnly" only pauses the game.
         * 
         * @param theEvent propertyChange event that was fired
         */
        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
       
            if (!myGameIsOver) {
                final String propertyName = theEvent.getPropertyName();
                
                if ("pause".equals(propertyName)) {
                    //toggle pause on timer
                    pauseToggle();
                } else if ("pauseOnly".equals(propertyName)) {
                    myTetrisBoard.setJLabelVisible(true);
                    myBGAudio.pauseTrack();
                    myGameIsRunning = false;
                }
            }
            
            
        } //end of propertyChange
    } //end of PausePropertyChangeListener
        
    /**
     * PropertyChangeListener to adjust the dimensions of the board in the next game.
     * It also adjusts game speed.
     * 
     * 
     * @author Ray Kim
     * @version Autumn 2014
     */
    public class BoardDimenGameSpeedChangeListener implements PropertyChangeListener {
        
        /**
         * This method saves the row and column lengths in blocks recorded by the JSlider.
         * These values are used in creating a board of the specified dimensions in the
         * next game.
         * 
         * @param theEvent propertyChange event that was fired
         */
        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
            final String propertyName = theEvent.getPropertyName();
                      
            if ("block rows".equals(propertyName)) {
                myBoardRowsInBlocks = (int) theEvent.getNewValue();
            } else if ("block columns".equals(propertyName)) {
                myBoardColumnsInBlocks = (int) theEvent.getNewValue();
            } else if ("game speed".equals(propertyName)) {
                //divide value sent by slider by max game speed. Then add this value to 1
                //for new game speed. max game speed is 2x
                final double gameSpeedModifier = 1 + ((int) theEvent.getNewValue()
                                                / HUNDRED_PERCENT);
                myGameSpeed = (int) ((BASE_GAME_SPEED * ((1 - GAME_SPEED_MULTIPLIER 
                                                * myCurrentLevel) / gameSpeedModifier)));
                myGameTimer.setDelay(myGameSpeed);
                
                //set music speed (adjusts for higher levels so music doesn't automatically
                //speed up with the increasing game speeds)
                final double musicSpeed = 1 + ((int) theEvent.getNewValue() / HUNDRED_PERCENT);
                
                myBGAudio.setMusicSpeed(musicSpeed);
                
            }
            
        } //end of propertyChange
    } //end of BoardBlockDimenPropertyChangeListener
    
    /**
     * PropertyChangeListener for Menu Bar options.
     * 
     * @author Ray Kim
     * @version Autumn 2014
     */
    public class MenuBarPropertyChangeListener implements PropertyChangeListener {
        
        /**
         * This method differentiates commands from the menu bar and executes the
         * appropriate commands for the option.
         * 
         * @param theEvent propertyChange event that was fired
         */
        @Override
        public void propertyChange(final PropertyChangeEvent theEvent) {
                     
            switch (theEvent.getPropertyName()) {
                case "new game":
                    startNewGame(0);
                    break;
                    
                case "end game":
                    setGameOver();
                    break;
                    
                case "save":
                    saveGame();
                    break;
                    
                case "load":
                    loadGame();
                    break;
                    
                case "reverse tetris mode":
                    reverseTetrisModeToggle();
                    break;
                    
                case "mute":
                    JOptionPane.showMessageDialog(null, "This option is not available "
                                                    + "with this trial version of Regretris");
                    break;
                
                case "view replay":
                    viewReplayMode();
                    break;
                
                case "about...":
                    JOptionPane.showMessageDialog(null, new AboutJPanel(), 
                                                  "About", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default: 
                    break;
            }
            
        } //end of propertyChange
    } //end of BoardBlockDimenPropertyChangeListener
    
}
