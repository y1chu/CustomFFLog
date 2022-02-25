import javax.swing.text.Document;
import java.net.*;
import java.io.*;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        // <div id="graph-and-table-container">
        String page = "http://www.something.com/";
        //Connecting to the web page
        Connection conn = Jsoup.connect(page);
        //executing the get request
        Document doc = conn.get();
        //Retrieving the contents (body) of the web page
        String result = doc.body().text();
        System.out.println(result);




    }


}



