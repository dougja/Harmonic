package Source;

class URLObject
{
	private String name;
	private String url;
	private String country;
	private String genre;
	private Boolean range;
	URLObject(String name0,String url0,String country0,String genre0,Boolean range0,String song_name0 )
	{
		name = name0;
		url = url0;
		country = country0;
		genre = genre0;
		range = range0;
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
	
	public void showURL()
	{
		System.out.println(name+" "+url+" "+country+" "+genre+" "+range);
	}
}