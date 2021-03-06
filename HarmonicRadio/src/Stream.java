class Stream
{
	private String name;
	private String url;
	private String country;
	private String genre;
	private Boolean range;
	
	Stream(String name, String url, String country, String genre, Boolean range)
	{
		this.name = name;
		this.url = url;
		this.country = country;
		this.genre = genre;
		this.range = range;
	}
	
	String getURL()
	{
		return url;
	}
	
	String getName()
	{
		return name;
	}
	
	String getCountry()
	{
		return country;
	}
	
	String getGenre()
	{
		return genre;
	}
	
	Boolean isRange()
	{
		return range;
	}
	
	// Meant for debugging.
	public void showURL()
	{
		System.out.println(name+" "+url+" "+country+" "+genre+" "+range);
	}
}
