package com.example.finnkinoapp.ui.Favorites;

import android.content.Context;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.util.Xml;

import androidx.annotation.NonNull;

import com.example.finnkinoapp.ui.Movie.GalleryFragment;
import com.example.finnkinoapp.ui.Movie.Movie;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class HandleFavXML {

    private String xmlFilePath;
    private Favorites favorites;
    private ArrayList<Favorites> favoritesArrayList;


    public void writeXML(Context context, Favorites favorites) throws FileNotFoundException {

        try {
            // setting xml-path
            xmlFilePath = context.getFilesDir() + "/" + "favorites.xml";

            // initializing document
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("favorites");
            document.appendChild(root);

            // favorite element
            Element favorite = document.createElement("favorite");
            root.appendChild(favorite);

            // set an ID attribute to favorite element
            Attr attr = document.createAttribute("ID");
            attr.setValue("10"); // set favorite ID
            favorite.setAttributeNode(attr);

            // user element
            Element firstName = document.createElement("user");
            firstName.appendChild(document.createTextNode( favorites.getUser() )); // set user
            favorite.appendChild(firstName);

            // favorite1 element
            Element favorite1 = document.createElement("favorite1");
            favorite1.appendChild(document.createTextNode( favorites.getFavorite1() ));
            favorite.appendChild(favorite1);

            // favorite2 element
            Element favorite2 = document.createElement("favorite2");
            favorite2.appendChild(document.createTextNode( favorites.getFavorite2() ));
            favorite.appendChild(favorite2);

            // favorite3 element
            Element favorite3 = document.createElement("favorite3");
            favorite3.appendChild(document.createTextNode( favorites.getFavorite3() ));
            favorite.appendChild(favorite3);

            // favorite4 element
            Element favorite4 = document.createElement("favorite4");
            favorite4.appendChild(document.createTextNode( favorites.getFavorite4() ));
            favorite.appendChild(favorite4);

            // favorite5 element
            Element favorite5 = document.createElement("favorite5");
            favorite5.appendChild(document.createTextNode( favorites.getFavorite5() ));
            favorite.appendChild(favorite5);


            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } finally {
            System.out.println( "##########FAVORITES WRITE SUCCESSFUL##########" ); // --debug
        }
    }


    public ArrayList<Favorites> readXML(@NonNull Context context) {
        favoritesArrayList = new ArrayList<Favorites>(); // initializing arraylist for favorites

        try {
            InputStream inputstream = new FileInputStream(context.getFilesDir() + "/" + "favorites.xml");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse( inputstream );
            doc.getDocumentElement().normalize();
            //System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName( "favorite" );

            for (int i = 0; i < nList.getLength() ; i++) {
                Node node = nList.item( i );

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // setting new favorites
                    favorites = new Favorites();
                    favorites.setUser( element.getElementsByTagName( "user" ).item(0).getTextContent() );
                    favorites.setFavorite1( element.getElementsByTagName( "favorite1" ).item(0).getTextContent() );
                    favorites.setFavorite2( element.getElementsByTagName( "favorite2" ).item(0).getTextContent() );
                    favorites.setFavorite3( element.getElementsByTagName( "favorite3" ).item(0).getTextContent() );
                    favorites.setFavorite4( element.getElementsByTagName( "favorite4" ).item(0).getTextContent() );
                    favorites.setFavorite5( element.getElementsByTagName( "favorite5" ).item(0).getTextContent() );
                    favoritesArrayList.add(favorites);


                    System.out.println(element.getElementsByTagName( "user" ).item(0).getTextContent()); // -debug
                    System.out.println(element.getElementsByTagName( "favorite1" ).item(0).getTextContent()); // -debug
                    System.out.println(element.getElementsByTagName( "favorite2" ).item(0).getTextContent()); // -debug
                    System.out.println(element.getElementsByTagName( "favorite3" ).item(0).getTextContent()); // -debug
                    System.out.println(element.getElementsByTagName( "favorite4" ).item(0).getTextContent()); // -debug
                    System.out.println(element.getElementsByTagName( "favorite5" ).item(0).getTextContent()); // -debug

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (SAXException e) {
            e.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        } finally {
            System.out.println( "##########FAVORITES READ SUCCESSFUL##########" );
        }

        return(favoritesArrayList);
    }
}