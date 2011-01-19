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
	private ConnectToDatabase connect = null;
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
		// needs to connect to the database.
		connect = new ConnectToDatabase("streams.xml");
		factory = new MediaPlayerFactory(new String[] {});
		streamPlayer = factory.newMediaPlayer(null);
	}
	
	//------------------------------------------------------------------//
	// This method starts stream playback.
	//------------------------------------------------------------------//
	void playnewURL(String url)
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
	void stopStream()
	{
		this.play = false;
		streamPlayer.stop();
		streamPlayer.release();
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
	void updateRadio(String radio0)
	{
		radio = radio0;
	}
	public void run() 
	{
		
		while (runner == Thread.currentThread()) 
		{
			try 
			{
				Thread.sleep(500);
				if (play)
				{
					playnewURL(radio);
				}	
			}
			catch(InterruptedException e) 
			{
				System.out.println("Thread failed");
			}
		}
	}
	void refreshDatabase(String filename)
	{
		connect = new ConnectToDatabase(filename);
	}
	ConnectToDatabase returnConnection()
	{
		return connect;
	}
}