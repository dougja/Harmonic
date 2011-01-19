import java.util.ArrayList;
import java.util.HashMap;

public class Compilations
{
	private HashMap<String, ArrayList<Stream>> compilations;
	private ArrayList<Stream> recentlyPlayed;
	private ConnectToDatabase connect = null;
	private StreamPlayer player = null;
	ArrayList<Stream> current = null;
	
	//------------------------------------------------------------------//
	// The constructor for the class. This should initialise all the
	// lists that are being tracked.
	//------------------------------------------------------------------//
	public Compilations()
	{
		this.compilations = new HashMap<String, ArrayList<Stream>>();
		player = new StreamPlayer();
		connect = player.returnConnection();
		connect.createList();
		connect.createStreamList();
		recentlyPlayed = connect.returnList();
		compilations.put("recent", recentlyPlayed);
	}
	
	//------------------------------------------------------------------//
	// When a new station is being listened to, the list should be updated
	// so that the recently listened to list is up to date.
	// This should be kept to a certain size.
	//------------------------------------------------------------------//
	void choosePlaylist(String playlistName)
	{
		current = compilations.get(playlistName);
		
	}
	void scroll(int index)
	{
		play(current.get(index));
	}
	void play(Stream object)
	{
		
		player.updateRadio(object.getURL());
		player.setPlayOn();
	}
	public void newStation(Stream nowPlaying)
	{
		recentlyPlayed.add(nowPlaying);
		
		if (recentlyPlayed.size() > 5)
		{
			recentlyPlayed.remove(5);
		}
	}
}
