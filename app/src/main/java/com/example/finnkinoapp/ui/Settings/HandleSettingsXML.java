package com.example.finnkinoapp.ui.Settings;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.finnkinoapp.ui.Favorites.Favorites;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class HandleSettingsXML {
    private static HandleSettingsXML handleSettingsXML = null;
    private String xmlFilePath;
    private Settings settings;
    private ArrayList<Settings> settingsArrayList;

    private HandleSettingsXML(){}

    public static HandleSettingsXML getInstance() {
        if (handleSettingsXML == null)
            handleSettingsXML = new HandleSettingsXML();
        return handleSettingsXML;
    }

    public void writeSetXML(Context context, Settings settings) throws FileNotFoundException {
        try {
            // setting xml-path
            xmlFilePath = context.getFilesDir() + "/" + "settings.xml";

            File yourFile = new File(xmlFilePath);
            yourFile.createNewFile(); // if file already exists will do nothing
            FileOutputStream oFile = new FileOutputStream(yourFile, false);

            // initializing document
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("settings");
            document.appendChild(root);

            // favorite element
            Element setting = document.createElement("setting");
            root.appendChild(setting);

            // set an ID attribute to favorite element
            Attr attr = document.createAttribute("ID");
            attr.setValue("10"); // set settings ID
            setting.setAttributeNode(attr);

            // user element
            Element language = document.createElement("language");
            language.appendChild(document.createTextNode( settings.getLanguage() ));
            setting.appendChild(language);

            // favorite1 element
            Element theme = document.createElement("theme");
            theme.appendChild(document.createTextNode( String.valueOf( settings.getTheme() ) ));
            setting.appendChild(theme);


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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println( "##########SETTINGS WRITE SUCCESSFUL##########" ); // --debug
        }
    }


    public Settings readSetXML(@NonNull Context context) {
        settingsArrayList = new ArrayList<>(); // initializing arraylist for favorites

        try {
            InputStream inputstream = new FileInputStream(context.getFilesDir() + "/" + "settings.xml");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse( inputstream );
            doc.getDocumentElement().normalize();
            //System.out.println("Root element: " + doc.getDocumentElement().getNodeName()); // --debug

            NodeList nList = doc.getDocumentElement().getElementsByTagName( "setting" );

            for (int i = 0; i < nList.getLength() ; i++) {
                Node node = nList.item( i );

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // setting new favorites
                    settings = Settings.getInstance();
                    if (element.getElementsByTagName( "language" ).item(0).getTextContent() == null) {
                        settings.setLanguage( "English" );
                    }
                    else {
                        settings.setLanguage( element.getElementsByTagName( "language" ).item(0).getTextContent() );
                    }

                    if (Boolean.valueOf( element.getElementsByTagName( "theme" ).item(0).getTextContent() ) == null) {
                        settings.setTheme( false );
                    }
                    else {
                        settings.setTheme( Boolean.valueOf( element.getElementsByTagName( "theme" ).item(0).getTextContent() ) );
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } catch (SAXException e) {
            e.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();

        } finally {
            System.out.println( "##########SETTINGS READ SUCCESSFUL##########" ); // --debug
        }

        return(settings);
    }
}