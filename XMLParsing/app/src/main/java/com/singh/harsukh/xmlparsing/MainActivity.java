package com.singh.harsukh.xmlparsing;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_URL = "http://vivianaranha.com/Users.xml";
    public static final String SERVER_URL_B = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%2720770%27%20and%20query%3D%27pizza%27";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //DownloadXMLData dxd = new DownloadXMLData();
        //dxd.execute();
        XML_PARSE xp = new XML_PARSE();
        xp.execute();
    }


    //pulling via XML parser class

    public class XML_PARSE extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL local_url = new URL(SERVER_URL_B);
                XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
                xppf.setNamespaceAware(true); //set the namespace of object
                XmlPullParser xpp = xppf.newPullParser();
                xpp.setInput(local_url.openConnection().getInputStream(), "UTF_8");

                //since xpp is event based event type needs to be checked
                int eventType = xpp.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        //find the starting of the tag
                        if (xpp.getName().equalsIgnoreCase("title")) {
                            System.out.println("Pizza: " + xpp.nextText().toString());
                        }
                    }
                    eventType = xpp.next();
                }
            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(XmlPullParserException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
    //pulling via document builder
    public class DownloadXMLData extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL local_url = new URL(SERVER_URL);
                //document building starts here
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new InputSource(local_url.openStream()));
                doc.getDocumentElement().normalize();

                //getting the nodes of the XML (tags)
                //takes the tag of the element to retrieve a list of
                NodeList nodeList = doc.getElementsByTagName("Fruit");
                for (int i = 0; i < nodeList.getLength(); ++i)
                {
                    Node node = nodeList.item(i);
                    //cast the node into an element to retrieve the data
                    Element element = (Element)node;
                    NodeList data_list = element.getElementsByTagName("name");
                    Element element_name = (Element)data_list.item(0);
                    data_list = element_name.getChildNodes();
                    System.out.println("Fruit: " + data_list.item(0).getNodeValue());
                }
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(ParserConfigurationException e)
            {
                e.printStackTrace();
            }
            catch (SAXException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }
}
