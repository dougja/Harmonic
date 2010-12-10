/*==================================================================
 * This is the class that plays the stream.
 * Currently, this uses the open source vlcj libraries which
 * interface with libvlc. This allows the streams to be easily
 * played back. 
 =================================================================*/

package Source;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

public class StreamHttp
{
	private ConnectToDatabase db;
	private MediaPlayerFactory factory;
	private MediaPlayer streamPlayer;

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
	}
	
	// Initialise the connection to the database and initialise the media player.
	public void initialise() throws InterruptedException
	{
		db = new ConnectToDatabase("jdbc:mysql://89.16.179.91:3307/studentd_harmonic","studentd_harmusr","u1?VA!f~ozkS");
		db.establishConnection();
	  
		factory = new MediaPlayerFactory(new String[] {});
		streamPlayer = factory.newMediaPlayer(null);
	}
	
	// Play a new URL.
	void playnewURL(int URLindex) throws InterruptedException
	{
		String streamURL;
		URLObject stream;
		
		//find URLindex from GUI
		stream = db.search(URLindex);
		streamURL = stream.getURL();
	  
		streamPlayer.playMedia(streamURL);
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
}