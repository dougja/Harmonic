import java.util.ArrayList;
import java.util.HashMap;

public class Compilations
{
	private HashMap<String, ArrayList<Stream>> compilations;
	private ArrayList<Stream> current, recentlyPlayed, ipStations, randomShuffle;
	private DatabaseConnect connection = null;
	private StreamPlayer player = null;
	
	//------------------------------------------------------------------//
	// The constructor for the class. This should initialise all the
	// lists that are being tracked.
	//------------------------------------------------------------------//
	public Compilations()
	{
		this.compilations = new HashMap<String, ArrayList<Stream>>();
		//player = new StreamPlayer();
		connection = new DatabaseConnect();
		connection.createList();
		connection.createStreamList();
		randomShuffle = connection.returnList();
		compilations.put("shuffle", randomShuffle);
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
	
	void play(int index)
	{
		player.updateRadio(current.get(index).getURL());
		player.setPlayOn();
	}

	void stopStream()
	{
		player.stopStream();
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
