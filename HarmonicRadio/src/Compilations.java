import java.util.ArrayList;
import java.util.HashMap;

public class Compilations
{
	private HashMap<String, ArrayList<Stream>> compilations;
	private ArrayList<Stream> current, recentlyPlayed, randomShuffle;
	private DatabaseConnect connection;
	private StreamPlayer player;
	
	//------------------------------------------------------------------//
	// The constructor for the class. This should initialise all the
	// lists that are being tracked.
	//------------------------------------------------------------------//
	public Compilations()
	{
		this.compilations = new HashMap<String, ArrayList<Stream>>();
		this.player = new StreamPlayer();
		this.connection = new DatabaseConnect();
		connection.createRecordList();
		this.randomShuffle = connection.createRandomList();
		compilations.put("$*shuffle*$", randomShuffle);
		choosePlaylist("$*shuffle*$");
	}
	
	//------------------------------------------------------------------//
	// When a new station is being listened to, the list should be updated
	// so that the recently listened to list is up to date.
	// This should be kept to a certain size.
	//------------------------------------------------------------------//
	public void choosePlaylist(String playlistName)
	{
		current = compilations.get(playlistName);
	}
	
	//------------------------------------------------------------------//
	// This method tells the Stream player to start playing the selected
	// stream. It should also update the "recently played" playlist.
	//------------------------------------------------------------------//
	public void play(int index)
	{
		player.updateRadio(current.get(index).getURL());
		player.setPlayOn();
		//updateRecent(current.get(index));		// Needs to be done but want to load recently played from file.
	}
	
	//------------------------------------------------------------------//
	// Method that stops the current stream.
	//------------------------------------------------------------------//
	public void stopStream()
	{
		player.stopStream();
	}
	
	//------------------------------------------------------------------//
	// This method grabs a new random playlist from the main database.
	//------------------------------------------------------------------//
	public void updateRandom()
	{
		randomShuffle = compilations.get("$*shuffle*$");
		randomShuffle = connection.createRandomList();
		compilations.remove("$*shuffle*$");
		compilations.put("$*shuffle*$", randomShuffle);	
	}
	
	//------------------------------------------------------------------//
	// Method that updates the recently played playlist.
	//------------------------------------------------------------------//
	public void updateRecent(Stream nowPlaying)
	{
		recentlyPlayed.add(nowPlaying);
		
		while (recentlyPlayed.size() > 10)
		{
			recentlyPlayed.remove(10);
		}
	}
}
