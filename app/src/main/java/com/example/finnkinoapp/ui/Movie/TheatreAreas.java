package com.example.finnkinoapp.ui.Movie;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TheatreAreas {

    private static TheatreAreas instance = null;
    private ArrayList<Theatre> theatres;
    private String urlString = "https://www.finnkino.fi/xml/TheatreAreas/";
    private TheatreAreas(){
        theatres = new ArrayList<Theatre>();
    }

    public static TheatreAreas getInstance() {
        if (instance == null) {
            instance = new TheatreAreas();
        }
        return(instance);
    }

    public int theatreAmount() {
        return(theatres.size());
    }

    public void setTheatre(Theatre thre){
        theatres.add(thre);
    }

    public Theatre getTheatre(int i) {
        return(this.theatres.get( i ));
    }

    public void readTheatreAreasXML() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getDocumentElement().getElementsByTagName( "TheatreArea" );

            for (int i = 0; i < nList.getLength() ; i++) {
                Node node = nList.item( i );
                System.out.println("Element is " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // asetetaan tiedot omaan luokkaan
                    Theatre thre = new Theatre();
                    thre.setID(Integer. valueOf(element.getElementsByTagName( "ID" ).item(0).getTextContent()));
                    thre.setName(element.getElementsByTagName( "Name" ).item(0).getTextContent());
                    setTheatre(thre);

                    System.out.println("Theatre ID is " + thre.getID()); // --debug
                    //System.out.println(element.getElementsByTagName( "Name" ).item(0).getTextContent()); // --debug

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (SAXException e) {
            e.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        }   finally {
            System.out.println("#######THEATRES DONE#######");
        }

    }

    public static int getTheatreID(int listID){
        int theatreID;

        if (listID == 1) {
            theatreID = 1014;
        }
        else if (listID == 2) {
            theatreID = 1012;
        }
        else if (listID == 3) {
            theatreID = 1039;
        }
        else if (listID == 4) {
            theatreID = 1038;
        }
        else if (listID == 5) {
            theatreID = 1002;
        }
        else if (listID == 6) {
            theatreID = 1045;
        }
        else if (listID == 7) {
            theatreID = 1031;
        }
        else if (listID == 8) {
            theatreID = 1032;
        }
        else if (listID == 9) {
            theatreID = 1033;
        }
        else if (listID == 10) {
            theatreID = 1013;
        }
        else if (listID == 11) {
            theatreID = 1015;
        }
        else if (listID == 12) {
            theatreID = 1016;
        }
        else if (listID == 13) {
            theatreID = 1017;
        }
        else if (listID == 14) {
            theatreID = 1041;
        }
        else if (listID == 15) {
            theatreID = 1018;
        }
        else if (listID == 16) {
            theatreID = 1019;
        }
        else if (listID == 17) {
            theatreID = 1021;
        }
        else if (listID == 18) {
            theatreID = 1034;
        }
        else if (listID == 19) {
            theatreID = 1035;
        }
        else if (listID == 20) {
            theatreID = 1022;
        }
        else {
            theatreID = -1;
            System.out.println("List ID does not match with any theatre ID");
        }

        return(theatreID);
    }

}
