package dance.danieldavisericvanthorn.traktorcollectionconverter.ui.mainpage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9201761805139104694L;

	public MainFrame() {
		super("Traktor Collection Converter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 300);
		Dimension d = getToolkit().getScreenSize();
		setLocation(((d.width - getSize().width) / 2), ((d.height - getSize().height) / 2));
		setLayout(new BorderLayout());

		Container content = getContentPane();

		JLabel textLabel = new JLabel(
				"<html><p style='text-align:center'>Welcome to the \"Traktor Collection Converter\". This application helps you to sync the collection between two computers.</p>"
						+ "<br><p style='text-align:center'>Please choose all directories mentioned below.</p></html>");

		content.add(textLabel, BorderLayout.NORTH);

		JPanel contentPanel = new SettingsPanel();
		content.add(contentPanel, BorderLayout.CENTER);
	}

}
