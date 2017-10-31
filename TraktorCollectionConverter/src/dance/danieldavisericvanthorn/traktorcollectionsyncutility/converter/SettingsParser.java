package dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SettingsParser {

	public static final String ContentContentImportRootID = "Browser.Dir.ContentImportRoot";
	public static final String ITunesMusicID = "Browser.Dir.ITunesMusic";
	public static final String LoopsID = "Browser.Dir.Loops";
	public static final String MusicID = "Browser.Dir.Music";
	public static final String RecordingsID = "Browser.Dir.Recordings";
	public static final String RootID = "Browser.Dir.Root";

	private String traktorSettingsPath;

	private String ContentImportRoot;
	private String ITunesMusic;
	private String Loops;
	private List<String> Music = new ArrayList<>();
	private String Recordings;
	private String Root;

	public SettingsParser(String traktorSettingsPath) {
		this.traktorSettingsPath = traktorSettingsPath;
		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(traktorSettingsPath);

			Element nixml = dom.getDocumentElement();
			Node traktorSettings = nixml.getChildNodes().item(0);
			NodeList entryList = traktorSettings.getChildNodes();

			for (int element = 0; element <= entryList.getLength(); element++) {
				Node entry = entryList.item(element);
				NamedNodeMap entryAttributes = null;
				if (entry != null) {
					entryAttributes = entry.getAttributes();
				}
				Node entryName = null;
				if (entryAttributes != null) {
					entryName = entryAttributes.item(0);
				}
				if (entryName != null) {
					getRelevantValue(entryName, entryAttributes);
				}
			}

		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (SAXException se) {
			System.out.println(se.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	private void getRelevantValue(Node entryName, NamedNodeMap entryAttributes) {
		switch (entryName.getNodeValue()) {
		case ContentContentImportRootID:
			ContentImportRoot = notNullableValue(entryAttributes);
			break;
		case ITunesMusicID:
			ITunesMusic = notNullableValue(entryAttributes);
			break;
		case LoopsID:
			Loops = notNullableValue(entryAttributes);
			break;
		case MusicID:
			String musicString = notNullableValue(entryAttributes);
			String[] splittedMusicString = musicString.split(";");
			for (String string : splittedMusicString) {
				Music.add(string);
			}
			break;
		case RecordingsID:
			Recordings = notNullableValue(entryAttributes);
			break;
		case RootID:
			Root = notNullableValue(entryAttributes);
			break;
		default:
			break;
		}
	}

	private String notNullableValue(NamedNodeMap attr) {
		String output = attr.item(2).getNodeValue();
		if (output == null) {
			return "";
		}
		return output;
	}

	public String getSettingsPath() {
		return traktorSettingsPath;
	}

	public String getContentImportRoot() {
		return ContentImportRoot;
	}

	public String getITunesMusic() {
		return ITunesMusic;
	}

	public String getLoops() {
		return Loops;
	}

	public List<String> getMusicFolders() {
		return Music;
	}

	public String getRecordings() {
		return Recordings;
	}

	public String getRoot() {
		return Root;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><p>");
		sb.append("From file ");
		sb.append(traktorSettingsPath);
		sb.append(" got the following paths:\n");
		sb.append("Remix Sets: ");
		sb.append(ContentImportRoot);
		sb.append("<br>");
		sb.append("ITunes: ");
		sb.append(ITunesMusic);
		sb.append("<br>");
		sb.append("Loops: ");
		sb.append(Loops);
		sb.append("<br>");
		sb.append("Music folders: ");
		for (String string : Music) {
			sb.append(string);
			sb.append(" ");
		}
		sb.append("<br>");
		sb.append("Recordings folder: ");
		sb.append(Recordings);
		sb.append("<br>");
		sb.append("Root directory: ");
		sb.append(Root);
		sb.append("</p></html>");
		return sb.toString();
	}

}
