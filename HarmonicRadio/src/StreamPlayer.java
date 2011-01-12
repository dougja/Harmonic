/*==================================================================
 * This is the class that plays the stream.
 * Currently, this uses the open source vlcj libraries which
 * interface with libvlc. This allows the streams to be easily
 * played back. 
 =================================================================*/

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

public class StreamPlayer implements Runnable
{
	private Thread runner;
	private MediaPlayerFactory factory;
	private MediaPlayer streamPlayer;
	private boolean play = false;

	//------------------------------------------------------------------//
	// The constructor for the class. Initialises the player factory and
	// starts the player thread.
	//------------------------------------------------------------------//
	StreamPlayer()
	{
		try 
		{
			this.initialise();
			startThread();
		} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	//------------------------------------------------------------------//
	// Initialise the connection to the database and initialise the 
	// media player.
	//------------------------------------------------------------------//
	public void initialise() throws InterruptedException
	{
		// needs to connect to the database.
		
		factory = new MediaPlayerFactory(new String[] {});
		streamPlayer = factory.newMediaPlayer(null);
	}
	
	//------------------------------------------------------------------//
	// This method starts stream playback.
	//------------------------------------------------------------------//
	void playnewURL() throws InterruptedException
	{	  
		streamPlayer.playMedia("http://network.absoluteradio.co.uk/core/audio/wmp/live.asx?service=vr");
		streamPlayer.setPlaySubItems(true);
		Thread.currentThread().join();
	}
	
	//------------------------------------------------------------------//
	// Method that can stop the stream.
	//------------------------------------------------------------------//
	void stopStream()
	{
		streamPlayer.stop();
	}
	
	//------------------------------------------------------------------//
	// Change the volume of the stream to the volume given.
	//------------------------------------------------------------------//
	void changeVol(int volume)
	{
		streamPlayer.setVolume(volume);
	}
	
	//------------------------------------------------------------------//
	// Mute or unmute the stream.
	//------------------------------------------------------------------//
	void muteStream(boolean mute)
	{
		streamPlayer.mute(mute);
	}
	
	//------------------------------------------------------------------//
	// Returns whether a stream is playing or not.
	//------------------------------------------------------------------//
	public boolean getPlaying()
	{
		return play;
	}
	
	//------------------------------------------------------------------//
	// Method to set the stream to start playing.
	//------------------------------------------------------------------//
	public void setPlayOn()
	{
		this.play = true;
	}
	
	//------------------------------------------------------------------//
	// This method stops the stream playing.
	//------------------------------------------------------------------//
	public void setPlayOff()
	{
		this.play = false;
	}

	//------------------------------------------------------------------//
	// This method starts the thread associated with this class.
	//------------------------------------------------------------------//
	private void startThread() 
	{
		if (runner == null) 
		{
			runner = new Thread(this);
		}
		runner.start();
	}
	
	//------------------------------------------------------------------//
	// Executes on this thread.
	//------------------------------------------------------------------//
	public void run() 
	{
		while (runner == Thread.currentThread()) 
		{
			try 
			{
				Thread.sleep(500);
				if (play)
				{
					playnewURL();
				}	
			}
			catch(InterruptedException e) 
			{
				System.out.println("Thread failed");
			}
		}
	}
}