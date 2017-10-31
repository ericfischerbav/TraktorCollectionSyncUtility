package dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.SettingsParser;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorDirectories;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;

public class InternalSettingsManager {

	private static final String file = "settings/settings.xml";
	// needed for xml parsing
	private static Document dom;
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder db;
	private static Element tcc;

	private static Map<TraktorFileType, String> filePaths = new HashMap<>();
	private static Map<TraktorDirectories, List<String>> originalDirectories = new HashMap<>();
	private static Map<TraktorDirectories, List<String>> targetDirectories = new HashMap<>();

	private InternalSettingsManager() {

	}

	public static void loadInternalSettings() throws ParserConfigurationException, SAXException, IOException {
		// use the factory to take an instance of the document builder
		if (db == null) {
			db = dbf.newDocumentBuilder();
		}
		// parse using the builder to get the DOM mapping of the
		// XML file
		dom = db.parse(file);

		tcc = dom.getDocumentElement();
		NodeList originalNode = tcc.getElementsByTagName("original");
		for (int i = 0; i <= originalNode.getLength() - 1; i++) {
			Node element = originalNode.item(i);
			if (element.getNodeType() == Node.ELEMENT_NODE) {
				NodeList originalChildNodes = element.getChildNodes();
				for (int a = 0; a <= originalChildNodes.getLength(); a++) {
					Node childElement = originalChildNodes.item(a);
					if (childElement != null && childElement.getNodeType() == Node.ELEMENT_NODE) {
						if (childElement.getNodeName().equals("path")) {
							if (((Element) childElement).getAttribute("name").equals("TSI")) {
								setTraktorPath(TraktorFileType.SETTINGS, childElement.getTextContent());
							} else if (((Element) childElement).getAttribute("name").equals("NML")) {
								setTraktorPath(TraktorFileType.COLLECTION, childElement.getTextContent());
							} else if (((Element) childElement).getAttribute("name").equals("root")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setOriginalDirectory(TraktorDirectories.ROOT, elements);
							} else if (((Element) childElement).getAttribute("name").equals("recordings")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setOriginalDirectory(TraktorDirectories.RECORDINGS, elements);
							} else if (((Element) childElement).getAttribute("name").equals("remixsets")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setOriginalDirectory(TraktorDirectories.REMIXSETS, elements);
							} else if (((Element) childElement).getAttribute("name").equals("itunes")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setOriginalDirectory(TraktorDirectories.ITUNES, elements);
							} else if (((Element) childElement).getAttribute("name").equals("loops")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setOriginalDirectory(TraktorDirectories.LOOPS, elements);
							}
						} else if (childElement.getNodeName().equals("collection")
								&& ((Element) childElement).getAttribute("name").equals("music")) {
							NodeList musicFolders = childElement.getChildNodes();
							List<String> musicPaths = new ArrayList<>();
							for (int m = 0; m <= musicFolders.getLength(); m++) {
								Node musicFolderElement = musicFolders.item(m);
								if (musicFolderElement != null && musicFolderElement.getNodeType() == Node.ELEMENT_NODE
										&& musicFolderElement.getNodeName().equals("path")) {
									musicPaths.add(musicFolderElement.getTextContent());
								}
							}
							setOriginalDirectory(TraktorDirectories.MUSIC, musicPaths);
						}
					}
				}
			}
		}

		NodeList targetNode = tcc.getElementsByTagName("target");
		for (int i = 0; i <= originalNode.getLength() - 1; i++) {
			Node element = targetNode.item(i);
			if (element.getNodeType() == Node.ELEMENT_NODE) {
				NodeList targetChildNodes = element.getChildNodes();
				for (int a = 0; a <= targetChildNodes.getLength(); a++) {
					Node childElement = targetChildNodes.item(a);
					if (childElement != null && childElement.getNodeType() == Node.ELEMENT_NODE) {
						if (childElement.getNodeName().equals("path")) {
							// if (((Element)
							// childElement).getAttribute("name").equals("TSI"))
							// {
							// setTraktorPath(TraktorFileType.SETTINGS,
							// childElement.getTextContent());
							// } else if (((Element)
							// childElement).getAttribute("name").equals("NML"))
							// {
							// setTraktorPath(TraktorFileType.COLLECTION,
							// childElement.getTextContent());
							// } else if (((Element)
							// childElement).getAttribute("name").equals("root"))
							// {
							if (((Element) childElement).getAttribute("name").equals("root")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setTargetDirectory(TraktorDirectories.ROOT, elements);
							} else if (((Element) childElement).getAttribute("name").equals("recordings")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setTargetDirectory(TraktorDirectories.RECORDINGS, elements);
							} else if (((Element) childElement).getAttribute("name").equals("remixsets")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setTargetDirectory(TraktorDirectories.REMIXSETS, elements);
							} else if (((Element) childElement).getAttribute("name").equals("itunes")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setTargetDirectory(TraktorDirectories.ITUNES, elements);
							} else if (((Element) childElement).getAttribute("name").equals("loops")) {
								List<String> elements = new ArrayList<>();
								elements.add(childElement.getTextContent());
								setTargetDirectory(TraktorDirectories.LOOPS, elements);
							}
						} else if (childElement.getNodeName().equals("collection")
								&& ((Element) childElement).getAttribute("name").equals("music")) {
							NodeList musicFolders = childElement.getChildNodes();
							List<String> musicPaths = new ArrayList<>();
							for (int m = 0; m <= musicFolders.getLength(); m++) {
								Node musicFolderElement = musicFolders.item(m);
								if (musicFolderElement != null && musicFolderElement.getNodeType() == Node.ELEMENT_NODE
										&& musicFolderElement.getNodeName().equals("path")) {
									musicPaths.add(musicFolderElement.getTextContent());
								}
							}
							setTargetDirectory(TraktorDirectories.MUSIC, musicPaths);
						}
					}
				}

			}
		}

		System.out.println("---Originals---");
		System.out.println("File paths:");
		for (TraktorFileType type : filePaths.keySet()) {
			System.out.println("- " + type.getFileFilter().getDescription() + ": " + filePaths.get(type));
		}
		System.out.println("Directories:");
		for (TraktorDirectories key : originalDirectories.keySet()) {
			System.out.println("- " + key.getName() + ": " + originalDirectories.get(key));
		}
	}

