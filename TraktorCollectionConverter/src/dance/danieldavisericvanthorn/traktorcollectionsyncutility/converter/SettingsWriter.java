package dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorDirectories;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;

public class SettingsWriter {

	private SettingsWriter() {

	}

	public static void updateTSIFile(String tsiPath) throws TransformerException {
		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(tsiPath);

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
					updateRelevantValue(entryName, entryAttributes);
				}
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(dom);
			StreamResult result = new StreamResult(
					new File(InternalSettingsManager.getTargetTraktorPath(TraktorFileType.SETTINGS)));

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (SAXException se) {
			System.out.println(se.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}

	private static void updateRelevantValue(Node entryName, NamedNodeMap entryAttributes) {
		switch (entryName.getNodeValue()) {
		case SettingsParser.ContentContentImportRootID:
			entryAttributes.item(2)
					.setNodeValue(InternalSettingsManager.getTargetDirecory(TraktorDirectories.REMIXSETS).get(0));
			break;
		case SettingsParser.ITunesMusicID:
			entryAttributes.item(2)
					.setNodeValue(InternalSettingsManager.getTargetDirecory(TraktorDirectories.ITUNES).get(0));
			break;
		case SettingsParser.LoopsID:
			entryAttributes.item(2)
					.setNodeValue(InternalSettingsManager.getTargetDirecory(TraktorDirectories.LOOPS).get(0));
			break;
		case SettingsParser.MusicID:
			List<String> values = InternalSettingsManager.getTargetDirecory(TraktorDirectories.MUSIC);
			StringBuilder sb = new StringBuilder();
			for (String path : values) {
				sb.append(path);
				sb.append(";");
			}
			entryAttributes.item(2).setNodeValue(sb.toString());
			break;
		case SettingsParser.RecordingsID:
			entryAttributes.item(2)
					.setNodeValue(InternalSettingsManager.getTargetDirecory(TraktorDirectories.RECORDINGS).get(0));
			break;
		case SettingsParser.RootID:
			entryAttributes.item(2)
					.setNodeValue(InternalSettingsManager.getTargetDirecory(TraktorDirectories.ROOT).get(0));
			break;
		default:
			break;
		}
	}

}
