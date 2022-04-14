package com.example.finnkinoapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieSearch {

    private ArrayList<Movie> movies = new ArrayList<>();
    private String urlString = "https://www.finnkino.fi/xml/Schedule/";
    private String reformattedStr;


    public int movieAmount() {
        return(movies.size());
    }

    public void setMovie(Movie thre){
        try {
            if (thre != null) {
                movies.add( thre );
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Movie getMovie(int i) {
        return(this.movies.get( i ));
    }

    public ArrayList<Movie> getMovieList() {
        return(this.movies);
    }

    // read
    public void readMoviesXML(int theatreID, String date) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString + "?area=" + theatreID +"&dt=" + date);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName( "Show" );

            for (int i = 0; i < nList.getLength() ; i++) {
                Node node = nList.item( i );
                System.out.println("Element is " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");

                    // asetetaan tiedot omaan luokkaan
                    Movie thre = new Movie();
                    thre.setID(Integer. valueOf(element.getElementsByTagName( "ID" ).item(0).getTextContent()));
                    thre.setTitle(element.getElementsByTagName( "Title" ).item(0).getTextContent());

                    // luetaan ja parsitaan aika
                    try {
                        reformattedStr = myFormat.format(fromUser.parse(element.getElementsByTagName( "dttmShowStart" ).item(0).getTextContent()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    thre.setShowStart(reformattedStr);


                    //System.out.println("Movie is " + thre.getTitle()); // --debug
                    System.out.println(element.getElementsByTagName( "Title" ).item(0).getTextContent()); // --debug

                    setMovie(thre);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (SAXException e) {
            e.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        }   finally {
            System.out.println("#######MOVIES DONE#######");
        }

    }
}
