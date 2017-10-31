package dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.mainpage;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import dance.danieldavisericvanthorn.traktorcollectionsyncutility.converter.SettingsParser;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.ErrorCase;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.enums.TraktorFileType;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.interfaces.Redrawer;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.settings.InternalSettingsManager;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.filechooser.TraktorFileChooserFrame;
import dance.danieldavisericvanthorn.traktorcollectionsyncutility.ui.utils.GridBagLayoutUtils;

public class SettingsPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -5746874836269587411L;

	private GridBagLayout gbl = new GridBagLayout();
	private JTextField pathSettingsTSI = null;
	private JButton settingsTSIButton;
	private SettingsParser originalSettingsParser;
	private Redrawer mainframe;
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
							originalSettingsParser = new SettingsParser(pathSettingsTSI.getText());
							try {
								InternalSettingsManager.updateOriginalSettings(originalSettingsParser);
							} catch (ParserConfigurationException | SAXException | IOException
									| TransformerException e1) {
								createErrorMessage(ErrorCase.FILE_UPDATE_NOT_POSSIBLE);
							}
							redrawPanel();
						}

					}
				});
		pathSettingsTSI.setText(InternalSettingsManager.getOriginalTraktorPath(TraktorFileType.SETTINGS));

		rootPathField = new JTextField(300);
		rootPathButton = new JButton("...");
		createChooseFileCombi("Choose target settings file:", rootPathField, rootPathButton, 4, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TraktorFileChooserFrame fileChooser = new TraktorFileChooserFrame(TraktorFileType.SETTINGS,
						rootPathField.getText());
				if (fileChooser.showSaveDialog(SettingsPanel.this) == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					rootPathField.setText(path);
					InternalSettingsManager.setTargetTraktorPath(TraktorFileType.SETTINGS, path);
					try {
						InternalSettingsManager.loadTargetSettingsFromTSI();
					} catch (FileNotFoundException e1) {
						createErrorMessage(ErrorCase.getErrorCase(e1.getMessage()));
					}
					redrawPanel();
				}
			}
		});
		rootPathField.setText(InternalSettingsManager.getTargetTraktorPath(TraktorFileType.SETTINGS));

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

		if (originalSettingsParser != null) {
			repaint();
			mainframe.redraw();
		}

	}

	private void createErrorMessage(ErrorCase error) {
		switch (error) {
		case FILE_UPDATE_NOT_POSSIBLE:
			new JOptionPane("Settings could not be updated.", JOptionPane.ERROR_MESSAGE);
			break;
		case COLLECTION_FILE_NOT_FOUND:
			new JOptionPane(
					"Collection file could not be found at the default location. Please make shure you synced all files right.",
					JOptionPane.ERROR_MESSAGE);
			break;
		default:
			break;

		}
	}

}
