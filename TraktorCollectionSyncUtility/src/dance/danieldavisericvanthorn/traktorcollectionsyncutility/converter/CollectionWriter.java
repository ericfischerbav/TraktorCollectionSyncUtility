package dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter;

import java.io.File;
import java.io.IOException;

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

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.exceptions.TCSUException;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.enums.ErrorCase;

public class CollectionWriter {

	private CollectionWriter() {
	}

	public static void changeFilePaths()
			throws TransformerException, SAXException, IOException, ParserConfigurationException, TCSUException {
		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// use the factory to take an instance of the document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(InternalSettingsManager.getTargetTraktorPath(TraktorFileType.COLLECTION));

		Element nml = dom.getDocumentElement();
		NodeList content = nml.getChildNodes();
		Node collection = null;
		for (int i = 0; i <= content.getLength() - 1; i++) {
			Node element = content.item(i);
			if (element != null && element.getNodeType() == Node.ELEMENT_NODE
					&& element.getNodeName().equals("COLLECTION")) {
				collection = element;
			}
		}
		if (collection == null) {
			throw new TCSUException(ErrorCase.PARSE_ERROR);
		}
		NodeList collectionEntries = collection.getChildNodes();

		for (int element = 0; element <= collectionEntries.getLength(); element++) {
			Node entry = collectionEntries.item(element);
			if (entry != null && entry.getNodeType() == Node.ELEMENT_NODE && entry.getNodeName().equals("ENTRY")) {
				NodeList entryNodes = entry.getChildNodes();
				for (int en = 0; en <= entryNodes.getLength() - 1; en++) {
					Node subitem = entryNodes.item(en);
					if (subitem != null && subitem.getNodeType() == Node.ELEMENT_NODE
							&& subitem.getNodeName().equals("LOCATION")) {
						NamedNodeMap attr = subitem.getAttributes();
						Node dir = attr.getNamedItem("DIR");
						Node volume = attr.getNamedItem("VOLUME");
						Node file = attr.getNamedItem("FILE");
						
						StringBuilder pathBuilder = new StringBuilder();
						pathBuilder.append(dir.getNodeValue());
						pathBuilder.append(volume.getNodeValue());
						pathBuilder.append(file.getNodeValue());

					}
				}
			}
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dom);
		StreamResult result = new StreamResult(
				new File(InternalSettingsManager.getTargetTraktorPath(TraktorFileType.COLLECTION)));

		transformer.transform(source, result);
	}

}
