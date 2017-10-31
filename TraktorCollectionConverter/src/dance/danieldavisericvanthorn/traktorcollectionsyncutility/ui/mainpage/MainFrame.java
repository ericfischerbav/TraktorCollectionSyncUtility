package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.mainpage;

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

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.interfaces.Redrawer;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;

public class MainFrame extends JFrame implements Redrawer {

	/**
	 *
	 */
	private static final long serialVersionUID = 9201761805139104694L;

	private static int width = 600;
	private static int height = 300;

	public MainFrame() {
		super("Traktor Collection Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				"<html><p style='text-align:center'>Welcome to the \"Traktor Collection Converter\". This application helps you to sync the collection between two computers.</p>"
						+ "<br><p style='text-align:center'>Please choose all directories mentioned below.</p></html>");

		content.add(textLabel, BorderLayout.NORTH);

		SettingsPanel settingsPanel = new SettingsPanel(this);

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

}
