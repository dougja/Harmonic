import java.util.ArrayList;

public class Compilations
{
	private ArrayList<Stream> recentlyPlayed;
	
	//------------------------------------------------------------------//
	// The constructor for the class. This should initialise all the
	// lists that are being tracked.
	//------------------------------------------------------------------//
	public Compilations()
	{
		this.recentlyPlayed = new ArrayList<Stream>();
	}
	
	//------------------------------------------------------------------//
	// When a new station is being listened to, the list should be updated
	// so that the recently listened to list is up to date.
	// This should be kept to a certain size.
	//------------------------------------------------------------------//
	public void newStation(Stream nowPlaying)
	{
		recentlyPlayed.add(nowPlaying);
		
		if (recentlyPlayed.size() > 5)
		{
			recentlyPlayed.remove(5);
		}
	}
}
