package com.example.finnkinoapp.ui.Movie;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieSearch {
    private ArrayList<Movie> movies = new ArrayList<>();
    private String urlScheduleString = "https://www.finnkino.fi/xml/Schedule/";
    private String reformattedStr;
    private SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private SimpleDateFormat myFormat = new SimpleDateFormat("HH:mm");

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

    // read Finnkino schedule XML by search criteria
    public void readMoviesXML(int theatreID, String date, String time) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlScheduleString + "?area=" + theatreID +"&dt=" + date);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName( "Show" );

            for (int i = 0; i < nList.getLength() ; i++) {
                Node node = nList.item( i );

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // set data to movie-class
                    Movie mve = new Movie();
                    ImdbApiReader imdb = new ImdbApiReader();
                    mve.setID(Integer. valueOf(element.getElementsByTagName( "ID" ).item(0).getTextContent()));
                    mve.setTitle(element.getElementsByTagName( "Title" ).item(0).getTextContent());
                    mve.setOriginalTitle(element.getElementsByTagName( "OriginalTitle" ).item(0).getTextContent());
                    // System.out.println(mve.getOriginalTitle()); -- for debugging


                    // read and parse of time data
                    try {
                        reformattedStr = myFormat.format(fromUser.parse(element.getElementsByTagName( "dttmShowStart" ).item(0).getTextContent()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    mve.setShowStart(reformattedStr);

                    try {
                        Date d1 = myFormat.parse(reformattedStr);
                        Date d2 = myFormat.parse(time);

                        if (d1.compareTo(d2) > 0) {
                            setMovie(mve);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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
