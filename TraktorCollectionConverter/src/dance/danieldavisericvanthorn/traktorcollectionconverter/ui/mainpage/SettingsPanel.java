package dance.danieldavisericvanthorn.traktorcollectionconverter.ui.mainpage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionconverter.converter.SettingsParser;
import dance.danieldavisericvanthorn.traktorcollectionconverter.enums.ErrorCase;
import dance.danieldavisericvanthorn.traktorcollectionconverter.enums.TraktorDirectories;
import dance.danieldavisericvanthorn.traktorcollectionconverter.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionconverter.interfaces.Redrawer;
import dance.danieldavisericvanthorn.traktorcollectionconverter.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.filechooser.TraktorFileChooserFrame;
import dance.danieldavisericvanthorn.traktorcollectionconverter.ui.utils.GridBagLayoutUtils;

public class SettingsPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5746874836269587411L;

	private GridBagLayout gbl = new GridBagLayout();
	private JTextField pathSettingsTSI = null;
	private JButton action;
	private JButton settingsTSIButton;
	private SettingsParser settingsParser;
	private Redrawer mainframe;
	private JLabel pathsLabel;
	private JTextField rootPathField;
	private JButton rootPathButton;

	public SettingsPanel(Redrawer mainframe) {
		this.mainframe = mainframe;

		setLayout(gbl);

		pathSettingsTSI = new JTextField(300);
		settingsTSIButton = new JButton("...");
		createChooseFileCombi("Choose your original settings file:", pathSettingsTSI, settingsTSIButton, 1,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.SETTINGS,
								pathSettingsTSI.getText());
						if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
							String path = fileChooser.getSelectedFile().getAbsolutePath();
							pathSettingsTSI.setText(path);
							settingsParser = new SettingsParser(pathSettingsTSI.getText());
							try {
								InternalSettingsManager.updateOriginalSettings(settingsParser);
							} catch (ParserConfigurationException | SAXException | IOException
									| TransformerException e1) {
								createErrorMessage(ErrorCase.FILE_UPDATE_NOT_POSSIBLE);
							}
							redrawPanel();
						}

					}
				});
		pathSettingsTSI.setText(InternalSettingsManager.getTraktorPath(TraktorFileType.SETTINGS));

		rootPathField = new JTextField(300);
		rootPathButton = new JButton("...");
		createChooseFileCombi("Choose new Root path:", rootPathField, rootPathButton, 4, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.FOLDER,
						rootPathField.getText());
				if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					rootPathField.setText(path);
					List<String> rootPath = new ArrayList<>();
					rootPath.add(path);
					InternalSettingsManager.setTargetDirectory(TraktorDirectories.ROOT, rootPath);
					redrawPanel();
				}
			}
		});
		rootPathField.setEnabled(false);
		rootPathButton.setEnabled(false);

	}

	private void createChooseFileCombi(String label, JTextField text, JButton button, int line,
			ActionListener actionListener) {
		JLabel chooseSettingsTSI = new JLabel(label);
		GridBagLayoutUtils.addComponent(this, gbl, chooseSettingsTSI, null, 0, line, 1, 1, 1, 1,
				GridBagConstraints.VERTICAL, GridBagConstraints.WEST);

		text.setEnabled(true);
		text.setMinimumSize(new Dimension(300, 30));
		text.setMaximumSize(new Dimension(600, 50));
		GridBagLayoutUtils.addComponent(this, gbl, text, null, 1, line, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.WEST);

		button.addActionListener(actionListener);
		GridBagLayoutUtils.addComponent(this, gbl, button, null, 3, line, 1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
				GridBagConstraints.EAST);
	}

	private void redrawPanel() {

		if (settingsParser != null) {
			action = null;
			rootPathField.setEnabled(true);
			rootPathButton.setEnabled(true);
			repaint();
			mainframe.redraw();
		}

	}

	private void createErrorMessage(ErrorCase error) {
		switch (error) {
		case FILE_UPDATE_NOT_POSSIBLE:
			JOptionPane errorPane = new JOptionPane("Settings could not be updated.", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;

		}
	}

}