	/**
	 * Updates the original paths according to the paths from the original .TSI
	 * file.
	 *
	 * @param parser
	 *            - The {@link SettingsParser} to take all information from.
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void updateOriginalSettings(SettingsParser parser)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		if (dom == null) {
			loadInternalSettings();
		}

		NodeList originalNode = tcc.getElementsByTagName("original");
		for (int i = 0; i <= originalNode.getLength() - 1; i++) {
			Node element = originalNode.item(i);
			if (element.getNodeType() == Node.ELEMENT_NODE) {
				NodeList originalChildNodes = element.getChildNodes();
				for (int a = 0; a <= originalChildNodes.getLength(); a++) {
					Node childElement = originalChildNodes.item(a);
					if (childElement != null && childElement.getNodeType() == Node.ELEMENT_NODE) {
						if (childElement.getNodeName().equals("path")) {
							if (((Element) childElement).getAttribute("name").equals("TSI")) {
								editElement(childElement, filePaths.get(TraktorFileType.SETTINGS));
								String collectionPath = filePaths.get(TraktorFileType.SETTINGS).substring(0,
										filePaths.get(TraktorFileType.SETTINGS).length() - 20) + "collection.nml";
								filePaths.put(TraktorFileType.COLLECTION, collectionPath);
							} else if (((Element) childElement).getAttribute("name").equals("NML")) {
								editElement(childElement, filePaths.get(TraktorFileType.COLLECTION));
							} else if (((Element) childElement).getAttribute("name").equals("root")) {
								editElement(childElement, parser.getRoot());
								List<String> value = new ArrayList<>();
								value.add(parser.getRoot());
								originalDirectories.put(TraktorDirectories.ROOT, value);
							} else if (((Element) childElement).getAttribute("name").equals("recordings")) {
								editElement(childElement, parser.getRecordings());
								List<String> value = new ArrayList<>();
								value.add(parser.getRecordings());
								originalDirectories.put(TraktorDirectories.RECORDINGS, value);
							} else if (((Element) childElement).getAttribute("name").equals("remixsets")) {
								editElement(childElement, parser.getContentImportRoot());
								List<String> value = new ArrayList<>();
								value.add(parser.getContentImportRoot());
								originalDirectories.put(TraktorDirectories.REMIXSETS, value);
							} else if (((Element) childElement).getAttribute("name").equals("itunes")) {
								editElement(childElement, parser.getITunesMusic());
								List<String> value = new ArrayList<>();
								value.add(parser.getITunesMusic());
								originalDirectories.put(TraktorDirectories.ITUNES, value);
							} else if (((Element) childElement).getAttribute("name").equals("loops")) {
								editElement(childElement, parser.getLoops());
								List<String> value = new ArrayList<>();
								value.add(parser.getLoops());
								originalDirectories.put(TraktorDirectories.LOOPS, value);
							}
						} else if (childElement.getNodeName().equals("collection")
								&& ((Element) childElement).getAttribute("name").equals("music")) {
							NodeList musicFolders = childElement.getChildNodes();
							List<String> musicPaths = parser.getMusicFolders();
							for (int m = 0; m <= musicFolders.getLength(); m++) {
								Node musicFolderElement = musicFolders.item(m);
								// TODO check if information is lost here
								if (musicFolderElement.getNodeType() == Node.ELEMENT_NODE) {
									childElement.removeChild(musicFolderElement);
								}
							}
							for (String path : musicPaths) {
								Node newChild = dom.createElement("path");
								NodeList newChildNodes = newChild.getChildNodes();
								for (int ncn = 0; ncn <= newChildNodes.getLength(); ncn++) {
									Node childNode = newChildNodes.item(ncn);
									if (childNode != null && childNode.getNodeType() == Node.TEXT_NODE) {
										childNode.setNodeValue(path);
									}
								}
								childElement.appendChild(newChild);
							}
							setOriginalDirectory(TraktorDirectories.MUSIC, musicPaths);
						}
					}
				}

			}
		}

		writeFile();

	}

	private static void editElement(Node childElement, String content) {
		NodeList childsNodes = childElement.getChildNodes();
		for (int child = 0; child <= childsNodes.getLength(); child++) {
			Node childNode = childsNodes.item(child);
			if (childNode != null && childNode.getNodeType() == Node.TEXT_NODE) {
				childNode.setNodeValue(content);
			}
		}
	}

	public static void writeSettings()
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		if (dom == null) {
			loadInternalSettings();
		}

		NodeList targetNodes = tcc.getElementsByTagName("target");
		for (int i = 0; i <= targetNodes.getLength(); i++) {
			Node targetChild = targetNodes.item(i);
			if (targetChild != null && targetChild.getNodeType() == Node.ELEMENT_NODE) {
				NodeList targetPaths = targetChild.getChildNodes();
				for (int p = 0; p <= targetPaths.getLength(); p++) {
					Node targetPathElement = targetPaths.item(p);
					if (targetPathElement != null && targetPathElement.getNodeType() == Node.ELEMENT_NODE) {
						if (targetPathElement.getNodeName().equals("path")) {
							if (((Element) targetPathElement).getAttribute("name").equals("TSI")) {

							} else if (((Element) targetPathElement).getAttribute("name").equals("NML")) {

							} else if (((Element) targetPathElement).getAttribute("name").equals("root")
									&& targetDirectories.containsKey(TraktorDirectories.ROOT)) {
								NodeList targetPathElementChilds = targetPathElement.getChildNodes();
								if (targetPathElementChilds.getLength() > 0) {
									for (int tpec = 0; tpec <= targetPathElementChilds.getLength(); tpec++) {
										Node node = targetPathElementChilds.item(tpec);
										if (node != null && node.getNodeType() == Node.TEXT_NODE) {
											node.setNodeValue(targetDirectories.get(TraktorDirectories.ROOT).get(0));
										}
									}
								} else {
									targetPathElement.appendChild(
											dom.createTextNode(targetDirectories.get(TraktorDirectories.ROOT).get(0)));
								}
							}
						} else if (targetPathElement.getNodeName().equals("collection")) {

						}
					}
				}
			}
		}

		writeFile();

		System.out.println("Settings stored.");

	}

	private static void writeFile() throws TransformerException {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dom);
		StreamResult result = new StreamResult(new File(file));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);
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

	public static List<String> getOriginalDirecory(TraktorDirectories type) {
		List<String> path = originalDirectories.get(type);
		if (path != null) {
			return path;
		} else {
			return new ArrayList<String>();
		}
	}

	public static void setOriginalDirectory(TraktorDirectories type, List<String> path) {
		originalDirectories.put(type, path);
	}

	public static List<String> getTargetDirecory(TraktorDirectories type) {
		List<String> path = targetDirectories.get(type);
		if (path != null) {
			return path;
		} else {
			return new ArrayList<String>();
		}
	}

	public static void setTargetDirectory(TraktorDirectories type, List<String> path) {
		targetDirectories.put(type, path);
	}

}
