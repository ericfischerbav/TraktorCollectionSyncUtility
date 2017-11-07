package dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.model.Folder;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.exceptions.TCSUException;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.enums.ErrorCase;

public class CollectionParser {

	private String filepath;

	private Set<Folder> musicFolderPaths = new LinkedHashSet<Folder>();

	public CollectionParser(String filepath)
			throws ParserConfigurationException, SAXException, IOException, TCSUException {
		this.filepath = filepath.replaceAll(Matcher.quoteReplacement("\\"), Matcher.quoteReplacement("\\\\"));

		Document dom;
		// Make an instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// use the factory to take an instance of the document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(this.filepath);

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
						Node volume = attr.getNamedItem("VOLUME");
						Node dir = attr.getNamedItem("DIR");

						Folder thisFolder = new Folder(volume.getNodeValue() + dir.getNodeValue());

						if (!musicFolderPaths.contains(thisFolder)) {
							musicFolderPaths.add(thisFolder);
						}
					}
				}
			}
		}

		for (Folder folder : musicFolderPaths) {
			System.out.println(folder.toString());
		}
	}

	public void normalizeMusicFolders() {

	}

	public Set<Folder> getMusicFolders() {
		return musicFolderPaths;
	}

	public List<String> getMusicFolderPaths() {
		List<String> musicFolderPaths = new ArrayList<>();
		for (Folder folder : this.musicFolderPaths) {
			musicFolderPaths.add(folder.toString());
		}
		return musicFolderPaths;
	}

}
