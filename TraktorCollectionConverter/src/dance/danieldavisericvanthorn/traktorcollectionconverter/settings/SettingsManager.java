package dance.danieldavisericvanthorn.traktorcollectionconverter.settings;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class SettingsManager {

	private static String TraktorSettingsPath = "";

	private SettingsManager() {

	}

	public static void loadInternalSettings() throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("settings/settings.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		// optional, but recommended
		// read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();

		Element originalTSI = doc.getElementById("originalTSIPath");
		TraktorSettingsPath = originalTSI.getTextContent();

	}

	public static String getTraktorSettingsPath() {
		return TraktorSettingsPath;
	}

}
