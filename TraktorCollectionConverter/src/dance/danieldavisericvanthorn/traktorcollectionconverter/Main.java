package dance.danieldavisericvanthorn.traktorcollectionconverter;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionconverter.settings.SettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.mainpage.MainFrame;

public class Main {

	public static void main(String[] args) {
		try {
			SettingsManager.loadInternalSettings();
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
