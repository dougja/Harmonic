package com.harmonic.harmonicRadio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToDatabase 
{
  private Connection conn = null;
  private String name = "default";
  private String url;
  private String country = "default";
  private String genre = "default";
  private String song_name = "default";
  private Boolean range = true;
  private Statement stmt = null;
  private ResultSet result = null;
  private String db_url;
  private String username;
  private String password;
  
  ConnectToDatabase(String db_url, String username, String password)
  {
      this.db_url = db_url;
      this.username = username; 
      this.password = password;
  }
  void establishConnection()
  {
	  try
	  {
		  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  conn = DriverManager.getConnection(db_url,username,password);	  
	  }
	  catch (ClassNotFoundException ex) {System.err.println(ex.getMessage());}
      catch (IllegalAccessException ex) {System.err.println("illegal access");}
      catch (InstantiationException ex) {System.err.println("instantiation error");}
      catch (SQLException ex)           {System.err.println(ex.getMessage());}
  }
  
  URLObject search(int index)
  {
	  URLObject url_object = null;
	  try
	  {
		  conn.commit();
		  conn.setAutoCommit(true);
		  stmt = conn.createStatement();
		  result = stmt.executeQuery("SELECT * FROM streams WHERE id='107'");
		  url = result.getString(1);
		  url_object = new URLObject(name,url,country,genre,range,song_name);
	  }
	  catch(SQLException e)
	  {
		  System.out.println(e);
	  }
		  
	  return url_object;	  
		  
  }
}
