import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.*;

public class ConnectToDatabase 
{
	 static NodeList nodes = null;
	 private File xml = null;
	 private DocumentBuilderFactory factory = null;
	 private DocumentBuilder builder = null;
	 private Document doc = null;
	 private String fileName = null;
	 ArrayList<URLObject> urlList = null;
	 
	 public ConnectToDatabase(String filename)
	 {
		 fileName = filename;
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
	 void createURLList()
	 {
		 urlList = new ArrayList<URLObject>();
		 for(int i = 0; i < nodes.getLength(); i++)
		 {
			 Node node = nodes.item(i);
			 if (node.getNodeType() == Node.ELEMENT_NODE)
			 {
				 Element element = (Element) node;
				 String urlName = getTagValue("url",element);
				 URLObject url = new URLObject("xx",urlName,"xx","xx",true,"xx");
				 urlList.add(url);
				 
			 }
		 }
	 }
	 private static String getTagValue(String tag, Element element)
	 {
	    NodeList list = element.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) list.item(0);	 
	    return nValue.getNodeValue();    
	 }
	 
	}
