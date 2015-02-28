/*
 * TCSS 305: Assignment 6
 */


package file.interaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Block;
import model.Piece;

/**
 * The class stores board block information and is serializable.
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
public final class TetrisGameData implements Serializable {
    
    
    //constants
    /**
     * Serial UID.
     */
    private static final long serialVersionUID = -4007394649888186246L;

    //variables
    /**
     * Variable that contains the current level.
     */
    private int myLevel;
    
    /**
     * Contains the number of points scored.
     */
    private int myScore;
    
    /**
     * This variable tracks the number of rows the last List of frozen blocks had.
     */
    private int myLastFrozenBlockHeight;
    
    /**
     * Saves the number of milliseconds into the level song at the time of serialization.
     */
    private int myPositionInMusic;
    
    /**
     * Variable for board width.
     */
    private int myBoardWidthInBlocks;
    
    /**
     * Variable for board height.
     */
    private int myBoardHeightInBlocks;
    
    /**
     * The current piece that will be serialized.
     */
    private Piece myCurrentPiece;
    
    /**
     * The next piece that will be serialized.
     */
    private Piece myNextPiece;
    
    /**
     * The current frozen blocks (in the format originally derived from the Board class)
     * that will be serialized.
     */
    private List<List<Block>> myFrozenBlocksList;
    
    
    //replay variables
    /**
     * Holds a List of current piece Block coordinates for replay mode.
     */
    private final List<int[][]> myCurrentPieceReplayList;
    
    /**
     * Holds a List of frozen block coordinates for replay mode.
     */
    private final List<List<int[]>> myFrozenBlockReplayList;
    
    /**
     * Holds a List of next block coordinates for replay mode.
     */
    private final List<int[][]> myNextPieceReplayList;
    
    
    
    
    //constructor and methods
    /**
     * Constructor only initializes game replay data collections.
     * 
     */
    public TetrisGameData() {
        
        myCurrentPieceReplayList = new ArrayList<>();
        myFrozenBlockReplayList = new ArrayList<>();
        myNextPieceReplayList = new ArrayList<>();
    }
    
    /**
     * This method clears all replay data collections, and score and myLastFrozenBlockHeight.
     */
    public void clearData() {
        myScore = 0;
        myLastFrozenBlockHeight = 0;
        myCurrentPieceReplayList.clear();
        myFrozenBlockReplayList.clear();
        myNextPieceReplayList.clear();
    }
    
    //setters
    
    /**
     * This method stores the current score.
     * @param theScore int score;
     */
    public void setScore(final int theScore) {
        myScore = theScore;
    }
    
    /**
     * This method adds another amount to the current score.
     * @param theAddedAmount int amount added to current score.
     */
    public void addToScore(final int theAddedAmount) {
        myScore += theAddedAmount;
    }
    
    /**
     * This method stores the number or rows in the last set of frozen blocks.
     * @param theLastFrozenBlockHeight int the Last Frozen Block Height;
     */
    public void setLastFrozenBlockHeight(final int theLastFrozenBlockHeight) {
        myLastFrozenBlockHeight = theLastFrozenBlockHeight;
    }
    
    /**
     * This method saves game data in preparation for game serialization and save.
     * 
     * @param theLevel int level
     * @param theBoardWidthInBlocks saved state board width
     * @param theBoardHeightInBlocks saved state board height
     * @param thePositionInMusic int milliseconds into the music
     * @param theCurrentPiece current piece data
     * @param theNextPiece next piece data
     * @param theFrozenBlocksList frozen blocks list data
     */
    public void saveState(final int theLevel,
                          final int theBoardWidthInBlocks,
                          final int theBoardHeightInBlocks,
                          final int thePositionInMusic,
                          final Piece theCurrentPiece, 
                          final Piece theNextPiece, 
                          final List<List<Block>> theFrozenBlocksList) {
        myLevel = theLevel;
        myBoardWidthInBlocks = theBoardWidthInBlocks;
        myBoardHeightInBlocks = theBoardHeightInBlocks;
        myPositionInMusic = thePositionInMusic;
        myCurrentPiece = theCurrentPiece;
        myNextPiece = theNextPiece;
        myFrozenBlocksList = theFrozenBlocksList;
    }
    
    
    
    
    /**
     * Saves current piece replay data.
     * 
     * @param theCurrentPieceCoords int[][] most current current gameboard piece coords
     */
    public void addToCurrentPieceReplay(final int[][] theCurrentPieceCoords) {
        
        //add new coords to myCurrentPieceReplayList
        myCurrentPieceReplayList.add(theCurrentPieceCoords);
    }
    
    /**
     * Saves next piece replay data.
     * 
     * @param theNextPieceCoords int[][] most current next gameboard piece coords
     */
    public void addToNextPieceReplay(final int[][] theNextPieceCoords) {

        //add new coords to myNextPieceReplayList
        myNextPieceReplayList.add(theNextPieceCoords);
    }
    
    /**
     * Saves frozen block replay data.
     * 
     * @param theFrozenBlocksList List<int[]> currently frozen blocks
     */
    public void addToFrozenBlockReplay(final List<int[]> theFrozenBlocksList) {
        
        //add new coords to myFrozenBlockReplayList
        myFrozenBlockReplayList.add(theFrozenBlocksList);
    }
    
    
    //getters
    /**
     * Method returns int current level.
     * 
     * @return int level
     */
    public int getLevel() {
        return myLevel;
    }
    
    /**
     * This method returns the score.
     * @return int myScore
     */
    public int getScore() {
        return myScore;
    }
    
    /**
     * This method retunrs the last frozen block height.
     * @return int myLastFrozenBlockHeight
     */
    public int getLastFrozenBlockHeight() {
        return myLastFrozenBlockHeight;
    }
    
    /**
     * Method returns myBoardWidthInBlocks.
     * 
     * @return int myBoardWidthInBlocks
     */
    public int getBoardWidthInBlocks() {
        return myBoardWidthInBlocks;
    }
    
    /**
     * Method returns myBoardHeightInBlocks.
     * 
     * @return int myBoardHeightInBlocks
     */
    public int getBoardHeightInBlocks() {
        return myBoardHeightInBlocks;
    }
    
    /**
     * Method returns the number of milliseconds into the music when game data was
     * serialized.
     * @return int milliseconds into the music when game was serialized
     */
    public int getPositionInMusic() {
        return myPositionInMusic;
    }
    
    
    /**
     * Method returns saved current piece data.
     * @return Piece current piece data
     */
    public Piece getCurrentPiece() {
        return myCurrentPiece;
    }
    
    /**
     * Method returns saved next piece data.
     * @return Piece current piece data
     */
    public Piece getNextPiece() {
        return myNextPiece;
    }
    
    /**
     * Method returns saved frozen block data.
     * @return Piece current piece data
     */
    public List<List<Block>> getFrozenBlocks() {
        return myFrozenBlocksList;
    }
    
    /**
     * This method retrieves current piece block coordinates saved in the replay
     * List myCurrentPieceReplayList.
     * @return List<int[][]> coordinates of current pieces throughout saved game
     */
    public List<int[][]> getCurrentPieceReplay() {
        return myCurrentPieceReplayList;
    }
    
    /**
     * This method retrieves next piece block coordinates saved in the replay
     * List myNextPieceReplayList.
     * @return List<int[][]> coordinates of next pieces throughout save game
     */
    public List<int[][]> getNextPieceReplayCoords() {
        return myNextPieceReplayList;
    }
    
    /**
     * This method retrieves frozen piece block coordinates saved in the replay
     * List myFrozenBlockReplayList.
     * @return List<List<int[]>> coordinates of frozen blocks
     */
    public List<List<int[]>> getFrozenBlockReplayCoords() {
        return myFrozenBlockReplayList;
    }
    
    /**
     * This method retrieves current piece block coordinates saved in the replay
     * List myCurrentPieceReplayList.
     * @param theReplayIndex int cycle number
     * @return int[][] coordinates of current piece at cycle theReplayIndex
     */
    public int[][] getCurrentPieceReplayCoords(final int theReplayIndex) {
        return myCurrentPieceReplayList.get(theReplayIndex);
    }
    
    /**
     * This method retrieves next piece block coordinates saved in the replay
     * List myNextPieceReplayList.
     * @param theReplayIndex int cycle number
     * @return int[][] coordinates of next piece at cycle theReplayIndex
     */
    public int[][] getNextPieceReplayCoords(final int theReplayIndex) {
        return myNextPieceReplayList.get(theReplayIndex);
    }
    
    /**
     * This method retrieves frozen piece block coordinates saved in the replay
     * List myFrozenBlockReplayList.
     * @param theReplayIndex int cycle number
     * @return List<int[]> coordinates of frozen blocks at cycle theReplayIndex
     */
    public List<int[]> getFrozenBlockReplayCoords(final int theReplayIndex) {
        return myFrozenBlockReplayList.get(theReplayIndex);
    }
    
}
