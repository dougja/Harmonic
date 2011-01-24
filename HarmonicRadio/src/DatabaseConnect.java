import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatabaseConnect
{
	 static NodeList nodes = null;
	 private File xml = null;
	 private DocumentBuilderFactory factory = null;
	 private DocumentBuilder builder = null;
	 private Document doc = null;
	 private ArrayList<Stream> urlList = null;
	 private ArrayList<Stream> playlist = new ArrayList<Stream>();
	 private HashMap<String, Integer> radioNames;
	 
	 // This class should return streams from the XML files and added to playlists in compilations.
	 // I think this class should be able to connect to the URL holding the xml file and download a fresh one.
	 
	 DatabaseConnect()
	 {
		radioNames = new HashMap<String, Integer>();
		
		try
		{
		    xml = new File("streams.xml");
		    factory = DocumentBuilderFactory.newInstance();
		    builder = factory.newDocumentBuilder();
		    doc = builder.parse(xml);
		    doc.getDocumentElement().normalize();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	 }
	 
	 
	 public void createList()
	 {
		 nodes = doc.getElementsByTagName("record");
	 }
	 
	 void createStreamList()
	 {
		 urlList = new ArrayList<Stream>();
		 Random r = new Random(); 
		 for (int i = 0; i < 10; i++)
		 {
			 int rand = r.nextInt(nodes.getLength());
			 Node node = nodes.item(rand);
			 
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
				 Element element = (Element) node;
				 String name = getTagValue("name", element);
				 String stream = getTagValue("url", element);
				 
				 if(stream.equals("Invalid url") == false)
				 {
					 Stream url = new Stream(name,stream,"xx","xx",true);
					 urlList.add(url);
					 radioNames.put(name, i);
				 }
			 }
		 }
		 printPlaylist(urlList);
	 }
	 
	 //returns a string depending on which tag you want
	 private String getTagValue(String tag, Element element)
	 {
	    NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
	    Node node = (Node) list.item(0);
	    if(node == null && tag.equals("name") == true) return "Unknown Radio";
	    if(node == null && tag.equals("url") == true) return "Invalid url";
	    
	    return node.getNodeValue();    
	 }
	 
	 boolean addToPlaylist(String name)
	 {
		 if(playlist.size() < 10)
		 {
			 Stream object = findRadioStation(name);
			if(object != null)
			{
				playlist.add(object);
				return true;
			}
			return false;	
		 }
		 return false;
	}
	 Random r = new Random(); 
	public ArrayList<Stream> returnPlaylist()
	{
		return playlist;
	}
	 
	public Stream findRadioStation(String name)
	{
		int i ;
		if(radioNames.get(name) != null)
			i = radioNames.get(name);
		else return null;
	 
		Stream object = urlList.get(i);
		return object;
	}
	 
	public void savePlaylist(String playlistName)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(playlistName+".xml"));
			int i = 0;
			while(i < playlist.size())
			{
				writer.write(playlist.get(i).getName()+","+playlist.get(i).getURL()+","+playlist.get(i).getCountry()+"," + playlist.get(i).getGenre()+","+playlist.get(i).isRange());
				writer.newLine();
				i++;
			}
			writer.close();
		}
		catch(Exception e)
		{
			System.err.println(e.getLocalizedMessage());
		}
		playlist = new ArrayList<Stream>();
	 }
	 
	 void loadPlaylist()
	 {
		 try
		 {
			 playlist = new ArrayList<Stream>();
			 BufferedReader reader = new BufferedReader(new FileReader("Playlist.txt"));
			 String line;
			 while((line = reader.readLine()) != null)
			 {
				 String[] split = line.split(",");
				 System.out.println(split[0]);
				 boolean range = Boolean.parseBoolean(split[4]);
				 Stream object = new Stream(split[0],split[1],split[2],split[3],range);
				 playlist.add(object);
			 }
		 }
		 catch(Exception e)
		 {
			 System.err.println(e.getMessage());
		 }
	 }
	 
	 void printPlaylist(ArrayList<Stream> list)
	 {
		 for(int i = 0;i < list.size();i++)
		 {
			 System.out.println(list.get(i).getName());
		 }
	 }
	 
	 public ArrayList<Stream> returnList()
	 {
		 return urlList;
	 }
	 
	 public void updateDatabase()
	 {
		 try
		 {
			 URL database = new URL("http://www.studentdimension.co.uk/harmonic/xml.php");
			 URLConnection con = database.openConnection();
			 BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			 BufferedWriter out = new BufferedWriter(new FileWriter("streams.xml"));
			 String line;
			
			 while ((line = in.readLine()) != null)
			 {
				 line = XMLEntities.unescapeHTML(line);				 
				 out.write(line);
				 out.newLine();
				 out.flush();
			 }
			 in.close();
			 out.close();
		 }
		 catch (Exception e) {e.printStackTrace();}
	 }
}
