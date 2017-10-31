package dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.ErrorCase;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorDirectories;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;

/**
 * Class for managing the internal settings. It holds the settings in variables
 * and writes it to the permanent settings file.
 *
 * @author Eric Fischer aka Eric van Thorn - eric@danieldavisericvanthorn.dance
 *
 */
public class InternalSettingsManager {

	private static final String file = "settings/settings.xml";
	// needed for xml parsing
	private static Document dom;
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder db;
	private static Element tcc;

	private static Map<TraktorFileType, String> originalFilePaths = new HashMap<>();
	private static Map<TraktorFileType, String> targetFilePaths = new HashMap<>();

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
								setOriginalTraktorPath(TraktorFileType.SETTINGS, childElement.getTextContent());
							} else if (((Element) childElement).getAttribute("name").equals("NML")) {
								setOriginalTraktorPath(TraktorFileType.COLLECTION, childElement.getTextContent());
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
		for (TraktorFileType type : originalFilePaths.keySet()) {
			System.out.println("- " + type.getFileFilter().getDescription() + ": " + originalFilePaths.get(type));
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

		String collectionPath = originalFilePaths.get(TraktorFileType.SETTINGS).substring(0,
				originalFilePaths.get(TraktorFileType.SETTINGS).length() - 20) + "collection.nml";
		originalFilePaths.put(TraktorFileType.COLLECTION, collectionPath);

		List<String> value = new ArrayList<>();
		value.add(parser.getRoot());
		originalDirectories.put(TraktorDirectories.ROOT, value);
		value.remove(0);
		value.add(parser.getRecordings());
		originalDirectories.put(TraktorDirectories.RECORDINGS, value);
		value.remove(0);
		value.add(parser.getContentImportRoot());
		originalDirectories.put(TraktorDirectories.REMIXSETS, value);
		value.remove(0);
		value.add(parser.getITunesMusic());
		originalDirectories.put(TraktorDirectories.ITUNES, value);
		value.remove(0);
		value.add(parser.getLoops());
		originalDirectories.put(TraktorDirectories.LOOPS, value);

		List<String> musicPaths = parser.getMusicFolders();
		setOriginalDirectory(TraktorDirectories.MUSIC, musicPaths);

		writeSettings();

	}

	/**
	 * Updates target settings according to the selected .tsi file.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void loadTargetSettingsFromTSI() throws FileNotFoundException {
		File tsi = new File(targetFilePaths.get(TraktorFileType.SETTINGS));
		String rootFolder = tsi.getParent();
		List<String> value = new ArrayList<>();
		value.add(rootFolder);
		targetDirectories.put(TraktorDirectories.ROOT, value);
		String nmlPath = rootFolder + "collection.nml";
		File nml = new File(nmlPath);
		if (nml.exists()) {
			targetFilePaths.put(TraktorFileType.COLLECTION, nmlPath);
		} else {
			throw new FileNotFoundException(ErrorCase.COLLECTION_FILE_NOT_FOUND.getCode());
		}
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

		createBackup();

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
								editElement(childElement, originalFilePaths.get(TraktorFileType.SETTINGS));
								String collectionPath = originalFilePaths.get(TraktorFileType.SETTINGS).substring(0,
										originalFilePaths.get(TraktorFileType.SETTINGS).length() - 20)
										+ "collection.nml";
								originalFilePaths.put(TraktorFileType.COLLECTION, collectionPath);
							} else if (((Element) childElement).getAttribute("name").equals("NML")) {
								editElement(childElement, originalFilePaths.get(TraktorFileType.COLLECTION));
							} else if (((Element) childElement).getAttribute("name").equals("root")) {
								editElement(childElement, originalDirectories.get(TraktorDirectories.ROOT).get(0));
							} else if (((Element) childElement).getAttribute("name").equals("recordings")) {
								editElement(childElement,
										originalDirectories.get(TraktorDirectories.RECORDINGS).get(0));
							} else if (((Element) childElement).getAttribute("name").equals("remixsets")) {
								editElement(childElement, originalDirectories.get(TraktorDirectories.REMIXSETS).get(0));
							} else if (((Element) childElement).getAttribute("name").equals("itunes")) {
								editElement(childElement, originalDirectories.get(TraktorDirectories.ITUNES).get(0));
							} else if (((Element) childElement).getAttribute("name").equals("loops")) {
								editElement(childElement, originalDirectories.get(TraktorDirectories.LOOPS).get(0));
							}
						} else if (childElement.getNodeName().equals("collection")
								&& ((Element) childElement).getAttribute("name").equals("music")) {
							while (childElement.hasChildNodes()) {
								childElement.removeChild(childElement.getFirstChild());
							}
							for (String musicPath : getOriginalDirecory(TraktorDirectories.MUSIC)) {
								Element newChild = dom.createElement("path");
								Node textNode = dom.createTextNode(musicPath);
								newChild.appendChild(textNode);
								childElement.appendChild(newChild);
							}
						}
					}
				}

			}
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

	private static void createBackup() throws IOException, TransformerException {
		removeOldestBackup();
		Calendar date = Calendar.getInstance();
		String path = "settings/backup/settings-" + date.getTimeInMillis() + ".xml";
		File f = new File(path);
		f.getParentFile().mkdirs();
		f.createNewFile();
		writeFile(path);
	}

	private static void removeOldestBackup() {
		Set<File> fileSet = new HashSet<>();
		File folder = new File("settings/backup");
		for (File file : folder.listFiles()) {
			fileSet.add(file);
		}
		if (fileSet.size() >= 5) {
			File oldest = Collections.min(fileSet);
			oldest.delete();
			removeOldestBackup();
		}
	}

	private static void writeFile() throws TransformerException {
		writeFile(file);
	}

	private static void writeFile(String path) throws TransformerException {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dom);
		StreamResult result = new StreamResult(new File(path));

		transformer.transform(source, result);
	}

	public static String getOriginalTraktorPath(TraktorFileType type) {
		String path = originalFilePaths.get(type);
		if (path != null) {
			return path;
		} else {
			return "";
		}
	}

	public static void setOriginalTraktorPath(TraktorFileType type, String path) {
		originalFilePaths.put(type, path);
	}

	public static String getTargetTraktorPath(TraktorFileType type) {
		String path = targetFilePaths.get(type);
		if (path != null) {
			return path;
		} else {
			return "";
		}
	}

	public static void setTargetTraktorPath(TraktorFileType type, String path) {
		targetFilePaths.put(type, path);
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
