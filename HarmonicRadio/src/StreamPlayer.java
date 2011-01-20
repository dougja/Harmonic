/*==================================================================
 * This is the class that plays the stream.
 * The class uses libvlc to play the streams, and provides
 * the necessary functionality to control the stream playback.
 =================================================================*/

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

public class StreamPlayer implements Runnable
{
	private Thread runner;
	private MediaPlayerFactory factory;
	private MediaPlayer streamPlayer;
	private boolean play = false;
	private String radio = null;
	
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
		factory = new MediaPlayerFactory(new String[] {});
		streamPlayer = factory.newMediaPlayer(null);
	}
	
	//------------------------------------------------------------------//
	// This method starts stream playback.
	//------------------------------------------------------------------//
	public void playStream(String url)
	{
		try 
		{
			streamPlayer.playMedia(url);
			streamPlayer.setPlaySubItems(true);
			Thread.currentThread().join();
		}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	//------------------------------------------------------------------//
	// Method that can stop the stream.
	//------------------------------------------------------------------//
	public void stopStream()
	{
		this.play = false;
		streamPlayer.stop();
	}
	
	//------------------------------------------------------------------//
	// Releases the current stream.
	//------------------------------------------------------------------//
	public void killStream()
	{
		this.play = false;
		this.radio = null;
		streamPlayer.release();
	}
	
	//------------------------------------------------------------------//
	// Change the volume of the stream to the volume given.
	//------------------------------------------------------------------//
	public void changeVol(int volume)
	{
		streamPlayer.setVolume(volume);
	}
	
	//------------------------------------------------------------------//
	// Mute or unmute the stream.
	//------------------------------------------------------------------//
	public void muteStream(boolean mute)
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
	// Updates the current radio station.
	//------------------------------------------------------------------//
	public void updateRadio(String radio)
	{
		this.radio = radio;
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
				if (play && (radio != null))
				{
					playStream(radio);
				}	
			}
			catch(InterruptedException e) 
			{
				System.out.println("Thread failed");
			}
		}
	}
}