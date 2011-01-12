/*==================================================================
 * This is the class that plays the stream.
 * Currently, this uses the open source vlcj libraries which
 * interface with libvlc. This allows the streams to be easily
 * played back. 
 =================================================================*/

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

public class StreamHttp implements Runnable
{
	private Thread runner;
	private MediaPlayerFactory factory;
	private MediaPlayer streamPlayer;
	private boolean play = false;

	StreamHttp()
	{
		try 
		{
			this.initialise();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		startThread();
	}
	
	// Initialise the connection to the database and initialise the media player.
	public void initialise() throws InterruptedException
	{	  
		factory = new MediaPlayerFactory(new String[] {});
		streamPlayer = factory.newMediaPlayer(null);
	}

	// Play a new URL.
	void playnewURL(int URLindex) throws InterruptedException
	{	  
		streamPlayer.playMedia("http://network.absoluteradio.co.uk/core/audio/wmp/live.asx?service=vr");
		streamPlayer.setPlaySubItems(true);
		Thread.currentThread().join();
	}
	
	// Stop the stream.
	void stopStream()
	{
		streamPlayer.stop();		
	}
	
	// Change the volume of the stream.
	void changeVol(int volume)
	{
		streamPlayer.setVolume(volume);
	}
	
	public void setPlayOn()
	{
		play = true;
	}
	
	public void setPlayOff()
	{
		play = false;
	}

	private void startThread() 
	{
		if(runner == null) runner = new Thread(this);
		runner.start();
	}
	
	public void run() 
	{
		// Stuff in this thread is executed here.
		
		
		
		
		while (runner == Thread.currentThread()) 
		{
			try 
			{
				Thread.sleep(1000);
				if (play)
				{
					playnewURL(5);
				}	
			}
			catch(InterruptedException e) 
			{
				System.out.println("Thread failed");
			}
		}
	}
}