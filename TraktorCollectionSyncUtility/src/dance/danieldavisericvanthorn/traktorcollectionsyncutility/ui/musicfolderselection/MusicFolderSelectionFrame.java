package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.musicfolderselection;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.interfaces.Redrawer;

public class MusicFolderSelectionFrame extends JFrame implements Redrawer {

	/**
	 *
	 */
	private static final long serialVersionUID = 9201761805139104694L;

	private static int width = 1100;
	private static int height = 500;

	public MusicFolderSelectionFrame(Redrawer mainframe) {
		super("Traktor Collection Converter");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(width, height);
		Dimension d = getToolkit().getScreenSize();
		setLocation(((d.width - getSize().width) / 2), ((d.height - getSize().height) / 2));
		setLayout(new BorderLayout());
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					mainframe.redraw();
					InternalSettingsManager.writeSettings();
				} catch (ParserConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SAXException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Container content = getContentPane();

		JLabel textLabel = new JLabel(
				"<html><p style='text-align:center'>Please choose all directories mentioned below.</p></html>");

		content.add(textLabel, BorderLayout.NORTH);

		MusicFolderPanel settingsPanel = new MusicFolderPanel(this, mainframe);

		JScrollPane scrolledPane = new JScrollPane(settingsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrolledPane.setBounds(0, 0, width, height);
		content.add(settingsPanel, BorderLayout.CENTER);
	}

	@Override
	public void redraw() {
		repaint();
		setSize(width + 1, height + 1);
		setSize(width, height);
	}

	@Override
	public void close() {
		try {
			InternalSettingsManager.writeSettings();
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dispose();
	}

}
