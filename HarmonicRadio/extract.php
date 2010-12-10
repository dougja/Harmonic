<html><head><title>Harmonic Stream Manager</title>
<style type="text/css">
@charset "utf-8";
body {
  font-family: Tahoma, Verdana, Geneva, Arial, helvetica, sans-serif;
  font-size:12px;
}

td {
	font-family: Tahoma, Verdana, Geneva, Arial, helvetica, sans-serif;
  font-size:12px;
}

th {
	font-family: Tahoma, Verdana, Geneva, Arial, helvetica, sans-serif;
  font-size:12px;
}
</style>
</head>
<body>
<h1>Harmonic Stream Manager</h1>
<p><br>
<a href="extract.php">Crawl a webpage</a></p>
<p><a href="viewdb.php">View database contents</a></p><br />
<hr />
<?php
ini_set( "display_errors", 0);
if (!$_POST['url']) {
  echo("<h2>Enter the URL of a webpage to extract streams from:</h2><form action='extract.php' method='post'><input type='text' name='url'> <input type='submit' value='Go'></form><br><br>");

} else {
  include 'dbconnect.php';
  $url=$_POST['url'];

  function get_remote($urlpage,$getwhat) {
          $dom = new DOMDocument();
          if($dom->loadHTMLFile($urlpage)) {
              
              $list = $dom->getElementsByTagName($getwhat);
              if ($list->length > 0) {
                  return $list->item(0)->textContent;
              }
          }
  }
  
  $handle = fopen($url,"r");
  $contents = stream_get_contents($handle);
  fclose($handle);

  $contents = @file_get_contents($url) or die("Could not access url: $url"); 

  $regexp = "<a\s[^>]*href=(\"??)([^\" >]*?)\\1[^>]*>(.*)<\/a>"; 
  preg_match_all("/$regexp/siU", $contents, $matches, PREG_SET_ORDER);

  echo("The following streams have been added:<br><br><table>");
  echo("<tr><td>Stream Name</td><td>URL</td></tr>");

  foreach($matches as $match) {

    $url=$match[2];
    
    if (
    strpos($url,".asx")==true || 
    strpos($url,".m3u")==true || 
    strpos($url,".ram")==true || 
    strpos($url,".rm")==true ){
      $name=get_remote($match[2],"title");
      if ($name=='') $name=get_remote($match[2],"author");
      
      echo("<tr><td>$name</td><td>$url</td></tr>\n");
      
      $sql = "SELECT * FROM streams WHERE url='$url'";
      $result = mysql_query($sql, $connection) or die(mysql_error());
      $rowurl = mysql_fetch_array($result);
      
      $sql = "SELECT * FROM streams WHERE name='$name'";
      $result = mysql_query($sql, $connection) or die(mysql_error());
      $rowname = mysql_fetch_array($result);
      $nameindb = trim($rowname['name']);
      
      if ($rowurl!='') {
        if ($nameindb!=$name) {
          $sql = "UPDATE streams SET name='$name' WHERE url='$url'";
          $result = mysql_query($sql, $connection) or die(mysql_error());
        }
      } else {
        $sql = "INSERT INTO streams (name, url) VALUES ('$name','$url')";
        $result = mysql_query($sql, $connection) or die(mysql_error());
      }
    }
  }

  echo("</table>");
}
?>

</body>
</html>