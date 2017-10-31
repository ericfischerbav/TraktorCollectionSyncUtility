package dance.danieldavisericvanthorn.traktorcollectionsyncutility;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.mainpage.MainFrame;

public class Main {

	public static void main(String[] args) {
		try {
			InternalSettingsManager.loadInternalSettings();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}
