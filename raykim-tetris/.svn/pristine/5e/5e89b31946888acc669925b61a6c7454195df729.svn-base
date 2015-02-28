/*
 * TCSS 305: Assignment 6
 */

package media;

import file.interaction.FileInteractionHelper;

import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;



/**
 * This class creates the background music used on the game levels.
 * 
 * Code for javafx media borrowed from:
 * stackoverflow.com/questions/20171316/how-to-add-jfxpanel-to-already-existing-swing-app
 * 
 * @author Ray Kim
 * @version Autumn 2014
 *
 */
@SuppressWarnings("serial")
public class BGAudio extends JFXPanel {
    
    //variables
    /**
     * Reference to the current media player(which contains the currently paying song).
     */
    private MediaPlayer myCurrentMediaPlayer;
    
    /**
     * MediaView that holds the current track being played.
     */
    private final MediaView myViewWindow;
    
    /**
     * List that contains audio MediaPlayers.
     */
    private final List<MediaPlayer> myMediaPlayerList;
    
    //constructor and methods
    /**
     * Creates a new BGAudio object.
     */
    public BGAudio() {
        super();
//        System.out.println(SwingUtilities.isEventDispatchThread());
        myMediaPlayerList = new ArrayList<>();
        myViewWindow = new MediaView();
        
        //create audio system
        generateAudioSystem();
        
    }

    /**
     * This method creates this JFXPanel and loads songs into 
     * myMusicList.
     */
    private void generateAudioSystem() {
        //pane which contains all 
        final StackPane root = new StackPane();
        
        //retrieve audio file directories and create Media objects from the String directories
        //then, create MediaPlayers of the Media objects and load them into a List
        final List<String> audioFileDirectories = FileInteractionHelper.
                                        generateListOfAudioDirectories("audio");
        
        for (final String directory : audioFileDirectories) {
            myMediaPlayerList.add(new MediaPlayer(new Media(directory)));
        }

        
        //set default music for mediaview
        myViewWindow.setMediaPlayer(myMediaPlayerList.get(0));
        
        //add video view to stack pane
        root.getChildren().add(myViewWindow);
        
        
    } //end of generateAudioSystem
    
    /**
     * This method, passed in an index X, plays the track myMusicList[X].
     * @param theTrackIndex int index used to get track from myMusicList
     */
    public void loadTrack(final int theTrackIndex) {
        //set myCurrentMediaPlayer to new index
        myCurrentMediaPlayer = myMediaPlayerList.get(theTrackIndex);
        
        //set media player for media view
        myViewWindow.setMediaPlayer(myCurrentMediaPlayer);
        
        //set on stopped action
        myCurrentMediaPlayer.setOnEndOfMedia(new Runnable() {

            /**
             * At the end of the song, fire property change to change level.
             */
            @Override
            public void run() {
                firePropertyChange("next level", false, true);
            }

        });
    }
    
    /**
     * This method pauses the currently playing song.
     */
    public void pauseTrack() {
        //pause current mediaplayer
        myCurrentMediaPlayer.pause();
    }
    
    /**
     * This method resumes playing the track of the current media player.
     */
    public void resumeTrack() {
        //resume current mediaplayer
        myCurrentMediaPlayer.play();
    }
    
    /**
     * This method is invoked when a game ends, thus stopping the current track.
     */
    public void stopTrack() {
        //stop current mediaplayer
        myCurrentMediaPlayer.stop();
    }
    
    /**
     * This method mutes the music based on the number of lines cleared.
     * @param theBoolean true mutes the current track, false unmutes
     */
    public void muteTrack(final boolean theBoolean) {
        myCurrentMediaPlayer.setMute(theBoolean);
    }
    
    //setters
    /**
     * This method allows the JSlider which determines the speed of the game to also change
     * the speed of the song.
     * 
     * @param theNewRate double where 1.0 is normal, 2.0 is double the normal speed.
     */
    public void setMusicSpeed(final double theNewRate) {
        myCurrentMediaPlayer.setRate(theNewRate);
    }
    
    /**
     * This method sets the music to the specified position thePositionInTrack.
     * The level determines what track is played.
     * @param theLevel int level
     * @param thePositionInTrack int seconds into track
     */
    public void setPosition(final int theLevel, final int thePositionInTrack) {
        
        //load track and resume playing from thePositionInTrack
        loadTrack(theLevel);
        myCurrentMediaPlayer.seek(new Duration(thePositionInTrack));
    }
    
    //getters
    /**
     * This method gets the seconds elapse since the start of current track.
     * @return int milliseconds into current track
     */
    public int getPosition() {
        return (int) myCurrentMediaPlayer.getCurrentTime().toMillis();
    }
    
    /**
     * This method checks to see if the track is in mute.
     * @return boolean true if track is muted, false
     */
    public boolean trackIsMuted() {
        return myCurrentMediaPlayer.isMute();
    }
    
    /**
     * This method checks to see if the song is over.
     * @return boolean true means song is over.
     */
    public boolean songIsOver() {
        boolean songOver = false;
        if (myCurrentMediaPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
            songOver = true;
        }
        
        return songOver;
    }
}
