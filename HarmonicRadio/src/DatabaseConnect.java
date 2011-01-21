import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;

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
	 private Hashtable<String,Integer> radioNames = new Hashtable<String,Integer>();
	 
	 DatabaseConnect()
	 {
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
	 
	 void createList()
	 {
		 nodes = doc.getElementsByTagName("record");
	 }
	 
	 void createStreamList()
	 {
		 urlList = new ArrayList<Stream>();
		 for(int i = 0; i < nodes.getLength(); i++)
		 {
			 Node node = nodes.item(i);
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
				 Element element = (Element) node;
				 String name = getTagValue("name",element);
				 String stream = getTagValue("url",element);
				 
				 if(stream.equals("Invalid url") == false)
				 {
					 Stream url = new Stream(name,stream,"xx","xx",true);
					 urlList.add(url);
					 radioNames.put(name, i);
				 }
				 
			 }
		 }
	 }
	 
	 String getTagValue(String tag, Element element)
	 {
	    NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
	    Node node = (Node) list.item(0);
	    if(node == null && tag.equals("name") == true)
	    	return "Unknown Radio";
	    if(node == null && tag.equals("url") == true)
	    	return "Invalid url";
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
	 
	 ArrayList<Stream> returnPlaylist()
	 {
		 return playlist;
	 }
	 
	 Stream findRadioStation(String name)
	 {
		 int i ;
		 if(radioNames.get(name) != null)
			 i = radioNames.get(name);
		 else
			 return null;
		 
		 Stream object = urlList.get(i);
		 return object;
	 }
	 
	 void savePlaylist()
	 {
		 try
		 {
			 BufferedWriter writer = new BufferedWriter(new FileWriter("Playlist.txt"));
			 int i = 0;
			 while(i < playlist.size())
			 {
				 writer.write(playlist.get(i).getName() + "," + playlist.get(i).getURL() + "," + playlist.get(i).getCountry() + "," + playlist.get(i).getGenre() + "," + playlist.get(i).isRange());
				 writer.newLine();
				 i++;
			 }
			 writer.close();
		 }
		 catch(Exception e)
		 {
			 System.err.println(e.getLocalizedMessage());
		 }
		 playlist = new ArrayList<Stream>() ;
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
	 
	 void printPlaylist()
	 {
		 for(int i = 0;i < playlist.size();i++)
		 {
			 System.out.println(playlist.get(i).getName());
		 }
	 }
	 
	 public ArrayList<Stream> returnList()
	 {
		 return urlList;
	 }
}
