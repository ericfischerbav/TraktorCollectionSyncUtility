package dance.danieldavisericvanthorn.traktorcollectionconverter.settings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionconverter.enums.TraktorFileType;

public class InternalSettingsManager {

	// needed for xml parsing
	private static Document dom;
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder db;

	private static Map<TraktorFileType, String> filePaths = new HashMap<>();

	private InternalSettingsManager() {

	}

	public static void loadInternalSettings() throws ParserConfigurationException, SAXException, IOException {
		// use the factory to take an instance of the document builder
		if (db == null) {
			db = dbf.newDocumentBuilder();
		}
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse("settings/settings.xml");

		Element tcc = dom.getDocumentElement();
		Node original = tcc.getChildNodes().item(0);
		NodeList originalNodes = original.getChildNodes();
		for (int i = 0; i <= originalNodes.getLength() - 1; i++) {
			Node element = originalNodes.item(i);
			if (element.getNodeName().equals("TSI")) {
				setTraktorPath(TraktorFileType.SETTINGS, element.getTextContent());
			}
		}

	}

	public static void writeSettings() throws ParserConfigurationException, SAXException, IOException {
		if (dom == null) {
			loadInternalSettings();
		}

		Element tcc = dom.getDocumentElement();
		Node original = tcc.getChildNodes().item(0);
		NodeList originalNodes = original.getChildNodes();
		for (int i = 0; i <= originalNodes.getLength() - 1; i++) {
			Node element = originalNodes.item(i);
			if (element.getNodeName().equals("TSI")) {
				element.setTextContent(getTraktorPath(TraktorFileType.SETTINGS));
			}
		}

		System.out.println("Settings stored.");

	}

	public static String getTraktorPath(TraktorFileType type) {
		String path = filePaths.get(type);
		if (path != null) {
			return path;
		} else {
			return "";
		}
	}

	public static void setTraktorPath(TraktorFileType type, String path) {
		filePaths.put(type, path);
	}

}
